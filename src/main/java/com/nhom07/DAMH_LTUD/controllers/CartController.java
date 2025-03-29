package com.nhom07.DAMH_LTUD.controllers;


import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.*;
import com.nhom07.DAMH_LTUD.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private VnPayService vnPayServiceOK;

    @Autowired
    ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StatusOrderService statusOrderService;

    @Autowired
    private OrderDetailService orderDetailService;

    private static final List<CartItemVM> cartItems = new ArrayList<>();
    private static CheckoutVM checkoutVMStatic;

    @GetMapping
    private String showCart(Model model) {
        double totalAmount = cartItems.stream().mapToDouble(CartItemVM::getTongTien).sum();
        if (cartItems.isEmpty()) {
            model.addAttribute("notify", "không có sản phẩm nào được thêm vào giỏ hàng");
        }
        model.addAttribute("carts", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long id, @RequestParam(name = "soLuongMua") Integer soLuongMua) {
        if (soLuongMua == null || soLuongMua <= 0) {
            soLuongMua = 1;
        }
        CartItemVM checkCart = cartItems.stream()
                .filter(item -> Objects.equals(item.getMaSp(), id))
                .findFirst()
                .orElse(null);
        if (checkCart == null) {
            try {
                Product getProductById = productService.getById(id);
                if (getProductById != null) {
                    checkCart = new CartItemVM(getProductById.getId(), getProductById.getNameProduct(),
                            getProductById.getPrice(), getProductById.getImageClotheFootball(), soLuongMua);
                    cartItems.add(checkCart);
                }
            } catch (NotFoundByIdException e) {
                throw new RuntimeException(e);
            }
        } else {
            Integer updateSoLuong = checkCart.getSoLuong() + soLuongMua;
            checkCart.setSoLuong(updateSoLuong);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    private String removeCartItem(@PathVariable("id") Long id) {
        cartItems.removeIf(item -> Objects.equals(item.getMaSp(), id));
        return "redirect:/cart";
    }


    @GetMapping("/checkout")
    private String checkoutCart(@NotNull Model model) {
        float a = 5f;
        double totalAmount = cartItems.stream().mapToDouble(CartItemVM::getTongTien).sum();
        model.addAttribute("carts", cartItems);
        model.addAttribute("checkout", new CheckoutVM());
        model.addAttribute("totalAmount", totalAmount);
        return "cart/checkout";
    }

    @PostMapping("/checkout/payment")
    private String checkout(@NotNull @ModelAttribute("checkout") CheckoutVM checkoutVM, HttpServletRequest request, @RequestParam("payment") String payment) {
        checkoutVMStatic = checkoutVM;
        var oke = payment;
        double totalPrice = (cartItems.stream().mapToDouble(CartItemVM::getTongTien).sum()) * 1000 + 30000;
        OrderInfo vnPay = new OrderInfo();
        vnPay.setTotalPrice(totalPrice);
        vnPay.setCreatedDate(LocalDateTime.now()); // Use appropriate date handling in Java
        vnPay.setUserOrder(checkoutVM.getFirstName() + " " + checkoutVM.getLastName());
        vnPay.setOrderId((long) (new Random().nextInt(9000) + 1000));

        return "redirect:" + vnPayServiceOK.initialPayment(request, vnPay);

    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @GetMapping("/paymentCallBack")
    private String handlePaymentCallback(@RequestParam Map<String, String> parameterMap, RedirectAttributes redirectAttributes) {
        // 00 :giao dich thanh cong
        VnpayResponse vnpayResponse = vnPayServiceOK.executePayment(parameterMap);
        if (vnpayResponse == null || !Objects.equals(vnpayResponse.getVnPayResponseCode(), "00")) {
            redirectAttributes.addFlashAttribute("message", "Lỗi thanh toán VN Pay");
            return "redirect:/cart/payment/fail";
        }
        try {
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            List<CartItemVM> danhSachSanPham;
            double totalPrice = cartItems.stream().mapToDouble(CartItemVM::getTongTien).sum();
            double amountProduct = cartItems.stream().mapToDouble(CartItemVM::getSoLuong).sum();

            LocalDateTime ngaydat = LocalDateTime.now();
            LocalDateTime ngay_giao_du_kien = ngaydat.plusDays(3);

            KhachHang khachHangNew = new KhachHang();
            if (userLogin != null) {
                khachHangNew.setNameAccountLogin(userLogin);
                khachHangNew.setEmail(checkoutVMStatic.email);
                khachHangNew.setDienThoai(checkoutVMStatic.phone);
                khachHangNew.setDiaChi(checkoutVMStatic.address);
                khachHangNew.setHoTen(checkoutVMStatic.getFirstName() + " " + checkoutVMStatic.getLastName());

                khachHangService.save(khachHangNew);

                Order order = new Order();
                order.setDiaChi(checkoutVMStatic.address);
                order.setHoTen(checkoutVMStatic.getFirstName() + " " + checkoutVMStatic.getLastName());
                order.setGhiChu(checkoutVMStatic.getNote());
                order.setNgayDat(toDate(ngaydat));
                order.setNgayGiaoDuKien(toDate(ngay_giao_du_kien));
                order.setPhiVanChuyen(30);
                order.setPhuongThucThanhToan("Thanh Toán Bằng vnPay");
                order.setStatusorder(statusOrderService.getStatusOrderById(1L));
                order.setKhachhang(khachHangNew);

                orderService.save(order);

                for (CartItemVM item : cartItems) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setTen_san_pham(item.getTenSp());
                    orderDetail.setTong_tien(item.getTongTien());
                    orderDetail.setSo_luong(item.getSoLuong());
                    orderDetail.setProduct(productService.getById(item.getMaSp()));

                    orderDetailService.save(orderDetail);
                }
                cartItems.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NotFoundByIdException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/cart/payment/success";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "/payment/paymentsuccess"; // Trả về trang thanh toán thành công
    }

    @GetMapping("/payment/fail")
    public String paymentFail(Model model) {
        String message = (String) model.getAttribute("message");
        model.addAttribute("message", message);
        return "/payment/paymentfail"; // Trả về trang thanh toán thất bại
    }


    @GetMapping("/history/order")
    private String HistoryOrderProduct(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderDetail> orders = orderDetailService.getListOrderDetail().stream().filter(
                orderDetail -> orderDetail.getOrder() != null &&
                        orderDetail.getOrder().getKhachhang() != null &&
                        orderDetail.getOrder().getKhachhang().getNameAccountLogin().trim().equals(userLogin.trim())
        ).collect(Collectors.toList());
        model.addAttribute("orders", orders);

        for (OrderDetail item : orders) {
            item.getProduct().getImageClotheFootball();
            item.getId();
            item.getOrder().getPhiVanChuyen();
            item.getTen_san_pham();
            item.getProduct().getImageClotheFootball();
            item.getSo_luong();
            item.getTen_san_pham();
        }

        return "cart/history_order";
    }
}

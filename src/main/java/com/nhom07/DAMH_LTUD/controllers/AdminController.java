package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.model.Order;
import com.nhom07.DAMH_LTUD.model.OrderDetail;
import com.nhom07.DAMH_LTUD.service.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@NoArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StatusOrderService statusOrderService;

    @Autowired
    private ContactUserService contactUserService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @GetMapping()
    public String admin(Model model) {
        int sanphamBanDuoc = 0;
        double tongtien = 0;
        List<Order> orders = orderService.getListOrder();
        for (Order order : orders)
        {
            for (OrderDetail orderDetail : order.getOrderDetailList())
            {
                sanphamBanDuoc += orderDetail.getSo_luong();
                tongtien += orderDetail.getTong_tien();
            }
        }
        model.addAttribute("soluong",sanphamBanDuoc);
        model.addAttribute("tongtien",tongtien);
        model.addAttribute("sanpham",productService.getAllProducts().size());
        return "admin/page-index-1";
    }

    @GetMapping("/admin/page-products-list")
    public String product() {
        return "admin/page-products-list";
    }

    @GetMapping("/contact_list")
    public String contact_list() {
        return "admin/contact_list";
    }

}
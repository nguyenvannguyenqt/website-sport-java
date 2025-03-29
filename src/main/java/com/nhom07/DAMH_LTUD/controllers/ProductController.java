package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.*;
import com.nhom07.DAMH_LTUD.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private TeamClubService teamClubService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StatusOrderService statusOrderService;

    @Autowired
    private ContactUserService contactUserService;

    @GetMapping
    public String showListProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/products-list";
    }

    @GetMapping("/add/country")
    public String showAddProductFormCountry(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("countries", countryService.getAllCountry());
        model.addAttribute("teams", null);
        return "/products/add-product";
    }
    @GetMapping("/add/teamclb")
    public String showAddProductFormTeamClub(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("countries",null );
        model.addAttribute("teams", teamClubService.getAllTeamClubs());
        return "/products/add-product";
    }

    @PostMapping("/add")
    public String addProductCountry(@Valid @ModelAttribute("product") Product product,
                                    BindingResult bindingResult , @RequestParam("image") MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            return "/products/add-product";
        }
        if (!imageFile.isEmpty()) {
            try {
                String imageName = saveImageStatic(imageFile);
                product.setImageClotheFootball(imageName); // Lưu tên ảnh vào cơ sở dữ liệu
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }
        if (product.getCountry() != null && product.getCountry().getId() != null) {
            Country country = null;
            try {
                country = countryService.getByIdCountry(product.getCountry().getId());
            } catch (NotFoundByIdException e) {
                throw new RuntimeException(e);
            }
            product.setCountry(country);
        }
        if (product.getTeamClub() != null && product.getTeamClub().getId() != null) {
            TeamClub teamClub = null;
            try {
                teamClub = teamClubService.getById(product.getTeamClub().getId());
            } catch (NotFoundByIdException e) {
                throw new RuntimeException(e);
            }
            product.setTeamClub(teamClub);
        }

        productService.addProduct(product);
        return "redirect:/products";
    }
    private String saveImageStatic(MultipartFile image) throws IOException {
        // Đường dẫn tới thư mục lưu trữ hình ảnh trong thư mục static/images

        Path dirImages = Paths.get("target/classes/static/img");
        // Tạo thư mục nếu chưa tồn tại
        if (!Files.exists(dirImages)) {
            Files.createDirectories(dirImages);
        }

        // Tạo tên tệp duy nhất cho hình ảnh
        String imageName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());

        // Lưu hình ảnh vào thư mục lưu trữ

        Path pathFileUpload = dirImages.resolve(imageName);
        Files.copy(image.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);

        return imageName;
    }
    // Phương thức để xóa ảnh cũ
    private void deleteOldImage(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            Path dirImages = Paths.get("target/classes/static/images");
            Path imagePath = dirImages.resolve(imageName); // Sử dụng resolve thay vì Paths.get
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        try {

            Product product = productService.getById(id);
            if(product.getCountry() != null)
            {
                model.addAttribute("countries", countryService.getAllCountry());
                model.addAttribute("teams", null);
            }
            else if(product.getTeamClub() != null)
            {
                model.addAttribute("countries", null);
                List<TeamClub> gets = teamClubService.getAllTeamClubs();
                model.addAttribute("teams", teamClubService.getAllTeamClubs());
            }
            model.addAttribute("product", product);
            model.addAttribute("type", "edit");
            return "/products/edit-product";
        } catch (NotFoundByIdException e) {
            return "redirect:/products";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute("product") Product product, BindingResult result,
                                @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            product.setId(id);
            return "/products/edit-product";
        }
        if (!imageFile.isEmpty()) {
            deleteOldImage(product.getImageClotheFootball());
            try {
                String imageName = saveImageStatic(imageFile);
                product.setImageClotheFootball(imageName); // Lưu tên ảnh mới vào cơ sở dữ liệu
            } catch (IOException e) {
                e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
            }
        }
        else {
            Product existingProduct = null;
            try {
                existingProduct = productService.getById(id);
            } catch (NotFoundByIdException e) {
                throw new RuntimeException(e);
            }
            product.setImageClotheFootball(existingProduct.getImageClotheFootball());
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/products";
        } catch (IllegalStateException e) {
            return "redirect:/products";
        }
    }
    @GetMapping("/product/search")
    public String filterSearchNameProduct(@RequestParam(name = "nameProduct") String nameProduct,Model model) {
        if (nameProduct == null || nameProduct.isEmpty()) {
            model.addAttribute("products", productService.getAllProducts());
            return "/products/products-list";
        } else {
            List<Product> searchResults = productService.getListFindByNameProduct(nameProduct);
            model.addAttribute("products", searchResults);
            return "/products/products-list";
        }
    }
    @GetMapping("/history/order/user")
    private String HistoryOrderProduct(Model model)
    {
        List<Order> orders = orderService.getListOrder();
        //orders.get(0).getPhuongThucThanhToan()
        /*orders.get(0).g*/

        model.addAttribute("orders",orders);
        return "/admin/history_order_user";
    }
    @GetMapping("/user")
    private String HistoryCustomer(Model model)
    {
        List<KhachHang> khachHangs = khachHangService.getAllKhachHang();
        model.addAttribute("customers",khachHangs);
        return "/admin/manager_user";
    }
    @GetMapping("/contact")
    private String HistoryContact(Model model)
    {
        List<ContactUser> contactUsers = contactUserService.getListContacts();
        model.addAttribute("contacts",contactUsers);
        return "/admin/admin_contact";
    }
}
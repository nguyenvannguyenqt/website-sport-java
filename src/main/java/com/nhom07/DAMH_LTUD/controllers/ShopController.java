package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Comment;
import com.nhom07.DAMH_LTUD.model.Product;
import com.nhom07.DAMH_LTUD.service.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    @Autowired
    private ProductService productService;

    @Autowired
    private TournamentCategoryService tournamentCategoryService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SizeProductService sizeProductService;

    @Autowired
    private LeagueClubService leagueClubService;


    @Autowired
    private TeamClubService teamClubService;


    @Autowired
    private CommentService commentService;

    @GetMapping
    public String shopProduct(@NotNull Model model,
                              @RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "9") Integer pageSize,
                              @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", (int) Math.ceil((double) productService.getAllProducts().size() / pageSize));

        model.addAttribute("categories", tournamentCategoryService.getAllTournamentCategory());
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("leagues", leagueClubService.getAllLeagueClubs());
        model.addAttribute("teamclubs", teamClubService.getAllTeamClubs());
        model.addAttribute("products", productService.getListProducts(pageNo, pageSize, sortBy));
        return "/sport/shop"; // Tên tệp HTML bạn muốn trả về (không cần đuôi .html)
    }
    @GetMapping("/filter/color")
    public String filterColorSport(@RequestParam("loai") int categoryId, Model model,
                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "9") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy)
    {
        if(categoryId == 1)
        {
            int totalProducts = productService.getAllProducts().size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", productService.getListProductFilterColor(categoryId,pageNo,pageSize,sortBy));
            return "sport/shop";
        }
        else
        {
            List<Product> searchResults = productService.getListProductFilterColor(categoryId,pageNo,pageSize,sortBy);
            int totalSearchResults = searchResults.size();
            int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("typefilter","Color");
            model.addAttribute("products", searchResults.subList(Math.min(pageNo * pageSize, totalSearchResults), Math.min((pageNo + 1) * pageSize, totalSearchResults)));
            model.addAttribute("loai", categoryId); // add colorId for pagination
            return "sport/shop_filter_color";
        }
    }
    @GetMapping("/filter/price")
    public String filterPriceSport(@RequestParam("loai") int categoryId, Model model,
                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "9") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy)
    {
         if(productService == null)
        {
            System.out.println("productService null");
        }
        if(categoryId == 1)
        {
            int totalProducts = productService.getAllProducts().size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", productService.getListProductFilterPrice(categoryId,pageNo,pageSize,sortBy));
            return "sport/shop";
        }
        else
        {
            List<Product> searchResults = productService.getListProductFilterPrice(categoryId,pageNo,pageSize,sortBy);
            int totalSearchResults = searchResults.size();
            int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", searchResults.subList(Math.min(pageNo * pageSize, totalSearchResults), Math.min((pageNo + 1) * pageSize, totalSearchResults)));
            model.addAttribute("loai", categoryId); // add colorId for pagination
            model.addAttribute("typefilter","Price");
            return "sport/shop_filter_color";
        }
    }
    @GetMapping("/search")
    public String filterSearchNameProduct(@RequestParam(name = "nameProduct") String nameProduct, Model model,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "9") Integer pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        if (nameProduct == null || nameProduct.isEmpty()) {
            int totalProducts = productService.getAllProducts().size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", productService.getListProducts(pageNo, pageSize, sortBy));
            return "sport/shop";
        } else {
            List<Product> searchResults = productService.getListFindByNameProduct(nameProduct);
            int totalSearchResults = searchResults.size();
            int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", searchResults.subList(Math.min(pageNo * pageSize, totalSearchResults), Math.min((pageNo + 1) * pageSize, totalSearchResults)));
            model.addAttribute("nameProduct", nameProduct); // add nameProduct for pagination
            return "sport/shop_search";
        }
    }
    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Long id, Model model)
    {
        try {
            Product productDetail = productService.getById(id);
            model.addAttribute("product",productDetail);
            model.addAttribute("sizes",sizeProductService.getListAllSize());
            model.addAttribute("products",productService.getAllProducts());
            model.addAttribute("comment",new Comment());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                model.addAttribute("checkcomment","yes");
            }
            else
            {
                model.addAttribute("checkcomment","no");
            }
            return "sport/detail";
        } catch (NotFoundByIdException e) {
            return "redirect:/shop";
        }
    }

    /*@GetMapping("/checkrating")
    private String checkRating(@NotNull Model model,@PathVariable("id") Long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("checkcomment","yes");
        }
        else
        {
            model.addAttribute("checkcomment","no");
        }
        return "redirect:shop/detail/"+id;
    }*/

    @PostMapping("/rating/{id}")
    public String ratingProduct(@PathVariable("id") Long id,@ModelAttribute("comment") Comment comment, BindingResult bindingResult)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(bindingResult.hasErrors())
        {
            return "redirect:/shop/detail/" + id;
        }
        Comment newComment = new Comment();
        newComment.setNoiDung(comment.getNoiDung());
        newComment.setLuotSaoDanhGia(comment.getLuotSaoDanhGia());
        newComment.setEmail(username);
        newComment.setNoiDung(comment.getNoiDung());
        newComment.setNgayCmt(LocalDateTime.now());
        try {
            Product productForeign = productService.getById(id);
            newComment.setProduct(productForeign);
            commentService.saveComment(newComment);
            return "redirect:/shop/detail/" + id;
        } catch (NotFoundByIdException e) {
            return "redirect:/shop";
        }
    }
    @GetMapping("/filter/country/teamclub/{id}")
    public String getCountryOrTeamClubById(@NotNull @PathVariable("id") Long id, @RequestParam("name") String name,
                                           Model model,
                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "9") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        String getName = name;
        List<Product> searchResults = productService.getListFilterCountryOrTeamClub(id,getName,pageNo,pageSize,sortBy);
        int totalSearchResults = searchResults.size();
        int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("products", searchResults.subList(Math.min(pageNo * pageSize, totalSearchResults), Math.min((pageNo + 1) * pageSize, totalSearchResults)));
        model.addAttribute("id", id); // add colorId for pagination
        model.addAttribute("name", name); // add colorId for pagination
        model.addAttribute("typefilter","country_teamclub");
        return "sport/shop_filter_color";
    }

}

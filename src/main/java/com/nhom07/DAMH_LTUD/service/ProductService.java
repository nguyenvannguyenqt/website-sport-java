package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Comment;
import com.nhom07.DAMH_LTUD.model.Product;
import com.nhom07.DAMH_LTUD.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getAllProductsDesc() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .collect(Collectors.toList());
    }
    public List<Product> getListProducts(Integer pageNo,Integer pageSize, String sortBy) {
        return productRepository.findAllProducts(pageNo,pageSize,sortBy);
    }


    public Product getById(Long id) throws NotFoundByIdException {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public List<Product> getListProductFilterColor(int colorId,Integer pageNo,Integer pageSize, String sortBy)
    {
        List<Product> filter = new ArrayList<>();
        if(colorId == 1)
        {
            return getListProducts(pageNo,pageSize,sortBy);
        }
        else if(colorId == 2)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("đen"))
                    .collect(Collectors.toList());
        }
        else if(colorId == 3)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("trắng"))
                    .collect(Collectors.toList());
        }
        else if(colorId == 4)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("đỏ"))
                    .collect(Collectors.toList());
        }
        else if(colorId == 5)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("xanh"))
                    .collect(Collectors.toList());
        }
        else if(colorId == 6)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("vàng"))
                    .collect(Collectors.toList());
        }
        else if(colorId == 7)
        {
            filter = getAllProducts().stream()
                    .filter(nameProduct -> nameProduct.getNameProduct().toLowerCase().trim().contains("tím"))
                    .collect(Collectors.toList());
        }

        return filter;
    }
    public List<Product> getListProductFilterPrice(int colorId,Integer pageNo,Integer pageSize, String sortBy)
    {
        List<Product> filter = new ArrayList<>();
        if(colorId == 1)
        {
            return getListProducts(pageNo,pageSize,sortBy);
        }
        else if(colorId == 2)
        {
            filter = getAllProducts().stream()
                    .filter(priceProduct -> priceProduct.getPrice() >= 100.0 && priceProduct.getPrice() <= 120.0)
                    .collect(Collectors.toList());
        }
        else if(colorId == 3)
        {
            filter = getAllProducts().stream()
                    .filter(priceProduct -> priceProduct.getPrice() >= 150.0 && priceProduct.getPrice() <= 200.0)
                    .collect(Collectors.toList());
        }
        else if(colorId == 4)
        {
            filter = getAllProducts().stream()
                    .filter(priceProduct -> priceProduct.getPrice() >= 205.0 && priceProduct.getPrice() <= 400.0)
                    .collect(Collectors.toList());
        }
        else if(colorId == 5)
        {
            filter = getAllProducts().stream()
                    .filter(priceProduct -> priceProduct.getPrice() >= 450.0 && priceProduct.getPrice() <= 700.0)
                    .collect(Collectors.toList());
        }
        return filter;
    }

    public List<Product> getListFilterCountryOrTeamClub(Long countryOrClubId, String name, Integer pageNo, Integer pageSize, String sortBy) {
        List<Product> filter = new ArrayList<>();
        if ("clb".equals(name)) {
            filter = getAllProducts().stream()
                    .filter(idCountryOrClub -> idCountryOrClub.getTeamClub() != null)
                    .filter(idCountryOrClub -> Objects.equals(idCountryOrClub.getTeamClub().getId(), countryOrClubId))
                    .collect(Collectors.toList());
        }
        else if("country".equals(name))
        {
            filter = getAllProducts().stream()
                    .filter(idCountryOrClub -> idCountryOrClub.getCountry() != null)
                    .filter(idCountryOrClub -> Objects.equals(idCountryOrClub.getCountry().getId(), countryOrClubId))
                    .collect(Collectors.toList());
        }
        return filter;
    }


    public void updateProduct(@NotNull Product product) {
        Product productExists = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with id: " + product.getId() + " does not exist."));
        productExists.setNameProduct(product.getNameProduct());
        productExists.setPrice(product.getPrice());
        productExists.setDescription(product.getDescription());
        productExists.setQuantity(product.getQuantity());
        productExists.setImageClotheFootball(product.getImageClotheFootball());
        if(product.getTeamClub() == null)
        {
            productExists.setTeamClub(null);
            productExists.setCountry(product.getCountry());
        }
        else if(product.getCountry() == null)
        {
            productExists.setTeamClub(null);
            productExists.setCountry(product.getCountry());
        }
        productRepository.save(productExists);
    }


    public List<Product> getListFindByNameProduct(String nameProduct)
    {
        return getAllProducts().stream()
                .filter(name -> name.getNameProduct().toLowerCase().trim().contains(nameProduct.toLowerCase().trim()))
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        productRepository.deleteById(id);
    }
}
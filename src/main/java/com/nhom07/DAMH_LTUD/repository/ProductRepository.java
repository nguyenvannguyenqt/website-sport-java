package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Book;
import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long>, JpaRepository<Product,Long> {
    default List<Product> findAllProducts(Integer pageNo,Integer pageSize,String sortBy)
    {
        return findAll(PageRequest.of(pageNo,pageSize, Sort.by(sortBy))).getContent();
    }
}

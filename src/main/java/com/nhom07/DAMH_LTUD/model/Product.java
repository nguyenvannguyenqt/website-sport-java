package com.nhom07.DAMH_LTUD.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 10, message = "Tên sản phẩm phải từ 20 ký tự")
    @Column(name = "name_product")
    private String nameProduct;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    @Size(min = 10, max = 1000, message = "Mô tả sản phẩm phải từ 10 đến 255 ký tự")
    @Column(name = "description")
    private String description;

    @Size(max = 255, message = "Đường dẫn ảnh sản phẩm không quá 255 ký tự")
    @Column(name = "image_clothe_football")
    private String imageClotheFootball;

    @Positive(message = "Giá sản phẩm phải là số dương")
    @Column(name = "price")
    private double price;

    @Min(value = 0, message = "Số lượng sản phẩm không được nhỏ hơn 0")
    @Column(name = "quantity")
    private int quantity;

    @Min(value = 0, message = "Số lượng mua sản phẩm không được nhỏ hơn 0")
    @Column(name = "quantity_buy")
    private int quantityBuy;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "team_club_id", nullable = true)
    private TeamClub teamClub;


    @OneToMany(mappedBy = "product")
    private List<Comment> comments;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetailList;
}

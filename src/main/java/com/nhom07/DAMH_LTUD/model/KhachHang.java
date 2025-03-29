package com.nhom07.DAMH_LTUD.model;


import jakarta.persistence.*;
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
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameAccountLogin;

    private String hoTen;

    private String diaChi;

    private String dienThoai = "";

    private String Email;


    @OneToMany(mappedBy = "khachhang")
    private List<Order> orders;
}

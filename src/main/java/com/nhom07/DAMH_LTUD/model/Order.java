package com.nhom07.DAMH_LTUD.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date ngayDat;

    private Date ngayGiaoDuKien;

    private String hoTen;

    private String phuongThucThanhToan;

    private String diaChi;

    private String ghiChu;

    private double phiVanChuyen;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachhang;


    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrder statusorder;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetailList;
}

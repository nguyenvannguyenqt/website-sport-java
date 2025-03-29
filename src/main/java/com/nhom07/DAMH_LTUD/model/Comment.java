package com.nhom07.DAMH_LTUD.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_comment")
    private Long maComment;

    @NotBlank(message = "Bắt buộc(*)")
    @Size(max = 250, message = "Nội dung comment không vượt quá 250 kí tự")
    @Column(name = "noi_dung")
    private String noiDung;

    @NotBlank(message = "Bắt buộc(*)")
    @Size(max = 50, message = "Email không vượt quá 50 kí tự")
    //@Email(message = "Email không hợp lệ")
    @Column(name = "email")
    private String email;

    @Column(name = "ngay_cmt")
    private LocalDateTime ngayCmt;

    @Column(name = "luot_sao_danh_gia")
    private Integer luotSaoDanhGia;

    @ManyToOne
    @JoinColumn(name = "ma_san_pham")
    private Product product;
}

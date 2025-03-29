package com.nhom07.DAMH_LTUD.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class ContactUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bắt buộc(*)")
    @Size(max = 100, message = "Họ Tên không vượt quá 100 kí tự")
    private String name_user;

    @NotBlank(message = "Bắt buộc(*)")
    private String content;

    @NotBlank(message = "Bắt buộc(*)")
    @Size(max = 50, message = "Email không vượt quá 50 kí tự")
    @Email(message = "Email không hợp lệ")
    @Column(name = "email")
    private String email;

    private LocalDateTime date_send;


    @Size(max = 250, message = "Địa chỉ không vượt quá 250 kí tự")
    private String address;


    @NotBlank(message = "Bắt buộc(*)")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải chứa kí tự số")
    @Size(min = 10,max = 100, message = "Số điện thoại phải là 10 kí tự")
    private String phone;
}

package com.example.finalproject.domain.user.entity;


import com.example.finalproject.domain.user.dto.Register;
import java.time.LocalDate;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor()
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(length = 50)
    private String email;

    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String pw;

    @Column(length = 50)
    private String role;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String phone;

    @Column(length = 50)
    private String profile;

    @Column(length = 50)
    private Integer loginType;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "sign_up_source", length = 50)
    private String signupSource;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(length = 50)
    private String address;

    @Column(name = "address_detail", length = 50)
    private String addressDetail;

    private Integer point;

    private Integer status;

    private Integer cancel;

    private Integer penalty;

    private Integer type;

    public void userEntity(String id, Register register) {
        this.email = register.getEmail();
        this.id = id;
        this.pw = register.getPw();
        this.role = register.getRole();
        this.name = register.getName();
        this.phone = register.getPhone();
        this.createDate = LocalDate.now();
        this.loginType = register.getLoginType();
        this.type = register.getType();

        if (register.getProfile() != null) {
            this.profile = Arrays.toString(register.getProfile());
        } else {
            this.profile = null;
        }
        if (register.getPostalCode() != null) {
            this.postalCode = Integer.valueOf(register.getPostalCode());
        } else {
            this.postalCode = null;
        }
        if (register.getAddress() != null) {
            this.address = register.getAddress();
        } else {
            this.address = null;
        }
        if (register.getAddressDetail() != null) {
            this.addressDetail = register.getAddressDetail();
        } else {
            this.addressDetail = null;
        }
    }
}

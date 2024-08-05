package com.example.finalproject.domain.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(name = "order_id")
    private Integer orderId;

    private Integer amount;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "payment_status", length = 50)
    private String paymentStatus;

    @Column(length = 3)
    private String currency;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "card_type", length = 30)
    private String cardType;

    @Column(name = "payment_gateway", length = 50)
    private String paymentGateway;

    @Column(name = "receipt_url", length = 255)
    private String receiptUrl;

    @Column(name = "refund_status", length = 50)
    private String refundStatus;
}

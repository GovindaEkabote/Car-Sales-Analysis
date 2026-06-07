package com.sales.analytics.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number" , nullable = false)
    private String carNumber;

    private String brand;
    private String model;
    private String color;
    private int year;

    @Column(name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;

    @Column(name = "time_of_purchase")
    private LocalDateTime timeOfPurchase;

    private long price;
    private double mileage;
    private int engine;
    private String fuletype;
    @Column(name = "payment_mode")
    private String paymentMode;
    private String state;
    private String city;
    private String customerName;
    @Column(name = "customer_number")
    private String customerNumber;
    private String email;
    @Column(name = "warranty_period")
    private int warrantyPeriod;

}

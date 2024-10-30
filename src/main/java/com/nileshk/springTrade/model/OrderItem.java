package com.nileshk.springTrade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @ManyToOne
    private Coin coin;

    private double buyPrice;
    private double sellPrice;
    private Long quantity;
    @JsonIgnore
    @OneToOne
    private Order order;
}

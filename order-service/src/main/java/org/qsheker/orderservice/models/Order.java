package org.qsheker.orderservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    private LocalDateTime orderDate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal total;

    @PrePersist
    public void prePersist(){
        this.orderDate = LocalDateTime.now();
    }
    public void addItem(OrderItem item){
        orderItems.add(item);
        item.setOrder(this);
    }
}

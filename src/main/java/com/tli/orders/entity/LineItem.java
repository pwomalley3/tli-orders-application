package com.tli.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@IdClass(LineItemId.class)
@Table(name = "ORDER_LINE_ITEMS", uniqueConstraints = @UniqueConstraint(columnNames = {"number", "orderId"}))
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LineItem {
    @Id
    private Integer number;
    @Id
    private Long orderId;
    private String name;
    private Double price;
    private Double quantity;
    private Timestamp createdDate;
    private Integer createdBy;
    private Timestamp modifiedDate;
    private Integer modifiedBy;
}

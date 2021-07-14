package com.tli.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer statusId;
    private Timestamp createdDate;
    private Integer createdBy;
    private Timestamp modifiedDate;
    private Integer modifiedBy;

    public Orders(Integer statusId, Timestamp createdDate, Integer createdBy, Timestamp modifiedDate, Integer modifiedBy) {
        this.statusId = statusId;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }
}

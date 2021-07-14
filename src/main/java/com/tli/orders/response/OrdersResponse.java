package com.tli.orders.response;

import com.tli.orders.entity.LineItem;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    Long id;
    String status;
    List<LineItemResponse> lineItems;
    Timestamp datePlaced;
}

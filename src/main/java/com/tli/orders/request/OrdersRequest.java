package com.tli.orders.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRequest {
    List<LineItemRequest> items;
}

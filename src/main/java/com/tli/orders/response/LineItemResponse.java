package com.tli.orders.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LineItemResponse {
    Integer number;
    Long orderId;
    String name;
    Double price;
    Double quantity;
}

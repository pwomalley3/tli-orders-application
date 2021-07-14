package com.tli.orders.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequest {
    String name;
    Double price;
    Double quantity;
}

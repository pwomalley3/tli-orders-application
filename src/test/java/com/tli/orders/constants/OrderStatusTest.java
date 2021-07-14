package com.tli.orders.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderStatusTest {

    @Test
    public void getValueByIndexTest() {
        Assertions.assertEquals("New", OrderStatus.getValueByIndex(1));
        Assertions.assertEquals("Processing", OrderStatus.getValueByIndex(2));
        Assertions.assertEquals("In Transit", OrderStatus.getValueByIndex(3));
        Assertions.assertEquals("Delivered", OrderStatus.getValueByIndex(4));
        Assertions.assertEquals("Cancelled", OrderStatus.getValueByIndex(5));
    }
}

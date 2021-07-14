package com.tli.orders.service;

import com.tli.orders.entity.Orders;
import com.tli.orders.repository.LineItemRepository;
import com.tli.orders.repository.OrdersRepository;
import com.tli.orders.request.LineItemRequest;
import com.tli.orders.request.OrdersRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @InjectMocks
    OrdersService service;

    @Mock
    OrdersRepository orderRepo;

    @Mock
    LineItemRepository itemRepo;

    private Orders order;
    private Object[] objectArray;
    private List<Object[]> objectList;

    @BeforeEach
    public void initOrder() {
        order = new Orders(1L, 1,
                new Timestamp(System.currentTimeMillis()), 1,
                new Timestamp(System.currentTimeMillis()), 1);
        objectArray = new Object[9];
        objectArray[0] = 1;
        objectArray[1] = 1L;
        objectArray[2] = "Widget";
        objectArray[3] = 10.99;
        objectArray[4] = 100.;
        objectArray[5] = new Timestamp(System.currentTimeMillis());
        objectArray[6] = 1;
        objectArray[7] = new Timestamp(System.currentTimeMillis());
        objectArray[8] = 1;
        objectList = Collections.singletonList(objectArray);
    }

    @Test
    public void viewOrderTest_Success() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.viewOrder(1L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void viewOrderTest_NoContent() {
        Mockito.when(orderRepo.findById(10L)).thenThrow(NoSuchElementException.class);

        ResponseEntity response = service.viewOrder(10L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void placeOrderTest_Success() {
        LineItemRequest itemRequest = new LineItemRequest("Widget", 10.99, 200.);
        OrdersRequest request = new OrdersRequest(Collections.singletonList(itemRequest));
        Orders newOrder = new Orders(6L, 1,
                new Timestamp(System.currentTimeMillis()), 1,
                new Timestamp(System.currentTimeMillis()), 1);
        Mockito.when(orderRepo.save(any(Orders.class))).thenReturn(newOrder);

        ResponseEntity response = service.placeOrder(request);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void cancelOrderTest_Success() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);
        Mockito.when(orderRepo.save(any(Orders.class))).thenReturn(new Orders(1L, 5,
                new Timestamp(System.currentTimeMillis()), 1,
                new Timestamp(System.currentTimeMillis()), 1));

        ResponseEntity response = service.cancelOrder(1L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void cancelOrderTest_NoContent() {
        Mockito.when(orderRepo.findById(10L)).thenThrow(NoSuchElementException.class);

        ResponseEntity response = service.cancelOrder(10L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void cancelOrderTest_BadRequest() {
        order.setId(3L);
        order.setStatusId(3);
        Mockito.when(orderRepo.findById(3L)).thenReturn(Optional.of(order));

        ResponseEntity response = service.cancelOrder(3L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void cancelOrderTest_AlreadyCancelled() {
        order.setId(5L);
        order.setStatusId(5);
        Mockito.when(orderRepo.findById(3L)).thenReturn(Optional.of(order));

        ResponseEntity response = service.cancelOrder(3L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void changeQuantityTest_Success() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.changeQuantity(1L, 1, 200.);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void changeQuantityTest_NoContent() {
        Mockito.when(orderRepo.findById(10L)).thenThrow(NoSuchElementException.class);

        ResponseEntity response = service.changeQuantity(10L, 3, 100.);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void changeQuantityTest_BadRequest_NegativeQuantity() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.changeQuantity(1L, 1, -200.);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void changeQuantityTest_BadRequest_InvalidLineItem() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.changeQuantity(1L, 2, 200.);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void removeLineItemTest_Success() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.removeLineItem(1L, 1);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void removeLineItem_NoContent() {
        Mockito.when(orderRepo.findById(10L)).thenThrow(NoSuchElementException.class);

        ResponseEntity response = service.removeLineItem(10L, 3);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void removeLineItem_BadRequest() {
        Mockito.when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(itemRepo.findAllByOrderId(1L)).thenReturn(objectList);

        ResponseEntity response = service.removeLineItem(1L, 2);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}

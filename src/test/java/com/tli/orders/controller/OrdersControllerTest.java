package com.tli.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tli.orders.constants.OrderStatus;
import com.tli.orders.request.LineItemRequest;
import com.tli.orders.request.OrdersRequest;
import com.tli.orders.response.LineItemResponse;
import com.tli.orders.response.OrdersResponse;
import com.tli.orders.service.OrdersService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest (OrdersController.class)
public class OrdersControllerTest {

    @MockBean
    OrdersService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void viewOrderTest() throws Exception {
        LineItemResponse itemResponse = new LineItemResponse(1, 1L, "Widget", 10.99, 100.0);
        List<LineItemResponse> itemResponseList = Collections.singletonList(itemResponse);
        OrdersResponse response = new OrdersResponse(1L, OrderStatus.getValueByIndex(1), itemResponseList, new Timestamp(System.currentTimeMillis()));
        Mockito.when(service.viewOrder(1L)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
        mockMvc.perform(get("/orders/view").param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("status", Matchers.is("New")));
    }

    @Test
    public void placeOrderTest() throws Exception {
        LineItemRequest itemRequest = new LineItemRequest("Widget", 10.99, 100.);
        List<LineItemRequest> requestList = Collections.singletonList(itemRequest);
        OrdersRequest request = new OrdersRequest(requestList);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);

        LineItemResponse itemResponse = new LineItemResponse(1, 1L, "Widget", 10.99, 100.0);
        List<LineItemResponse> itemResponseList = Collections.singletonList(itemResponse);
        OrdersResponse response = new OrdersResponse(6L, OrderStatus.getValueByIndex(1), itemResponseList, new Timestamp(System.currentTimeMillis()));

        Mockito.when(service.placeOrder(any(OrdersRequest.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
        mockMvc.perform(post("/orders/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", Matchers.is(6)))
                .andExpect(jsonPath("status", Matchers.is("New")));
    }

    @Test
    public void cancelOrderTest() throws Exception {
        LineItemResponse itemResponse = new LineItemResponse(1, 1L, "Widget", 10.99, 100.0);
        List<LineItemResponse> itemResponseList = Collections.singletonList(itemResponse);
        OrdersResponse response = new OrdersResponse(1L, OrderStatus.getValueByIndex(5), itemResponseList, new Timestamp(System.currentTimeMillis()));
        Mockito.when(service.cancelOrder(1L)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
        mockMvc.perform(get("/orders/cancel")
                .param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("status", Matchers.is(OrderStatus.getValueByIndex(5))));
    }

    @Test
    public void changeQuantityTest() throws Exception {
        LineItemResponse itemResponse = new LineItemResponse(1, 1L, "Widget", 10.99, 200.0);
        List<LineItemResponse> itemResponseList = Collections.singletonList(itemResponse);
        OrdersResponse response = new OrdersResponse(1L, OrderStatus.getValueByIndex(1), itemResponseList, new Timestamp(System.currentTimeMillis()));
        Mockito.when(service.changeQuantity(1L, 1, 200.0)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("orderId", "1");
        paramsMap.add("itemNumber", "1");
        paramsMap.add("quantity", "200.0");
        mockMvc.perform(get("/orders/change")
                .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("status", Matchers.is(OrderStatus.getValueByIndex(1))));
    }

    @Test
    public void removeLineItemTest() throws Exception {
        List<LineItemResponse> itemResponseList = Collections.emptyList();
        OrdersResponse response = new OrdersResponse(1L, OrderStatus.getValueByIndex(1), itemResponseList, new Timestamp(System.currentTimeMillis()));
        Mockito.when(service.removeLineItem(1L, 1)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("orderId", "1");
        paramsMap.add("itemNumber", "1");
        mockMvc.perform(get("/orders/remove")
                .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("status", Matchers.is(OrderStatus.getValueByIndex(1))));
    }
}

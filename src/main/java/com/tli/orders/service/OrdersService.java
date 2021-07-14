package com.tli.orders.service;

import com.tli.orders.constants.OrderStatus;
import com.tli.orders.entity.LineItem;
import com.tli.orders.entity.Orders;
import com.tli.orders.repository.LineItemRepository;
import com.tli.orders.repository.OrdersRepository;
import com.tli.orders.request.LineItemRequest;
import com.tli.orders.request.OrdersRequest;
import com.tli.orders.response.LineItemResponse;
import com.tli.orders.response.OrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository orderRepo;

    @Autowired
    LineItemRepository itemRepo;

    public ResponseEntity<OrdersResponse> viewOrder(Long orderId) {
        Orders order = fetchAndValidateOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(new OrdersResponse(), HttpStatus.NO_CONTENT);
        }
        Iterable<Object[]> itemIterable = itemRepo.findAllByOrderId(orderId);
        List<LineItemResponse> itemList = mapLineItemResponse(itemIterable);
        OrdersResponse response = mapOrdersResponse(order, itemList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<LineItemResponse> mapLineItemResponse(Iterable<Object[]> itemIterable) {
        List<LineItemResponse> itemList = new ArrayList<>();
        for (Object[] objectArray : itemIterable) {
            LineItemResponse lineItem = new LineItemResponse((Integer) objectArray[0],
                    (Long) objectArray[1],
                    (String) objectArray[2],
                    (Double) objectArray[3],
                    (Double) objectArray[4]);
            itemList.add(lineItem);
        }
        return itemList;
    }

    private List<LineItem> mapLineItem(Iterable<Object[]> itemIterable) {
        List<LineItem> itemList = new ArrayList<>();
        for (Object[] objectArray : itemIterable) {
            LineItem item = new LineItem((Integer) objectArray[0],
                    (Long) objectArray[1],
                    (String) objectArray[2],
                    (Double) objectArray[3],
                    (Double) objectArray[4],
                    (Timestamp) objectArray[5],
                    (Integer) objectArray[6],
                    (Timestamp) objectArray[7],
                    (Integer) objectArray[8]);
            itemList.add(item);
        }
        return itemList;
    }

    private OrdersResponse mapOrdersResponse(Orders order, List<LineItemResponse> itemList) {
        OrdersResponse response = new OrdersResponse();
        response.setId(order.getId());
        response.setDatePlaced(order.getCreatedDate());
        response.setLineItems(itemList);
        response.setStatus(OrderStatus.getValueByIndex(order.getStatusId()));

        return response;
    }

    public ResponseEntity<OrdersResponse> placeOrder(OrdersRequest request) {
        Orders order = new Orders(1, new Timestamp(System.currentTimeMillis()), 1, new Timestamp(System.currentTimeMillis()), 1);
        order = orderRepo.save(order);

        List<LineItem> itemList = new ArrayList<>();
        List<LineItemResponse> responseList = new ArrayList<>();
        for (int i = 0; i < request.getItems().size() ; i++) {
            LineItem item = new LineItem();
            LineItemResponse response = new LineItemResponse();
            LineItemRequest itemRequest = request.getItems().get(i);
            item.setNumber(i + 1);
            item.setOrderId(order.getId());
            item.setName(itemRequest.getName());
            item.setPrice(itemRequest.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            item.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            item.setCreatedBy(1);
            item.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            item.setModifiedBy(1);
            itemList.add(item);

            response.setNumber(item.getNumber());
            response.setOrderId(item.getOrderId());
            response.setName(item.getName());
            response.setPrice(item.getPrice());
            response.setQuantity(item.getQuantity());
            responseList.add(response);
        }
        itemRepo.saveAll(itemList);

        OrdersResponse response = new OrdersResponse(order.getId(), OrderStatus.N.getValue(), responseList, order.getCreatedDate());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<OrdersResponse> cancelOrder(Long orderId) {
        Orders order = fetchAndValidateOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(new OrdersResponse(), HttpStatus.NO_CONTENT);
        }
        Iterable<Object[]> itemIterable = itemRepo.findAllByOrderId(orderId);
        OrdersResponse response = new OrdersResponse(order.getId(),
                OrderStatus.getValueByIndex(order.getStatusId()),
                mapLineItemResponse(itemIterable),
                order.getCreatedDate());

        if (!Objects.equals(order.getStatusId(), OrderStatus.T.getIndex())
                && !Objects.equals(order.getStatusId(), OrderStatus.D.getIndex())
                && !Objects.equals(order.getStatusId(), OrderStatus.C.getIndex())) {
            order.setStatusId(OrderStatus.C.getIndex());
        } else if (Objects.equals(order.getStatusId(), OrderStatus.C.getIndex())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        order.setModifiedBy(1);
        order.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        order = orderRepo.save(order);

        response.setStatus(OrderStatus.getValueByIndex(order.getStatusId()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<OrdersResponse> changeQuantity(Long orderId, Integer itemNumber, Double quantity) {
        Orders order = fetchAndValidateOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(new OrdersResponse(), HttpStatus.NO_CONTENT);
        }
        Iterable<Object[]> itemIterable = itemRepo.findAllByOrderId(orderId);
        OrdersResponse response = new OrdersResponse(order.getId(),
                OrderStatus.getValueByIndex(order.getStatusId()),
                mapLineItemResponse(itemIterable),
                order.getCreatedDate());

        List<LineItem> itemList = this.mapLineItem(itemIterable);
        if (quantity >= 1 && itemNumber <= itemList.size() && itemNumber > 0) {
            itemList.get(itemNumber - 1).setQuantity(quantity);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        itemRepo.saveAll(itemList);
        itemIterable = itemRepo.findAllByOrderId(orderId);

        response.setLineItems(mapLineItemResponse(itemIterable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<OrdersResponse> removeLineItem(Long orderId, Integer itemNumber) {
        Iterable<Object[]> itemIterable;
        Orders order = fetchAndValidateOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(new OrdersResponse(), HttpStatus.NO_CONTENT);
        }

        itemIterable = itemRepo.findAllByOrderId(orderId);
        List<LineItemResponse> responseList = this.mapLineItemResponse(itemIterable);
        OrdersResponse response = new OrdersResponse(order.getId(),
                OrderStatus.getValueByIndex(order.getStatusId()),
                responseList,
                order.getCreatedDate());

        if (itemNumber <= responseList.size() && itemNumber > 0) {
            itemRepo.removeLineItemByOrderIdAndNumber(orderId, itemNumber);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        itemIterable = itemRepo.findAllByOrderId(orderId);
        response.setLineItems(mapLineItemResponse(itemIterable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Orders fetchAndValidateOrder(Long orderId) {
        Orders order;
        try {
            order = orderRepo.findById(orderId).get();
        } catch (NoSuchElementException e) {
            return null;
        }
        return order;
    }
}

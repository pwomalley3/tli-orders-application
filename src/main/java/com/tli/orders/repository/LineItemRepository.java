package com.tli.orders.repository;

import com.tli.orders.entity.LineItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {

    @Query("SELECT number, orderId, name, price, quantity, createdDate, createdBy, modifiedDate, modifiedBy FROM LineItem where orderId = :orderId")
    Iterable<Object[]> findAllByOrderId (@Param("orderId") Long orderId);
}

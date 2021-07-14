package com.tli.orders.repository;

import com.tli.orders.entity.LineItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {

    @Query("SELECT number, orderId, name, price, quantity, createdDate, createdBy, modifiedDate, modifiedBy FROM LineItem where orderId = :orderId")
    Iterable<Object[]> findAllByOrderId (@Param("orderId") Long orderId);

    @Query("DELETE FROM LineItem WHERE orderId = :orderId AND number = :itemNumber")
    @Modifying
    @Transactional
    void removeLineItemByOrderIdAndNumber(@Param("orderId")Long orderId, @Param("itemNumber") Integer itemNumber);
}

package com.example.exchange;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    @Test
    public void nullOrderId() {
        Throwable exception = assertThrows(NullPointerException.class,
                () -> new Order(null, LocalTime.now(), "Navi", true, 100.00, 100));
        assertEquals(exception.getMessage(), "order Id can not be null");
    }

    @Test
    public void nullStockId() {
        Throwable exception = assertThrows(NullPointerException.class,
                () -> new Order("1", LocalTime.now(), null, true, 100.00, 100));
        assertEquals(exception.getMessage(), "stock Id can not be null");
    }

    @Test
    public void negativePrice() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Order("1", LocalTime.now(), "Navi", true, -100.00, 100));
        assertEquals(exception.getMessage(), "You should trade with positive price");
    }

    @Test
    public void negativeQuantity() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Order("1", LocalTime.now(), "Navi", true, 100.00, -100));
        assertEquals(exception.getMessage(), "Quantity of the stock should be positive");
    }

    @Test
    public void validOrder() {
        Order order = new Order("1", LocalTime.MIDNIGHT, "Navi", true, 10.00, 10);
        String orderId = order.getOrderId();
        Integer quantities = order.getQuantities();
        String stockId = order.getStockId();
        Double price = order.getPrice();
        boolean sellingStatus = order.getIsSelling();
        LocalTime eventTime = order.getEventTime();
        order.setQuantities(quantities+1);
        quantities = order.getQuantities();

        assertEquals(orderId, "1");
        assertEquals(quantities, 11);
        assertEquals(stockId, "Navi");
        assertEquals(price, 10.00);
        assertTrue(sellingStatus);
        assertEquals(eventTime, LocalTime.MIDNIGHT);
    }
}

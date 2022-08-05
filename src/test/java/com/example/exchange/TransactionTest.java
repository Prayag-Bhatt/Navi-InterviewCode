package com.example.exchange;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void nullBuyOrderId() {
        Throwable exception = assertThrows(NullPointerException.class,
                () -> new Transaction(null, 10.00, 10, "2"));
        assertEquals("buy order Id can not be null", exception.getMessage());
    }

    @Test
    public void nullSellOrderId() {
        Throwable exception = assertThrows(NullPointerException.class,
                () -> new Transaction("1", 10.00, 10, null));
        assertEquals("sell order Id can not be null", exception.getMessage());
    }

    @Test
    public void negativePrice() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Transaction("1", -10.00, 10, "2"));
        assertEquals("You should trade with positive price", exception.getMessage());
    }

    @Test
    public void negativeQuantity() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Transaction("1", 10.00, -10, "2"));
        assertEquals("Quantity of the transaction should be positive", exception.getMessage());
    }

    @Test
    public void validTransaction() {
        Transaction transaction = new Transaction("1", 10.00, 10, "2");
        Transaction transaction1 = new Transaction("2", 10.00, 10, "3");
        Transaction transaction2 = new Transaction("1", 11.00, 10, "3");
        Transaction transaction3 = new Transaction("1", 10.00, 11, "3");
        Transaction transaction4 = new Transaction("1", 10.00, 10, "3");
        String string = transaction.toString();
        assertEquals(transaction.getQuantities(), 10);
        assertEquals(transaction.getBuyOrderId(), "1");
        assertEquals(transaction.getSellOrderId(), "2");
        assertEquals(transaction.getSellPrice(), 10.00);
        assertEquals(string, "1 10.0 10 2");
        assertNotEquals(transaction, transaction1);
        assertNotEquals(transaction, transaction2);
        assertNotEquals(transaction, transaction3);
        assertNotEquals(transaction, transaction4);
        assertNotEquals(transaction, null);
        assertNotEquals(transaction, string);
    }
}

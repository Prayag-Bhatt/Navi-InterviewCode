package com.example.exchange;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatcherTest {

    @Test
    public void fullTransactionOfSameStockId() {
        Order order1 = new Order("1", LocalTime.now(), "Navi", true, 100.75, 100);
        Order order2 = new Order("2", LocalTime.now().plusHours(1L), "Navi", false, 100.75, 100);

        Matcher matcher = new Matcher();

        List<Transaction> transaction1 = matcher.match(order1);
        List<Transaction> transaction2 = matcher.match(order2);

        Transaction transaction = new Transaction("2", 100.75, 100, "1");
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        assertEquals(transaction1.size(), 0);
        assertEquals(transaction2.size(), 1);
        assertTrue(transaction2.containsAll(transactionList));
    }

    @Test
    public void partialTransactionOfSameStockIdSellThenBuy() {
        Order order1 = new Order("1", LocalTime.now(), "Navi", true, 100.75, 100);
        Order order2 = new Order("2", LocalTime.now().plusHours(1L), "Navi", false, 101.00, 50);
        Order order3 = new Order("3", LocalTime.now().plusHours(2L), "Navi", false, 100.75, 125);
        Order order4 = new Order("4", LocalTime.now().plusHours(3L), "Navi", true, 90.00, 100);
        Order order5 = new Order("5", LocalTime.now().plusHours(4L), "Navi", true, 80.00, 25);
        Order order6 = new Order("6", LocalTime.now().plusHours(5L), "Navi", false, 125.00, 60);

        Matcher matcher = new Matcher();

        List<Transaction> transaction1 = matcher.match(order1);
        List<Transaction> transaction2 = matcher.match(order2);
        List<Transaction> transaction3 = matcher.match(order3);
        List<Transaction> transaction4 = matcher.match(order4);
        List<Transaction> transaction5 = matcher.match(order5);
        List<Transaction> transaction6 = matcher.match(order6);

        List<Transaction> transactionList2 = new ArrayList<>();
        transactionList2.add(new Transaction("2", 100.75, 50, "1"));
        List<Transaction> transactionList3 = new ArrayList<>();
        transactionList3.add(new Transaction("3", 100.75, 50, "1"));
        List<Transaction> transactionList4 = new ArrayList<>();
        transactionList4.add(new Transaction("3", 90.00, 75, "4"));
        List<Transaction> transactionList6 = new ArrayList<>();
        transactionList6.add(new Transaction("6", 90.00, 25, "4"));
        transactionList6.add(new Transaction("6", 80.00, 25, "5"));

        assertTrue(transaction1.isEmpty());
        assertTrue(transaction2.containsAll(transactionList2));
        assertTrue(transaction3.containsAll(transactionList3));
        assertTrue(transaction4.containsAll(transactionList4));
        assertTrue(transaction5.isEmpty());
        assertTrue(transaction6.containsAll(transactionList6));
    }

    @Test
    public void partialTransactionOfSameStockIdBuyThenSell() {
        Order order1 = new Order("1", LocalTime.now(), "Navi", false, 100.75, 100);
        Order order2 = new Order("2", LocalTime.now().plusHours(1L), "Navi", true, 100.75, 50);
        Order order3 = new Order("3", LocalTime.now().plusHours(2L), "Navi", true, 100.75, 25);

        Matcher matcher = new Matcher();

        List<Transaction> transaction1 = matcher.match(order1);
        List<Transaction> transaction2 = matcher.match(order2);
        List<Transaction> transaction3 = matcher.match(order3);

        List<Transaction> transactionList2 = new ArrayList<>();
        transactionList2.add(new Transaction("1", 100.75, 50, "2"));
        List<Transaction> transactionList3 = new ArrayList<>();
        transactionList3.add(new Transaction("1", 100.75, 25, "3"));

        assertTrue(transaction1.isEmpty());
        assertTrue(transaction2.containsAll(transactionList2));
        assertTrue(transaction3.containsAll(transactionList3));
    }

    @Test
    public void buyingAndSellingMultipleTimes() {
        Order order1 = new Order("1", LocalTime.now(), "Navi", false, 100.75, 100);
        Order order2 = new Order("2", LocalTime.now().plusHours(1L), "Navi", true, 100.75, 50);
        Order order3 = new Order("3", LocalTime.now().plusHours(2L), "Navi", true, 100.75, 25);

        Matcher matcher = new Matcher();

        List<Transaction> transaction1 = matcher.match(order1);
        List<Transaction> transaction2 = matcher.match(order2);
        List<Transaction> transaction3 = matcher.match(order3);

        List<Transaction> transactionList2 = new ArrayList<>();
        transactionList2.add(new Transaction("1", 100.75, 50, "2"));
        List<Transaction> transactionList3 = new ArrayList<>();
        transactionList3.add(new Transaction("1", 100.75, 25, "3"));

        assertTrue(transaction1.isEmpty());
        assertTrue(transaction2.containsAll(transactionList2));
        assertTrue(transaction3.containsAll(transactionList3));
    }
}

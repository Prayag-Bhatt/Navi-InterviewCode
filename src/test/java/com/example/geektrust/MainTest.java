package com.example.geektrust;

import com.example.exchange.Order;
import com.example.exchange.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void invalidFile() {
        String[] strings = {"\temp.txt"};

        Throwable exception = assertThrows(IOException.class, () -> Main.main(strings));
        assertEquals("Unable to load input file", exception.getMessage());
    }

    @Test
    public void parsingOrderFromFile() throws IOException {
        String[] strings = {"src/../sample_input/input1.txt"};
        ArrayList<Order> orders = Main.parseOrders(strings[0]);

        Order order1 = new Order("#1", LocalTime.parse("09:45"), "BAC", true, 240.12, 100);
        Order order2 = new Order("#2", LocalTime.parse("09:46"), "BAC", true, 237.45, 90);
        Order order3 = new Order("#3", LocalTime.parse("09:47"), "BAC", false, 238.10, 110);
        Order order4 = new Order("#4", LocalTime.parse("09:48"), "BAC", false, 237.80, 10);
        Order order5 = new Order("#5", LocalTime.parse("09:49"), "BAC", false, 237.80, 40);
        Order order6 = new Order("#6", LocalTime.parse("09:50"), "BAC", true, 236.00, 50);

        ArrayList<Order> actualOrders = new ArrayList<Order>();
        actualOrders.add(order1);
        actualOrders.add(order2);
        actualOrders.add(order3);
        actualOrders.add(order4);
        actualOrders.add(order5);
        actualOrders.add(order6);

        assertTrue(actualOrders.containsAll(orders));
    }

    @Test
    public void groupByStockIdToOrder() {
        Order order1 = new Order("#1", LocalTime.parse("09:45"), "BAC", true, 240.12, 100);
        Order order2 = new Order("#2", LocalTime.parse("09:46"), "BAC", true, 237.45, 90);
        Order order3 = new Order("#3", LocalTime.parse("09:47"), "BAC", false, 238.10, 110);
        Order order4 = new Order("#4", LocalTime.parse("09:48"), "BAC", false, 237.80, 10);
        Order order5 = new Order("#5", LocalTime.parse("09:49"), "BAC", false, 237.80, 40);
        Order order6 = new Order("#6", LocalTime.parse("09:50"), "BAC", true, 236.00, 50);

        ArrayList<Order> actualOrders = new ArrayList<Order>();
        actualOrders.add(order1);
        actualOrders.add(order2);
        actualOrders.add(order3);
        actualOrders.add(order4);
        actualOrders.add(order5);
        actualOrders.add(order6);

        Map<String, ArrayList<Order>> stockIdToOrders = Main.groupByOrder(actualOrders);

        Map<String, ArrayList<Order>> actualStockIdToOrders = new HashMap<>();
        actualStockIdToOrders.put("BAC", actualOrders);

        assertEquals(actualStockIdToOrders, stockIdToOrders);
    }

    @Test
    public void processAllTheOrders() {
        Order order1 = new Order("#1", LocalTime.parse("09:45"), "BAC", true, 240.12, 100);
        Order order2 = new Order("#2", LocalTime.parse("09:46"), "BAC", true, 237.45, 90);
        Order order3 = new Order("#3", LocalTime.parse("09:47"), "BAC", false, 238.10, 110);
        Order order4 = new Order("#4", LocalTime.parse("09:48"), "BAC", false, 237.80, 10);
        Order order5 = new Order("#5", LocalTime.parse("09:49"), "BAC", false, 237.80, 40);
        Order order6 = new Order("#6", LocalTime.parse("09:50"), "BAC", true, 236.00, 50);

        ArrayList<Order> actualOrders = new ArrayList<Order>();
        actualOrders.add(order1);
        actualOrders.add(order2);
        actualOrders.add(order3);
        actualOrders.add(order4);
        actualOrders.add(order5);
        actualOrders.add(order6);

        Map<String, ArrayList<Order>> actualStockIdToOrders = new HashMap<>();
        actualStockIdToOrders.put("BAC", actualOrders);

        ArrayList<Transaction> transactions = Main.processOrders(actualStockIdToOrders);

        ArrayList<Transaction> actualTransactions = new ArrayList<>();
        actualTransactions.add(new Transaction("#3", 237.45, 90, "#2"));
        actualTransactions.add(new Transaction("#3", 236.00, 20, "#6"));
        actualTransactions.add(new Transaction("#4", 236.00, 10, "#6"));
        actualTransactions.add(new Transaction("#5", 236.00, 20, "#6"));

        assertEquals(actualTransactions, transactions);
    }

    // This class is for testing the main method
    @Test
    public void testingMain() throws IOException {
        String[] strings = {"src/../sample_input/input1.txt"};
        Main.main(strings);
    }
}
package com.example.exchange;

import java.util.*;
public class Matcher {
    private final List<Order> buyOrderList;
    private final List<Order> sellOrderList;
    public Matcher() {
        this.buyOrderList = new ArrayList<>();
        this.sellOrderList = new ArrayList<>();
    }
    private void refreshList() {
        this.buyOrderList.removeIf(order -> order.getQuantities() == 0);
        this.sellOrderList.removeIf(order -> order.getQuantities() == 0);
    }
    public List<Transaction> match(Order order) {
        this.refreshList();
        if (order.getIsSelling()) {
            return this.matchSellingOrder(order);
        }

        return this.matchBuyingOrder(order);
    }
    private List<Transaction> matchBuyingOrder(Order order) {

        List<Transaction> transactions = new ArrayList<>();

        for (Order sellOrder : this.sellOrderList) {
            if(sellOrder.getPrice() <= order.getPrice()) {
                if (sellOrder.getQuantities() >= order.getQuantities()) {
                    Transaction transaction = new Transaction(order.getOrderId(), sellOrder.getPrice(), order.getQuantities(), sellOrder.getOrderId());
                    transactions.add(transaction);
                    sellOrder.setQuantities(sellOrder.getQuantities() - order.getQuantities());
                    return transactions;
                } else {
                    Transaction transaction = new Transaction(order.getOrderId(), sellOrder.getPrice(), sellOrder.getQuantities(), sellOrder.getOrderId());
                    transactions.add(transaction);
                    order.setQuantities(order.getQuantities() - sellOrder.getQuantities());
                    sellOrder.setQuantities(0);
                }
            }
        }

        this.buyOrderList.add(order);
        return transactions;
    }
    private List<Transaction> matchSellingOrder(Order order) {

        List<Transaction> transactions = new ArrayList<>();

        for (Order buyOrder : this.buyOrderList) {
            if(buyOrder.getPrice() >= order.getPrice()) {
                if (buyOrder.getQuantities() >= order.getQuantities()) {
                    Transaction transaction = new Transaction(buyOrder.getOrderId(), order.getPrice(), order.getQuantities(), order.getOrderId());
                    transactions.add(transaction);
                    buyOrder.setQuantities(buyOrder.getQuantities() - order.getQuantities());
                    return transactions;
                } else {
                    Transaction transaction = new Transaction(buyOrder.getOrderId(), order.getPrice(), buyOrder.getQuantities(), order.getOrderId());
                    transactions.add(transaction);
                    order.setQuantities(order.getQuantities() - buyOrder.getQuantities());
                    buyOrder.setQuantities(0);
                }
            }
        }

        this.sellOrderList.add(order);

        return transactions;
    }
}
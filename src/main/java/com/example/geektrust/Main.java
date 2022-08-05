package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.*;
import java.time.LocalTime;
import java.util.*;

import com.example.exchange.*;
public class Main {
    public static ArrayList<Order> parseOrders(String path) throws IOException{
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(path);

            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read

            while (sc.hasNextLine()) {
                //Add your code here to process input commands
                String inputLine = sc.nextLine();

                String orderId = inputLine.substring(0, 2);
                LocalTime eventTime = LocalTime.parse(inputLine.substring(3, 8));

                StringBuilder stockId = new StringBuilder();
                int index=9;
                while(inputLine.charAt(index) != ' ') {
                    stockId.append(inputLine.charAt(index));
                    index++;
                }

                index++;

                StringBuilder sellingStatus = new StringBuilder();

                while(inputLine.charAt(index) != ' ') {
                    sellingStatus.append(inputLine.charAt(index));
                    index++;
                }

                while(inputLine.charAt(index) == ' ') index++;

                StringBuilder pricing = new StringBuilder();

                while(inputLine.charAt(index) != ' ') {
                    pricing.append(inputLine.charAt(index));
                    index++;
                }

                while(inputLine.charAt(index) == ' ') index++;

                StringBuilder quantity = new StringBuilder();

                while(index<inputLine.length() && inputLine.charAt(index) != ' ') {
                    quantity.append(inputLine.charAt(index));
                    index++;
                }

                Boolean isSelling = Objects.equals(sellingStatus.toString(), "sell");
                Double stockPrice = Double.parseDouble(pricing.toString());
                Integer stockQuantity = Integer.parseInt(quantity.toString());
                Order currOrder = new Order(orderId, eventTime, stockId.toString(), isSelling, stockPrice, stockQuantity);
                orderList.add(currOrder);
            }
            sc.close(); // closes the scanner

        } catch (IOException e) {
            throw new IOException("Unable to load input file", e);
        }

        return orderList;
    }

    public static Map<String, ArrayList<Order>> groupByOrder(ArrayList<Order> orders) {
        Map<String, ArrayList<Order>> stockIdToOrder = new HashMap<>();

        for(Order order : orders) {
            stockIdToOrder.putIfAbsent(order.getStockId(), new ArrayList<>());
            stockIdToOrder.get(order.getStockId()).add(order);
        }

        return stockIdToOrder;
    }

    public static ArrayList<Transaction> processOrders(Map<String, ArrayList<Order>> stockIdToOrder) {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Order>> entry : stockIdToOrder.entrySet()) {
            Matcher matcher = new Matcher();
            List<Order> values = entry.getValue();
            Collections.sort(values);

            for (Order value : values) {
                transactionList.addAll(matcher.match(value));
            }
        }

        return transactionList;
    }
    public static void main(String[] args) throws IOException {
        ArrayList<Order> orderList = parseOrders(args[0]);
        Map<String, ArrayList<Order>> stockIdToOrder = groupByOrder(orderList);
        ArrayList<Transaction> transactions = processOrders(stockIdToOrder);
        transactions.forEach(System.out::println);
    }
}

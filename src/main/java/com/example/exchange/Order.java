package com.example.exchange;

import java.time.LocalTime;
public class Order implements Comparable<Order> {
    private final String orderId;
    private final LocalTime eventTime;
    private final String stockId;
    private final Boolean isSelling;
    private final Double price;
    private Integer quantities;
    public Order(String orderId, LocalTime eventTime, String stockId, Boolean isSelling, Double price, Integer quantities) {

        if (orderId == null) {
            throw new NullPointerException("order Id can not be null");
        }

        if (stockId == null) {
            throw new NullPointerException("stock Id can not be null");
        }

        if (price <= 0.0) {
            throw new IllegalArgumentException("You should trade with positive price");
        }

        if (quantities <= 0.0) {
            throw new IllegalArgumentException("Quantity of the stock should be positive");
        }

        this.orderId = orderId;
        this.eventTime = eventTime;
        this.stockId = stockId;
        this.isSelling = isSelling;
        this.price = price;
        this.quantities = quantities;
    }
    public String getOrderId() {
        return orderId;
    }
    public LocalTime getEventTime() { return eventTime; }
    public String getStockId() { return stockId; }
    public Boolean getIsSelling() { return isSelling; }
    public Double getPrice() { return price; }
    public Integer getQuantities() {
        return quantities;
    }
    public void setQuantities(Integer quantities) { this.quantities = quantities; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!orderId.equals(order.orderId)) return false;
        if (!eventTime.equals(order.eventTime)) return false;
        if (!stockId.equals(order.stockId)) return false;
        if (!isSelling.equals(order.isSelling)) return false;
        if (!price.equals(order.price)) return false;
        return quantities.equals(order.quantities);
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + eventTime.hashCode();
        result = 31 * result + stockId.hashCode();
        result = 31 * result + isSelling.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + quantities.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", eventTime=" + eventTime +
                ", stockId='" + stockId + '\'' +
                ", isSelling=" + isSelling +
                ", price=" + price +
                ", quantities=" + quantities +
                '}';
    }

    @Override
    public int compareTo(final Order order) {
        if (this.eventTime.isBefore(order.getEventTime())) {
            return -1;
        } else {
            return 1;
        }
    }
}
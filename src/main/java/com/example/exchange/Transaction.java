package com.example.exchange;
public class Transaction {
    private final String buyOrderId;
    private final double sellPrice;
    private final Integer quantities;
    private final String sellOrderId;

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public Integer getQuantities() {
        return quantities;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public Transaction(String buyOrderId, double sellPrice, Integer quantities, String sellOrderId) {

        if(buyOrderId == null) {
            throw new NullPointerException("buy order Id can not be null");
        }

        if(sellOrderId == null) {
            throw new NullPointerException("sell order Id can not be null");
        }

        if(sellPrice <= 0.0) {
            throw new IllegalArgumentException("You should trade with positive price");
        }

        if(quantities <= 0.0) {
            throw new IllegalArgumentException("Quantity of the transaction should be positive");
        }

        this.buyOrderId = buyOrderId;
        this.sellPrice = sellPrice;
        this.quantities = quantities;
        this.sellOrderId = sellOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (Double.compare(that.sellPrice, sellPrice) != 0) return false;
        if (!buyOrderId.equals(that.buyOrderId)) return false;
        if (!quantities.equals(that.quantities)) return false;
        return sellOrderId.equals(that.sellOrderId);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = buyOrderId.hashCode();
        temp = Double.doubleToLongBits(sellPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quantities.hashCode();
        result = 31 * result + sellOrderId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return buyOrderId + " " + String.format("%.2f", sellPrice) + " " + quantities + " " + sellOrderId;
    }
}
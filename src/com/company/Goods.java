package com.company;

/**
 * Created by dell on 2016/1/4.
 */
public class Goods {

    private String barcode;      //条形码
    private String name;         //商品名称
    private String unit;         //商品单位
    private Double price;        //商品价格
    private Double discount=1.0; //打折信息

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}

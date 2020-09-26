package com.sim.localecomapp;

import com.sim.localecomapp.network.ProductEntry;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<ProductEntry> products;
    private float total;


    public Cart(List<ProductEntry> products) {
        this.products = products;
    }

    public Cart(){
            products=new ArrayList<>();
    }

    public List<ProductEntry> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntry> products) {
        this.products = products;
    }

    public void addProduct(ProductEntry productEntry){
        products.add(productEntry);
        calculTotal();

    }

    public void  removeProduct(int index){
        products.remove(index);
        calculTotal();
    }

    public void clear(){
        products.clear();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void calculTotal(){
        float sum=0;
        for (int i = 0; i < products.size() ; i++) {
            sum+=Float.parseFloat(products.get(i).price);
        }
        total=sum;
    }
}

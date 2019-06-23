/**
 * Implemented by Rhadjni Phipps
 */

package com.example.rhadjni.lifeplanner.Tables;


public class Product {
    private	int	id;
    private String name;
    private	float	quantity;
    private String purchasedate;

    public Product(String name, float quantity,String purchasedate) {
        this.name = name;
        this.quantity = quantity;
        this.purchasedate=purchasedate;
    }

    public Product(int id, String name, float quantity,String purchasedate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.purchasedate=purchasedate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }
}

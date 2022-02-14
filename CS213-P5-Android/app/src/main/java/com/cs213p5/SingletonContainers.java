package com.cs213p5;

public class SingletonContainers {
    private static SingletonContainers instance;
    private Order myOrder = new Order();
    private StoreOrders storeOrder = new StoreOrders();

    private SingletonContainers() {
    }

    static SingletonContainers getInstance() {
        if(instance == null) {
            instance = new SingletonContainers();
        }
        return instance;
    }

    public Order getMyOrder() {
        return myOrder;
    }

    public StoreOrders getStoreOrder() {
        return storeOrder;
    }

    public Order setMyOrder() {
        this.myOrder = new Order();
        return myOrder;
    }

}


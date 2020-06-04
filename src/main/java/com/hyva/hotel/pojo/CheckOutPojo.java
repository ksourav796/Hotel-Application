package com.hyva.hotel.pojo;

import java.util.List;

public class CheckOutPojo {

   private OrdersPojo OrdersObj;
   private List<GuestsPojo> guestsPojoList;

    public OrdersPojo getOrdersObj() {
        return OrdersObj;
    }

    public void setOrdersObj(OrdersPojo ordersObj) {
        OrdersObj = ordersObj;
    }

    public List<GuestsPojo> getGuestsPojoList() {
        return guestsPojoList;
    }

    public void setGuestsPojoList(List<GuestsPojo> guestsPojoList) {
        this.guestsPojoList = guestsPojoList;
    }
}

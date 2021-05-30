package com.kpi.springlabs.backend.utils;

public interface Constants {

    interface TableNames {
        String GOODS = "goods";
        String SHOPS = "shops";
        String WAREHOUSES = "warehouses";
        String GOODS_IN_STOCKS = "goods_in_stocks";
        String GOODS_IN_SHOPS = "goods_in_shops";
        String DELIVERY_ITEMS = "delivery_items";
        String DELIVERY_REQUESTS = "delivery_requests";
    }

    interface User {
        String USERNAME = "username";
        String EMAIL = "email";
    }

    interface AccessTokenType {
        String BEARER = "Bearer";
    }
}

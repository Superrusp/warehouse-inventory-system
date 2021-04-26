package com.kpi.springlabs.backend.utils;

public interface Constants {

    interface TableNames {
        String GOODS = "goods";
        String SHOPS = "shops";
        String WAREHOUSES = "warehouses";
        String GOODS_IN_STOCKS = "goods_in_stocks";
        String GOODS_IN_SHOPS = "goods_in_shops";
        String DELIVERY_ITEMS = "delivery_items";
    }

    interface PrimaryKeys {
        String GOODS_ID = "goods_id";
        String SHOP_ID = "shop_id";
        String WAREHOUSE_ID = "warehouse_id";
        String GOODS_IN_STOCK_ID = "goods_in_stock_id";
        String GOODS_IN_SHOP_ID = "goods_in_shop_id";
        String DELIVERY_ITEM_ID = "delivery_item_id";
    }
}

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

    interface DocumentNames {
        String USERS = "users";
        String ROLES = "roles";
        String CONFIRMATION_TOKENS = "confirmation_tokens";
        String REFRESH_TOKENS = "refresh_tokens";
        String JWT_BLACK_LISTS = "jwt_black_list";
    }

    interface BasicFields {
        String ID = "id";
    }

    interface UserFields {
        String USERNAME = "username";
        String EMAIL = "email";
    }

    interface TokenFields {
        String TOKEN = "token";
        String TOKEN_VALUE = "tokenValue";
        String EXPIRATION_DATE = "expirationDate";
        String REFRESH_TOKEN = "refreshToken";
    }

    interface AccessTokenType {
        String BEARER = "Bearer";
    }
}

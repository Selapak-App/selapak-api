package com.selapak.selapakapi.constant;

public class AppPath {

    public final static String CUSTOMER_PATH = "/api/customers";
    public final static String RENT_PERIOD_PATH = "/api/rent-periods";
    public final static String LAND_OWNER_PATH = "/api/land-owners";
    public final static String LAND_PRICE_PATH = "/api/land-prices";
    public final static String BUSINESS_TYPE_PATH = "/api/business-types";
    public final static String LAND_PATH = "/api/lands";
    public final static String TRANSACTION_PATH = "/api/transactions";


    public final static String REGISTER_PATH = "/api/auth/register";
    public final static String LOGIN_PATH = "/api/auth/login";
    public final static String REGISTER_ADMIN_PATH = "/api/auth/admin/register";
    public final static String LOGIN_ADMIN_PATH = "/api/auth/admin/login";

    public final static String GET_BY_ID = "/{id}";
    public final static String UPDATE_BY_ID = "/{id}";
    public final static String DELETE_BY_ID = "/{id}";

    public final static String REGISTER_SUPER_ADMIN_PATH = SUPER_ADMIN_PATH + REGISTER_PATH;
    public final static String REGISTER_ADMIN_PATH = ADMIN_PATH + REGISTER_PATH;
    public final static String LOGIN_ADMIN_PATH = ADMIN_PATH + LOGIN_PATH;


}

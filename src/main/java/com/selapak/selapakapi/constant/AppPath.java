package com.selapak.selapakapi.constant;

public class AppPath {
    
    public final static String ADMIN_PATH = "/admin";
    public final static String SUPER_ADMIN_PATH = "/super-admin";
    public final static String CUSTOMER_PATH = "/customers";
    public final static String RENT_PERIOD_PATH = "/rent-periods";
    public final static String LAND_OWNER_PATH = "/land-owners";
    public final static String LAND_PRICE_PATH = "/land-prices";
    public final static String BUSINESS_TYPE_PATH = "/business-types";
    public final static String LAND_PATH = "/lands";
    public final static String LAND_PHOTO_PATH = "/land-photo";
    public final static String TRANSACTION_PATH = "/transactions";

    public final static String REGISTER_PATH = "/register";
    public final static String LOGIN_PATH = "/login";

    public final static String GET_BY_ID = "/{id}";
    public final static String UPDATE_BY_ID = "/{id}";
    public final static String DELETE_BY_ID = "/{id}";

    public final static String REGISTER_SUPER_ADMIN_PATH = SUPER_ADMIN_PATH + REGISTER_PATH;
    public final static String REGISTER_ADMIN_PATH = ADMIN_PATH + REGISTER_PATH;
    public final static String LOGIN_ADMIN_PATH = ADMIN_PATH + LOGIN_PATH;

}

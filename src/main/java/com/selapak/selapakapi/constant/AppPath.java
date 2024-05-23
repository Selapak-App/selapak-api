package com.selapak.selapakapi.constant;

public class AppPath {

    public final static String SUPER_ADMIN_PATH = "/api/super-admins";
    public final static String ADMIN_PATH = "/api/admins";
    public final static String CUSTOMER_PATH = "/api/customers";
    public final static String RENT_PERIOD_PATH = "/api/rent-periods";
    public final static String LAND_OWNER_PATH = "/api/land-owners";
    public final static String LAND_PRICE_PATH = "/api/land-prices";
    public final static String BUSINESS_TYPE_PATH = "/api/business-types";
    public final static String LAND_PATH = "/api/lands";
    public final static String LAND_PHOTO_PATH = "/land-photos";
    public final static String TRANSACTION_PATH = "/api/transactions";

    public final static String REGISTER_PATH = "/api/auth/register";
    public final static String LOGIN_PATH = "/api/auth/login";
    public final static String REGISTER_SUPER_ADMIN_PATH = "/api/auth/super-admin/register";
    public final static String REGISTER_ADMIN_PATH = "/api/auth/admin/register";
    public final static String LOGIN_ADMIN_PATH = "/api/auth/admin/login";

    public final static String GET_BY_ID = "/{id}";
    public final static String UPDATE_BY_ID = "/{id}";
    public final static String DELETE_BY_ID = "/{id}";

    public final static String TRANSACTION_APPROVE = "/approve/{transactionId}";
    public final static String TRANSACTION_REJECT = "/reject/{transactionId}";
    public final static String TRANSACTION_SURVEY = "/survey/{transactionId}";
    public final static String TRANSACTION_ACCEPT = "/accept/{transactionId}";
    public final static String TRANSACTION_DECLINE = "/decline/{transactionId}";
    public final static String TRANSACTION_PAY = "/pay/{transactionId}";
    public final static String DEACTIVE_ADMIN = "/deactive/{adminId}";
    public final static String ACTIVE_ADMIN = "/active/{adminId}";
    public final static String DASHBOARD = "/dashboard";

}

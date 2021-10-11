package com.example.bank;

public class ApiUtils {

    public static final String BASE_URL = "https://virtserver.swaggerhub.com/creative6/CevastLogin/1.0.0/auth/login/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
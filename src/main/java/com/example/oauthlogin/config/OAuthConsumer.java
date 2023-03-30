package com.example.oauthlogin.config;

@FunctionalInterface
public interface OAuthConsumer<T> {

    void accept(T t, String authProvider);

}

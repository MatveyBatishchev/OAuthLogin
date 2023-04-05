package com.example.oauthlogin.config.oAuth;

@FunctionalInterface
public interface OAuthConsumer<T> {

    void accept(T t, String authProvider);

}

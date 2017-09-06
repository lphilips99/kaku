package com.stringauto.security.jwt;


public interface TokenExtractor {
    public String extract(String payload);

}

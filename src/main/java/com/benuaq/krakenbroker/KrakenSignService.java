package com.benuaq.krakenbroker;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class KrakenSignService {

    private final String PRIVATE_KEY = "Ca/L5dLjGTJlVdAKEbrYhIjKwyNF3q8zX+dxrA3+n4K2mDcysqLVpxvmiPHeIfMhiD+E9j71wCdpzHZvQldP0Q==";
    
    @SneakyThrows
    public String sign(String uri, String nounce, String data) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return "";
    }
}

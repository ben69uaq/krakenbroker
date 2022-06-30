package com.benuaq.krakenbroker;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.SneakyThrows;

/**
 * HMAC-SHA512 of (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key
 */
public class KrakenSignService {

    private final Mac mac; // Ca/L5dLjGTJlVdAKEbrYhIjKwyNF3q8zX+dxrA3+n4K2mDcysqLVpxvmiPHeIfMhiD+E9j71wCdpzHZvQldP0Q==

    @SneakyThrows
    public KrakenSignService(String privateKey) {
        byte[] decodedPrivateKey = decode(privateKey);
        SecretKeySpec secretKey = new SecretKeySpec(decodedPrivateKey, "HmacSHA512");
        mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKey);
    }

    public String sign(String uri, String nonce, String data) {
        byte[] hmac = hmac(uri, sha256(nonce + data));
        return encode(hmac);
    }

    private byte[] hmac(String uri, byte[] sha256) {
        mac.update(uri.getBytes());
        mac.update(sha256);
        return mac.doFinal();
    }

    @SneakyThrows
    private static byte[] sha256(String input) {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        return md.digest();
    }

    private static byte[] decode(String input) {
        final byte[] inputByte = input.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(inputByte);
    }

    private static String encode(byte[] input) {
        final byte[] outputByte = Base64.getEncoder().encode(input);
        return new String(outputByte, StandardCharsets.UTF_8);
    }
}

package com.benuaq.krakenbroker;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class KrakenClient {

    private final String BASE_URL = "https://api.kraken.com";
    
    private final String publicKey;
    private final WebClient client;
    private final KrakenSignService krakenSignService;
    
    public KrakenClient(String publicKey, KrakenSignService krakenSignService) {
        this.publicKey = publicKey;
        this.krakenSignService = krakenSignService;
        this.client = WebClient.builder().baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0")
            .build();
    }

    public Mono<String> getStatus() {
        String uri = "/0/public/SystemStatus";
        return this.client.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getBalance() {
        String nonce = generateNonce();
        String uri = "/0/private/Balance";
        return this.client.post()
            .uri(uri)
            .header("API-Key", this.publicKey)
            .header("API-Sign", this.krakenSignService.sign(uri, nonce, "nonce=" + nonce))
            .body(fromFormData("nonce", nonce))
            .retrieve()
            .bodyToMono(String.class);
    }

    private static String generateNonce() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

}

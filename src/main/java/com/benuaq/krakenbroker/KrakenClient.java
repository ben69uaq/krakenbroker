package com.benuaq.krakenbroker;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KrakenClient {

    private final String BASE_URL = "https://api.kraken.com";
    private final String API_KEY = "KCgpM3akRVTx1ZWxna57y2hdv6Se+gScsSdNLMTeoWxyVfyvqopAw/pb";
    
    private final WebClient client;
    private final KrakenSignService krakenSignService;

    private AtomicInteger atomicNounce = new AtomicInteger(0);
    
    public KrakenClient(final KrakenSignService krakenSignService) {
        this.krakenSignService = krakenSignService;
        this.client = WebClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0")
        .build();
    }

    public Mono<String> getStatus() {
        return this.client.get()
            .uri("/SystemStatus")
            .retrieve()
            .bodyToMono(String.class);
    }

    public Mono<String> getBalance() {
        String nounceValue = Integer.toString(atomicNounce.incrementAndGet());
        String uri = "/0/private/Balance";
        return this.client.post()
            .uri(uri)
            .header("API-Key", API_KEY)
            .header("API-Sign", krakenSignService.sign(uri, nounceValue, "nonce=" + nounceValue))
            .bodyValue(fromFormData("nounce", nounceValue))
            .retrieve()
            .bodyToMono(String.class);
    }

}

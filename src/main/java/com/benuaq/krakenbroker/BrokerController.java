package com.benuaq.krakenbroker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class BrokerController {

    private final KrakenClient client;

    @GetMapping("/status")
    public Mono<String> getStatus() {
        return client.getStatus();
    }

    @GetMapping("/balance")
    public Mono<String> getBalance() {
        return client.getBalance();
    }
}

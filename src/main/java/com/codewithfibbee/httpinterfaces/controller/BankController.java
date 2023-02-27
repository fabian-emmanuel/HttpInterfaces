package com.codewithfibbee.httpinterfaces.controller;

import com.codewithfibbee.httpinterfaces.payloads.BankTransferDto;
import com.codewithfibbee.httpinterfaces.payloads.BaseResponse;
import com.codewithfibbee.httpinterfaces.payloads.ListBanksResponse;
import com.codewithfibbee.httpinterfaces.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Value
public class BankController {

    BankService service;
    final String DEFAULT_PROVIDER = "flutterwave";


    @GetMapping("/banks")
    public List<ListBanksResponse> listBanks(@RequestParam(name = "provider", defaultValue = DEFAULT_PROVIDER) String provider) {
        return service.fetchBanks(provider);
    }

    @PostMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public Mono<BaseResponse> transferFunds(@RequestBody BankTransferDto dto,
                                            @RequestParam(name = "provider", defaultValue = DEFAULT_PROVIDER) String provider) throws InterruptedException {
        return service.transferFunds(dto, provider);
    }

    @GetMapping(path = "/trx/{reference}", produces = "application/json")
    public BaseResponse getTransactionStatus(@PathVariable String reference,
                                             @RequestParam(name = "provider", defaultValue = DEFAULT_PROVIDER) String provider) {
        return service.getTransactionStatus(reference);
    }
}

package com.codewithfibbee.httpinterfaces.service;

import com.codewithfibbee.httpinterfaces.client.FlutterWaveClient;
import com.codewithfibbee.httpinterfaces.client.PayStackClient;
import com.codewithfibbee.httpinterfaces.payloads.*;
import com.codewithfibbee.httpinterfaces.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.micrometer.common.util.StringUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.codewithfibbee.httpinterfaces.utils.Util.generateUniqueRef;

@Service
@Value
@Slf4j
public class BankServiceImpl implements BankService {
    FlutterWaveClient flutterWave;
    PayStackClient payStack;
    Gson gson;

    @Override
    public List<ListBanksResponse> fetchBanks(String provider) {
        return switch (provider.toLowerCase()) {
            case "paystack" -> getListBanksResponses(payStack.listBanks());
            case "flutterwave" -> getListBanksResponses(flutterWave.listBanks());
            default -> throw new IllegalArgumentException("Invalid provider");
        };
    }

    @Override
    public Mono<BaseResponse> transferFunds(BankTransferDto dto, String provider) throws InterruptedException {

        Flux<BaseResponse> responseFlux;

        switch (provider.toLowerCase()) {
            case "flutterwave" -> {
                FlwTransferRequest req = buildFlwTransferRequest(dto);
                responseFlux = flutterWave.bankTransfer(req);
            }
            case "paystack" -> {
                PaystackTransferRequest req = buildPayStackTransferRequest(dto, getRecipientCode(dto));
                log.info("PayStack Transfer req: {}", req);
                responseFlux = payStack.transferFunds(req);
            }
            default -> throw new IllegalArgumentException("Invalid provider");
        }

        return responseFlux.doOnNext(res -> log.info("res: {}", res))
                .next();
    }

    private String getRecipientCode(BankTransferDto dto) {

        PaystackTransferRecipientRequest recipientRequest = PaystackTransferRecipientRequest.builder()
                .accountNumber(dto.getBeneficiaryAccountNumber())
                .bankCode(dto.getBeneficiaryBankCode())
                .type("nuban")
                .build();

        Object recipient = payStack.createTransferRecipient(recipientRequest);

        var data = gson.fromJson(gson.toJson(recipient), JsonObject.class).get("data");

        return data.getAsJsonObject().get("recipient_code").getAsString();
    }

    @Override
    public BaseResponse getTransactionStatus(String reference) {
        return flutterWave.getBankTransferStatus(reference).block(Duration.ofSeconds(10));
    }

    private List<ListBanksResponse> getListBanksResponses(BaseResponse response) {
        return gson.fromJson(gson.toJson(response.getData()), new TypeToken<List<ListBanksResponse>>() {
        }.getType());
    }

    private FlwTransferRequest buildFlwTransferRequest(BankTransferDto dto) {
        return FlwTransferRequest.builder()
                .amount(dto.getAmount())
                .accountNumber(dto.getBeneficiaryAccountNumber())
                .accountBank(dto.getBeneficiaryBankCode())
                .currency(dto.getCurrencyCode())
                .narration(dto.getNarration())
                .reference(StringUtils.isBlank(dto.getTransactionReference()) ? generateUniqueRef() : dto.getTransactionReference())
                .build();
    }

    private PaystackTransferRequest buildPayStackTransferRequest(BankTransferDto dto, String recipientCode) {
        return PaystackTransferRequest.builder()
                .source("balance")
                .amount(dto.getAmount())
                .recipient(recipientCode)
                .reason(dto.getNarration())
                .reference(StringUtils.isBlank(dto.getTransactionReference()) ? Util.generateUniqueRef() : dto.getTransactionReference())
                .build();
    }
}

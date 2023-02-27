package com.codewithfibbee.httpinterfaces.service;

import com.codewithfibbee.httpinterfaces.payloads.BankTransferDto;
import com.codewithfibbee.httpinterfaces.payloads.BaseResponse;
import com.codewithfibbee.httpinterfaces.payloads.ListBanksResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BankService {
    List<ListBanksResponse> fetchBanks(String provider);

    Mono<BaseResponse> transferFunds(BankTransferDto dto, String provider) throws InterruptedException;

    BaseResponse getTransactionStatus(String reference);
}

package com.codewithfibbee.httpinterfaces.client;

import com.codewithfibbee.httpinterfaces.payloads.BaseResponse;
import com.codewithfibbee.httpinterfaces.payloads.FlwTransferRequest;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.codewithfibbee.httpinterfaces.constants.Api.*;

@HttpExchange
public interface FlutterWaveClient {

    @GetExchange(FLW_LIST_BANKS_URI)
    BaseResponse listBanks();

    @PostExchange(value = FLW_TRANSFER_URI, accept = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    Flux<BaseResponse> bankTransfer(@RequestBody FlwTransferRequest bankTransferDto);

    @GetExchange(value=FLW_VERIFY_TRANSACTION_STATUS_URI)
    Mono<BaseResponse> getBankTransferStatus(@RequestParam(name = "tx_ref") String reference);

}

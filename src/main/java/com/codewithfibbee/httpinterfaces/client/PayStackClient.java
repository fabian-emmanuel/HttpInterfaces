package com.codewithfibbee.httpinterfaces.client;

import com.codewithfibbee.httpinterfaces.constants.Api;
import com.codewithfibbee.httpinterfaces.payloads.BaseResponse;
import com.codewithfibbee.httpinterfaces.payloads.PaystackTransferRecipientRequest;
import com.codewithfibbee.httpinterfaces.payloads.PaystackTransferRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;

@HttpExchange
public interface PayStackClient {
    @GetExchange(value = Api.PSTK_LIST_BANKS_URI)
    BaseResponse<Object> listBanks();

    @PostExchange(value = Api.PSTK_TRANSFER_RECIPIENT_URI)
    Object createTransferRecipient(@RequestBody PaystackTransferRecipientRequest request);

    @PostExchange(value = Api.PSTK_TRANSFER_URI)
    Flux<BaseResponse> transferFunds(@RequestBody PaystackTransferRequest request);

}

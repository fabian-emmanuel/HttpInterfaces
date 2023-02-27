package com.codewithfibbee.httpinterfaces.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PaystackTransferRequest {
    @JsonProperty String source;
    @JsonProperty String reason;
    @JsonProperty BigDecimal amount;
    @JsonProperty String recipient;
    @JsonProperty String reference;
}

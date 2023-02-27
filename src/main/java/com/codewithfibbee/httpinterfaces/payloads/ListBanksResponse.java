package com.codewithfibbee.httpinterfaces.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ListBanksResponse {
    String code;
    @JsonProperty("bankName")
    String name;
    @JsonProperty("longCode")
    String longcode;
}

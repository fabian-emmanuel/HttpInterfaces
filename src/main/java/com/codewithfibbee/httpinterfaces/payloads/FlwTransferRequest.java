package com.codewithfibbee.httpinterfaces.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class FlwTransferRequest {
    /*
    {
    "account_bank": "FMM",
    "account_number": "676064566",
    "amount": 15000,
    "narration": "Test francophone transfer",
    "currency": "XAF",
    "beneficiary_name": "Cornelius Ashley-Osuzoka",
    "reference": "Sample_PMCK",
    "debit_currency": "XAF",
    "meta": {
        "first_name": "Cornelius",
        "last_name": "Ashley-Osuzoka",
        "email": "user@gmail.com",
        "mobile_number": "676064566",
        "recipient_address": "Immueble CiSo, Boulevard de la liberte, Akwa Douala"
    }
}
     */
    @JsonProperty("account_bank")
    String  accountBank;
    @JsonProperty("account_number")
    String accountNumber;
    BigDecimal amount;
    String narration;
    String currency;
    @JsonProperty("beneficiary_name")
    String beneficiaryName;
    String reference;
//    @JsonProperty("callback_url")
//    String callbackUrl;
    @JsonProperty("debit_currency")
    String  debitCurrency;
}

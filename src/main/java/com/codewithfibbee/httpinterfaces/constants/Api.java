package com.codewithfibbee.httpinterfaces.constants;

public class Api {
    public static final String FLW_BASE_URL = "https://api.flutterwave.com/v3";
    public static final String FLW_LIST_BANKS_URI = "/banks/NG";
    public static final String FLW_TRANSFER_URI = "/transfers";
    public static final String FLW_VERIFY_TRANSACTION_STATUS_URI = "/transactions/verify_by_reference";
    public static final String FLW_VALIDATE_BANK_URI = "/accounts/resolve";


    //PayStack
    public static final String PSTK_BASE_URI = "https://api.paystack.co";
    public static final String PSTK_VALIDATE_BANK_URI = "/bank/resolve";
    public static final String PSTK_LIST_BANKS_URI = "/bank";
    public static final String PSTK_TRANSFER_URI = "/transfer";
    public static final String PSTK_TRANSFER_RECIPIENT_URI = "/transferrecipient";
    public static final String PSTK_VERIFY_TRANSACTION_STATUS_URI = "/transaction/verify/";
}

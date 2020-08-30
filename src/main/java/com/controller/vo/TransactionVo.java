package com.controller.vo;

import lombok.Data;

@Data
public class TransactionVo {

    private int amount;

    private String transactionName;

    private int creator;

    private String callbackUrl;

    private String fromSystem;//order systme
}

package com.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionVo {

    private int amount;

    private String transactionName;

    private int creator;

    private String callbackUrl;

    @JsonProperty("from_system")
    private String fromSystem;
}

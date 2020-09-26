package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PaymentTransaction {

    public static final String STATUS_NEW = "new";

    @TableId
    private int id;
    private String unid;
    private String fromSystem;
    private String TransactionName;

    private int amount;

    private String status;

    private String callbackUrl;

    private int notifyCount;

    private String createdAt;

    private String updatedAt;

}

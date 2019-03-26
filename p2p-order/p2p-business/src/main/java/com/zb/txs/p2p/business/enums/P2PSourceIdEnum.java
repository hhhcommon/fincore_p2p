package com.zb.txs.p2p.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum P2PSourceIdEnum {

    MSD("马上贷"),
    ZD("资鼎"),
    TXS("唐小僧"),
    ;

    private final String desc;
}
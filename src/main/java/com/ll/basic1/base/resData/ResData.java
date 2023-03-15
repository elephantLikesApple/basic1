package com.ll.basic1.base.resData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResData {
    private final String resultCode;
    private final String msg;

    public static ResData of(String resultCode, String msg) {
        return new ResData(resultCode, msg);
    }
}

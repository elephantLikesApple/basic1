package com.ll.basic1.base.resData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResData {
    private final String resultCode;
    private final String msg;
    private final Object data;

    public static ResData of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static ResData of(String resultCode, String msg, Object data) {
        return new ResData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        return this.getResultCode().startsWith("S-");
    }
}

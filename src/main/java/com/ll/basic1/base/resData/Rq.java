package com.ll.basic1.base.resData;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Setter
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse res;

    public void setCookie(String cookieName, Object cookieValue) {
        this.res.addCookie(new Cookie(cookieName, cookieValue+""));
    }

    public String getCookie(String cookieName, String defaultValue) {
        if (req.getCookies() == null) return defaultValue;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(defaultValue);
    }

    public Long getCookieAsLong(String cookieName, long defualtValue) {
        String value = getCookie(cookieName, null);

        if (value == null) return defualtValue;
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return defualtValue;
        }
    }

    public boolean removeCookie(String cookieName) {
        if(this.req.getCookies() != null) {
            Arrays.stream(this.req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        this.res.addCookie(cookie);
                    });
            return Arrays.stream(this.req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .count() > 0;
        }
        return false;
    }
}

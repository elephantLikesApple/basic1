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

    public String getCookie(String cookieName, String defualtValue) {
        String targetCookie = defualtValue;
        if(this.req.getCookies() != null) {
            targetCookie = Arrays.stream(this.req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(defualtValue);
        }
        return targetCookie;
    }

    public long getCookieAsLong(String cookieName, long defualtValue) {
        long targetCookie = defualtValue;
        if(this.req.getCookies() != null) {
            targetCookie = Arrays.stream(this.req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .map(Cookie::getValue)
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(defualtValue);
        }
        return targetCookie;
    }

    public void removeCookie(String cookieName) {
        if(this.req.getCookies() != null) {
            Arrays.stream(this.req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        this.res.addCookie(cookie);
                    });
        }
    }
}

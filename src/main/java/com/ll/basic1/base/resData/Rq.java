package com.ll.basic1.base.resData;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Setter
@Component
@RequestScope
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
            Cookie cookie = Arrays.stream(req.getCookies())
                    .filter(c ->c.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);

            if(cookie != null) {
                cookie.setMaxAge(0);
                res.addCookie(cookie);
                return true;
            }
        }
        return false;
    }
}

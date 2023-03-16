package com.ll.basic1.base.resData;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Enumeration;

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
    public void setSession(String name, Object value) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);
    }

    public long getSessionAsLong(String name, long defaultValue) {
        try {
            long value = (long) req.getSession().getAttribute(name);
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private String getSessionAsStr(String name, String defaultValue) {
        try {
            String value = (String) req.getSession().getAttribute(name);
            if (value == null) return defaultValue;
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean removeSession(String name) {
        HttpSession session = req.getSession();

        if (session.getAttribute(name) == null) return false;

        session.removeAttribute(name);
        return true;
    }

    // 디버깅용 함수
    public String getSessionDebugContents() {
        HttpSession session = req.getSession();
        StringBuilder sb = new StringBuilder("Session content:\n");

        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sb.append(String.format("%s: %s\n", attributeName, attributeValue));
        }

        return sb.toString();
    }
}

package com.zeer.demo.service;

import com.zeer.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Service
public class TokenService {
    @Autowired
    private JwtUtil jwtUtil;
    public boolean isHave(HttpSession session, HttpServletRequest request) {
//        Object loginUser = session.getAttribute("loginUser");
//        if (loginUser != null) {
//            return true;
//        } else {
//            //回到登录页面
//            return false;
//        }
        Object token = session.getAttribute(jwtUtil.getHeader());
        System.out.println(token);
        //判断Authorization是否为空
        if (StringUtils.isEmpty(token)) {
            System.out.println("1");
            return false;
        }
        // HTTP请求头中TOKEN解析出的用户信息
        Claims claims = null;
        try {
            claims = jwtUtil.parseToken(token+"");
        } catch (Exception e) {
            System.out.println("4");
            return false;

        }
        if (claims == null) {
            System.out.println("2");

            return false;
        }

        //token正常，获取用户信息，比如这里的subject存的是用户id
        String subject = claims.getSubject();
        //将用户信息存入request，以便后面处理请求使用
        request.setAttribute("subject", subject);

        //校验是否过期
        boolean flag = jwtUtil.isExpired(claims.getExpiration());
        if (flag) {
            System.out.println("3");

            return false;
        }

        return true;

    }
}

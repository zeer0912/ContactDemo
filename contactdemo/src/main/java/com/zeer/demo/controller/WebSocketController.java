package com.zeer.demo.controller;

import com.zeer.demo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    TokenService tokenService;
    @RequestMapping("/chat/{fromUser}")
    public String webSocket(@PathVariable String fromUser,
                            HttpSession session, HttpServletRequest request,Model model){
        if(tokenService.isHave(session,request))
        try {
            System.out.println(fromUser);

            log.info("跳转到websocket的页面上");
            model.addAttribute("fromUser", fromUser);
            return "chat";
        }catch (Exception e) {
            log.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            model.addAttribute("msg", "跳转到websocket的页面上发生异常");
            return "chat";
        }
        else {
            model.addAttribute("msg","请重新登录");
            return "redirect:login";
        }
    }

}

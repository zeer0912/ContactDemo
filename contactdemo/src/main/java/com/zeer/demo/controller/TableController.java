package com.zeer.demo.controller;

import com.zeer.demo.bean.User;
import com.zeer.demo.error.UserError;
import com.zeer.demo.service.TokenService;
import com.zeer.demo.service.mpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TableController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserError userError;
    @Autowired
    TokenService tokenService;
    @GetMapping(value = {"/friend"})
    public String friendPage(HttpSession session, HttpServletRequest request, Model model){

        if(tokenService.isHave(session,request))
        {
            List<User> users= userService.getAll();
            model.addAttribute("users",users);
//            users.indexOf(new User())
            return "friend";
        }
        else {
            model.addAttribute("msg","请重新登录");
            return "redirect:login";
        }
    }


}

package com.zeer.demo.controller;


import com.zeer.demo.bean.User;
import com.zeer.demo.error.UserError;
import com.zeer.demo.service.TokenService;
import com.zeer.demo.service.mpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserError userError;
    @Autowired
    TokenService tokenService;
    @GetMapping(value = {"/changePa"})
    public String changePaPage(HttpSession session,HttpServletRequest request,Model model){

        if(tokenService.isHave(session,request))
            return "changePa";
        else {
            model.addAttribute("msg","请重新登录");
            return "login";
        }
    }
    @GetMapping(value = {"/changePaByText"})
    public String changePaByTextPage(Model model){
        model.addAttribute("form", "form2");


        return "changePaByText";

    }

    @PostMapping("/changePa")
    public String changePa(
            String password,
            String newPassword, HttpSession session, Model model) {
        User query=(User) session.getAttribute("user");
        log.info("成功查询");
        System.out.println(query);
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)) {
            model.addAttribute("msg", "密码不能为空");
            return "changePa";
        } else if (query == null) {

            model.addAttribute("msg", "旧密码错误");
            return "changePa";

        } else if (userError.rexCheckPassword(newPassword) == false) {
            model.addAttribute("msg", "密码规定为 8-20 位的字母、数字、字符");
            return "changePa";

        } else {
            userService.upDatePa(query.getId(), newPassword);
            return "redirect:chat/"+((User)session.getAttribute("user")).getUsername();
        }


    }

    @PostMapping(value = {"/ChangeSendText"})
    public String  ChangeSendText(Model model,String phoneNumber){
        if(!userError.rexCheckNumber(phoneNumber))
        {
            model.addAttribute("msg","手机号格式有误");
            model.addAttribute("phone", ""+phoneNumber);
            model.addAttribute("form", "form2");
            return "loginByText";
        }
        model.addAttribute("msg", "验证码获取成功");
        model.addAttribute("phone", ""+phoneNumber);
        model.addAttribute("form", "form1");
        return "changePaByText";
    }


    @PostMapping("/changePaByText")
    public String changePaByText(String phoneNumber,
                                        String newPassword, String text,Model model) {

        System.out.println(phoneNumber);
        if(!userError.rexCheckNumber(phoneNumber))
        {
            model.addAttribute("msg","手机号格式有误");
            model.addAttribute("form", "form2");

            return "changePaByText";
        }
        User query = userService.getByPhoneNumber(phoneNumber);
        log.info("成功查询");
        System.out.println(query);
        if (query == null) {

            model.addAttribute("msg", "手机号未注册");
            model.addAttribute("form", "form2");

            return "changePaByText";

        } else if(!text.equals("123456"))
        {
            model.addAttribute("msg","验证码错误");
            model.addAttribute("form", "form2");

            return "changePaByText";
        } else if (userError.rexCheckPassword(newPassword) == false) {
            model.addAttribute("msg", "密码规定为 8-20 位的字母、数字、字符");
            model.addAttribute("form", "form2");

            return "changePaByText";

        } else {
            userService.upDatePa(query.getId(), newPassword);
            return "redirect:login";
        }

    }

    @PostMapping("/selectAll")
    public String selectAll(String name, String object, HttpSession session,Model model) {
        List<User> list=new LinkedList<>();
        if(object.isEmpty()){
            list= userService.getAll();
            model.addAttribute("users",list);
            return "friend";
        }
            switch (name){
            case "phoneNumber":list.add(userService.getByPhoneNumber(object));break;
            case "username":list= userService.getByUsername(object);break;
            case "studentID":list= userService.getByStudentID(object);break;
        }
        if(list.isEmpty()) model.addAttribute("msg","找不到符合条件的用户");
        else {
            model.addAttribute("users",list);
        }

        return "friend";

    }


}

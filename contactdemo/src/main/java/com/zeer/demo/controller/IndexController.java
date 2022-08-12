package com.zeer.demo.controller;



import com.zeer.demo.bean.User;
import com.zeer.demo.error.UserError;
import com.zeer.demo.service.TokenService;
import com.zeer.demo.service.mpl.UserServiceImpl;

import com.zeer.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
@Controller
@Validated
public class IndexController {

    @Autowired
    TokenService tokenService;
    @GetMapping(value = {"/","/login"})
    public String loginPage(){

        return "login";
    }
    @GetMapping(value = {"loginByText"})
    public String loginByTextPage(Model model){
        model.addAttribute("form", "form2");

        return "loginByText";
    }
    @GetMapping(value = {"/register"})
    public String registerPage(Model model){
        model.addAttribute("form", "form2");

        return "register";
    }


    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtUtil jwtUtil;



    @Autowired
    UserError userError;

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model){ //RedirectAttributes

        User query=null;
        if(StringUtils.isEmpty(user.getPhoneNumber())||StringUtils.isEmpty(user.getPassword()))
        {
            model.addAttribute("msg","手机号和密码不能为空");
            return "login";
        }
        if(!userError.rexCheckNumber(user.getPhoneNumber()))
        {
            model.addAttribute("msg","手机号格式有误");
            return "login";
        }
        if(!userError.rexCheckPassword(user.getPassword()))
        {
            model.addAttribute("msg","密码格式有误");
            return "login";
        }
        query=userService.getByPP(user.getPhoneNumber(), user.getPassword());
//        System.out.println(query);
        if(query!=null){
            String token = jwtUtil.createToken(query.getId()+"");
            System.out.println(token);
            session.setAttribute(jwtUtil.getHeader(),token);
            session.setAttribute("user",query);
            return "redirect:/chat/"+query.getUsername();

            //登录成功重定向到main.html;  重定向防止表单重复提交

        }else {
            model.addAttribute("msg","账号密码错误");
            return "login";


        }


    }

    @PostMapping("/loginByText")

    public String loginByText(String phoneNumber, String text, HttpSession session, Model model){
        User query=null;
        if(!userError.rexCheckNumber(phoneNumber))
        {
            model.addAttribute("msg","手机号格式有误");
            return "loginByText";
        }
        Map<String,Object> map=new HashMap<>();
        query=userService.getByPhoneNumber(phoneNumber);
//        System.out.println(query);
        if(query==null){
            model.addAttribute("msg","手机号未注册");
            model.addAttribute("form", "form2");

        }else if(!text.equals("123456"))
        {
           model.addAttribute("msg","验证码错误");
            model.addAttribute("form", "form2");

        }else {
            String token = jwtUtil.createToken(query.getId()+"");
            System.out.println(token);
            session.setAttribute(jwtUtil.getHeader(),token);
            session.setAttribute("user",query);
//            return "redirect:/chat/"+query.getUsername()+"/"+query.getUsername();
            return "redirect:/chat/"+query.getUsername();

        }
        return "loginByText";


    }


    @PostMapping(value = {"/RegisterSendText"})
    public String  RegisterSendText(Model model,String phoneNumber){
        if(!userError.rexCheckNumber(phoneNumber))
        {
            model.addAttribute("phone", ""+phoneNumber);
            model.addAttribute("msg","手机号格式有误");
            model.addAttribute("form", "form2");
            return "register";
        }
        System.out.println(phoneNumber);
        model.addAttribute("msg", "登录验证码获取成功");
        model.addAttribute("phone", ""+phoneNumber);
        model.addAttribute("form", "form1");


        return "register";
    }


    @PostMapping(value = {"/LoginSendText"})
    public String LoginSendText(Model model,String phoneNumber){
        if(!userError.rexCheckNumber(phoneNumber))
        {
            model.addAttribute("msg","手机号格式有误");
            model.addAttribute("phone", ""+phoneNumber);
            model.addAttribute("form", "form2");
            return "loginByText";
        }
        System.out.println(phoneNumber);
        model.addAttribute("msg", "登录验证码获取成功");
        model.addAttribute("phone", ""+phoneNumber);
        model.addAttribute("form", "form1");


        return "loginByText";
    }


    @PostMapping("/register")
    public String register(User user, String text,Model model){

        if(!userError.rexCheckNumber(user.getPhoneNumber()))
        {
            model.addAttribute("msg","手机号格式有误");
            model.addAttribute("form", "form2");

            return "register";
        }
        if(!userError.rexCheckPassword(user.getPassword()))
        {
            model.addAttribute("msg","密码格式有误");

            model.addAttribute("form", "form2");

            return "register";
        }
        User query=userService.getByPhoneNumber(user.getPhoneNumber());
        if(query!=null)
        {
            model.addAttribute("msg","该手机号已被注册");
            model.addAttribute("form", "form2");

            return "register";


        }else if(!text.equals("123456"))
        {
            model.addAttribute("msg","验证码错误");
            model.addAttribute("form", "form2");

            return "register";

        }else {
            userService.insert(user);
            model.addAttribute("msg", "注册成功");
        }
        return "login";
    }




}

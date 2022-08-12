package com.zeer.demo.error;


import org.springframework.stereotype.Service;

@Service
public class UserError {
    public boolean rexCheckPassword(String input) {
        // 6-20 位，字母、数字、字符
        String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){8,20}$";
        return input.matches(regStr);
    }

    public  boolean rexCheckNumber(String input) {
        // 手机号格式不符
        String regStr ="^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        return input.matches(regStr);
    }
}

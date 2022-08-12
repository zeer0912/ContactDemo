package com.zeer.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zeer.demo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@ServerEndpoint("/chat/{fromUser}")
public class WebSocketService {
    public static int onlineNumber=0;
    private static Set<String> onlineSet=new HashSet<>();//在线

    //    名单为key service对象为value
//    ConcurrentHashMap是线程安全并且高效的HashMap(不安全情况：
//    key已经存在，需要修改HashEntry对应的value；
//    key不存在，在HashEntry中做插入。）
    private static Map< Set<String>,WebSocketService> clients=new ConcurrentHashMap<>();

    private Session session;//websocket会话
    private String fromUser;
//    会话对象
private Set<String> set=new HashSet<>();


    @OnOpen
    public void onOpen(@PathParam("fromUser")  String fromUser,
                                   Session session){
//        String fromUser=((User)httpSession.getAttribute("user")).getUsername();
        set.add(fromUser);
        onlineSet.add(fromUser);
        onlineNumber++;
        log.info("id:"+session.getId()+" "+fromUser);
        this.fromUser=fromUser;
        this.session=session;
        log.info("在线"+onlineNumber);
        try {
            //上线对所以用户的通知
            Map<String,Object> map1=new HashMap<>();
            //messageType 1上线 2下线 3在线名单 4普通消息
            map1.put("messageType",1);
            map1.put("fromUser",fromUser);
            sendMessageAll(JSON.toJSONString(map1));

            clients.put(set,this);//排除传递信息给自己

//        给自己发在线人数的消息
            Map<String,Object> map2=new HashMap<>();
            map2.put("messageType",3);
            Set<String> set=onlineSet;//取一方即可
            log.info("online:"+set);
            map2.put("onlineUsers",set);
            sendMessageToSelf(JSON.toJSONString(map2));
        }catch (IOException e){
            log.info(fromUser+"上线错误");
        }
    }
    @OnError
    public void onError(Throwable error) {
        log.info("服务端发生了错误" + error.getMessage());
    }


    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        clients.remove(set);
        onlineSet.remove(fromUser);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 2);
            map1.put("onlineUsers", clients.keySet());
            map1.put("fromUser", fromUser);
            sendMessageAll(JSON.toJSONString(map1));
        } catch (IOException e) {
            log.info(fromUser + "下线的时候通知所有人发生了错误");
        }
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            log.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromusername = jsonObject.getString("fromUser");
            String tousername = jsonObject.getString("to");
            String time = jsonObject.getString("time");
            String file = jsonObject.getString("file");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap<>();
            map1.put("messageType", 4);
            map1.put("textMessage", textMessage);
            map1.put("fromusername", fromusername);
            map1.put("time",time);
            map1.put("file",file);
            if (tousername.equals("All")) {
                map1.put("tousername", "所有人");
                sendMessageAll(JSON.toJSONString(map1));
            } else {
//                List<String> list=map.get(fromusername);
//                list.add(tousername);
                map1.put("tousername", tousername);
                sendMessageTo(JSON.toJSONString(map1), fromUser, tousername);
            }
        } catch (Exception e) {
            log.info("发生了错误了"+e.getMessage());
        }
    }


        public void sendMessageTo(String message,String fromUser,String toUser)throws IOException{
           log.info("111");
            for (WebSocketService item:clients.values()){
            if(item.fromUser.equals(toUser)){
                log.info("send:",fromUser);
                log.info("receive",toUser);
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }


    public void sendMessageToSelf(String message)throws IOException{
        for (WebSocketService item:clients.values()){
            if(item.set.contains(fromUser)){
                log.info("send:",fromUser);
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }
    public void sendMessageAll(String message)throws IOException{
        for (WebSocketService item:clients.values()){
            item.session.getAsyncRemote().sendText(message);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }//synchronized 对类的锁，是通过该类直接调用加类锁的方法,

}

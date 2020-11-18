package websocket;

import org.springframework.stereotype.Component;
import visitor.Base64Utils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/webSocket/msg")
@Component
public class WebSocketServer {
    public static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] cards = {
            "0|#|张三|#|男|#|汉|#|19920128|#|湖南长沙鑫远杰座|#|432135199201287789|#|长沙公安局|#|20181001|#|20401001|#|20|#|预留|#|3.0|#|1|#|",
            "0|#|李四|#|男|#|回|#|19901010|#|湖南株洲|#|433135199010101256|#|醴陵公安局|#|20181001|#|20401001|#|20|#|预留|#|3.0|#|1|#|",
            "0|#|王五|#|女|#|藏|#|19870106|#|湖南湘西花垣十八洞村二组|#|433122198701063578|#|花垣公安局|#|20181001|#|20401001|#|20|#|预留|#|3.0|#|1|#|",
            "0|#|赵钱|#|女|#|藏|#|19850508|#|湖南郴州沙湾村二组|#|423677198505083578|#|郴州公安局|#|20181001|#|20401001|#|20|#|预留|#|3.0|#|1|#|"
    };

    public String getIdCardInfo(){
        int index = (int)Math.floor(Math.random() * 4);
        String message = cards[index];
        String imageStr = Base64Utils.GetImageStr("D://testFiles/avator" + index + ".png");
        message += imageStr;
        return message;
    }

    @OnOpen
    public void onOpen(Session session) {
        sessionPools.put(session.getId(), session);
        try {
            sendMessage(session, "欢迎" + session.getId() + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Closed sessionID is " + session.getId());
        sessionPools.remove(session.getId());
    }

    public static int time = 1;
    @OnMessage
    public void onMessage(String message) throws IOException {
        time++;
        System.out.println(time + "接收的消息为：" + message);
        String action = message.split("\\|#\\|")[0];
        if("OPEN".equalsIgnoreCase(action)){
            message = "0|#|opendevice success";
        }else if("READIDCARDWITHPHOTO".equalsIgnoreCase(action)){
            if(time%10 == 0){
//                String imageStr = Base64Utils.GetImageStr("D://testFiles/avator1.png");
//                message = "0|#|张三|#|男|#|汉|#|19920128|#|湖南长沙鑫远杰座|#|433135199201287789|#|长沙公安局|#|20181001|#|20401001|#|20|#|预留|#|3.0|#|1|#|"
//                        + imageStr;
                message = getIdCardInfo();
            }else{
                message = "1|#|2";
            }
        }
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
}

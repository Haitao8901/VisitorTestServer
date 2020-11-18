package visitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*", methods={RequestMethod.GET,RequestMethod.POST}, allowedHeaders = "*")
public class QueryController {
    public static final String TRANCODE = "TranCode";
    public static final String ACTION = "ACTION";

    @PostMapping("/queryDevice")
    @ResponseBody
    public JSONObject queryDevice(HttpServletRequest request, @RequestBody JSONObject requetOb){
        System.out.println(requetOb.toString());
        JSONObject jso = JSONObject.parseObject("{trancode:'0001', action:'queryDevice', code:'0000', status:'查询成功', deviceNo: '110022', devicePort:'10086', school: '鑫远实验学校', location:'南大门', remarks:''}");
        System.out.println(requetOb.toString());
        return jso;
    }

    public static Map<String, String > visitStatusMap = new HashMap<>();
    static{
        visitStatusMap.put("0000","您尚未登记身份证");
        visitStatusMap.put("0001","您的信息不完善");
        visitStatusMap.put("0002","您的信息不完善");
        visitStatusMap.put("0003","您的信息不完善");
        visitStatusMap.put("9999","您的信息已经完善");
    }

    @PostMapping("/queryIdCard")
    @ResponseBody
    public JSONObject queryIdCard(HttpServletRequest request, @RequestBody JSONObject requetOb){
        System.out.println(requetOb.toString());

        JSONObject jso = new JSONObject();
        jso.put("trancode","0002");
        jso.put("action","queryChildren");
        jso.put("code","0000");
        jso.put("status","查询成功");

        int random = (int)Math.floor(Math.random() * 5);
        switch(random){
            case 1:
                jso.put("visitStatus", "0001");
                jso.put("visitStatusDesc", visitStatusMap.get("0001"));
                break;
            case 2:
                jso.put("visitStatus", "0002");
                jso.put("visitStatusDesc", visitStatusMap.get("0002"));
                break;
            case 3:
                jso.put("visitStatus", "0003");
                jso.put("visitStatusDesc", visitStatusMap.get("0003"));
                break;
            case 4:
                jso.put("visitStatus", "9999");
                jso.put("visitStatusDesc", visitStatusMap.get("9999"));
                break;
            default:
                jso.put("visitStatus", "0000");
                jso.put("visitStatusDesc", visitStatusMap.get("0000"));
        }
        System.out.println(jso.toString());
        return jso;
    }

    @PostMapping("/queryErWei")
    @ResponseBody
    public JSONObject queryErWei(HttpServletRequest request, @RequestBody JSONObject requetOb){
        System.out.println(requetOb.toString());

        JSONObject jso = new JSONObject();
        jso.put("trancode","0007");
        jso.put("action","queryErWei");
        jso.put("code","0000");
        jso.put("status","查询成功");
        int random = (int)Math.floor(Math.random() * 5);
        switch(random){
            case 1:
                jso.put("type", "0001");
                jso.put("typeDescription", visitStatusMap.get("0001"));
                jso.put("image", Base64Utils.GetImageStr("D://testFiles/erwei0000.png"));
                break;
            case 2:
                jso.put("type", "0002");
                jso.put("typeDescription", visitStatusMap.get("0002"));
                jso.put("image", Base64Utils.GetImageStr("D://testFiles/erwei0000.png"));
                break;
            case 3:
                jso.put("type", "0003");
                jso.put("typeDescription", visitStatusMap.get("0003"));
                jso.put("image", Base64Utils.GetImageStr("D://testFiles/erwei0000.png"));
                break;
            case 4:
                jso.put("type", "9999");
                jso.put("typeDescription", visitStatusMap.get("9999"));
                jso.put("image", Base64Utils.GetImageStr("D://testFiles/erwei9999.png"));
                break;
            default:
                jso.put("type", "0000");
                jso.put("typeDescription", visitStatusMap.get("0000"));
                jso.put("image", Base64Utils.GetImageStr("D://testFiles/erwei0000.png"));
        }
        jso.put("remarks", "this is remarks");
        System.out.println(jso.toString());
        return jso;
    }

    public static boolean canReturn = false;
    public static boolean canReturnWaitingNow(){
//        int cnt = (int)Math.floor(Math.random() * 2000);
//        return cnt%33 == 0;
        return false;
    }

    @GetMapping("/stopWaiting")
    public String stopWaiting(HttpServletRequest request, @PathParam("start") String start){
        if("1".equalsIgnoreCase(start)){
            canReturn = false;
            return "ok, server can not return waiting Result now.";
        }

        canReturn = true;
        return "ok, server can return waiting Result now.";
    }

    public static int cnt = 0;
    public static int[] TYPE = {1,2};
    public static int[] REASON = {1,2,3};

    @PostMapping("/waitingResult")
    @ResponseBody
    public JSONObject waitingResult(HttpServletRequest request, @RequestBody JSONObject requetOb){
        System.out.println(requetOb.toString());
        cnt++;
        if(!canReturn && cnt != 20){
            JSONObject jso = new JSONObject();
            jso.put("trancode","0002");
            jso.put("action","waitingResult");
            jso.put("code","0002");
            jso.put("status","用户还未完成信息登记");
            jso.put("visitType","");
            jso.put("visitReason","");
            jso.put("children", "");
            jso.put("visitTarget", "");
            jso.put("remark","");
            jso.put("historyVisitRecords", "");
            System.out.println(jso.toString());
            return jso;
        }
        cnt = 0;
        JSONArray childrenArray = new JSONArray();
        for(int i = 0; i < 3; i++){
            if(i == 0){
                JSONObject child = new JSONObject();
                child.put("name", "张三");
                child.put("grade","六年级");
                child.put("class","二班");
                child.put("image", Base64Utils.GetImageStr("D://testFiles/child1.jpg"));
                childrenArray.add(child);
                continue;
            }
            if(i == 1){
                JSONObject child = new JSONObject();
                child.put("name", "李四");
                child.put("grade","七年级");
                child.put("class","八班");
                child.put("image",Base64Utils.GetImageStr("D://testFiles/child2.jpg"));
                childrenArray.add(child);
                continue;
            }
            if(i == 2){
                JSONObject child = new JSONObject();
                child.put("name", "王五");
                child.put("grade","四年级");
                child.put("class","一班");
                child.put("image",Base64Utils.GetImageStr("D://testFiles/child3.jpg"));
                childrenArray.add(child);
                continue;
            }

        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String[] vnames = {"赵四", "刘能", "广坤", "王七"};
        String[] reasons = {"探访", "办事", "其他"};
        String[] names = {"玉田", "永强", "刘英", "小萌"};
        String[] grades = {"六年级", "七年级", "九年级", "三年级"};
        String[] classez = {"二班", "三班", "", ""};
        JSONArray visitorRecordArray = new JSONArray();
        for(int i = 0; i < 1; i++){
            JSONObject record = new JSONObject();
            if(i == 0){
                record.put("visitName",vnames[(int)Math.floor(Math.random() * 4)]);
                record.put("visitTime", sdf.format(new Date()));
                record.put("visitReason",reasons[(int)Math.floor(Math.random() * 3)]);
                record.put("name",names[(int)Math.floor(Math.random() * 4)]);
                record.put("grade",grades[(int)Math.floor(Math.random() * 4)]);
                record.put("class",classez[(int)Math.floor(Math.random() * 4)]);
//                record.put("remarks","备注信息");
                visitorRecordArray.add(record);
                continue;
            }
            if(i == 1){
                record.put("visitName","张三爸爸");
                record.put("visitTime", "202010101558");
                record.put("visitReason","探望");
                record.put("name","张三");
                record.put("grade","六年级");
                record.put("class","二班");
//                record.put("remarks","备注信息");
                visitorRecordArray.add(record);
                continue;
            }
            if(i == 2){
                record.put("visitName","张三爸爸");
                record.put("visitTime", "202010221323");
                record.put("visitReason","探望");
                record.put("name","张三");
                record.put("grade","六年级");
                record.put("class","二班");
//                record.put("remarks","备注信息");
                visitorRecordArray.add(record);
                continue;
            }
            if(i == 3){
                record.put("visitName","湖面家");
                record.put("visitTime", "202011161013");
                record.put("visitReason","办事");
                record.put("name","哈哈哈");
                record.put("grade","教务处");
                record.put("class","");
//                record.put("remarks","备注信息");
                visitorRecordArray.add(record);
                continue;
            }
            if(i == 4){
                record.put("visitName","湖面家");
                record.put("visitTime", "202011161013");
                record.put("visitReason","办事");
                record.put("name","哈哈哈");
                record.put("grade","教务处");
                record.put("class","");
//                record.put("remarks","备注信息");
                visitorRecordArray.add(record);
                continue;
            }
        }

        JSONObject target = new JSONObject();
        target.put("name","张三");
        target.put("grade","六年级");
        target.put("image","二班");


        int type = TYPE[(int)Math.floor(Math.random() * 2)];
        int reason = REASON[(int)Math.floor(Math.random() * 3)];

        JSONObject jso = new JSONObject();
        jso.put("trancode","0002");
        jso.put("action","queryChildren");
        jso.put("code","0000");
        jso.put("status","查询成功");

        if(type == 1){
            jso.put("visitType","家长");
        }else{
            jso.put("visitType","非家长");
        }

        if(reason == 1 ){
            jso.put("visitReason","探访");
        }else if(reason == 2 ){
            jso.put("visitReason","办事");
        }else{
            jso.put("visitReason","其他");
        }

        if(reason == 1){
            jso.put("children", childrenArray);
            jso.put("visitTarget", "");
        }else{
            jso.put("children", "");
            jso.put("visitTarget", target);
        }
        jso.put("remark","备注信息");

        jso.put("historyVisitRecords", visitorRecordArray);
        System.out.println(jso.toString());
        return jso;
    }
}

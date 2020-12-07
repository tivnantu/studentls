package cn.tivnan.studentls.controller;

import cn.tivnan.studentls.bean.Student;
import cn.tivnan.studentls.bean.User;
import cn.tivnan.studentls.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: studentls
 * @description: controller the login&auth ability
 * @author: tivnan
 * @create: 2020-2020/11/30-下午7:51
 * @version: 1.0
 **/
@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;


    private static final String APPSECRET = "e4880f49fecea8a6b9fd49a9e5d4dc50";
    private static final String APPID = "wx07026120b8ca5c85";

    /**
     * 登录接口
     *
     * @param code
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestParam("code") String code) {

        //获取OpenId，然后从数据库读取用户数据

        String openId = getOpenId(code);
        // String openId = code;
        User user = userService.login(openId);

        HashMap<String, Object> map = new HashMap<>();

        if (user == null) {
            //如果未曾这个微信登录过本小程序，也就是说未曾认证过
            //返回如下信息(其中包含openId信息)
            map.put("isAuthenticated", Boolean.FALSE);
            user = new User();
            user.setOpenId(openId);
            map.put("user", user);
        } else {

            //如果用户曾经用这个微信登录过本小程序，数据库就会有保存到用户信息
            //返回如下信息

            map.put("isAuthenticated", Boolean.TRUE);

            if (user instanceof Student) {
                map.put("type", "student");
            } else {
                map.put("type", "teacher");
            }

            map.put("user", user);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Map<String, Object> auth(@RequestBody Map<String, Object> requestMap) {

        /*
         * identity: 认证的身份，student或者teacher
         * id: 认证的身份id
         * openId: 这个微信号的openId
         * */
        String type = (String) requestMap.get("identify");
        Integer id = (Integer) requestMap.get("id");
        String openId = (String) requestMap.get("openId");

        User authUser = userService.auth(type, id, openId);

        HashMap<String, Object> map = new HashMap<>(2);

        map.put("type", type);
        map.put("user", authUser);

        return map;
    }

    public String getOpenId(String code) {

        HashMap<String, String> map = new HashMap<>(3);
        map.put("appid", APPID);
        map.put("secret", APPSECRET);
        map.put("js_code", code);

        // System.out.println("code = " + code);

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";

        String openIdBean = restTemplate.getForObject(url, String.class, map);


        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(openIdBean, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // System.out.println(jsonMap.get("openid"));

        return (String) jsonMap.get("openid");
    }


}

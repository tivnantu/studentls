package cn.tivnan.studentls.controller;

import cn.tivnan.studentls.bean.Student;
import cn.tivnan.studentls.bean.User;
import cn.tivnan.studentls.service.UserService;
import cn.tivnan.studentls.utils.OpenIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        String openId = OpenIdUtil.getOpenId(code);
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


}

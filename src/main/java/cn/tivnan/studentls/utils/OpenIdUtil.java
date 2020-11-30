package cn.tivnan.studentls.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @project: studentls
 * @description: util to get user openid
 * @author: tivnan
 * @create: 2020-2020/11/30-下午7:54
 * @version: 1.0
 **/
public class OpenIdUtil {

    private static final String APPSECRET = "APPSECRET";
    private static final String APPID = "APPID";

    @Autowired
    private RestTemplate restTemplate;

    private static OpenIdUtil openIdUtil;

    @PostConstruct
    public void init() {
        openIdUtil = this;
        openIdUtil.restTemplate = this.restTemplate;
    }


    public static String getOpenId(String code) {

        HashMap<String, String> map = new HashMap<>(4);
        map.put("appid", APPID);
        map.put("secret", APPSECRET);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String url = "https://api.weixin.qq.com/sns/jscode2session";

        OpenIdBean openIdBean = openIdUtil.restTemplate.getForObject(url, OpenIdBean.class, map);

        return openIdBean.getOpenId();
    }

}

class OpenIdBean {
    private String openId;

    private OpenIdBean() {
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

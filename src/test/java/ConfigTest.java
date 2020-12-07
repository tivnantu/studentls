// import com.alibaba.fastjson.JSONObject;
import cn.tivnan.studentls.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: studentls
 * @description: test the spring config
 * @author: tivnan
 * @create: 2020-2020/11/30-上午11:06
 * @version: 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class ConfigTest {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 测试restTemplate
     */
    @Test
    public void restTemplateBeanTest() {
        //
        // HttpHeaders requestHeaders = new HttpHeaders();
        // requestHeaders.add("Content-Type", "application/json");

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("type", "student");
        map.put("id", 1111);

        String url = "http://112.74.95.237:5000/webservice/getUserInfo?type={type}&id={id}";

        User user = restTemplate.getForObject(url, User.class, map);
        System.out.println("user = " + user);

        // System.out.println(restTemplate.getForObject("http://112.74.95.237:5000/webservicegetUserInfo?type=student&id=1111", String.class));

    }

}

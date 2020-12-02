package cn.tivnan.studentls.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class UserServiceTest extends TestCase {


    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testGetUserFromEdu() {

        UserService userService = new UserService();

        System.out.println("userService.getUserFromEdu(\"student\",1111) = " + userService.getUserFromEdu("student", 1111));
    }
}
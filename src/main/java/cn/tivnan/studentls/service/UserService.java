package cn.tivnan.studentls.service;

import cn.tivnan.studentls.bean.*;
import cn.tivnan.studentls.dao.StudentMapper;
import cn.tivnan.studentls.dao.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @project: studentls
 * @description: service for user(student and teacher)
 * @author: tivnan
 * @create: 2020-2020/11/30-下午8:29
 * @version: 1.0
 **/
@Service
public class UserService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    RestTemplate restTemplate;


    /**
     * 用户登录的操作，通过openId来确认系统中是否包含用户的身份
     *
     * @param openId 用户的微信唯一标识openId
     * @return 可以成功登录的用户的信息
     */
    public User login(String openId) {

        //查找数据库中是否有该openId对应的学生用户
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria studentExampleCriteria = studentExample.createCriteria();
        studentExampleCriteria.andOpenIdEqualTo(openId);
        List<Student> students = studentMapper.selectByExample(studentExample);

        //查找数据库中是否有该openId对应的教师用户
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria teacherExampleCriteria = teacherExample.createCriteria();
        teacherExampleCriteria.andOpenIdEqualTo(openId);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);

        //如果students有元素，可以知道openId对应的是学生
        if (students != null && students.size() != 0) {
            Student student = students.get(0);

            //通过studentId从教务系统获取学生身份，如果获取不到，那么就说明这个身份在教务系统不存在了，不应该再继续接下来的请假操作
            User studentFromEdu = getUserFromEdu("student", student.getId());
            if (studentFromEdu == null) {
                return null;
            }

            //通过studentId从教务系统成功获取到了身份，检查和请假项目的数据库中的学生身份是否对应，查看是否有信息修改过了
            // 由于本项目数据库比较简单，实际上的个人信息只有姓名，假如来自教务系统的信息显示姓名修改了，直接更新本地数据库
            if (!studentFromEdu.equals(student)) {
                student.setName(studentFromEdu.getName());
                studentMapper.updateByPrimaryKeySelective(student);
            }

            return student;
        }

        //如果teachers有元素，可以知道openId对应的是教师
        if (teachers != null && teachers.size() != 0) {
            Teacher teacher = teachers.get(0);

            //通过teacherId从教务系统获取教师身份，如果获取不到，那么就说明这个身份在教务系统不存在了，不应该再继续接下来的请假操作
            User teacherFromEdu = getUserFromEdu("teacher", teacher.getId());
            if (teacherFromEdu == null) {
                return null;
            }

            //通过teacherId从教务系统成功获取到了身份，检查和请假项目的数据库中的教师身份是否对应，查看是否有信息修改过了
            // 由于本项目数据库比较简单，实际上的个人信息只有姓名，假如来自教务系统的信息显示姓名修改了，直接更新本地数据库
            if (!teacherFromEdu.equals(teacher)) {
                teacher.setName(teacherFromEdu.getName());
                teacherMapper.updateByPrimaryKeySelective(teacher);
            }
            return teacher;
        }

        //这表示的是openId在数据库中找不到有人
        return null;
    }

    /**
     * 判断教师/学生的身份是否在教务系统还存在
     * 如果存在则会拉取信息
     *
     * @param type 用户身份
     * @param id   用户id
     * @return 在教务系统中的用户的身份信息
     */
    public User getUserFromEdu(String type, Integer id) {

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("type", type);
        map.put("id", id);

        String url = "http://112.74.95.237:5000/webservice/getUserInfo";

        return restTemplate.getForObject(url, User.class, map);
    }

    /**
     * 用户认证身份的操作
     *
     * @param type   用户身份
     * @param id     用户id
     * @param openId 用户的微信唯一标识openid
     * @return 认证成功的用户身份
     */
    public User auth(String type, Integer id, String openId) {
        User userFromEdu = getUserFromEdu(type, id);

        //确认教务系统是否存在该id所对应的身份
        if (userFromEdu == null) {
            return null;
        }

        //如果认证的是学生身份
        if ("student".equals(type)) {

            Student student = new Student();
            student.setName(userFromEdu.getName());
            student.setId(userFromEdu.getId());
            student.setOpenId(openId);

            //认证，本质上是从教务系统拉取用户信息并且加入到本地数据库
            int insertState;
            try {
                insertState = studentMapper.insert(student);
            } catch (Exception e) {
                insertState = 0;
            }

            //借用insertState确认一些奇怪情况
            //由于student和teacher都设置了主键，假如用户认证的时候填写的是其他人(已经认证过)的id，那么则会失败
            return insertState > 0 ? student : null;
        }

        //如果认证的是教师身份
        if ("teacher".equals(type)) {

            Teacher teacher = new Teacher();
            teacher.setName(userFromEdu.getName());
            teacher.setId(userFromEdu.getId());
            teacher.setOpenId(openId);

            //认证，本质上是从教务系统拉取用户信息并且加入到本地数据库
            int insertState;
            try {
                insertState = teacherMapper.insert(teacher);
            } catch (Exception e) {
                insertState = 0;
            }
            
            return insertState > 0 ? teacher : null;
        }

        return null;
    }
}

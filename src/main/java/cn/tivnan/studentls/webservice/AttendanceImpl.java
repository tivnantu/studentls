package cn.tivnan.studentls.webservice;

import cn.tivnan.studentls.bean.Student;
import cn.tivnan.studentls.dao.NoteMapper;
import cn.tivnan.studentls.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: studentls
 * @description:
 * @author: tivnan
 * @create: 2020-2020/11/26-上午1:16
 * @version:
 **/
@Component("attendance")
@WebService(endpointInterface = "cn.tivnan.studentls.webservice.Attendance", serviceName = "Attendance", targetNamespace = "http://studentls.tivnan.cn/")
public class AttendanceImpl implements cn.tivnan.studentls.webservice.Attendance {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    StudentMapper studentMapper;


    @Override
    public String leaveNum(Integer timeId, String courseDate) {

        String str = "";

        List<Integer> leaStuNums = noteMapper.getLeaStuNum(timeId, courseDate);

        str += leaStuNums.size() + "%";

        ArrayList<String> list = new ArrayList<>();

        for (Integer leaStuNum : leaStuNums) {
            Student student = studentMapper.selectByPrimaryKey(leaStuNum);
            str += student.getName() + "%";
        }

        return str;
    }
}
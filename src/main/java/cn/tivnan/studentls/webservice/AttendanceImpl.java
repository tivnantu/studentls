package cn.tivnan.studentls.webservice;

import cn.tivnan.studentls.bean.SelectExample;
import cn.tivnan.studentls.dao.SelectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

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
    SelectMapper selectMapper;

    @Override
    public Integer leaveNum(Integer timeId) {

        SelectExample example = new SelectExample();
        SelectExample.Criteria criteria = example.createCriteria();
        criteria.andTimeIdEqualTo(timeId);


        long l = selectMapper.countByExample(example);

        return Math.toIntExact(l);
    }


}

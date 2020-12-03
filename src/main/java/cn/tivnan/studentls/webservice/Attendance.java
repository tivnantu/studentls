package cn.tivnan.studentls.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "Attendance", targetNamespace = "http://studentls.tivnan.cn/")
public interface Attendance {
    public String leaveNum(@WebParam(name = "timeId") Integer timeId, @WebParam(name = "courseDate") String courseDate);
}

package cn.tivnan.studentls.bean.vo;

/**
 * @project: studentls
 * @description: note the need to be review
 * @author: tivnan
 * @create: 2020-2020/12/2-下午1:21
 * @version: 1.0
 **/
public class NoteNeedReviewVO {

    /*
     * reqId: 请假单id
     * sectionId: 请假单连接课程最小粒度id
     * courseId：课程id
     * courseName: 课程名称
     * sectionName：课程时间
     * stuName：学生名字
     * stuId：学生Id
     * reqType: 请假类型
     * content：请假原因
     * */

    private String reqId;
    private Integer sectionId;
    private Integer courseId;
    private String courseName;
    private String sectionTime;
    private String stuName;
    private Integer stuId;
    private Integer reqType;
    private String content;

    public NoteNeedReviewVO() {
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(String sectionTime) {
        this.sectionTime = sectionTime;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

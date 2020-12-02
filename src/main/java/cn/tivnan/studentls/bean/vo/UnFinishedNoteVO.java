package cn.tivnan.studentls.bean.vo;

import cn.tivnan.studentls.bean.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @project: studentls
 * @description: use to encapsulate unfinished note
 * @author: tivnan
 * @create: 2020-2020/12/2-下午12:05
 * @version: 1.0
 **/
public class UnFinishedNoteVO {

    private Integer reqType;
    private String startDate;
    private String endDate;
    private Integer total;
    private String content;

    public UnFinishedNoteVO() {
    }

    public UnFinishedNoteVO(Note note) {
        reqType = note.getType();
        startDate = note.getStartTime();
        endDate = note.getEndTime();
        total = getTotalDate(startDate, endDate);
        content=note.getContent();
    }

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 求出两个日期之间的间隔天数
     * @param startDateStr 开始日期
     * @param endDateStr 结束日期
     * @return 间隔天数
     */
    public Integer getTotalDate(String startDateStr, String endDateStr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;
        Date endDate = null;

        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();

        long between_days = (time2 - time1) / (1000 * 3600 * 24) + 1;
        return Integer.parseInt(String.valueOf(between_days));
    }
}

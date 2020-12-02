package cn.tivnan.studentls.bean.vo;

import cn.tivnan.studentls.bean.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @project: studentls
 * @description: use for encapsulating finished note
 * @author: tivnan
 * @create: 2020-2020/12/2-上午11:50
 * @version: 1.0
 **/
public class FinishedNoteVO {

    //processType: 请假状态
    //reqType: 请假类型
    //startDateStr: 请假的开始日期
    //endDateStr: 请假的结束日期
    //total: 请假时长

    private Integer processType;
    private Integer reqType;
    private String startDate;
    private String endDate;
    private Integer total;

    public FinishedNoteVO() {
    }

    /*
     * 由于前端及后端请假单状态定义不同，所以需要进行一个转换
     * 对于后端，state
     *      -1:审核拒绝
     *      0：审核通过
     * 对于前端，processType
     *      1:通过
     *      2:拒绝
     * */

    public FinishedNoteVO(Note note) {

        if (note.getState().equals(-1)) {
            processType = 2;
        } else {
            processType = 1;
        }
        reqType = note.getType();
        startDate = note.getStartTime();
        endDate = note.getEndTime();
        total = getTotalDate(startDate, endDate);
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
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

    public Integer getProcessType() {
        return processType;
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

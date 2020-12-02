package cn.tivnan.studentls.controller;

import cn.tivnan.studentls.bean.Note;
import cn.tivnan.studentls.bean.vo.FinishedNoteVO;
import cn.tivnan.studentls.bean.vo.NoteNeedReviewVO;
import cn.tivnan.studentls.bean.vo.UnFinishedNoteVO;
import cn.tivnan.studentls.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @project: studentls
 * @description: controller for note
 * @author: tivnan
 * @create: 2020-2020/12/1-上午9:30
 * @version: 1.0
 **/
@Controller
public class NoteController {

    @Autowired
    NoteService noteService;


    /*
     * 请假单状态state
     * -1:审核拒绝
     * 0：审核通过
     * > 0:提交，正在审核，数字大小表示有（x）节课程需要审核，例如有3节课程需要审核，那么state=3
     * */


    /**
     * 请假学生提交请假单
     *
     * @param requestMap 请假单内容
     * @return 是否提交成功
     */
    @ResponseBody
    @RequestMapping(value = "/student/note", method = RequestMethod.POST)
    public boolean submitNote(@RequestBody Map<String, Object> requestMap) {

        //startTime 开始时间
        //endTime 结束时间
        //content 请假原因
        //type 请假类型
        //studentId 请假学生的id
        //selectedList 选择请假的课程的id
        String startTime = (String) requestMap.get("startTime");
        String endTime = (String) requestMap.get("endTime");
        String content = (String) requestMap.get("content");
        Integer type = (Integer) requestMap.get("type");
        Integer studentId = (Integer) requestMap.get("studentId");

        List<String> selectedListStr = (List<String>) requestMap.get("selectedList");

        ArrayList<Integer> selectedList = new ArrayList<>();

        for (String s : selectedListStr) {
            selectedList.add(new Integer(s));
        }

        //此时state数字大小表示有多少节课需要被审核
        Integer state = selectedList.size();

        Note note = new Note(startTime, endTime, content, state, type, studentId);

        //提交请假单
        return noteService.submitNote(note, selectedList);
    }

    /**
     * 拉取学生的已完成请假单，也就是审核通过或者审核拒绝的请假单
     *
     * @param id 学生id
     * @return finishedList
     */
    @ResponseBody
    @RequestMapping(value = "/student/note/loadFinishedRequest", method = RequestMethod.GET)
    public Map<String, Object> loadFinishedRequest(@RequestParam Integer id) {

        List<Note> notes = noteService.getSubmitNotes(id, "finished");

        //对notes做一个封装
        ArrayList<FinishedNoteVO> finishedNotes = new ArrayList<>();
        for (Note note : notes) {
            finishedNotes.add(new FinishedNoteVO(note));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("finishedList", finishedNotes);

        return map;
    }

    /**
     * 拉取学生未完成的请假单
     *
     * @param id 学生id
     * @return unfinishedList
     */
    @ResponseBody
    @RequestMapping(value = "/student/note/loadUnfinishedRequest", method = RequestMethod.GET)
    public Map<String, Object> loadUnfinishedRequest(@RequestParam Integer id) {

        List<Note> notes = noteService.getSubmitNotes(id, "unfinished");

        //对notes做一个封装
        ArrayList<UnFinishedNoteVO> unfinishedNotes = new ArrayList<>();
        for (Note note : notes) {
            unfinishedNotes.add(new UnFinishedNoteVO(note));
        }


        HashMap<String, Object> map = new HashMap<>();
        map.put("unfinishedList", unfinishedNotes);

        return map;
    }

    /**
     * 教师拉取需要审核的请假单
     *
     * @param id 教师id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/teacher/note/loadRequestListForTeacher", method = RequestMethod.GET)
    public Map<String, List<NoteNeedReviewVO>> loadRequestListForTeacher(@RequestParam("id") Integer id) {

        List<NoteNeedReviewVO> notesNeedReview = noteService.getNotesNeedReview(id);


        HashMap<String, List<NoteNeedReviewVO>> map = new HashMap<>();
        map.put("list", notesNeedReview);
        return map;
    }


    /**
     * 教师对请假单进行审核
     *
     * @param noteId  请假单id
     * @param opinion 秦家意见
     * @param id      教师id
     * @param timesId 请假课程的最小粒度id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/teacher/note/verifyRequest", method = RequestMethod.GET)
    public Boolean verifNote(@RequestParam("noteId") Integer noteId,
                             @RequestParam("opinion") String opinion,
                             @RequestParam("id") Integer id,
                             @RequestParam("sectionId") Integer timesId) {

        return noteService.verifyNote(noteId, opinion, id, timesId);

    }


}

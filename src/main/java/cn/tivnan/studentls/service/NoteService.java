package cn.tivnan.studentls.service;

import cn.tivnan.studentls.bean.Note;
import cn.tivnan.studentls.bean.NoteExample;
import cn.tivnan.studentls.bean.Review;
import cn.tivnan.studentls.bean.Select;
import cn.tivnan.studentls.bean.vo.NoteNeedReviewVO;
import cn.tivnan.studentls.dao.NoteMapper;
import cn.tivnan.studentls.dao.ReviewMapper;
import cn.tivnan.studentls.dao.SelectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @project: studentls
 * @description: service for note
 * @author: tivnan
 * @create: 2020-2020/12/1-下午4:55
 * @version: 1.0
 **/
@Service
public class NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    SelectMapper selectMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ReviewMapper reviewMapper;

    /**
     * 往数据库插入请假单
     *
     * @param note         请假单
     * @param selectedList 与之关联的请假课程的id
     * @return
     */
    public boolean submitNote(Note note, List<Integer> selectedList) {

        try {
            noteMapper.insert(note);
            // System.out.println("note = " + note);
            for (Integer selectValue : selectedList) {
                Select select = new Select(note.getNoteId(), selectValue);
                selectMapper.insert(select);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 获取数据中学生的请假单
     * 分成两类
     * 一类已完成
     * 一类未完成
     *
     * @param id    学生id
     * @param state 想要获取的是已完成还是未完成的请假单
     * @return 请假单
     */
    public List<Note> getSubmitNotes(Integer id, String state) {

        NoteExample noteExample = new NoteExample();
        NoteExample.Criteria criteria = noteExample.createCriteria();
        criteria.andStudentIdEqualTo(id);

        if ("finished".equals(state)) {
            criteria.andStateLessThanOrEqualTo(0);
        } else if ("unfinished".equals(state)) {
            criteria.andStateGreaterThanOrEqualTo(1);
        }

        List<Note> notes = noteMapper.selectByExample(noteExample);
        return notes;
    }

    /**
     * 根据教师id获取教师需要审核的请假单
     *
     * @param id
     * @return
     */
    public List<NoteNeedReviewVO> getNotesNeedReview(Integer id) {
        ArrayList<NoteNeedReviewVO> noteNeedReviewVOS = new ArrayList<>();

        List<Integer> teachTimeIdList = getTeachList(id);

        for (Integer timeId : teachTimeIdList) {

            //此时获得的noteNeeReview元素，差了courseId，courseName和sectionTime
            List<NoteNeedReviewVO> noteNeedReviewVO = noteMapper.getNoteNeedReview(id, timeId);
            /*利用timeId获得，上面三个元素*/

            for (NoteNeedReviewVO needReview : noteNeedReviewVO) {
                List<String> sectionInfo = getSectionInfo(needReview.getSectionId());
                if (sectionInfo == null) {
                    continue;
                }
                String courseId = sectionInfo.get(0);
                String courseName = sectionInfo.get(1);
                String sectionTime = sectionInfo.get(2);

                needReview.setCourseId(new Integer(courseId));
                needReview.setCourseName(courseName);
                needReview.setSectionTime(sectionTime);
            }

            noteNeedReviewVOS.addAll(noteNeedReviewVO);
        }
        return noteNeedReviewVOS;
    }

    /**
     * 通过教师的id获取到教师的最小粒度的课程id
     *
     * @param id 教师id
     * @return 课程最小粒度id
     */
    public List<Integer> getTeachList(Integer id) {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("id", id);

        String url = "http://112.74.95.237:5000/webservice/loadTeachSection?id={id}";
        String timeIdListStr = restTemplate.getForObject(url, String.class, map);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(timeIdListStr, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> list = new ArrayList<>();
        list = (ArrayList<Integer>) jsonMap.get("sections");
        // System.out.println("list = " + list);
        // System.out.println(list.get(0).getClass().getName());
        return list;
    }

    /**
     * 通过timeId获取课程的信息
     *
     * @param timeId 教师id
     * @return 课程最小粒度id
     */
    public List<String> getSectionInfo(Integer timeId) {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("timeId", timeId);

        String url = "http://112.74.95.237:5000//webservice/getSectionInfo?timeId={timeId}";
        String sectionInfo = restTemplate.getForObject(url, String.class, map);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> jsonMap = null;
        List<String> sections = null;
        try {
            jsonMap = mapper.readValue(sectionInfo, HashMap.class);

            sections = (List<String>) jsonMap.get("sections");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return sections;
    }

    /**
     * 对请假单进行审核
     *
     * @param noteId  请假单id
     * @param opinion 请假意见
     * @param id      教师id
     * @param timeId  最小粒度id
     * @return
     */
    public Boolean verifyNote(Integer noteId, String opinion, Integer id, Integer timeId) {
        Note note = noteMapper.selectByPrimaryKey(noteId);

        if ("agree".equals(opinion)) {
            if (note.getState() >= 1) {
                note.setState(note.getState() - 1);

                reviewMapper.insert(new Review(id, noteId, timeId));

                noteMapper.updateByPrimaryKeySelective(note);

                return true;
            }
        } else {
            note.setState(-1);
            reviewMapper.insert(new Review(id, noteId, timeId));
            noteMapper.updateByPrimaryKeySelective(note);

            return true;
        }


        return false;
    }
}

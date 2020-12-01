package cn.tivnan.studentls.controller;

import cn.tivnan.studentls.service.SectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: studentls
 * @description: controller for section
 * @author: tivnan
 * @create: 2020-2020/12/1-上午9:31
 * @version: 1.0
 **/
@Controller
public class SectionController {

    @Autowired
    SectionService sectionService;

    /**
     * 获取学生在请假期间所需要上的课程
     *
     * @param startTime 请假开始时间
     * @param endTime   请假结束时间
     * @param id        学生id
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadSection")
    public Map<String, Object> loadSection(@RequestParam String startTime,
                                           @RequestParam String endTime,
                                           @RequestParam("id") Integer id) {

        return sectionService.getSectionList(startTime, endTime, id);
    }

}

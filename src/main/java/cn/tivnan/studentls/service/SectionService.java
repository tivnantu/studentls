package cn.tivnan.studentls.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @project: studentls
 * @description: service for section
 * @author: tivnan
 * @create: 2020-2020/12/1-上午9:33
 * @version: 1.0
 **/
@Service
public class SectionService {

    @Autowired
    RestTemplate restTemplate;

    public HashMap<String, Object> getSectionList(String startTime, String endTime, Integer id) {


        HashMap<String, Object> map = new HashMap<>(3);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("id", id);

        String url = "http://112.74.95.237:5000/webservice/loadTakeSection";

        String sectionStr = restTemplate.getForObject(url, String.class, map);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(sectionStr, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }
}

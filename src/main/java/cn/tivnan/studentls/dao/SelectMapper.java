package cn.tivnan.studentls.dao;

import cn.tivnan.studentls.bean.Select;
import cn.tivnan.studentls.bean.SelectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SelectMapper {
    long countByExample(SelectExample example);

    int deleteByExample(SelectExample example);

    int insert(Select record);

    int insertSelective(Select record);

    List<Select> selectByExample(SelectExample example);

    int updateByExampleSelective(@Param("record") Select record, @Param("example") SelectExample example);

    int updateByExample(@Param("record") Select record, @Param("example") SelectExample example);
}
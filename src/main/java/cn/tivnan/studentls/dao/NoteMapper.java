package cn.tivnan.studentls.dao;

import cn.tivnan.studentls.bean.Note;
import cn.tivnan.studentls.bean.NoteExample;

import java.util.List;

import cn.tivnan.studentls.bean.vo.NoteNeedReviewVO;
import org.apache.ibatis.annotations.Param;

public interface NoteMapper {
    long countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Integer noteId);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Integer noteId);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);

    List<NoteNeedReviewVO> getNoteNeedReview(@Param("id") Integer id, @Param("timeId") Integer timeId);
}
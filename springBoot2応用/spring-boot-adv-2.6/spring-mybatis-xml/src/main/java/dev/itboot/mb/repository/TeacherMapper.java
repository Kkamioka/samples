package dev.itboot.mb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.itboot.mb.model.Teacher;

@Mapper
public interface TeacherMapper {
    // 各SQL文を削除します
    List<Teacher> selectAll();

    Teacher selectByPrimaryKey(Long id);

    int insert(Teacher record);

    int updateByPrimaryKey(Teacher record);

    int deleteByPrimaryKey(Long id);
}

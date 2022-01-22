package dev.itboot.mb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dev.itboot.mb.model.Teacher;

@Mapper
public interface TeacherMapper {

    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();

    // 以下を追加
    @Select({
        "SELECT * FROM teacher",
        "WHERE id = #{id}"
    })
    Teacher selectByPrimaryKey(Long id);

    @Insert({
        "INSERT INTO teacher(user_name, email)",
        "VALUES(#{userName}, #{email})"
    })
    int insert(Teacher record);

    @Update({
        "UPDATE teacher",
        "SET user_name = #{userName}, email = #{email}",
        "WHERE id = #{id}"
    })
    int updateByPrimaryKey(Teacher record);

    @Delete({
        "DELETE FROM teacher",
        "WHERE id = #{id}"
    })
    int deleteByPrimaryKey(Long id);
}

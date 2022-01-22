package dev.itboot.mb.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.itboot.mb.model.Teacher;
import dev.itboot.mb.repository.TeacherMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TeacherService {

    private final TeacherMapper mapper;

    // Pageableを追加
    public Page<Teacher> selectAll(Pageable pageable) {
        var rowBounds = new RowBounds(
                (int)pageable.getOffset(), pageable.getPageSize());
        var teachers = mapper.selectAll(rowBounds);

        var total = mapper.count();
        return new PageImpl<>(teachers, pageable, total);
    }

    public List<Teacher> selectAll() {
        return mapper.selectAll();
    }

    public Teacher selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public void save(Teacher teacher) {
        if (teacher.getId() == null) {
            mapper.insert(teacher);
        } else {
            mapper.updateByPrimaryKey(teacher);
        }
    }

    public void deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}

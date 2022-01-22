package com.example.todolist.form;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.example.todolist.entity.Todo;
import lombok.Data;
import java.util.List;
import javax.validation.Valid;
import com.example.todolist.common.Utils;
import com.example.todolist.entity.Task;
import java.util.ArrayList;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoData {
    private Integer id;

    @NotBlank
    private String title;

    @NotNull
    private Integer importance;

    @Min(value = 0)
    private Integer urgency;

    private String deadline;
    private String done;

    @Valid
    private List<TaskData> taskList;
    
    private TaskData newTask;

    // 入力データからTodo Entityを生成して返す
    public Todo toEntity() {
        Todo todo = new Todo();
        // ToDo
        todo.setId(id);
        todo.setTitle(title);
        todo.setImportance(importance);
        todo.setUrgency(urgency);
        todo.setDone(done);

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        long ms;
        try {
            ms = sdFormat.parse(deadline).getTime();
            todo.setDeadline(new Date(ms));
        } catch (ParseException e) {
            todo.setDeadline(null);
        }

        // Task
        Date date;
        Task task;
        if (taskList != null) {
            for (TaskData taskData : taskList) {
                date = Utils.str2dateOrNull(taskData.getDeadline());
                task = new Task(
                    taskData.getId(),
                    null,
                    taskData.getTitle(),
                    date,
                    taskData.getDone());
                todo.addTask(task);
            }
        }

        return todo;
    }
    
    //  Todoの内容から入力画面へ渡すTodoDataを生成する
    public TodoData(Todo todo) {
        // Todo部分
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.importance = todo.getImportance();
        this.urgency = todo.getUrgency();
        this.deadline = Utils.date2str(todo.getDeadline());
        this.done = todo.getDone();

        // 登録済Task
        this.taskList = new ArrayList<>();
        String dt;
        for (Task task : todo.getTaskList()) {
            dt = Utils.date2str(task.getDeadline());
            this.taskList.add(
                new TaskData(task.getId(), task.getTitle(), dt, task.getDone()));
        }

        // 追加用Task
        newTask = new TaskData();
    }
    
    //  TodoForm画面の新規タスク入力行の内容からTaskオブジェクトを生成して返す
    public Task toTaskEntity() {
        Task task = new Task();
        task.setId(newTask.getId());
        task.setTitle(newTask.getTitle());
        task.setDone(newTask.getDone());
        task.setDeadline(Utils.str2date(newTask.getDeadline()));
  
        return task;
    }


}

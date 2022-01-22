package com.example.todolist.form;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.example.todolist.entity.Todo;
import lombok.Data;

@Data
public class TodoData {
  private Integer id;

  @NotBlank(message = "件名を入力してください")
  private String title;

  @NotNull(message = "重要度を選択してください")
  private Integer importance;

  @Min(value = 0, message = "緊急度を選択してください")
  private Integer urgency;

  private String deadline;
  private String done;
  
  /**
   * 入力データからEntityを生成して返す
   */
  public Todo toEntity() {
    Todo todo = new Todo();
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

    return todo;
  }
}

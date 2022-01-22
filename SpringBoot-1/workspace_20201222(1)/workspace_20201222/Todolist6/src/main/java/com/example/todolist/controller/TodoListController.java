package com.example.todolist.controller;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.dao.TodoDaoImpl;
import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
import com.example.todolist.form.TodoQuery;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoListController {
  private final TodoRepository todoRepository;
  private final TodoService todoService;
  private final HttpSession session;

  @PersistenceContext
  private EntityManager entityManager;
  TodoDaoImpl todoDaoImpl;

  @PostConstruct
  public void init() {
    todoDaoImpl = new TodoDaoImpl(entityManager);
  }

  //  ToDo一覧初期表示
  @GetMapping("/todo")
//	step1
//	public ModelAndView showTodoList(ModelAndView mv, Pageable pageable) {
//		mv.setViewName("todoList");
//
//		Page<Todo> todoList = todoRepository.findAll(pageable);
//		mv.addObject("todoQuery", new TodoQuery());
//		mv.addObject("todoList", todoList);
//
//		return mv;
//	}
//	step2
  public ModelAndView showTodoList(ModelAndView mv,
                                   @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable) {
    mv.setViewName("todoList");

    Page<Todo> todoPage = todoRepository.findAll(pageable);
    mv.addObject("todoQuery", new TodoQuery());
    mv.addObject("todoPage", todoPage);
    mv.addObject("todoList", todoPage.getContent());
    session.setAttribute("todoQuery", new TodoQuery());

    return mv;
  }

  // ToDo表示
  @GetMapping("/todo/{id}")
  public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
    mv.setViewName("todoForm");
    Todo todo = todoRepository.findById(id).get();
    mv.addObject("todoData", todo);
    session.setAttribute("mode", "update");
    return mv;
  }

  // ToDo入力フォーム表示
  @PostMapping("/todo/create/form")
  public ModelAndView createTodo(ModelAndView mv) {
    mv.setViewName("todoForm");
    mv.addObject("todoData", new TodoData());
    session.setAttribute("mode", "create");
    return mv;
  }

  // ToDo追加処理
  @PostMapping("/todo/create/do")
  public String createTodo(@ModelAttribute @Validated TodoData todoData, 
                           BindingResult result,
                           Model model) {
    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);
    if (!result.hasErrors() && isValid) {
      // エラーなし
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return "redirect:/todo";

    } else {
      // エラーあり
      // model.addAttribute("todoData", todoData);
      return "todoForm";
    }
  }

  // ToDo更新処理
  @PostMapping("/todo/update")
  public String updateTodo(@ModelAttribute @Validated TodoData todoData, 
                           BindingResult result,
                           Model model) {
    // エラーチェック
    boolean isValid = todoService.isValid(todoData, result);
    if (!result.hasErrors() && isValid) {
      // エラーなし
      Todo todo = todoData.toEntity();
      todoRepository.saveAndFlush(todo);
      return "redirect:/todo";

    } else {
      // エラーあり
      // model.addAttribute("todoData", todoData);
      return "todoForm";
    }
  }

  // ToDo削除処理
  @PostMapping("/todo/delete")
  public String deleteTodo(@ModelAttribute TodoData todoData) {
    todoRepository.deleteById(todoData.getId());
    return "redirect:/todo";
  }

  //  ToDo検索処理
  @PostMapping("/todo/query")
  public ModelAndView queryTodo(@ModelAttribute TodoQuery todoQuery, BindingResult result,
                                @PageableDefault(page = 0, size = 5) Pageable pageable,
                                ModelAndView mv) {
    mv.setViewName("todoList");

    Page<Todo> todoPage = null;
    if (todoService.isValid(todoQuery, result)) {
      // エラーがなければ検索
      todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable);

      // 入力された検索条件をsessionへ保存
      session.setAttribute("todoQuery", todoQuery);

      mv.addObject("todoPage", todoPage);
      mv.addObject("todoList", todoPage.getContent());
    } else {
      mv.addObject("todoPage", null);
      mv.addObject("todoList", null);
    }

    // mv.addObject("todoQuery", todoQuery);

    return mv;
  }

  // ページリンク押下時
  @GetMapping("/todo/query")
  public ModelAndView queryTodo(  @PageableDefault(page = 0, size = 5) Pageable pageable, // ①
                  ModelAndView mv) {
    mv.setViewName("todoList");

    // sessionに保存されている条件で検索
    TodoQuery todoQuery = (TodoQuery)session.getAttribute("todoQuery");
    Page<Todo> todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable); // ②

    mv.addObject("todoQuery", todoQuery); // 検索条件表示用
    mv.addObject("todoPage", todoPage); // page情報
    mv.addObject("todoList", todoPage.getContent()); // 検索結果

    return mv;
  }
  
  // ToDo一覧へ戻る
  @PostMapping("/todo/cancel")
  public String cancel() {
    return "redirect:/todo";
  }
}

package com.example.todolist.controller;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.entity.AttachedFile;
import com.example.todolist.entity.Todo;
import com.example.todolist.repository.AttachedFileRepository;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.DownloadService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DownloadController {
    private final TodoRepository todoRepository;
    private final AttachedFileRepository attachedFileRepository;
    private final DownloadService downloadService;
    private final HttpSession session;

    // 添付ファイルのダウンロード処理
    @GetMapping("/todo/af/download/{afId}")
    public void downloadAttachedFile(@PathVariable(name = "afId") int afId,
                                     HttpServletResponse response, Locale locale) {

        // 添付ファイル情報を取得
        Optional<AttachedFile> someAf = attachedFileRepository.findById(afId);
        someAf.ifPresentOrElse(af -> {
            // 添付ファイルが存在する -> 添付先Todoの情報を取得
            Optional<Todo> someTodo = todoRepository.findById(af.getTodoId());
            someTodo.ifPresentOrElse(todo -> {
                // ToDoが存在する
                // このセッションのaccountId取得
                Integer accountId = (Integer)session.getAttribute("accountId");

                // 操作者のToDoか？
                if (todo.getOwnerId().equals(accountId)) {
                    // 本人のものなのでdownloadする
                    downloadService.downloadAttachedFile(afId, response);

                } else {
                    // 操作者のToDoでない
                    downloadService.invalidDownloadRequest(session, response, locale);
                }
            }, () -> {
                // ToDoが存在しない
                downloadService.invalidDownloadRequest(session, response, locale);
            });
        }, () -> {
            // 添付ファイルが存在しない
            downloadService.invalidDownloadRequest(session, response, locale);
        });
    }

}

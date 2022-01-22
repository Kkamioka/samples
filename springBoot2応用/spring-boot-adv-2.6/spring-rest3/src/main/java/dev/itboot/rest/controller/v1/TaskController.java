package dev.itboot.rest.controller.v1;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.itboot.rest.model.Task;
import dev.itboot.rest.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskRepository repository;

    @Operation(summary = "タスクを全件取得します")
    @GetMapping("/")
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Operation(summary = "タスクを登録します")
    @PostMapping("/")
    public Task save(@RequestBody Task task) {
        // taskに値が格納してあるので、そのまま保存できます
        return repository.save(task);
    }

    @Operation(summary = "タスクを1件取得します")
    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id) {
        // idに値が格納してあるので、そのまま検索に利用できます
        return repository.findById(id).get();
    }

    @Operation(summary = "タスクを更新します")
    @PutMapping("/{id}")
    public Task save(@RequestBody Task newTask, @PathVariable Long id) {

        return repository.findById(id).map(task -> {
            // 該当IDが存在する場合、更新します
            task.setName(newTask.getName());
            task.setCompleted(newTask.getCompleted());
            return repository.save(task);

        }).orElseGet(() -> {
            // 該当IDが存在しない場合、登録します
            newTask.setId(id);
            return repository.save(newTask);
        });
    }

    @Operation(summary = "タスクを削除します")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

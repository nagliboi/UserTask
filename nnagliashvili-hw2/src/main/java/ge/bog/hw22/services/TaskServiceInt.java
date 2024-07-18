
package ge.bog.hw22.services;

import ge.bog.hw22.modules.Task;

import java.util.List;

public interface TaskServiceInt {
     List<Task> getTaskByUserId(Long userId);
     String createTask(Long userId, Task task);
}
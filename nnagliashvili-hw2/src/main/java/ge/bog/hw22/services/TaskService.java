
package ge.bog.hw22.services;

import ge.bog.hw22.exceptions.UserNotFoundException;
import ge.bog.hw22.modules.Task;
import ge.bog.hw22.modules.User;
import ge.bog.hw22.repositories.TaskRepository;
import ge.bog.hw22.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskServiceInt {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Task> getTaskByUserId(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return taskRepository.findByUserId(userId);
        }else{
            throw new UserNotFoundException();
        }

    }

    public String createTask(Long userId, Task task){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            task.setUser(user.get());
            task.setCreateDate(LocalDateTime.now());
            taskRepository.save(task);
            return "task '" + task.getName() + "' has been created for user: " + userId;
        }else{
            throw new UserNotFoundException();
        }
    }
}

package ge.bog.hw22.controllers;


import ge.bog.hw22.exceptions.UserIdentificationNotUniqueException;
import ge.bog.hw22.exceptions.UserIdentificationNullException;
import ge.bog.hw22.exceptions.UserNotFoundException;
import ge.bog.hw22.modules.Task;
import ge.bog.hw22.modules.User;
import ge.bog.hw22.services.TaskService;
import ge.bog.hw22.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {return userService.userGetterService(id);}

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Long userId = userService.userCreatorService(user);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (UserIdentificationNotUniqueException | UserIdentificationNullException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.userUpdateService(user.getId(), user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserIdentificationNotUniqueException | UserIdentificationNullException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        String response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasks(@PathVariable long id) {
        return taskService.getTaskByUserId(id);
    }

    @PostMapping("/{id}/tasks")
    public String addTask(@PathVariable long id, @RequestBody Task task) {
        return taskService.createTask(id, task);
    }
}
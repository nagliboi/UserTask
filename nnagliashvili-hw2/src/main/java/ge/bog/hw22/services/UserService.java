package ge.bog.hw22.services;

import ge.bog.hw22.exceptions.UserIdentificationNotUniqueException;
import ge.bog.hw22.exceptions.UserIdentificationNullException;
import ge.bog.hw22.exceptions.UserNotFoundException;
import ge.bog.hw22.modules.Task;
import ge.bog.hw22.modules.User;
import ge.bog.hw22.repositories.TaskRepository;
import ge.bog.hw22.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInt {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public User userGetterService(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public Long userCreatorService(User user) throws UserIdentificationNotUniqueException, UserIdentificationNullException {
        if (user.getUserIdentification() == null) {
            throw new UserIdentificationNullException("User identification is null bro");
        } else if (userRepository.existsByUserIdentification(user.getUserIdentification())) {
            throw new UserIdentificationNotUniqueException("User identification is not unique bro");
        } else {
            userRepository.save(user);
            return user.getId();
        }
    }


    @Override
    public User userUpdateService(Long id, User user) throws UserIdentificationNotUniqueException, UserIdentificationNullException, UserNotFoundException {
        if (user.getUserIdentification() == null) {
            throw new UserIdentificationNullException("User identification is null");
        }

        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        // Check if the updated user identification is unique
        User userFromDb = existingUser.get();
        if (!userFromDb.getUserIdentification().equals(user.getUserIdentification()) &&
                userRepository.existsByUserIdentification(user.getUserIdentification())) {
            throw new UserIdentificationNotUniqueException("User identification is not unique");
        }

        userFromDb.setName(user.getName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setPhone(user.getPhone());
        userFromDb.setUserIdentification(user.getUserIdentification()); // Ensure you update the userIdentification

        return userRepository.save(userFromDb);
    }

    @Override
    @Transactional
    public String deleteUser(long userId) {
        // Check if user exists
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return "User doesn't exist bro";
        }

        // Find all tasks associated with the user
        List<Task> tasks = taskRepository.findByUserId(userId);

        // Get the names of the tasks
        String taskNames = tasks.stream()
                .map(Task::getName)
                .collect(Collectors.joining(", "));

        // Delete all tasks associated with the user
        taskRepository.deleteByUserId(userId);

        // Now delete the user
        userRepository.deleteById(userId);

        return "User " + userId + " and its tasks (" + taskNames + ") have been deleted";
    }




}

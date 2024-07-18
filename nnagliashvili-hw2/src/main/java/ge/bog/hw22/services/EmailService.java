package ge.bog.hw22.services;

import ge.bog.hw22.modules.Task;
import ge.bog.hw22.modules.User;
import ge.bog.hw22.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService implements EmailServiceInt {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Scheduled(cron = "0 04 14 * * ?")
    public void sendReminderEmails() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        List<Task> tasksDueToday = taskRepository.findTasksDueToday(startOfDay, endOfDay);

        // Group tasks by user
        Map<User, List<Task>> tasksByUser = tasksDueToday.stream().collect(Collectors.groupingBy(Task::getUser));

        for (Map.Entry<User, List<Task>> entry : tasksByUser.entrySet()) {
            User user = entry.getKey();
            List<Task> userTasks = entry.getValue();
            String taskNames = userTasks.stream().map(Task::getName).collect(Collectors.joining(", "));

            sendSimpleMessage(user.getEmail(), "Tasks Due Today", "Your tasks due today: " + taskNames);
        }
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

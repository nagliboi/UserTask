package ge.bog.hw22.services;

import org.springframework.scheduling.annotation.Scheduled;


public interface EmailServiceInt {
    @Scheduled(cron = "0 50 13 * * ?")
    void sendReminderEmails();

    void sendSimpleMessage(String to, String subject, String text);
}

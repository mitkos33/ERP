package com.wg.erp.scheduling;

import com.wg.erp.crm.model.entity.Task;
import com.wg.erp.crm.service.TaskService;
import com.wg.erp.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CronScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(CronScheduler.class);
    private final TaskService taskService;
    private final MailService mailService;

    public CronScheduler(TaskService taskService, MailService mailService) {
        this.taskService = taskService;
        this.mailService = mailService;
    }


    @Scheduled(cron = "*/60 * * * * *")
    public void testCronOnTenSeconds() {
        LOGGER.info("On cron - one minute!");
    }

    @Scheduled(cron = "* 0 16 * * 1-5")
    public void sendDailyReminderMail() {
        Map<String, List<Task>> allOpenTasks = taskService.getAllActiveTasks();
        List<Task> todayTasks = new ArrayList<>();
        if (allOpenTasks.containsKey("Today")) {
            todayTasks = allOpenTasks.get("Today");
        }
        todayTasks.forEach(task -> {
            mailService.sendMailToUser(task.getCreatedBy().getEmail(), "Daily reminder", "Don't forget to complete your task: " + task.getTitle());
        });
    }
}

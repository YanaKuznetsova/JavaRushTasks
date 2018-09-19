package com.javarush.task.task39.task3913;

import java.util.Date;

public class LogInfo {

    String ip;
    String user;
    Date date;
    Event event;
    int taskId;
    Status status;


    LogInfo(String ip, String user, Date date, Event event, int taskId, Status status) {
        this.ip = ip;
        this.user = user;
        this.date = date;
        this.event = event;
        this.taskId = taskId;
        this.status = status;
    }
}

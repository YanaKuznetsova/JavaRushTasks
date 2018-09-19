package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

    private Path logDir;
    private ArrayList<LogInfo> allLogsData = new ArrayList<>();

    public LogParser(Path logDir) {
        this.logDir = logDir;
        readLogFiles();
    }

    private void readLogFiles() {
        try (Stream<Path> paths = Files.walk(logDir)) {
            paths.filter(Files::isRegularFile)
                    .map(f -> f.toFile())
                    .filter(f -> f.getName().endsWith(".log"))
                    .forEach(f -> readDataFromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile(File file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] data = line.split("\t");
                String ip = data[0];
                String username = data[1];
                Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(data[2]);
                int taskId = -1;
                Event event;
                if (data[3].startsWith(Event.SOLVE_TASK.name()) || data[3].startsWith(Event.DONE_TASK.name())) {
                    String[] eventData = data[3].split(" ");
                    event = Event.valueOf(eventData[0]);
                    taskId = Integer.parseInt(eventData[1]);
                } else {
                    event = Event.valueOf(data[3]);
                }
                Status status = Status.valueOf(data[4]);
                allLogsData.add(new LogInfo(ip, username, date, event, taskId, status));

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        Set<String> uniquesIPs = getUniqueIPs(after, before);
        return uniquesIPs == null ? 0 : uniquesIPs.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> uniquesIP = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before)) {
                uniquesIP.add(logInfo.ip);
            }
        }
        return uniquesIP;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> usersIP = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)) {
                usersIP.add(logInfo.ip);
            }
        }
        return usersIP;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> eventIPs = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.event.equals(event)) {
                eventIPs.add(logInfo.ip);
            }
        }
        return eventIPs;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> statusIPs = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.status.equals(status)) {
                statusIPs.add(logInfo.ip);
            }
        }
        return statusIPs;
    }

    private boolean isCorrectDate(Date currentDate, Date after, Date before) {
        boolean result = false;
        if (after == null && before == null) {
            result = true;
        } else if (after == null){
            if(currentDate.before(before)) {
                result = true;
            }
        } else if (before == null){
            if (currentDate.after(after)) {
                result = true;
            }
        } else if (currentDate.after(after) && currentDate.before(before)) {
            result = true;
        }
        return result;
//        boolean result = false;
//        if (after == null && before == null) {
//            result = true;
//        } else if (after == null){
//            if(currentDate.getTime() <= before.getTime()) {
//                result = true;
//            }
//        } else if (before == null){
//            if (currentDate.getTime() >= after.getTime()) {
//                result = true;
//            }
//        } else if (currentDate.getTime() >= after.getTime() && currentDate.getTime() <= before.getTime()) {
//            result = true;
//        }
//        return result;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> users = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
           users.add(logInfo.user);
        }
        return users;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before)) {
                users.add(logInfo.user);
            }
        }
        return users.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<Event> userEvents = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)) {
                userEvents.add(logInfo.event);
            }
        }
        return userEvents.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> ipUsers = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.ip.equalsIgnoreCase(ip)) {
                ipUsers.add(logInfo.user);
            }
        }
        return ipUsers;
    }

    private Set<String> getUsersForEvent(Date after, Date before, Event event) {
        Set<String> users = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.event.equals(event)) {
                users.add(logInfo.user);
            }
        }
        return users;
    }

    private Set<String> getUsersForEventAndTask(Date after, Date before, Event event, int task) {
        Set<String> users = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.event.equals(event)
                    && logInfo.taskId == task) {
                users.add(logInfo.user);
            }
        }
        return users;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
       return getUsersForEvent(after, before, Event.LOGIN);
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getUsersForEvent(after, before, Event.DOWNLOAD_PLUGIN);
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getUsersForEvent(after, before, Event.WRITE_MESSAGE);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getUsersForEvent(after, before, Event.SOLVE_TASK);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUsersForEventAndTask(after, before, Event.SOLVE_TASK, task);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUsersForEvent(after, before, Event.DONE_TASK);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUsersForEventAndTask(after, before, Event.DONE_TASK, task);
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user) && logInfo.event.equals(event)) {
                dates.add(logInfo.date);
            }
        }
        return dates;
    }

    private Set<Date> getDatesForStatus(Status status, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.status.equals(status)) {
                dates.add(logInfo.date);
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDatesForStatus(Status.FAILED, after, before);
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesForStatus(Status.ERROR, after, before);
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Date result = null;
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)
                    && logInfo.event.equals(Event.LOGIN)) {
                Date currentDate = logInfo.date;
                if (result == null || currentDate.getTime() < result.getTime()) {
                    result = currentDate;
                }
            }
        }
        return result;
    }


    private Date getDateWhenTaskFirstTime(String user, int task, Event event, Date after, Date before) {
        Date result = null;
        for (LogInfo logInfo : allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)
                    && logInfo.event.equals(event) && logInfo.taskId == task) {
                Date currentDate = logInfo.date;
                if (result == null || currentDate.getTime() < result.getTime()) {
                    result = currentDate;
                }
            }
        }
        return result;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return getDateWhenTaskFirstTime(user, task, Event.SOLVE_TASK, after, before);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return getDateWhenTaskFirstTime(user, task, Event.DONE_TASK, after, before);
    }

    private Set<Date> getDatesWhenEvent(String user, Event event, Date after, Date before) {
        Set<Date> result = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)
                    && logInfo.event.equals(event)) {
                result.add(logInfo.date);
            }
        }
        return result;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesWhenEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesWhenEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        Set<Event> events = getAllEvents(after, before);
        return events == null ? 0 : events.size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before)) {
                events.add(logInfo.event);
            }
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.ip.equals(ip)) {
                events.add(logInfo.event);
            }
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.user.equalsIgnoreCase(user)) {
                events.add(logInfo.event);
            }
        }
        return events;
    }

    private Set<Event> getSpecificEvents(Status status, Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.status.equals(status)) {
                events.add(logInfo.event);
            }
        }
        return events;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getSpecificEvents(Status.FAILED, after, before);
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getSpecificEvents(Status.ERROR, after, before);
    }

    private int getNumberOfAttemptForTask(Event event, int task, Date after, Date before) {
        int count = 0;
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.taskId == task
                    && logInfo.event.equals(event)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return getNumberOfAttemptForTask(Event.SOLVE_TASK, task, after, before);
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return getNumberOfAttemptForTask(Event.DONE_TASK, task, after, before);
    }

    private Map<Integer, Integer> getAllTasksAndTheirNumber(Event event, Date after, Date before) {
        Map<Integer, Integer> result = new HashMap<>();
        for (LogInfo logInfo: allLogsData) {
            if (isCorrectDate(logInfo.date, after, before) && logInfo.event.equals(event)) {
                int count = 0;
                if (result.containsKey(logInfo.taskId)) {
                    count = result.get(logInfo.taskId);
                }
                result.put(logInfo.taskId, count + 1);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return getAllTasksAndTheirNumber(Event.SOLVE_TASK, after, before);
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return getAllTasksAndTheirNumber(Event.DONE_TASK, after, before);
    }

    private Function<LogInfo, Object> createFunction(String value) {
        Function<LogInfo, Object> function = null;
        switch (value) {
            case "ip":
                function = logInfo -> logInfo.ip;
                break;
            case "user":
                function = logInfo -> logInfo.user;
                break;
            case "date":
                //function = logInfo -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(logInfo.date);
                function = logInfo -> logInfo.date;
                break;
            case "event":
                function = logInfo -> logInfo.event;
                break;
            case "status":
                function = logInfo -> logInfo.status;
                break;
            default:
                function = logInfo -> "";
        }
        return function;
    }

    @Override
    public Set<Object> execute(String query) {
        Set<Object> result = new HashSet<>();
        String[] words = query.split(" ");
        String getWhat = words[1];
        String forWhat = "";
        String value = "";
        Date after = null;
        Date before = null;
        try {
        if (words.length > 2) {
            forWhat = words[3];
            String[] phrases = query.split("\"");
            value = phrases[1];
            if (phrases.length > 3) {
                after = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(phrases[3]);
                before = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(phrases[5]);
            }
        }
        Function<LogInfo, Object> getWhatFunction = createFunction(getWhat);
        Function<LogInfo, Object> forWhatFunction = createFunction(forWhat);
        if (forWhat.equalsIgnoreCase("date")) {
                Date dateValue = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(value);
                for (LogInfo logInfo: allLogsData) {
                    Date currentDate = (Date)forWhatFunction.apply(logInfo);
                    if (currentDate.compareTo(dateValue) == 0 && isCorrectDate(logInfo.date, after, before)) {
                        result.add(getWhatFunction.apply(logInfo));
                    }
                }
        } else {
            for (LogInfo logInfo: allLogsData) {
                if (forWhatFunction.apply(logInfo).toString().equals(value) && isCorrectDate(logInfo.date, after, before)) {
                    result.add(getWhatFunction.apply(logInfo));
                }
            }
        }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
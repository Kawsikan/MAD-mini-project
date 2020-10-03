package Model;

public class Task {

    private String taskTitle;
    private String taskDescription;
    private String date;
    private String time;

    public Task() {
    }

    public Task(String taskTitle, String taskDescription, String date, String time) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.date = date;
        this.time = time;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
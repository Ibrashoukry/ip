public class Task {
    protected boolean isDone;
    protected String description;

    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    public String getStatusIcon (boolean isDone) {
        return (isDone ? "X" : " ");
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String printTask () {
        return "[" + getStatusIcon(isDone) + "] " + description;
    }
}

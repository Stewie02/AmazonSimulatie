package com.nhlstenden.amazonsimulatie.models.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class Assignment {

    private final Queue<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask() {
        return tasks.peek();
    }

    public Task removeTask() {
        return tasks.poll();
    }

    public Queue<Task> getTasks() {
        return this.tasks;
    }

}

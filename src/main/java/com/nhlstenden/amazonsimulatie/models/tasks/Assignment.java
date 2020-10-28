package com.nhlstenden.amazonsimulatie.models.tasks;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An Assignment consists of different tasks that needs to be completed
 */
public class Assignment {

    private final Queue<Task> tasks = new LinkedList<>();

    /**
     * Add an Task to the Assignment
     * @param task Task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Return the next Task that needs to be executed
     * @return The next Task
     */
    public Task getTask() {
        return tasks.peek();
    }

    /**
     * Removes the first Task from the Assignment
     * @return The removed Task
     */
    public Task removeTask() {
        return tasks.poll();
    }

    /**
     * Returns all the Tasks in the Assignment
     * @return The Queue of Tasks
     */
    public Queue<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns true if the Assignment is finished
     * @return true if the Assignment is finished
     */
    public boolean isFinished() {
        return this.tasks.isEmpty();
    }

}

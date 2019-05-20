package com.mikkri.mazecrawler;

import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Deque;
import java.util.LinkedList;

@Component
public class MazeSolver {
    @Nonnull
    public MazeAnswer solveMaze(@Nonnull Maze maze) {
        MazeSolverQueue mazeSolverQueue = new MazeSolverQueue();
        mazeSolverQueue.addStep(new MazeSolverStep(maze.getStartX(), maze.getStartY(), 0));

        return new MazeAnswer(null, null);
    }
}

class MazeSolverStep {
    private final int x;
    private final int y;
    private final int counter;

    public MazeSolverStep(int x, int y, int counter) {
        this.x = x;
        this.y = y;
        this.counter = counter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCounter() {
        return counter;
    }
}

class MazeSolverQueue {
    private final Deque<MazeSolverStep> queue = new LinkedList<>();

    public void addStep(MazeSolverStep step) {
        queue.add(step);
    }
}
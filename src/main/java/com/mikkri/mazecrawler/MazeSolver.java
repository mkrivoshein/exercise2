package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;

@Component
public class MazeSolver {
    @Nonnull
    public MazeAnswer solveMaze(@Nonnull Maze maze) {
        MazeSolverStep[][] mazeMap = new MazeSolverStep[maze.rowCount()][];
        for (int row = 0; row < mazeMap.length; row++) {
            mazeMap[row] = new MazeSolverStep[maze.rowSize(row)];
        }

        MazeSolverQueue mazeSolverQueue = new MazeSolverQueue();

        generateMazeSolverSteps(maze, mazeSolverQueue, maze.getStartX(), maze.getStartY(), 0, null);

        boolean solutionFound = false;
        MazeSolverStep step = null;

        while (!solutionFound && !mazeSolverQueue.isEmpty()) {
            step = mazeSolverQueue.removeStep();

            int x = step.getX();
            int y = step.getY();

            char currentValue = maze.getValue(x, y);

            if (currentValue == Maze.END) {
                // found the answer
                solutionFound = true;
            } else if (currentValue != Maze.START && currentValue != Maze.WALL) {
                MazeSolverStep currentMapValue = mazeMap[y][x];

                if (currentMapValue == null || currentMapValue.getCounter() > step.getCounter()) {
                    // either first time here or found a faster path
                    mazeMap[y][x] = step;
                    generateMazeSolverSteps(maze, mazeSolverQueue, step);
                }
            }
        }

        if (solutionFound) {
            // unroll the quickest path
            step = step.getPreviousStep(); // one step back from the end
            for ( ; step != null; step = step.getPreviousStep()) {
                maze.setValue(step.getX(), step.getY(), Maze.PATH);
            }

            return new MazeAnswer(maze.toStringList(), null);
        } else {
            return new MazeAnswer(null, ImmutableList.of("There is no path from S to E"));
        }
    }

    void generateMazeSolverSteps(@Nonnull Maze maze, MazeSolverQueue mazeSolverQueue, MazeSolverStep previousStep) {
        int x = previousStep.getX();
        int y = previousStep.getY();
        int counter = previousStep.getCounter();

        generateMazeSolverSteps(maze, mazeSolverQueue, x, y, counter, previousStep);
    }

    void generateMazeSolverSteps(@Nonnull Maze maze, MazeSolverQueue mazeSolverQueue, int x, int y, int counter, @Nullable MazeSolverStep previousStep) {
        if (maze.isValidLocation(x + 1, y)) {
            mazeSolverQueue.addStep(new MazeSolverStep(x + 1, y, counter + 1, previousStep));
        }

        if (maze.isValidLocation(x - 1, y)) {
            mazeSolverQueue.addStep(new MazeSolverStep(x - 1, y, counter + 1, previousStep));
        }

        if (maze.isValidLocation(x, y + 1)) {
            mazeSolverQueue.addStep(new MazeSolverStep(x, y + 1, counter + 1, previousStep));
        }

        if (maze.isValidLocation(x, y - 1)) {
            mazeSolverQueue.addStep(new MazeSolverStep(x, y - 1, counter + 1, previousStep));
        }
    }

}

class MazeSolverStep {
    private final int x;
    private final int y;
    private final int counter;
    @Nullable
    private final MazeSolverStep previousStep;

    public MazeSolverStep(int x, int y, int counter, @Nullable MazeSolverStep previousStep) {
        this.x = x;
        this.y = y;
        this.counter = counter;
        this.previousStep = previousStep;
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

    @Nullable
    public MazeSolverStep getPreviousStep() {
        return previousStep;
    }
}

class MazeSolverQueue {
    private final Deque<MazeSolverStep> queue = new LinkedList<>();

    public void addStep(MazeSolverStep step) {
        queue.add(step);
    }

    public MazeSolverStep removeStep() {
        return queue.remove();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
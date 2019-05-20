package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;

import static com.mikkri.mazecrawler.model.Maze.*;

@Component
public class MazeSolver {
    @Nonnull
    public MazeAnswer solveMaze(@Nonnull Maze mazeToSolve) {
        MazeSolverTask task = new MazeSolverTask(mazeToSolve);
        return task.solveMaze();
    }

    static class MazeSolverTask {
        private final Maze maze;
        private final MazeSolverStep[][] mazeMap;
        private final MazeSolverQueue mazeSolverQueue = new MazeSolverQueue();

        MazeSolverTask(Maze maze) {
            // we will use our copy of the maze to plot the path, so need to make a copy
            this.maze = copyOf(maze);
            this.mazeMap = initializeMazeMap(maze);
        }

        MazeAnswer solveMaze() {
            prepareFirstSteps();
            boolean solutionFound = crawlThroughTheMaze();
            if (solutionFound) {
                updateMazeWithPath();
                return new MazeAnswer(getMaze().toStringList(), null);
            } else {
                return new MazeAnswer(null, ImmutableList.of("There is no path from S to E"));
            }
        }

        /**
         * Creates an array of <code>MazeSolverStep</code> that matches shape of the <code>maze</code>
         */
        @Nonnull
        private static MazeSolverStep[][] initializeMazeMap(@Nonnull Maze maze) {
            MazeSolverStep[][] result = new MazeSolverStep[maze.rowCount()][];

            for (int row = 0; row < result.length; row++) {
                result[row] = new MazeSolverStep[maze.rowSize(row)];
            }

            return result;
        }

        /**
         * Planning steps up, down, left, right from the start location
         */
        private void prepareFirstSteps() {
            prepareMoreSteps(maze, mazeSolverQueue, maze.getStartX(), maze.getStartY(), null);
        }

        /**
         * @return <code>true</code> if a path is found
         */
        private boolean crawlThroughTheMaze() {
            boolean solutionFound = false;
            MazeSolverStep step;

            while (!solutionFound && !mazeSolverQueue.isEmpty()) {
                step = mazeSolverQueue.removeStep();

                int x = step.getX();
                int y = step.getY();

                if (mazeMap[y][x] != null) {
                    // this location was visited before
                    continue;
                }

                char valueAtLocation = maze.getValue(x, y);

                if (valueAtLocation == END) {
                    // found the answer
                    solutionFound = true;
                    mazeMap[y][x] = step;
                } else if (valueAtLocation != START && valueAtLocation != WALL) {
                    // a valid location for making further advance and it is our first time visit
                    mazeMap[y][x] = step;
                    prepareMoreSteps(maze, mazeSolverQueue, step);
                }
            }

            return solutionFound;
        }

        private void updateMazeWithPath() {
            // unroll the quickest path
            MazeSolverStep finalMazeSolverStep = getFinalMazeSolverStep();

            // check for null as it is possible we never found the path to the maze end location
            // if it happens, do nothing
            if (finalMazeSolverStep != null) {
                MazeSolverStep step = finalMazeSolverStep.getPreviousStep(); // one step back from the end
                for (; step != null; step = step.getPreviousStep()) {
                    maze.setValue(step.getX(), step.getY(), PATH);
                }
            }
        }

        private void prepareMoreSteps(@Nonnull Maze maze, MazeSolverQueue mazeSolverQueue, MazeSolverStep previousStep) {
            int x = previousStep.getX();
            int y = previousStep.getY();

            prepareMoreSteps(maze, mazeSolverQueue, x, y, previousStep);
        }

        private void prepareMoreSteps(@Nonnull Maze maze, MazeSolverQueue mazeSolverQueue, int x, int y, @Nullable MazeSolverStep previousStep) {
            if (maze.isValidLocation(x + 1, y)) {
                mazeSolverQueue.addStep(new MazeSolverStep(x + 1, y, previousStep));
            }

            if (maze.isValidLocation(x - 1, y)) {
                mazeSolverQueue.addStep(new MazeSolverStep(x - 1, y, previousStep));
            }

            if (maze.isValidLocation(x, y + 1)) {
                mazeSolverQueue.addStep(new MazeSolverStep(x, y + 1, previousStep));
            }

            if (maze.isValidLocation(x, y - 1)) {
                mazeSolverQueue.addStep(new MazeSolverStep(x, y - 1, previousStep));
            }
        }

        /**
         * A helper method to access <code>MazeSolverStep</code> at maze end location
         */
        @Nullable
        private MazeSolverStep getFinalMazeSolverStep() {
            return mazeMap[maze.getEndY()][maze.getEndX()];
        }

        @Nonnull
        public Maze getMaze() {
            return maze;
        }
    }
}

class MazeSolverStep {
    private final int x;
    private final int y;
    @Nullable
    private final MazeSolverStep previousStep;

    MazeSolverStep(int x, int y, @Nullable MazeSolverStep previousStep) {
        this.x = x;
        this.y = y;
        this.previousStep = previousStep;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Nullable
    MazeSolverStep getPreviousStep() {
        return previousStep;
    }
}

class MazeSolverQueue {
    private final Deque<MazeSolverStep> queue = new LinkedList<>();

    void addStep(MazeSolverStep step) {
        queue.add(step);
    }

    MazeSolverStep removeStep() {
        return queue.remove();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}
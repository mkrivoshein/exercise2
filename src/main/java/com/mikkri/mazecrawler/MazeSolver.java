package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.mikkri.mazecrawler.model.Maze.END;
import static com.mikkri.mazecrawler.model.Maze.PATH;
import static com.mikkri.mazecrawler.model.Maze.START;
import static com.mikkri.mazecrawler.model.Maze.WALL;
import static com.mikkri.mazecrawler.model.Maze.copyOf;

@Component
public class MazeSolver {
    @Nonnull
    public MazeAnswer solveMaze(@Nonnull Maze mazeToSolve) {
        MazeSolverTask task = new MazeSolverTask(mazeToSolve);
        return task.solveMaze();
    }

    /**
     * Maze solver task starts crawling through the maze from the start location and iterates through
     * potential next steps in order of their distance from the start location. This way it finds the quickest path
     * first and doesn't put any effort into finding longer paths. If there are multiple paths of the same length
     * it picks the first it finds.
     *
     * This maze crawler respects walls, however if walls have gaps it can step outside as far as maze boundaries permit.
     *
     * To use this class, instantiate an instance with a maze to solve and then call <code>solveMaze()</code> once.
     *
     * This implementation assumes that the maze itself is reasonably small and fits into memory easily.
     */
    static class MazeSolverTask {
        private final Maze maze;
        private final MazeSolverStep[][] mazeMap;
        private final MazeSolverQueue mazeSolverQueue = new MazeSolverQueue();

        MazeSolverTask(Maze maze) {
            this.maze = copyOf(maze); // eager to clone input data to avoid potential side effects
            this.mazeMap = initializeMazeMap(maze);
        }

        @Nonnull
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

            // this loop works a bit like recursion but it is designed to inspect locations closer to the start first
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

        /**
         * Uses <code>Maze.PATH</code> symbols to plot the path in the maze.
         * Since it is called after <code>crawlThroughTheMaze()</code> it should be ok to modify <code>maze</code> instead
         * of making a copy and changing data there.
         */
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

        /**
         * Prepare steps up, down, left, right from the current location described by <code>previousStep</code>.
         * This method adds new steps to the queue.
         */
        private void prepareMoreSteps(@Nonnull Maze maze, MazeSolverQueue mazeSolverQueue, MazeSolverStep previousStep) {
            int x = previousStep.getX();
            int y = previousStep.getY();

            prepareMoreSteps(maze, mazeSolverQueue, x, y, previousStep);
        }

        /**
         * Prepare steps up, down, left, right from (x,y).
         * This method adds new steps to the queue.
         */
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

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .toString();
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
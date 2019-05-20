package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MazeSolverTest {
    private MazeSolver mazeSolver = new MazeSolver();

    @Test
    public void solveSimpleMaze() {
        MazeAnswer result = mazeSolver.solveMaze(TestMazes.SIMPLE_MAZE);
        assertThat(result, is(TestMazes.SIMPLE_MAZE_ANSWER));
    }

    /**
     * Solve the maze from the exercise description
     */
    @Test
    public void solveOriginalMaze() {
        Maze maze = TestMazes.originalMaze();
        MazeAnswer result = mazeSolver.solveMaze(maze);
        assertThat(result.getAnswerText(), is(TestMazes.originalMazeAnswerText()));
    }

    @Test
    public void solveUnsolvableMaze() {
        Maze maze = TestMazes.unsolvableMaze();
        MazeAnswer result = mazeSolver.solveMaze(maze);
        assertThat(result.getErrors(), is(ImmutableList.of("There is no path from S to E")));
    }

    /**
     * If a maze has no walls it still may be possible to find a path
     */
    @Test
    public void solveMazeWithoutWalls() {
        Maze maze = TestMazes.mazeWithoutWalls();
        MazeAnswer result = mazeSolver.solveMaze(maze);
        assertThat(result.getAnswerText(), is(TestMazes.mazeWithoutWallsAnswerText()));
    }

    /**
     * If a maze has windows in its walls, check that the solver doesn't fall through
     */
    @Test
    public void solveMazeWithWindows() {
        Maze maze = TestMazes.mazeWithWindows();
        MazeAnswer result = mazeSolver.solveMaze(maze);
        assertThat(result.getAnswerText(), is(TestMazes.mazeWithWindowsAnswerText()));
    }
}
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

    @Test
    public void solveUnsolvableMaze() {
        Maze maze = new MazeParser().parseMaze(TestMazes.unsolvableMaze());
        MazeAnswer result = mazeSolver.solveMaze(maze);
        assertThat(result.getErrors(), is(ImmutableList.of("There is no path from S to E")));
    }
}
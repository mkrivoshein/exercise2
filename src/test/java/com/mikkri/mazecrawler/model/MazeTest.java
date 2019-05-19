package com.mikkri.mazecrawler.model;

import com.mikkri.mazecrawler.MazeParser;
import org.junit.Test;

import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE;
import static com.mikkri.mazecrawler.TestMazes.unsolvableMaze;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MazeTest {
    @Test
    public void mazeToString() {
        String string = SIMPLE_MAZE.toString();
        assertThat(string, is("Maze\n#\t#\t#\t#\t\n#\tS\t \t#\t\n#\t \tE\t#\t\n#\t#\t#\t#\t\n"));
    }

    @Test
    public void compareTwoInstancesOfTheSameMaze() {
        Maze maze1 = new MazeParser().parseMaze(unsolvableMaze());
        Maze maze2 = new MazeParser().parseMaze(unsolvableMaze());

        assertEquals(maze1, maze2);
    }
}
package com.mikkri.mazecrawler.model;

import org.junit.Test;

import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE;
import static com.mikkri.mazecrawler.TestMazes.unsolvableMaze;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MazeTest {
    @Test
    public void mazeToString() {
        String string = SIMPLE_MAZE.toString();
        assertThat(string, is("Maze\n#\t#\t#\t#\t\n#\t \t \t#\t\n#\tS\t \t#\t\n#\t \tE\t#\t\n#\t#\t#\t#\t\n"));
    }

    @Test
    public void compareTwoInstancesOfTheSameMaze() {
        Maze maze1 = unsolvableMaze();
        Maze maze2 = unsolvableMaze();

        assertEquals(maze1, maze2);
    }

    @Test
    public void testGetStartX() {
        assertThat(SIMPLE_MAZE.getStartX(), is(1));
    }

    @Test
    public void testGetStartY() {
        assertThat(SIMPLE_MAZE.getStartY(), is(2));
    }

    @Test
    public void testGetEndX() {
        assertThat(SIMPLE_MAZE.getEndX(), is(2));
    }

    @Test
    public void testGetEndY() {
        assertThat(SIMPLE_MAZE.getEndY(), is(3));
    }

    @Test
    public void getValueTest() {
        Maze maze = unsolvableMaze();
        assertThat(maze.getValue(0, 0), is(Maze.WALL));
    }

    @Test
    public void setValueTest() {
        Maze maze = unsolvableMaze();

        maze.setValue(1, 2, '.');

        assertThat(maze.getValue(1, 2), is('.'));
    }

    @Test
    public void copyOfMakesDeepCopyOfMazeData() throws CloneNotSupportedException {
        Maze maze1 = unsolvableMaze();
        char originalValue = maze1.getValue(1, 1);

        Maze maze2 = Maze.copyOf(maze1);

        maze1.setValue(1, 1, Maze.PATH);

        assertThat(maze2.getValue(1, 1), is(originalValue));
        assertThat(maze2, is(not(maze1)));
    }
}
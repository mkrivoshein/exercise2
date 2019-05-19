package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MazeParserTest {
    private MazeParser mazeParser = new MazeParser();

    @Test(expected = IllegalArgumentException.class)
    public void parseEmptyMaze() {
        mazeParser.parseMaze(ImmutableList.of());
    }

    @Test
    public void parseSimpleMaze() {
        Maze result = mazeParser.parseMaze(TestMazes.SIMPLE_MAZE_TEXT);
        assertThat(result, is(TestMazes.SIMPLE_MAZE));
    }

    @Test
    public void parseIllegalMaze() {
        try {
            mazeParser.parseMaze(TestMazes.illegalMazeText());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException exception) {
            assertThat(exception.getMessage(), is("Only '#', ' ', 'S', 'E' characters are allowed"));
        }
    }

    @Test
    public void parseMazeWithMultipleEnds() {
        try {
            mazeParser.parseMaze(TestMazes.mazeWithMultipleEnds());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException exception) {
            assertThat(exception.getMessage(), is("Maze must contain 'E' exactly once"));
        }
    }

    @Test
    public void parseMazeWithoutEnd() {
        try {
            mazeParser.parseMaze(TestMazes.mazeWithoutEnd());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException exception) {
            assertThat(exception.getMessage(), is("Maze must contain 'E' exactly once"));
        }
    }

    @Test
    public void parseMazeWithMultipleStarts() {
        try {
            mazeParser.parseMaze(TestMazes.mazeWithMultipleStarts());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException exception) {
            assertThat(exception.getMessage(), is("Maze must contain 'S' exactly once"));
        }
    }

    @Test
    public void parseMazeWithoutStart() {
        try {
            mazeParser.parseMaze(TestMazes.mazeWithoutStart());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException exception) {
            assertThat(exception.getMessage(), is("Maze must contain 'S' exactly once"));
        }
    }
}
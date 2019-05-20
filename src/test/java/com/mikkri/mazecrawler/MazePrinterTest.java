package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.junit.Test;

import java.util.List;

import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE_ANSWER_TEXT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MazePrinterTest {
    private MazePrinter mazePrinter = new MazePrinter();

    @Test
    public void printOneError() {
        MazeAnswer mazeAnswer = new MazeAnswer(null, ImmutableList.of("Dummy error"));
        List<String> result = mazePrinter.print(mazeAnswer);

        assertThat(result, is(ImmutableList.of("Maze crawler encountered errors:", "- Dummy error")));
    }

    @Test
    public void printMultipleError() {
        MazeAnswer mazeAnswer = new MazeAnswer(null, ImmutableList.of("The first problem", "The second problem"));
        List<String> result = mazePrinter.print(mazeAnswer);

        assertThat(result, is(ImmutableList.of("Maze crawler encountered errors:", "- The first problem", "- The second problem")));
    }

    @Test
    public void printSolvedMaze() {
        MazeAnswer mazeAnswer = new MazeAnswer(SIMPLE_MAZE_ANSWER_TEXT, null);
        List<String> result = mazePrinter.print(mazeAnswer);
        assertThat(result, is(SIMPLE_MAZE_ANSWER_TEXT));
    }
}
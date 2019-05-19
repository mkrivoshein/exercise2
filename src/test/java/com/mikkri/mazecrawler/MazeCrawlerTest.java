package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE;
import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE_ANSWER;
import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE_ANSWER_TEXT;
import static com.mikkri.mazecrawler.TestMazes.SIMPLE_MAZE_TEXT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MazeCrawlerTest {
    @MockBean
    private MazeParser mazeParser;
    @MockBean
    private MazeSolver mazeSolver;
    @MockBean
    private MazePrinter mazePrinter;
    @Autowired
    private MazeCrawler mazeCrawler;

    @Test
    public void testMazeCrawlerBehaviour() {
        // prepare
        when(mazeParser.parseMaze(SIMPLE_MAZE_TEXT)).thenReturn(SIMPLE_MAZE);
        when(mazeSolver.solveMaze(SIMPLE_MAZE)).thenReturn(SIMPLE_MAZE_ANSWER);
        when(mazePrinter.print(SIMPLE_MAZE_ANSWER)).thenReturn(SIMPLE_MAZE_ANSWER_TEXT);

        // execute
        List<String> result = mazeCrawler.execute(SIMPLE_MAZE_TEXT);

        // verify
        assertThat(result, is(SIMPLE_MAZE_ANSWER_TEXT));
    }
}
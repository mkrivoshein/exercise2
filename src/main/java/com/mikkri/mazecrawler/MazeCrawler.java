package com.mikkri.mazecrawler;

import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class MazeCrawler {
    @Autowired
    MazeParser mazeParser;
    @Autowired
    MazeSolver mazeSolver;
    @Autowired
    MazePrinter mazePrinter;

    @Nonnull
    public List<String> execute(@Nonnull List<String> input) {
        Maze maze = mazeParser.parseMaze(input);
        MazeAnswer answer = mazeSolver.solveMaze(maze);
        return mazePrinter.print(answer);
    }
}

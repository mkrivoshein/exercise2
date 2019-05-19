package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class MazeSolver {
    @Nonnull
    public MazeAnswer solveMaze(@Nonnull Maze maze) {
        return new MazeAnswer(ImmutableList.of(), ImmutableList.of("Unable to solve the maze"));
    }
}

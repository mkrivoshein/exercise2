package com.mikkri.mazecrawler;

import com.mikkri.mazecrawler.model.Maze;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class MazeParser {
    @Nonnull
    public Maze parseMaze(@Nonnull List<String> lines) {
        return new Maze();
    }
}

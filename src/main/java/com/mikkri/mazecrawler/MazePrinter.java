package com.mikkri.mazecrawler;

import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MazePrinter {
    @Nonnull
    public List<String> print(@Nonnull MazeAnswer answer) {
        return new ArrayList<>();
    }
}

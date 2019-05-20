package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.MazeAnswer;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Component
public class MazePrinter {
    @Nonnull
    public List<String> print(@Nonnull MazeAnswer answer) {
        List<String> errors = answer.getErrors();
        if (errors != null && !errors.isEmpty()) {
            List<String> result = new ArrayList<>(errors.size() + 1);
            result.add("Maze crawler encountered errors:");

            errors.forEach((error) -> result.add("- " + error));

            return result;
        }

        return ImmutableList.copyOf(answer.getAnswerText());
    }
}

package com.mikkri.mazecrawler.model;

import javax.annotation.Nullable;
import java.util.List;

public class MazeAnswer {
    @Nullable
    private final List<String> answerText;
    @Nullable
    private final List<String> errors;

    public MazeAnswer(@Nullable List<String> answerText, @Nullable List<String> errors) {
        this.answerText = answerText;
        this.errors = errors;
    }

    public List<String> getAnswerText() {
        return answerText;
    }

    public List<String> getErrors() {
        return errors;
    }
}

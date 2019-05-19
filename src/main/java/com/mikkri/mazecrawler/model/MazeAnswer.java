package com.mikkri.mazecrawler.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class MazeAnswer {
    private final List<String> answerText;
    private final List<String> errors;

    public MazeAnswer(List<String> answerText, List<String> errors) {
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

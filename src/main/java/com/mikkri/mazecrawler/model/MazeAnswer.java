package com.mikkri.mazecrawler.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.MoreObjects.toStringHelper;

public class MazeAnswer {
    @Nonnull
    private final List<String> answerText;
    @Nonnull
    private final List<String> errors;

    public MazeAnswer(@Nullable List<String> answerText, @Nullable List<String> errors) {
        this.answerText = firstNonNull(answerText, Collections.emptyList());
        this.errors = firstNonNull(errors, Collections.emptyList());
    }

    public List<String> getAnswerText() {
        return answerText;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeAnswer answer = (MazeAnswer) o;
        return Objects.equals(answerText, answer.answerText) &&
                Objects.equals(errors, answer.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerText, errors);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("answer", answerText)
                .add("errors", errors)
                .toString();
    }
}

package com.mikkri.mazecrawler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MazeSolverStepTest {
    @Test
    public void testMazeSolverStepWithNullPreviousStep() {
        MazeSolverStep step = new MazeSolverStep(3, 5, null);
        assertThat(step.toString(), is("MazeSolverStep{x=3, y=5}"));
    }

    @Test
    public void testMazeSolverStepWithNonNullPreviousStep() {
        MazeSolverStep step = new MazeSolverStep(2, 15, mock(MazeSolverStep.class));
        assertThat(step.toString(), is("MazeSolverStep{x=2, y=15}"));
    }
}
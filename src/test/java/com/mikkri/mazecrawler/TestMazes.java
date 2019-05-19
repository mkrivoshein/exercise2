package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;

import java.util.List;

public class TestMazes {
    public static final List<String> SIMPLE_MAZE_TEXT = ImmutableList.of("####", "#S #", "# E#", "####");
    public static final List<String> SIMPLE_MAZE_ANSWER_TEXT = ImmutableList.of("####", "#S.#", "# E#", "####");
    public static final Maze SIMPLE_MAZE = new MazeParser().parseMaze(SIMPLE_MAZE_TEXT);
    public static final MazeAnswer SIMPLE_MAZE_ANSWER = new MazeAnswer(SIMPLE_MAZE_ANSWER_TEXT, ImmutableList.of());
}

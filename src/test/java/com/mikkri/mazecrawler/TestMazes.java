package com.mikkri.mazecrawler;

import com.google.common.collect.ImmutableList;
import com.mikkri.mazecrawler.model.Maze;
import com.mikkri.mazecrawler.model.MazeAnswer;

import javax.annotation.Nonnull;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestMazes {
    public static final List<String> SIMPLE_MAZE_TEXT = ImmutableList.of("####", "#S #", "# E#", "####");
    public static final List<String> SIMPLE_MAZE_ANSWER_TEXT = ImmutableList.of("####", "#S.#", "# E#", "####");
    public static final Maze SIMPLE_MAZE = new MazeParser().parseMaze(SIMPLE_MAZE_TEXT);
    public static final MazeAnswer SIMPLE_MAZE_ANSWER = new MazeAnswer(SIMPLE_MAZE_ANSWER_TEXT, ImmutableList.of());

    @Nonnull
    public static List<String> illegalMazeText() {
        return mazeText("illegalMaze.txt");
    }

    @Nonnull
    public static List<String> mazeWithMultipleEnds() {
        return mazeText("mazeWithMultipleEnds.txt");
    }

    @Nonnull
    public static List<String> mazeWithMultipleStarts() {
        return mazeText("mazeWithMultipleStarts.txt");
    }

    @Nonnull
    public static List<String> mazeWithoutEnd() {
        return mazeText("mazeWithoutEnd.txt");
    }

    @Nonnull
    public static List<String> mazeWithoutStart() {
        return mazeText("mazeWithoutStart.txt");
    }

    @Nonnull
    public static List<String> mazeWithoutWalls() {
        return mazeText("mazeWithoutWalls.txt");
    }

    @Nonnull
    public static List<String> mazeWithWindows() {
        return mazeText("mazeWithWindows.txt");
    }

    @Nonnull
    public static List<String> unsolvableMaze() {
        return mazeText("unsolvableMaze.txt");
    }

    private static List<String> mazeText(String fileName) {
        URL url = TestMazes.class.getResource(fileName);
        try {
            return Files.readAllLines(Paths.get(url.toURI()));
        } catch (Exception exception) {
            throw new RuntimeException("Unable to load text resources from " + fileName, exception);
        }
    }
}

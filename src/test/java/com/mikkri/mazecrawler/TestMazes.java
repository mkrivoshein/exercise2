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
    private static final MazeParser MAZE_PARSER = new MazeParser();

    public static final List<String> SIMPLE_MAZE_TEXT = ImmutableList.of("####", "#  #", "#S #", "# E#", "####");
    public static final List<String> SIMPLE_MAZE_ANSWER_TEXT = ImmutableList.of("####", "#  #", "#S.#", "# E#", "####");
    public static final Maze SIMPLE_MAZE = MAZE_PARSER.parseMaze(SIMPLE_MAZE_TEXT);
    public static final MazeAnswer SIMPLE_MAZE_ANSWER = new MazeAnswer(SIMPLE_MAZE_ANSWER_TEXT, ImmutableList.of());

    @Nonnull
    public static List<String> illegalMazeText() {
        return readFile("illegalMaze.txt");
    }

    @Nonnull
    public static List<String> mazeWithMultipleEndsText() {
        return readFile("mazeWithMultipleEnds.txt");
    }

    @Nonnull
    public static List<String> mazeWithMultipleStartsText() {
        return readFile("mazeWithMultipleStarts.txt");
    }

    @Nonnull
    public static List<String> mazeWithoutEndText() {
        return readFile("mazeWithoutEnd.txt");
    }

    @Nonnull
    public static List<String> mazeWithoutStartText() {
        return readFile("mazeWithoutStart.txt");
    }

    @Nonnull
    public static List<String> originalMazeText() {
        return readFile("originalMaze.txt");
    }

    @Nonnull
    public static List<String> originalMazeAnswerText() {
        return readFile("originalMazeAnswer.txt");
    }

    @Nonnull
    public static Maze originalMaze() {
        return MAZE_PARSER.parseMaze(originalMazeText());
    }

    @Nonnull
    public static List<String> mazeWithoutWallsText() {
        return readFile("mazeWithoutWalls.txt");
    }


    public static List<String> mazeWithoutWallsAnswerText() {
        return readFile("mazeWithoutWallsAnswer.txt");
    }

    @Nonnull
    public static Maze mazeWithoutWalls() {
        return MAZE_PARSER.parseMaze(mazeWithoutWallsText());
    }

    @Nonnull
    public static List<String> mazeWithWindowsText() {
        return readFile("mazeWithWindows.txt");
    }

    @Nonnull
    public static List<String> mazeWithWindowsAnswerText() {
        return readFile("mazeWithWindowsAnswer.txt");
    }

    @Nonnull
    public static Maze mazeWithWindows() {
        return MAZE_PARSER.parseMaze(mazeWithWindowsText());
    }

    @Nonnull
    public static List<String> unsolvableMazeText() {
        return readFile("unsolvableMaze.txt");
    }

    @Nonnull
    public static Maze unsolvableMaze() {
        return MAZE_PARSER.parseMaze(unsolvableMazeText());
    }

    private static List<String> readFile(String fileName) {
        URL url = TestMazes.class.getResource(fileName);
        try {
            return Files.readAllLines(Paths.get(url.toURI()));
        } catch (Exception exception) {
            throw new RuntimeException("Unable to load text from " + fileName, exception);
        }
    }
}

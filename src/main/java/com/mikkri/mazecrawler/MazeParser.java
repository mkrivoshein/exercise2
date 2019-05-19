package com.mikkri.mazecrawler;

import com.mikkri.mazecrawler.model.Maze;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class MazeParser {
    @Nonnull
    public Maze parseMaze(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Maze cannot be empty");
        }

        int[][] data = new int[lines.size()][];

        int[] start = null;

        int[] end = null;

        int rowCounter = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();

            int[] row = new int[chars.length];

            for (int index = 0; index < chars.length; index++) {
                switch (chars[index]) {
                    case '#':
                        row[index] = Maze.WALL;
                        break;
                    case ' ':
                        row[index] = Maze.UNKNOWN;
                        break;
                    case 'S':
                        row[index] = Maze.START;

                        if (start != null) {
                            throw new IllegalArgumentException("Maze must contain 'S' exactly once");
                        }

                        start = new int[]{rowCounter, index};

                        break;
                    case 'E':
                        row[index] = Maze.END;

                        if (end != null) {
                            throw new IllegalArgumentException("Maze must contain 'E' exactly once");
                        }

                        end = new int[]{rowCounter, index};

                        break;
                    default:
                        throw new IllegalArgumentException("Only '#', ' ', 'S', 'E' characters are allowed");
                }
            }

            data[rowCounter] = row;
            rowCounter++;
        }

        if (start == null) {
            throw new IllegalArgumentException("Maze must contain 'S' exactly once");
        }

        if (end == null) {
            throw new IllegalArgumentException("Maze must contain 'E' exactly once");
        }

        return new Maze(data, start, end);
    }
}

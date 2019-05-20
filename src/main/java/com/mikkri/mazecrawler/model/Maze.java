package com.mikkri.mazecrawler.model;

import java.util.Arrays;

public class Maze {
    public static final int START = 0;
    public static final int END = Integer.MAX_VALUE;
    public static final int UNKNOWN = Integer.MIN_VALUE;
    public static final int WALL = -1;

    private final int[][] data;

    /** {x,y} array representing the location of the start */
    private final int[] start;

    /** {x,y} array representing the location of the end */
    private final int[] end;

    public Maze(int[][] data, int[] start, int[] end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        return Arrays.deepEquals(data, maze.data) &&
                Arrays.equals(start, maze.start) &&
                Arrays.equals(end, maze.end);
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(start) + Arrays.hashCode(end);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Maze\n");

        for (int[] row : data) {
            for (int element : row) {
                switch (element) {
                    case START :
                        result.append('S');
                        break;
                    case END:
                        result.append('E');
                        break;
                    case UNKNOWN:
                        result.append(' ');
                        break;
                    case WALL:
                        result.append('#');
                        break;
                    default:
                        result.append(element);
                }
                result.append('\t');
            }
            result.append('\n');
        }

        return result.toString();
    }

    public int getStartX() {
        return start[1];
    }

    public int getStartY() {
        return start[0];
    }

    public int getValue(int x, int y) {
        return data[y][x];
    }

    public void setValue(int x, int y, int value) {
        data[y][x] = value;
    }
}

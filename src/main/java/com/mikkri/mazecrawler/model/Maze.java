package com.mikkri.mazecrawler.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    public static final char START = 'S';
    public static final char END = 'E';
    public static final char WALL = '#';
    public static final char SPACE = ' ';
    public static final char PATH = '.';

    private final char[][] data;

    /** {x,y} array representing the location of the start */
    private final int[] start;

    /** {x,y} array representing the location of the end */
    private final int[] end;

    public Maze(char[][] data, int[] start, int[] end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    /**
     * Creates a deep copy of the original maze
     */
    public static Maze copyOf(Maze maze) {
        char[][] copyOfData = new char[maze.data.length][];
        for (int index = 0; index < maze.data.length; index++) {
            copyOfData[index] = maze.data[index].clone();
        }
        int[] copyOfStart = maze.start.clone();
        int[] copyOfEnd = maze.end.clone();

        return new Maze(copyOfData, copyOfStart, copyOfEnd);
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

        for (char[] row : data) {
            for (Object element : row) {
                result.append(element).append('\t');
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

    public int getEndX() {
        return end[1];
    }

    public int getEndY() {
        return end[0];
    }

    public char getValue(int x, int y) {
        return data[y][x];
    }

    public void setValue(int x, int y, char value) {
        data[y][x] = value;
    }

    /**
     * Method checks if (x,y) is a part of the maze. It doesn't check if (x,y) is a wall or any other location
     * with special meaning
     * @return <code>true</code> if (x,y) is within boundaries of the maze
     */
    public boolean isValidLocation(int x, int y) {
        return y >= 0 && x >= 0 && y < data.length && x < data[y].length;
    }

    public int rowCount() {
        return data.length;
    }

    public int rowSize(int row) {
        return data[row].length;
    }

    public List<String> toStringList() {
        List<String> result = new ArrayList<>(data.length);
        for (char[] row : data) {
            result.add(String.valueOf(row));
        }
        return result;
    }
}

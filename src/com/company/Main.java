package com.company;

public class Main {

    public static void main(String[] args) {
//	    for (int i = 0; i <= 99; ++i) {
//            System.out.println(i);
//        }

        int i = 0, size = 50;
        int[] tab = new int[size];

        for (i = size - 1; i >= 0; --i) {
            tab[size - 1 - i] = i;
        }

        i = 0;
        while (i < size) {
//            System.out.println(tab[i]);
            i++;
        }

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

        int rows = 100, columns = 50;
        int[][] arr = new int[rows][columns];

        int row = 0, column = 0;
        while (row < rows && column < columns) {
            arr[row][column] = row * column;

            column++;
            if (column == columns) {
                column = 0;
                row++;
            }
        }

        for (int[] x : arr) {
            System.out.print("[");
            for (int item : x) {
                System.out.print(item + "\t\t");
            }
            System.out.println("]");
        }

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    }
}

package main.logic;
import java.util.ArrayList;
import main.models.ReadFile;


public class GameManager {
    static String filename;
    static long attempt = 0;
    public static boolean solutionFound = false;
    public static char[][] grid_solution;
    static long startTime;
    static long endTime;
    static int iteration;

    private static BoardUpdateListener boardUpdateListener;


    public static void setBoardUpdateListener(BoardUpdateListener listener) {
        boardUpdateListener = listener;
    }

    public interface BoardUpdateListener {
        void onBoardUpdate(char[][] currentBoard);
    }
    public static PuzzleResult runSolver(String file) {
        filename = file;
        
        int[] boardSpecs = ReadFile.BoardSpecs(filename);
        String mode = ReadFile.configuration(filename);
        int N = boardSpecs[0];
        int M = boardSpecs[1];
        
        char[][][] shapes = ReadFile.Shapes(filename, boardSpecs[2]);
        ArrayList<char[][]> listOfShapes = new ArrayList<>();
        for(char[][] block : shapes) {
            listOfShapes.add(block);
        }

        char[][] puzzle_board = new char[N][M];
        puzzle_board = initialize_board(puzzle_board, N, M);

        if(mode.equals("DEFAULT")){
            attempt = 0;
            solutionFound = false;
            startTime = System.nanoTime();
            bruteForce(listOfShapes, new boolean[listOfShapes.size()], puzzle_board, N, M);    
            endTime = System.nanoTime();
        }

        long time = (endTime - startTime) / 1_000_000;

        if(solutionFound){
            System.out.println("Solution Found!\n");
            print_board(grid_solution);
            System.out.println("\nEstimated time: " + time + "ms");
            System.out.println("Total attempts: " + attempt);

        }
        else {
            System.out.println("No solution!");
            System.out.println("Attempts: " + attempt);
        }

        return new PuzzleResult(solutionFound, attempt, time, grid_solution);
    }

    //mirroring    
    public static char[][] mirrorVertical(char[][]matrix, int row, int column){
        char[][] mirrored_matrix = new char[row][column];
        for (int i = 0 ; i < row ; i++){
            for (int j = 0 ; j < column ; j++){
                mirrored_matrix[i][j] = matrix[i][column-j-1];
            }
        }
        return mirrored_matrix;
    }
    public static char[][] mirrorHorizontal(char[][]matrix,int row, int column){
        char[][] mirrored_matrix = new char[row][column];
        for(int i = 0 ; i <row; i++){
            for(int j = 0 ; j <column; j++){
                mirrored_matrix[i][j] = matrix[row-i-1][j];
            }
        }
        return mirrored_matrix;
    }

    //rotation clockwise 
    public static char[][] rotate(char[][]matrix, int row, int column){
        char[][] transposed_matrix = new char[column][row];
        for ( int i = 0 ; i < row ; i ++){
            for ( int j = 0 ; j < column ; j++){
                transposed_matrix[j][i] = matrix[i][j];
            }
        }
        return mirrorVertical(transposed_matrix, column, row);
    }

    //print grid
    public static void print_board(char[][] matrix) {
        final String RESET = "\u001B[0m";
        final String[] COLORS = {
            "\u001B[31m",  // RED
            "\u001B[33m",  // ORANGE
            "\u001B[32m",  // YELLOW
            "\u001B[34m",  // GREEN
            "\u001B[35m",  // MAGENTA/PINK
            "\u001B[36m",  // CYAN
            "\u001B[95m",  // LIGHT MAGENTA
            "\u001B[94m"   // LIGHT BLUE
        };
    
        for (char[] row : matrix) {
            for (char c : row) {
                if (c == '.') {
                    System.out.print(c + "");
                } else {
                    int colorIndex = c - 'A';
                    System.out.print(COLORS[colorIndex % COLORS.length] + c + RESET);
                }
            }
            System.out.println();
        }
    }

    public static char[][] initialize_board(char[][] matrix, int row, int column){
        char[][] initialized_matrix = new char[row][column];
        for ( int i = 0 ; i < row ; i++){
            for ( int j = 0 ; j < column ; j++){
                initialized_matrix[i][j] = '.';
            }
        }
        return initialized_matrix;
    }

    public static boolean puzzle_done(char[][] matrix){
        for(char[] row : matrix){
            for (char c : row){
                if ( c == '.'){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validPosition(char[][] block, char[][] grid, int blockRow, int blockCol, int gridRowPosition, int gridColPosition) {
        if (gridRowPosition + blockRow > grid.length || gridColPosition + blockCol > grid[0].length) {
            return false;
        }
    
        for (int i = 0; i < blockRow; i++) {
            for (int j = 0; j < blockCol; j++) {
                if (block[i][j] != '.') {
                    if (grid[gridRowPosition + i][gridColPosition + j] != '.') {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean gameSolved(char[][] grid, boolean[] used) {
        for (char[] row : grid) {
            for (char c : row) {
                if (c == '.') {
                    return false; 
                }
            }
        }
        for (boolean blockUsed : used) {
            if (!blockUsed) {
                return false; 
            }
        }
        return true; 
    }

    public static char[][] placeBlock(char[][] grid, char[][] block, int blockRow, int blockCol, int gridRowPosition, int gridColPosition, int gridRow, int gridCol){
        for ( int i = 0 ; i < blockRow; i++){
            for (int j = 0 ; j < blockCol; j++){
                if(block[i][j] != '.'){
                    grid[gridRowPosition+i][gridColPosition+j] = block[i][j];
                }
            }
        }

        if(boardUpdateListener != null){
            boardUpdateListener.onBoardUpdate(copyGrid(grid)); //flag
        }
        return grid;
    }

    public static char[][] copyGrid(char[][] grid) {
        char[][] newGrid = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }

    public static ArrayList<char[][]> blockTransformation(char[][] block){
        ArrayList<char[][]> transformations = new ArrayList<>();
        transformations.add(block);
        char[][] rotated90 = rotate(block, block.length, block[0].length);
        transformations.add(rotated90);
        char[][] rotated180 = rotate(rotated90, rotated90.length, rotated90[0].length);
        transformations.add(rotated180);
        char[][] rotated270 = rotate(rotated180, rotated180.length, rotated180[0].length);
        transformations.add(rotated270);
        
        char[][] mirrored = mirrorVertical(block, block.length, block[0].length);
        transformations.add(mirrored);
        char[][] mirroredRotated90 = rotate(mirrored, mirrored.length, mirrored[0].length);
        transformations.add(mirroredRotated90);
        char[][] mirroredRotated180 = rotate(mirroredRotated90, mirroredRotated90.length, mirroredRotated90[0].length);
        transformations.add(mirroredRotated180);
        char[][] mirroredRotated270 = rotate(mirroredRotated180, mirroredRotated180.length, mirroredRotated180[0].length);
        transformations.add(mirroredRotated270);
        
        return transformations;
    }

    //bruteforce
    public static void bruteForce(ArrayList<char[][]> listOfBlocks, boolean[] used, char[][] grid, int rowGrid, int colGrid) {
        attempt++;
        if(gameSolved(grid, used)) {
            grid_solution = grid;
            solutionFound = true;
            return;
        }
    
        if(!hasValidMoves(listOfBlocks, used, grid, rowGrid, colGrid)) {
            return;
        }
    
        if(solutionFound) {  
            return;
        }
        
        for(int i = 0; i < listOfBlocks.size(); i++) {
            if(!used[i]) {
                used[i] = true;
                ArrayList<char[][]> transformations = blockTransformation(listOfBlocks.get(i));
    
                for(char[][] block : transformations) {
                    if(solutionFound) break;  
                    for(int row = 0; row <= rowGrid-block.length; row++) {
                        if(solutionFound) break;  
                        for(int col = 0; col <= colGrid-block[0].length; col++) {
                            if(solutionFound) break;  
                            if(validPosition(block, grid, block.length, block[0].length, row, col)) {
                                char[][] originalGrid = copyGrid(grid);
                                grid = placeBlock(grid, block, block.length, block[0].length, row, col, rowGrid, colGrid);
                                
                                bruteForce(listOfBlocks, used, grid, rowGrid, colGrid);
                                if(solutionFound) {
                                    return;  
                                }
                                
                                grid = originalGrid;
                            }
                        }
                    }
                }
                used[i] = false;
            }
        }
    }

    public static boolean hasValidMoves(ArrayList<char[][]> listOfBlocks, boolean[] used, char[][] grid, int rowGrid, int colGrid) {
        for(int i = 0; i < listOfBlocks.size(); i++) {
            if(!used[i]) {
                ArrayList<char[][]> transformations = blockTransformation(listOfBlocks.get(i));
                for(char[][] block : transformations) {
                    for(int row = 0; row <= rowGrid-block.length; row++) {
                        for(int col = 0; col <= colGrid-block[0].length; col++) {
                            if(validPosition(block, grid, block.length, block[0].length, row, col)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    
}
package main.models;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile{

    public static int[] BoardSpecs(String filename){
        try(BufferedReader reader = new BufferedReader( new FileReader(filename))){
            String line = reader.readLine();
            if (line != null){
                String[] words = line.split("\\s+");
                int[] numbers = new int[words.length];

                for (int i = 0 ;i < words.length; i++){
                    numbers[i] = Integer.parseInt(words[i]);
                }

                return numbers;
            }
        }
        catch(IOException e){
            System.out.println("can't read file");
        }
        return new int[0];
    }

    public static String configuration(String filename){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String Line2 = reader.readLine();
            Line2 = reader.readLine();
            Line2 = Line2.replaceAll("\\s+","");
            if ( Line2 != null){
                return Line2;
            }
        }
        catch(IOException e){
            System.out.println("can't read file");
        }
        return "";
    }

    private static boolean stillOneShape(char alphabet, String shapeLine){
        for (char c : shapeLine.toCharArray()){
            if (c == alphabet){
                return true;
            }
        }
        return false;
    }

    private static char[][] adjustShape(char[][] block, int blockRow, int blockCol) {
        ArrayList<Integer> rowRemoval = new ArrayList<>();
        ArrayList<Integer> colRemoval = new ArrayList<>();
        
        for (int i = 0; i < blockRow; i++) {
            boolean empty = true;
            for (int j = 0; j < blockCol; j++) {
                if (block[i][j] != '.') {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                rowRemoval.add(i);
            }
        }
        
        for (int j = 0; j < blockCol; j++) {
            boolean empty = true;
            for (int i = 0; i < blockRow; i++) {
                if (block[i][j] != '.') {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                colRemoval.add(j);
            }
        }

        int newRows = blockRow - rowRemoval.size();
        int newCols = blockCol - colRemoval.size();
        char[][] adjusted = new char[newRows][newCols];
        
        int newRow = 0;
        for (int i = 0; i < blockRow; i++) {
            if (!rowRemoval.contains(i)) {
                int newCol = 0;
                for (int j = 0; j < blockCol; j++) {
                    if (!colRemoval.contains(j)) {
                        adjusted[newRow][newCol] = block[i][j];
                        newCol++;
                    }
                }
                newRow++;
            }
        }
        
        return adjusted;
    }

    public static char[][][] Shapes(String filename, int number_of_shapes) {
        char[][][] store_of_shapes = new char[number_of_shapes][][];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            String words = line.replaceAll("\\s+","");
            
            for (int shapeIndex = 0; shapeIndex < number_of_shapes; shapeIndex++) {
                words = line.replaceAll("\\s+","");
                ArrayList<String> shapeLine = new ArrayList<>();
                char Alphabetic_id = words.charAt(0);
                
                while (line != null && stillOneShape(Alphabetic_id, line)) {
                    shapeLine.add(line);
                    line = reader.readLine();
                }
    
                if (!shapeLine.isEmpty()) {
                    int longest_column = shapeLine.stream().mapToInt(String::length).max().orElse(0);
    
                    char[][] shapeMatrix = new char[shapeLine.size()][longest_column];
                    
                    for (int k = 0; k < shapeLine.size(); k++) {
                        String currentLine = shapeLine.get(k);
                        for (int x = 0; x < longest_column; x++) {
                            char pushBackChar = '.'; 
                            if (x < currentLine.length()) {
                                pushBackChar = currentLine.charAt(x);
                                if (pushBackChar == ' ') {
                                    pushBackChar = '.';
                                }
                            }
                            shapeMatrix[k][x] = pushBackChar;
                        }
                    }
                    store_of_shapes[shapeIndex] = adjustShape(shapeMatrix, shapeMatrix.length, shapeMatrix[0].length);
                }
            }
        } catch (IOException lol) {
            System.out.println(lol.getMessage());
        }
        return store_of_shapes;
    }
}
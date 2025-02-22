package main.models;


import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    public static void writeSolutionBoard(char[][]grid, String file_input_name){
        String outputFile = file_input_name;
        try (FileWriter writer = new FileWriter(outputFile)){
            for(char[] row: grid){
                for(char c : row){
                    writer.write(c);
                }
                writer.write("\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

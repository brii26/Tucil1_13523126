import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile{

    static int[] BoardSpecs(String filename){
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

    static String configuration(String filename){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String Line2 = reader.readLine();
            Line2 = reader.readLine();
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

    static char[][][] Shapes(String filename, int number_of_shapes){
        char[][][] store_of_shapes = new char[number_of_shapes][][];
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            for ( int shapeIndex = 0 ; shapeIndex < number_of_shapes; shapeIndex++){
                ArrayList<String> shapeLine = new ArrayList<>();
                char Alphabetic_id = (char) ('A' + shapeIndex);
                while(line != null && stillOneShape((char)(Alphabetic_id), line)){
                    shapeLine.add(line);
                    line = reader.readLine();
                }

                int longest_column = shapeLine.stream().mapToInt(String::length).max().orElse(0);

                char[][] shapeMatrix = new char [shapeLine.size()][longest_column];
                for( int k = 0 ; k < shapeLine.size(); k++){
                    for( int x = 0 ; x < longest_column ; x++){
                        shapeMatrix[k][x] = (x < shapeLine.get(k).length()) ?  shapeLine.get(k).charAt(x) : ' ';
                    }
                }
                store_of_shapes[shapeIndex] = shapeMatrix;
            }
        }
        catch(IOException lol){
            System.out.println(lol);
        }
        return store_of_shapes;
    }
}
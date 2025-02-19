public class Main {
    
static void printShapes(char[][][] shapeMatrices) {
    for (int shapeIndex = 0; shapeIndex < shapeMatrices.length; shapeIndex++) {
        System.out.println("Shape " + (char)(('A' + shapeIndex)) + ":");
        char[][] shape = shapeMatrices[shapeIndex];

        for (char[] row : shape) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println(); 
        }
        System.out.println(); 
        }
    }

    public static char[][] mirror(char[][]matrix, int row, int column){
        char[][] mirrored_matrix = new char[row][column];
        for (int i = 0 ; i < row ; i++){
            for (int j = 0 ; j < column ; j++){
                mirrored_matrix[i][j] = matrix[i][column-j-1];
            }
        }
        return mirrored_matrix;
    }
    public static char[][] rotate(char[][]matrix, int row, int column){
        char[][] transposed_matrix = new char[column][row];
        for ( int i = 0 ; i < row ; i ++){
            for ( int j = 0 ; j < column ; j++){
                transposed_matrix[i][j] = matrix[j][i];
            }
        }
        return mirror(transposed_matrix, row, column);
    }

    public static void main(String[] args){
        int[] BoardSpecs = ReadFile.BoardSpecs("src/input.txt");
        String mode = ReadFile.configuration("src/input.txt");
        System.out.println("N = " + BoardSpecs[0]);
        System.out.println("M = " + BoardSpecs[1]);
        System.out.println("n = " + BoardSpecs[2]);
        System.out.println("mode = " + mode);
        char[][][] shapes =  ReadFile.Shapes("src/input.txt",BoardSpecs[2]);
        printShapes(shapes);
    }
}

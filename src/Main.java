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

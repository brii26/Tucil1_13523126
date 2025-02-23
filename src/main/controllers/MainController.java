package main.controllers;

import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import java.nio.file.Files;
import java.nio.file.Paths;
import main.models.*;
import main.logic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
public class MainController {

    private PuzzleResult currentPuzzleResult = new PuzzleResult(false, 0, 0, null);

    @FXML
    private String selectedFilePath;

    @FXML
    private AnchorPane gridContainer;  
    
    @FXML
    private GridPane gridPane; 
    @FXML
    private void handleEntry(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/solverPage.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(newRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private Label solutionResult;
    
    @FXML
    private Label attemptsResult;
    
    @FXML
    private Label timeResult;

    @FXML
    private void handleProcessFile(javafx.event.ActionEvent event) {
        resetState();
        if (selectedFilePath != null && !selectedFilePath.isEmpty()) {
            GameManager.setBoardUpdateListener(this::onBoardUpdate);
            
            Task<PuzzleResult> solverTask = new Task<>(){
                @Override
                protected PuzzleResult call() throws Exception{
                    return GameManager.runSolver(selectedFilePath);
                }
            };

            solverTask.setOnSucceeded(e -> {
                currentPuzzleResult = solverTask.getValue();
                solutionResult.setText(currentPuzzleResult.isSolutionFound() ? "Solution: Found!" : "Solution: Not Found!");
                attemptsResult.setText("Attempts: " + currentPuzzleResult.getAttempts());
                timeResult.setText("Time: " + currentPuzzleResult.getTime() + " ms");
                if (currentPuzzleResult.getGridSolution() != null && currentPuzzleResult.isSolutionFound()) {
                    updateGrid(currentPuzzleResult.getGridSolution());
                }
            });

            solverTask.setOnFailed(e -> {
                System.out.println("Solver task failed: " + solverTask.getException().getMessage());
            });
    
            new Thread(solverTask).start();

        } else {
            System.out.println("No file selected.");
        }
    }


    @FXML
    private void resetState() {
        solutionResult.setText("Solution: ");
        attemptsResult.setText("Attempts: ");
        timeResult.setText("Time: ");
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        
        gridContainer.setPrefWidth(750);
        gridContainer.setPrefHeight(750);
        
    }

    @FXML
    private void handleBackButton(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(newRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSelectFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File from 'test' folder");

        File initialDir = new File("src/test");
        if (initialDir.exists() && initialDir.isDirectory()) {
            fileChooser.setInitialDirectory(initialDir);
        }

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + selectedFilePath);
        }
    }

    public void processSelectedFile() {
        if (selectedFilePath != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(selectedFilePath)));
                System.out.println("File content: " + content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    private void updateGrid(char[][] grid) {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
    
        int rows = grid.length;
        int cols = grid[0].length;

        double cellSize = 750.0 / Math.max(rows, cols);
        
        double newWidth = cellSize * cols;
        double newHeight = cellSize * rows;
        
        gridContainer.setPrefWidth(newWidth);
        gridContainer.setPrefHeight(newHeight);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Label cell = new Label();
                cell.setMinSize(cellSize, cellSize);
                cell.setPrefSize(cellSize, cellSize);
                cell.setMaxSize(cellSize, cellSize);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-border-color: black; -fx-background-color: " + getColorForChar(grid[i][j]) + ";");
                gridPane.add(cell, j, i);
            }
        }
    }    
    
    private String getColorForChar(char c) {
        if (c == '.') {
            return "white";
        }
        int index = c - 'A';
        switch (index % 10) {
            case 0:  return "red";
            case 1:  return "orange";
            case 2:  return "yellow";
            case 3:  return "green";
            case 4:  return "blue";
            case 5:  return "cyan";
            case 6:  return "magenta";
            case 7:  return "pink";
            case 8:  return "purple";
            case 9:  return "brown";
            default: return "gray";
        }
    }

    @FXML
    private void handleSavetxt(javafx.event.ActionEvent event) {
        if (!currentPuzzleResult.isSolutionFound()) {
            showOkDialog("Not Found", "Can't Find Solution!");
            return;
        }
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Solution Text File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
        );
    
        File saveDir = new File("src/test");
        if (!saveDir.exists() && !saveDir.mkdirs()) {
            showOkDialog("Error", "Failed to create directory: " + saveDir.getAbsolutePath());
            return;
        }
        
        fileChooser.setInitialDirectory(saveDir);
        fileChooser.setInitialFileName("solution.txt");
    
        File outputFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (outputFile == null) return; 
    
        WriteFile.writeSolutionBoard(currentPuzzleResult.getGridSolution(), outputFile.getAbsolutePath());
        showOkDialog("yey", "SAVED!");
    }

    @FXML
    private void handleSaveimg(javafx.event.ActionEvent event) {
        if (!currentPuzzleResult.isSolutionFound()) {
            Alert alert = new Alert(AlertType.INFORMATION); 
            alert.setTitle("Not Found");
            alert.setHeaderText("Can't Find Solution!"); 
            alert.showAndWait(); 
            return;
        }
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Solution Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
            new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg")
        );
    
        File saveDir = new File("src/test");
        if (!saveDir.exists() && !saveDir.mkdirs()) {
            showOkDialog("Error", "Failed to create directory: " + saveDir.getAbsolutePath());
            return;
        }
        
        fileChooser.setInitialDirectory(saveDir);
        fileChooser.setInitialFileName("solution.png");
    
        File outputFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (outputFile == null) return; 
        WritableImage snapshot = gridContainer.snapshot(null, null);
        String fileType = outputFile.getName().toLowerCase().endsWith(".jpg") ? "jpg" : "png";
    
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), fileType, outputFile);
            showOkDialog("yey", "SAVED");
        } catch (IOException e) {
            e.printStackTrace();
            showOkDialog("Error", "Failed to save the image.");
        }
    }
    

    private void showOkDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private boolean isUpdating = false;

    private void onBoardUpdate(char[][] grid) {
        if (!isUpdating) {
            isUpdating = true;
            javafx.application.Platform.runLater(() -> {
                updateGrid(grid);
                isUpdating = false;
            });
        }
    }
    

}
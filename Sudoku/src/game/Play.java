package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Play extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Sudoku San");
		Group root = new Group();
		Scene scene = new Scene(root);

		Button button = new Button(30, 30, 5, 2, "abv");
		Board board = new Board(51, 2, 5, button);
		Header header = new Header(board, button);

		int takeSudokuIndex = takeCurrentIndexFromFile();
		SetAction action = new SetAction(board, 0, 0, 0);
		board.setCurrentSudokuIndex(takeSudokuIndex);


		Canvas canvas = new Canvas(board.getSize(), board.getSize() + header.getHeight());
		root.getChildren().add(canvas);
		action.addEventListeners(scene);

		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		board.setGraphicsContext(graphicsContext);
		board.setHeader(header);
		board.drawInitialBoard();
		action.drawActive();


		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public int takeCurrentIndexFromFile() {
		int takeSudokuIndex = 0;
		File currentIndex = new File("currentIndex.txt");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(currentIndex);
			int b = 0;
			String indexNumber = "";
			while (b != -1) {
				b = fileInputStream.read();
				if (b > -1) {
					indexNumber += (char)b;
				}
			}
			fileInputStream.close();
			takeSudokuIndex = convertStringToInt(indexNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return takeSudokuIndex;
	}


	public int convertStringToInt(String indexNumber) {
		int index = 0;
		for (int l = 0; l < indexNumber.length(); l++) {
			char c = indexNumber.charAt(l);
			int x = convertCharToInt(c);
			double power = indexNumber.length()-l-1;
			Double s = Math.pow(10.0, power);
			int p = s.intValue();
			index += x*p;
		}
		return index;
	}


	public int convertCharToInt(char c) {
		int x = 0;
		for (int i = 0; i < 10; i++) {
			String str = "0123456789";
			if(c == str.charAt(i)) {
				x = i;
				break;
			}
		}
		return x;
	}


}

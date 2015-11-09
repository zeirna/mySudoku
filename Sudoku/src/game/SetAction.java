package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SetAction {

	private Board board;
	String[] numbers;

	private int i;
	private int j;
	private int l;

	private boolean pencilState;

	private boolean buttonsOrTiles;

	private boolean isInfoPressed;

	public SetAction(Board board, int i, int j, int l) {
		super();
		this.board = board;
		this.i = i;
		this.j = j;
		this.l = l;
		pencilState = true;
		buttonsOrTiles = false;
		isInfoPressed = false;
	}

	public boolean getButtonsOrTiles() {
		return buttonsOrTiles;
	}

	public void setButtonsOrTiles(boolean buttonsOrTiles) {
		this.buttonsOrTiles = buttonsOrTiles;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public double getSize(Board board) {
		Square[][] squares = board.getSquares();
		Tile [][] tiles = squares[0][0].getTiles();
		return tiles[0][0].getSize();
	}

	public double getLine(Board board) {
		Square[][] squares = board.getSquares();
		return squares[0][0].getLine();
	}

	public double getBar(Board board) {
		return board.getBar();
	}

	public void onKeyPressed(KeyEvent event) {
		Square[][] squares = board.getSquares();
		Tile[][] tiles = squares[i/3][j/3].getTiles();
		Tile tile = tiles[i%3][j%3];

		Button[] buttons = board.getHeader().getButtons();
		Button button = buttons[l];

		if(event.getCode().equals(KeyCode.ENTER)) {
			controlEnter(button);
		} else if(event.getCode().equals(KeyCode.UP)) {
			controlUp();
		}else if(event.getCode().equals(KeyCode.DOWN)) {
			controlDown();
		}else if(event.getCode().equals(KeyCode.LEFT)) {
			controlLeft();
		}else if(event.getCode().equals(KeyCode.RIGHT)) {
			controlRight();
		} else if(event.getCode().equals(KeyCode.SPACE)) {
			controlSpace();
		} else if(event.getCode().equals(KeyCode.DIGIT0) || event.getCode().equals(KeyCode.NUMPAD0)) {
			controlZero(tile);
		} else if(event.getCode().equals(KeyCode.DIGIT1) || event.getCode().equals(KeyCode.NUMPAD1)) {
			controlNumbers(tile, 1);
		} else if(event.getCode().equals(KeyCode.DIGIT2)  || event.getCode().equals(KeyCode.NUMPAD2)) {
			controlNumbers(tile, 2);
		} else if(event.getCode().equals(KeyCode.DIGIT3)  || event.getCode().equals(KeyCode.NUMPAD3)) {
			controlNumbers(tile, 3);
		} else if(event.getCode().equals(KeyCode.DIGIT4)  || event.getCode().equals(KeyCode.NUMPAD4)) {
			controlNumbers(tile, 4);
		} else if(event.getCode().equals(KeyCode.DIGIT5)  || event.getCode().equals(KeyCode.NUMPAD5)) {
			controlNumbers(tile, 5);
		} else if(event.getCode().equals(KeyCode.DIGIT6)  || event.getCode().equals(KeyCode.NUMPAD6)) {
			controlNumbers(tile, 6);
		} else if(event.getCode().equals(KeyCode.DIGIT7)  || event.getCode().equals(KeyCode.NUMPAD7)) {
			controlNumbers(tile, 7);
		} else if(event.getCode().equals(KeyCode.DIGIT8)  || event.getCode().equals(KeyCode.NUMPAD8)) {
			controlNumbers(tile, 8);
		} else if(event.getCode().equals(KeyCode.DIGIT9)  || event.getCode().equals(KeyCode.NUMPAD9)) {
			controlNumbers(tile, 9);
		}

	}


	public void onKeyReleased(KeyEvent event) {
		Button[] buttons = board.getHeader().getButtons();
		Button button = buttons[l];
		if (event.getCode().equals(KeyCode.ENTER)) {
			if(!buttonsOrTiles){
				button.setIsClicked(false);
			}
		}
		board.drawBoardHeader();
	}

	public void addEventListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				onKeyPressed(event);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				onKeyReleased(event);
			}
		});

	}

	private void controlUp() {
		isInfoPressed = false;
		board.resetIsWrong();
		if(buttonsOrTiles) {
			j--;
			if(j==-1) {
				j=8;
			}
		} else {
			l--;
			if(l==-1) { l=5; }
		}
		board.drawBoard();
		drawSudoku();
	}

	private void controlDown() {
		isInfoPressed = false;
		board.resetIsWrong();
		if(buttonsOrTiles) {
			j++;
			if(j==9){
				j=0;
			}
		} else {
			l++;
			if(l==6) { l=0; }
		}
		board.drawBoard();
		drawSudoku();
	}

	private void controlLeft() {
		isInfoPressed = false;
		board.resetIsWrong();
		if(buttonsOrTiles) {
			i--;
			if(i==-1) {
				i=8;
			}
		} else {
			l-=2;
			if(l==-2) { l=4; }
			if(l==-1) { l=5; }
		}
		board.drawBoard();
		drawSudoku();
	}

	private void controlRight() {
		isInfoPressed = false;
		board.resetIsWrong();
		if(buttonsOrTiles) {
			i++;
			if(i==9) {
				i=0;
			}
			//setI(i);
		} else {
			l+=2;
			if(l==6) { l=0; }
			if(l==7) { l=1; }
		}
		board.drawBoard();
		drawSudoku();
	}

	private void controlNumbers(Tile tile, int number) {
		if(!tile.getIsBlocked() && buttonsOrTiles) {
			if(tile.getState()==1){
				tile.setNumber("" + number);
			} else {
				number--;
				if(tile.getIsVisible(number/3, number%3)) {
					tile.setIsVisible(false, number/3, number%3);
				} else {
					tile.setIsVisible(true, number/3, number%3);
		}}}
		drawSudoku();
	}

	private void controlZero(Tile tile) {
		if(!tile.getIsBlocked() && buttonsOrTiles) {
			if(tile.getState()==1){
				tile.setNumber("0");
			} else {
				for (int m = 0; m < 3; m++) {
					for (int n = 0; n < 3; n++) {
						tile.setIsVisible(false, m, n);
		}}}}
		drawSudoku();
	}

	private void controlSpace() {
		isInfoPressed = false;
		if(buttonsOrTiles) {
			buttonsOrTiles = false;
			board.resetIsWrong();
		} else {
			buttonsOrTiles = true;
		}
		drawSudoku();
	}

	private void controlEnter(Button button) {
		if(buttonsOrTiles) {
			if(!pencilState) {
				board.setPencilState(1);
				pencilState = true;
			} else {
				board.setPencilState(0);
				pencilState = false;
			}
			drawSudoku();
		} else {
			button.setIsClicked(true);
			drawSudoku();
			callButtonAction();
		}

	}

	private void callButtonAction() {
		switch (l) {
			case 0:
				showInfo();
				break;
			case 1:
				startNewGame();
				break;
			case 2:
				cleanBoard();
				break;
			case 3:
				board.checkBoard();
				drawSudoku();
				isGameOver();
				break;
			case 4:
				saveCurrentBoard();
				break;
			case 5:
				load();
				drawSudoku();
				break;
		}
	}

	private void isGameOver() {
		if(board.checkBoard()) {
			Image pinko = new Image("nMzmmH4.png");
			double x = (board.getSize() - 468)/2;
			double y = (board.getSize() - 222)/2 + board.getHeader().getHeight();;
			board.getGraphicsContext().drawImage(pinko, x, y);
		}
	}
	private void cleanBoard() {
		board.setPencilState(1);
		board.cleanBoard();
		board.drawInitialBoard();
	}

	private void saveCurrentBoard() {
		board.saveCurrentSudokuNumbers();
		board.saveCurrentIsBlocked();
		board.saveCurrentIsVisible();
		saveCurrentIndexToFile(board.getCurrentSudokuIndex(), "savedIndex.txt");
	}

	private void load() {
			board.loadSavedNumbers();
			board.loadSavedIsBlocked();
			board.loadSavedIsVisible();
			board.setCurrentSudokuIndex(takeCurrentIndexFromFile());
	}

	public int takeCurrentIndexFromFile() {
		int takeSudokuIndex = 0;
		File savedtIndex = new File("savedIndex.txt");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(savedtIndex);
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

	private void startNewGame() {
		board.unblockTiles();
		board.cleanBoard();
		int index = board.getCurrentSudokuIndex();
		index = index+1;
		if(index>=Sudokus.sudokus.length) {
			index=0;
		}
		board.setCurrentSudokuIndex(index);
		board.drawInitialBoard();
		saveCurrentIndexToFile(index, "currentIndex.txt");
	}

	private void saveCurrentIndexToFile(int index, String file) {
		File currentIndex = new File(file);
		if(currentIndex.exists()) {
			currentIndex.delete();
		}
		try {
			currentIndex.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(currentIndex);
			String currentIndexNumber = "" + board.getCurrentSudokuIndex();
			for (int i = 0; i < currentIndexNumber.length(); i++) {
				char c = currentIndexNumber.charAt(i);
				fileOutputStream.write(c);
			}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showInfo() {
		if(isInfoPressed) {
			isInfoPressed = false;
			drawSudoku();
		} else {
			isInfoPressed = true;
			Image img1 = new Image("howto.png");
			board.drawInfoBackground();
			board.getGraphicsContext().drawImage(img1, board.getSize()/2-232, board.getHeader().getHeight()+getBar(board)+board.getSize()/2-232);
			Image img2 = new Image("help.png");
			board.getGraphicsContext().drawImage(img2, board.getSize()/2-220, 2*board.getHeader().getHeight()+2*getBar(board)+board.getSize()/2-220-232+332);

		}
	}

	public void drawActive() {
			double x = i*getSize(board) + (i/3+1)*getBar(board) + (i/3*2 + i%3)*getLine(board);
			double y = j*getSize(board) + (j/3+1)*getBar(board) + (j/3*2 + j%3)*getLine(board) + board.getHeader().getHeight();
			board.drawActiveTile(x, y);
			board.drawActiveButton(board.getHeader().getButtons()[l]);
	}

	private void drawSudoku() {
		board.drawBoard();
		board.drawBoardHeader();
		drawActive();
	}

}

package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

	private double bar;
	private Square[][] squares;
	private Button button;
	private GraphicsContext graphicsContext;
	Header header;

	private int currentSudokuIndex;

	public Board(double size, double line, double bar, Button button) {
		this.bar = bar;
		this.squares = new Square[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				squares[i][j] = new Square(size, line);
			}
		}
		this.button = button;
	}

	public double getSize() {
		double size = squares[0][0].getSize();
		return 3*size + 4*bar;
	}

	public double getBar() {
		return bar;
	}

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public int getCurrentSudokuIndex() {
		return currentSudokuIndex;
	}

	public void setCurrentSudokuIndex(int currentSudokuIndex) {
		this.currentSudokuIndex = currentSudokuIndex;
	}


	public void drawBoard() {
		graphicsContext.setFill(Color.web("#663300"));
		graphicsContext.fillRect(0, header.getHeight(), getSize(), getSize());
		double x = 0;
		double y = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				x = i*squares[i][j].getSize() + (i+1)*bar;
				y = j*squares[i][j].getSize() + (j+1)*bar + 2*button.getHeight() + 3*10;
				squares[i][j].drawSquare(graphicsContext, x, y);
	}}}

	public void drawInitialBoard() {
		String[] numbers = Sudokus.sudokus[currentSudokuIndex];
		assignNumbersToSquares(numbers);
		header.drawHeader(graphicsContext);
		setBlockedTiles(numbers);
		drawBoard();
	}

	public void drawInfoBackground() {
		graphicsContext.setFill(Color.web("#663300"));
		graphicsContext.fillRect(0, header.getHeight(), getSize(), getSize());
		graphicsContext.setFill(Color.web("#ffcc99"));
		graphicsContext.fillRect(bar, header.getHeight()+bar, getSize()-2*bar, getSize()-2*bar);

	}

	public Square[][] getSquares() {
		return squares;
	}


	public void assignValueToSquare (Square square, int i, int j) {
		this.squares[i][j] = square;
	}

	public void assignNumbersToSquares(String[] numbers) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				squares[i][j].assignNumbersToSquare(numbers[i+3*j]);
	}}}

	public void setBlockedTiles(String[] numbers) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Tile[][] tiles = squares[i/3][i%3].getTiles();
				Tile tile = tiles[j/3][j%3];
				if (!tile.getNumber().equals("0")) {
					tile.setIsBlocked(true);
	}}}}

	public void unblockTiles() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Tile[][] tiles = squares[i/3][i%3].getTiles();
				Tile tile = tiles[j/3][j%3];
					tile.setIsBlocked(false);
	}}}

	public void drawBoardHeader() {
		header.drawHeader(graphicsContext);
	}

	public double getTileSize() {
		Tile [][] tiles = squares[0][0].getTiles();
		return tiles[0][0].getSize();
	}

	public double getLineSize() {
		return squares[0][0].getLine();
	}

	public void drawActiveTile(double x, double y) {
		graphicsContext.setFill(Color.web("#d00"));
		double a = getLineSize()+2;
		graphicsContext.fillRect(x-a+1, y-a+1, getTileSize()+2*a-2, a);
		graphicsContext.fillRect(x-a+1, y+getTileSize()-1, getTileSize()+2*a-2, a);
		graphicsContext.fillRect(x-a+1, y, a, getTileSize());
		graphicsContext.fillRect(x+getTileSize()-1, y, a, getTileSize());
	}

	public void drawActiveButton(Button button) {
		header.setButtonsUnactive();
		button.setIsActive(true);
		drawBoardHeader();
	}

	public void cleanBoard() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				squares[i][j].cleanSquare();
	}}}

	public void setPencilState(int state) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				squares[i][j].setPencilState(state);
	}}}

	public void resetIsWrong() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				squares[i][j].resetIsWrong();
	}}}

	private String[] getCurrentSudokuNumbers() {
		String[] numbers = new String[9];
		for (int i = 0; i < 9; i++) {
			Square square = squares[i%3][i/3];
			Tile[][] tiles = square.getTiles();
			String str = "";
			for (int j = 0; j < 9; j++) {
				Tile tile = tiles[j%3][j/3];
				String num = tile.getNumber();
				str += num;
			}
			numbers[i] = str;
		}
		return numbers;
	}

	public void saveCurrentSudokuNumbers() {
		String[] numbers = getCurrentSudokuNumbers();
		File savedSudoku = new File("savedSudoku.txt");
		if(savedSudoku.exists()) {
			savedSudoku.delete();
		}
		try {
			savedSudoku.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(savedSudoku);
			char c = '0';
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					c = numbers[i].charAt(j);
					fileOutputStream.write(c);
				}
			}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSavedNumbers() {
		String str = readStringFromFile("savedSudoku.txt");
		String[] numbers = convertStringToArrayString(str);
		assignNumbersToSquares(numbers);
	}

	private String readStringFromFile(String fileName) {
		File currentIndex = new File(fileName);
		FileInputStream fileInputStream;
		String str = "";
		try {
			fileInputStream = new FileInputStream(currentIndex);
			int b = 0;
			while (b != -1) {
				b = fileInputStream.read();
				if (b > -1) {
					str += (char)b;
				}
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	private String[] convertStringToArrayString(String str) {
		String[] arrayString = new String[9];
		for (int i = 0; i < 9; i++) {
			String string = "";
			for (int j = 0; j < 9; j++) {
				char c = str.charAt(i*9+j);
				string += c;
			}
			arrayString[i] = string;
		}
		return arrayString;
	}


	private String[] getCurrentIsBlocked() {
		String[] currentIsBlocked = new String[9];
		for (int i = 0; i < 9; i++) {
			Square square = squares[i%3][i/3];
			Tile[][] tiles = square.getTiles();
			String str = "";
			for (int j = 0; j < 9; j++) {
				Tile tile = tiles[j%3][j/3];
				boolean blocked = tile.getIsBlocked();
				String bl = "";
				if(blocked) {
					bl = "1";
				} else {
					bl = "0";
				}
				str += bl;
			}
			currentIsBlocked[i] = str;
		}
		return currentIsBlocked;
	}

	public void saveCurrentIsBlocked() {
		String[] currentIsBlocked = getCurrentIsBlocked();
		File savedIsBlocked = new File("savedIsBlocked.txt");
		if(savedIsBlocked.exists()) {
			savedIsBlocked.delete();
		}
		try {
			savedIsBlocked.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(savedIsBlocked);
			char c = '0';
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					c = currentIsBlocked[i].charAt(j);
					fileOutputStream.write(c);
				}
			}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSavedIsBlocked() {
		unblockTiles();
		String str = readStringFromFile("savedIsBlocked.txt");
		String savedIsBlocked[] = convertStringToArrayString(str);

		for (int i = 0; i < 9; i++) {
			Square square = squares[i%3][i/3];
			Tile[][] tiles = square.getTiles();
			for (int j = 0; j < 9; j++) {
				char c = savedIsBlocked[i].charAt(j);
				if(c=='1') {
					tiles[j%3][j/3].setIsBlocked(true);
				} else {
					tiles[j%3][j/3].setIsBlocked(false);
	}}}}

	public String[][] placeIsVisiblesIntoStringArrays() {
		String[][] currentIsVisibles = new String[9][9];
		for (int i = 0; i < 9; i++) {
			currentIsVisibles[i] = squares[i%3][i/3].placeIsVisiblesIntoStringArray();
		}
		return currentIsVisibles;
	}

	public void saveCurrentIsVisible() {
		String[][] currentIsVisible = placeIsVisiblesIntoStringArrays();
		File savedIsVisible = new File("savedIsVisible.txt");
		if(savedIsVisible.exists()) {
			savedIsVisible.delete();
		}
		try {
			savedIsVisible.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(savedIsVisible);
			char c = '0';
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					for (int k = 0; k < 9; k++) {
						c = currentIsVisible[i][j].charAt(k);
						fileOutputStream.write(c);
			}}}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSavedIsVisible() {
		String str = readStringFromFile("savedIsVisible.txt");
		String[] str1 = convertStringToArrayIsVisibleString(str);
		String[][] str2 = new String[9][9];
		for (int i = 0; i < 9; i++) {
			 str2[i] = convertStringToArrayString(str1[i]);
		}

		for (int i = 0; i < 9; i++) {
			Square square = squares[i%3][i/3];
			Tile[][] tiles = square.getTiles();
			for (int j = 0; j < 9; j++) {
				String str3 = str2[i][j];
				Tile tile = tiles[j%3][j/3];
				for (int k = 0; k < 9; k++) {
					if(str3.charAt(k)=='1') {
						tile.setIsVisible(true, k/3, k%3);
					} else {
						tile.setIsVisible(false, k/3, k%3);
	}}}}}

	private String[] convertStringToArrayIsVisibleString(String str) {
		String[] arrayString = new String[9];
		for (int i = 0; i < 9; i++) {
			String string = "";
			for (int j = 0; j < 81; j++) {
				char c = str.charAt(i*81+j);
				string += c;
			}
			arrayString[i] = string;
		}
		return arrayString;
	}


	public void saveCurrentIndex() {
		File savedIndex = new File("savedIndex.txt");
		if(savedIndex.exists()) {
			savedIndex.delete();
		}
		try {
			savedIndex.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(savedIndex);
			String currentIndexNumber = "" + currentSudokuIndex;
			for (int i = 0; i < currentIndexNumber.length(); i++) {
				char c = currentIndexNumber.charAt(i);
				fileOutputStream.write(c);
			}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int charToInt(char num) {
		int x = 0;
		for (int i = 0; i < 9; i++) {
			String str = "012345678";
			if(num == str.charAt(i)) {
				x = i;
				break;
			}
		}
		return x;
	}

	public boolean checkBoard() {
		boolean isWon = false;
		String[] numbers = getCurrentSudokuNumbers();
		checkSquares(numbers);
		checkRows(numbers);
		checkColumns(numbers);
		boolean areEmptyTiles = areThereAnyEmptyTiles();
		boolean areErrorTiles = areThereAnyErrorTiles();
		if(!areEmptyTiles && !areErrorTiles) {
			isWon = true;
		}
		return isWon;
	}

	private void checkSquares(String[] numbers) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = j+1; k < 9; k++) {
					if(numbers[i].charAt(j) == numbers[i].charAt(k) && numbers[i].charAt(j)!='0') {
						setIsWrongToCoordinates(i, j);
						setIsWrongToCoordinates(i, k);
	}}}}}

	private void setIsWrongToCoordinates(int i, int j) {
		Tile[][] tiles = squares[i%3][i/3].getTiles();
		tiles[j%3][j/3].setIsWrong(true);
	}

	private boolean areThereAnyEmptyTiles() {
		boolean isGameOver = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String c = squares[i/3][i%3].getTiles()[j/3][j%3].getNumber();
				if(c.equals("0")) {
					isGameOver = true;
					break;
		}}}
		return isGameOver;
	}

	private boolean areThereAnyErrorTiles() {
		boolean thereAreErrors = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boolean isWrong = squares[i/3][i%3].getTiles()[j/3][j%3].getIsWrong();
				if(isWrong) {
					thereAreErrors = true;
					break;
		}}}
		return thereAreErrors;
	}

	private void checkRows(String[] numbers) {
	for (int s = 0; s < 3; s++) {
			for (int g = 0; g < 3; g++) {
				String str = "";
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						str += numbers[i+s*3].charAt(j+g*3);
				}}
				String str1 = "";
				for (int j = 0; j < 9; j++) {
					for (int k = j+1; k < 9; k++) {
						if(str.charAt(j) == str.charAt(k) && str.charAt(j)!='0') {
							str1 += j + "" + k;
				}}}
				for (int i = 0; i < str1.length(); i+=2) {
					char a = str1.charAt(i);
					char b = str1.charAt(i+1);
					int aa = charToInt(a);
					int bb = charToInt(b);
					setIsWrongToCoordinates(aa/3+s*3, aa%3+g*3);
					setIsWrongToCoordinates(bb/3+s*3, bb%3+g*3);
	}}}}

	private void checkColumns(String[] numbers) {
		for (int s = 0; s < 3; s++) {

			for (int g = 0; g < 3; g++) {
				String str = "";
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						str += numbers[i*3+s].charAt(j*3+g);
				}}
				String str1 = "";
				for (int j = 0; j < 9; j++) {
					for (int k = j+1; k < 9; k++) {
						if(str.charAt(j) == str.charAt(k) && str.charAt(j)!='0') {
							str1 += j + "" + k;
				}}}
				for (int i = 0; i < str1.length(); i+=2) {
					char a = str1.charAt(i);
					char b = str1.charAt(i+1);
					int aa = charToInt(a);
					int bb = charToInt(b);
					setIsWrongToCoordinates(s+(aa/3)*3, (aa%3)*3+g);
					setIsWrongToCoordinates(s+(bb/3)*3, (bb%3)*3+g);
				}}
			}
		}



}

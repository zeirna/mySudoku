package game;

import javafx.scene.canvas.GraphicsContext;

public class Square {

	private double line;
	private Tile[][] tiles;

	public Square(double size, double line) {
		this.line = line;
		this.tiles = new Tile[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j] = new Tile(size);
	}}}

	public double getSize() {
		double size = tiles[0][0].getSize();
		return 3*size + 2*line;
	}

	public double getLine() {
		return line;
	}

	public void drawSquare(GraphicsContext graphicsContext, double x, double y) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j].drawTile(graphicsContext, (x+(i*(line+tiles[i][j].getSize()))), (y+(j*(line+tiles[i][j].getSize()))));
	}}}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void assignValueToTile (Tile tile, int i, int j) {
		this.tiles[i][j] = tile;
	}

	public void assignNumberToTile(String number, int i, int j) {
		tiles[i][j].setNumber(number);
	}

	public void assignNumbersToSquare(String numbers) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				char num = numbers.charAt(i + j*3);
				String number = ""+num;
				tiles[i][j].setNumber(number);
	}}}

	public void cleanSquare() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j].cleanTile();
	}}}

	public void setPencilState(int state) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if((!tiles[i][j].getIsBlocked()) && tiles[i][j].getNumber().equals("0")) {
					tiles[i][j].setState(state);
	}}}}

	public void resetIsWrong() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j].setIsWrong(false);
	}}}

	public String[] placeIsVisiblesIntoStringArray() {
		String[] strings = new String[9];
		for (int i = 0; i <9; i++) {
			strings[i] = tiles[i%3][i/3].placeIsVisiblesIntoString();
		}
		return strings;
	}

//#######################################
	public void setIsVisibles() {

	}

}

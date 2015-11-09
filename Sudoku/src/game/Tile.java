package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tile {

	private double size;

	private int state; // 0 or 1
	private String number;
	private boolean[][] isVisible;

	private boolean isWrong;
	private boolean isBlocked;

	public Tile(double size) {
		this.size = size;
		state = 1;
		number = "0";
		isVisible = new boolean[3][3];
				for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				isVisible[i][j] = false;
			}
		}
		isWrong = false;
	}

	public double getFactorWidth() {
		return 0.215;
	}

	public double getFactorHeight() {
		return 0.855;
	}

	public double getSize() {
		return size;
	}

	public String getNumber() {
		return number;
	}

	public int getState() {
		return state;
	}

	public boolean getIsVisible(int i, int j) {
		return isVisible[i][j];
	}

	public boolean getIsBlocked() {
		return isBlocked;
	}

	public boolean getIsWrong() {
		return isWrong;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setIsWrong(boolean isWrong) {
		this.isWrong = isWrong;
	}

	public void setState(int state) {
		if (!isBlocked) {
			this.state = state;
	}}

	public void setIsVisible(boolean visible, int i, int j) {
		this.isVisible[i][j] = visible;
	}

	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public void drawTile(GraphicsContext graphicsContext, double x, double y) {
		if(state==1) {
			drawTile1(graphicsContext, x, y);
		} else {
			drawTile9(graphicsContext, x, y);
	}}

	public void drawTile1(GraphicsContext graphicsContext, double x, double y) {
			graphicsContext.setFill(Color.web("#ffcc99"));
			graphicsContext.fillRect(x, y, size, size);
			String color = "";

			if(number.equals("0")) {
				color = "ffcc99"; //"ffcc99";  "#335";
			} else {
				if(isWrong && !number.equals("0")) {
					color = "#f00";
				} else {
					color = "#1f3f3f";
				}
			}

			graphicsContext.setFill(Color.web(color));
			Font font = new Font("Courier new", size);
			graphicsContext.setFont(font);
			graphicsContext.fillText(number, x+0.215*size, y+0.84*size);
	}

	public void drawTile9(GraphicsContext graphicsContext, double x, double y) {
		graphicsContext.setFill(Color.web("#ffcc99"));
		graphicsContext.fillRect(x, y, size, size);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (isVisible[j][i]) {
					graphicsContext.setFill(Color.GREEN);
				} else {
					graphicsContext.setFill(Color.web("#ffcc99"));
				}

				int f = i+j*3+1;
				String str = ""+f+"";
				double x1 = x+i*size/3;
				double y1 = y+j*size/3;
				Font font1 = new Font("Courier new", size/3);
				graphicsContext.setFont(font1);
				graphicsContext.fillText(str, x1+0.215*size/3, y1+0.84*size/3);
	}}}

	public void drawActiveSurrounding (GraphicsContext graphicsContext, double x, double y) {
			graphicsContext.setFill(Color.web("#f00"));
			int a = 4; //line+2
			graphicsContext.fillRect(x-a-1, y-a-1, size+2*a, a);
			graphicsContext.fillRect(x-a, y+size-1, size+2*a, a);
			graphicsContext.fillRect(x-a-1, y, a, size);
			graphicsContext.fillRect(x+size-1, y, a, size);
		}

	public void cleanTile() {
		state = 1;
		number = "0";
		for (int i = 0; i < 9; i++) {
				isVisible[i/3][i%3] = false;
		}
		isWrong = false;
	}

	public String placeIsVisiblesIntoString() {
		String str = "";
		for (int i = 0; i < 9; i++) {
			boolean isVisible = getIsVisible(i/3, i%3);
			if(isVisible) {
				str += '1';
			} else {
				str += '0';
			}
		}
		return str;
	}


}

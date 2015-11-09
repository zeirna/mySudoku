package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button {

	private double width;
	private double height;

	private double bigShadow;
	private double smallShadow;

	private String text;

	private boolean isClicked;
	private boolean isActive;




	public Button(double width, double height, double bigShadow, double smallShadow, String text) {
		super();
		this.width = width;
		this.height = height;
		this.bigShadow = bigShadow;
		this.smallShadow = smallShadow;
		this.text = text;
	}

//========================================================================================

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public boolean getIsClicked() {
		return isClicked;
	}

	public void setIsClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

//========================================================================================
	public void drawButton(GraphicsContext graphicsContext, double x, double y) {
		String color1 = "#ffb266";
		String color2 = "#996633";
		String color3 = "#004";   //"#002020";
		graphicsContext.setFill(Color.web(color1));
		graphicsContext.fillRect(x, y, width, height);
		double a = smallShadow;
		double b = bigShadow;
		double c = bigShadow - smallShadow;
		if(isActive) {
			color2 = "#663300";
			color3 = "#000";
		}
		if(!isClicked) {
			b = smallShadow;
			a = bigShadow;
		}
		graphicsContext.setFill(Color.web(color2));
		graphicsContext.fillRect(x, y, width, b);
		graphicsContext.fillRect(x, y+height-a, width, a);
		graphicsContext.fillRect(x, y, a, height);
		graphicsContext.fillRect(x+width-b, y, b, height);

		graphicsContext.setFill(Color.web(color3));
		Font font = new Font("Courier new", height-a-b);
		graphicsContext.setFont(font);
		if(!isClicked) {
			graphicsContext.fillText(text, x+9, y+height*(0.5+0.5*0.58)-4);
		} else {
			graphicsContext.fillText(text, x+9-c, y+height*(0.5+0.5*0.58)+c-4);
		}


	}



}

package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Header {

	private double height;
	private double vspace;

	Button button;
	Button[] buttons;
	Board board;

	public Header(Board board, Button button) {
		this.button = button;
		this.board = board;
		this.height = 2*button.getHeight()+3*10;
		setButtons();
	}

	public double getHeight() {
		return 2*button.getHeight()+3*10;
	}

	public double getVSpace() {
		return vspace;
	}

	public void drawHeader(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.web("#ffe5cc"));
		graphicsContext.fillRect(0, 0, board.getSize(), height);
		drawButtons(graphicsContext);
	}

	public void setButtons() {
		double x = (button.getHeight() - 7)*0.6;
		Button buttonClean = new Button(x*11+17, 30, 5, 2, "clean board");
		Button buttonCheck = new Button(x*11+17, 30, 5, 2, "check board");
		Button buttonSave = new Button(x*4+17, 30, 5, 2, "save");
		Button buttonLoad = new Button(x*4+17, 30, 5, 2, "load");
		Button buttonNew = new Button(x*3+17, 30, 5, 2, "new");
		Button buttonHow = new Button(x*3+17, 30, 5, 2, " ? ");
		Button[] buttons = {buttonHow, buttonNew, buttonClean, buttonCheck, buttonSave, buttonLoad};
		this.buttons = buttons;
	}

	public Button[] getButtons () {
		return buttons;
	}

	public void drawButtons(GraphicsContext graphicsContext) {
		double x = (button.getHeight() - 7)*0.6;
		double space = board.getSize() - x*18 - 3*17;
		double sp = space/4;
		buttons[0].drawButton(graphicsContext, sp, 10); 	// (graphicsContext, 3*sp+x*15+34, 10);
		buttons[1].drawButton(graphicsContext, sp, 50);		// (graphicsContext, 3*sp+x*15+34, 50);
		buttons[2].drawButton(graphicsContext, 2*sp+3*x+17, 10);
		buttons[3].drawButton(graphicsContext, 2*sp+3*x+17, 50);
		buttons[4].drawButton(graphicsContext, 3*sp+x*14+34, 10);
		buttons[5].drawButton(graphicsContext, 3*sp+x*14+34, 50);
	}

	public void setButtonsUnactive() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setIsActive(false);
		}
	}

}

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Sequence {
		Simon simon = new Simon();
	
	public void SimonSays() {

		start();
		simon.gameTimer.start();
		Graphics g = simon.contentPanel.getGraphics();
		this.simon.paint((Graphics2D) g);

	}

	public void start() {
		createSequence();

		simon.score = 0;
	}

	public void createSequence() {
		simon.sequence.clear();

		for (int counter = 0; counter < simon.numberofcolors; counter++) {
			int color = simon.random.nextInt(4) + 1;
			simon.sequence.add(color);
		}
	}
}

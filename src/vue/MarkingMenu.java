package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.PanelUI;

public class MarkingMenu extends JPanel {

	private ArrayList<Arc2D> arcsTools = new ArrayList<Arc2D>();
	private ArrayList<Arc2D> arcsChoices = new ArrayList<Arc2D>();
	private ArrayList<Arc2D> arcsColors = new ArrayList<Arc2D>();
	private int activate;
	private ArrayList<String> labelsTools = new ArrayList<String>();
	private ArrayList<String> labelsChoices = new ArrayList<String>();
	private ArrayList<Color> Colors = new ArrayList<Color>();
	int width, height;

	public StateMenu currentState;

	public MarkingMenu() {
		super();
		this.width = 0;
		this.height = 0;
		currentState = StateMenu.CHOICE;
	}

	public MarkingMenu(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		currentState = StateMenu.CHOICE;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		switch (currentState) {
		case CHOICE:
			drawChoices(g2);
			break;
		case TOOLS:
			drawTools(g2);
			break;
		case COLORS:
			drawColors(g2);
			break;
		default:
			break;
		}
	}

	public void drawColors(Graphics2D g) {
		int i = 0;
		for (Color c : Colors) {

//			double midAngle = 90 + (360 / Colors.size()) * i + (360 / Colors.size()) / 2;

			if (activate == i)
				g.setColor(c.darker());
			else
				g.setColor(c);
			g.fill(arcsColors.get(i));

			g.setColor(Color.BLACK);
			g.draw(arcsColors.get(i));

//			g.setColor(Color.WHITE);

			i++;

		}
	}

	public void drawTools(Graphics2D g) {
		int i = 0;
		for (String label : labelsTools) {

			double midAngle = 90 + (360 / labelsTools.size()) * i + (360 / labelsTools.size()) / 2;
			// System.out.println("i = "+i+" "+Math.cos(midAngle*Math.PI/180)+"
			// "+Math.sin(midAngle*Math.PI/180));

			if (activate == i)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.CYAN);
			g.fill(arcsTools.get(i));

			g.setColor(Color.BLACK);
			g.draw(arcsTools.get(i));

			g.setColor(Color.WHITE);
			g.drawString(label,
					Math.round(arcsTools.get(i).getCenterX() + Math.cos(midAngle * Math.PI / 180) * (width / 4)),
					Math.round(arcsTools.get(i).getCenterY() + -Math.sin(midAngle * Math.PI / 180) * (height / 4)));

			i++;
		}
	}

	public void drawChoices(Graphics2D g) {
		int i = 0;
		for (String label : labelsChoices) {

			double midAngle = 90 + (360 / labelsChoices.size()) * i + (360 / labelsChoices.size()) / 2;

			if (activate == i)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.CYAN);
			g.fill(arcsChoices.get(i));

			g.setColor(Color.BLACK);
			g.draw(arcsChoices.get(i));

			g.setColor(Color.WHITE);
			g.drawString(label,
					Math.round(arcsChoices.get(i).getCenterX() + Math.cos(midAngle * Math.PI / 180) * (width / 4)),
					Math.round(arcsChoices.get(i).getCenterY() + -Math.sin(midAngle * Math.PI / 180) * (height / 4)));

			i++;
		}
	}

	public ArrayList<Arc2D> getArcsColors() {
		return this.arcsColors;
	}

	public ArrayList<Arc2D> getArcsTools() {
		return this.arcsTools;
	}

	public ArrayList<Arc2D> getArcsChoices() {
		return this.arcsChoices;
	}

	public void setActivate(int activate) {
		this.activate = activate;
	}

	public void addToolLabel(String label) {
		labelsTools.add(label);
		updateToolArcs();
	}

	private void updateToolArcs() {
		arcsTools.clear();
		int i = 0;
		for (String label : labelsTools) {
			arcsTools.add(new Arc2D.Float(new Rectangle(0, 0, width - 5, height - 5),
					90 + (360 / labelsTools.size()) * i, 360 / labelsTools.size(), Arc2D.PIE));
			i++;
		}
	}

	public void addChoiceLabel(String label) {
		labelsChoices.add(label);
		updateChoicesArcs();
	}

	private void updateChoicesArcs() {
		arcsChoices.clear();
		int i = 0;
		for (String label : labelsChoices) {
			arcsChoices.add(new Arc2D.Float(new Rectangle(0, 0, width - 5, height - 5),
					90 + (360 / labelsChoices.size()) * i, 360 / labelsChoices.size(), Arc2D.PIE));
			i++;
		}
	}

	public void addColor(Color c) {
		Colors.add(c);
		updateColorsArcs();
	}

	private void updateColorsArcs() {
		arcsColors.clear();
		int i = 0;
		for (Color c : Colors) {
			arcsColors.add(new Arc2D.Float(new Rectangle(0, 0, width - 5, height - 5), 90 + (360 / Colors.size()) * i,
					360 / Colors.size(), Arc2D.PIE));
			i++;
		}
	}

	public StateMenu getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateMenu state) {
		this.currentState = state;
		System.out.println(currentState);
	}

}

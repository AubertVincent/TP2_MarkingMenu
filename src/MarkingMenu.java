import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.ToolBarUI;

public class MarkingMenu extends PanelUI{
	private Arc2D arcs[];
	private int activate;
//	private Color colors[];
//	private HashMap<Arc2D,Color> map;
	
	public MarkingMenu() {
		super();
	}
	
	
	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO Auto-generated method stub
//		super.paint(g, c);
		
		JPanel menu = (JPanel) c;
		
		Graphics2D g2 = (Graphics2D) g;

		Component tab[] = menu.getComponents();
		arcs = new Arc2D[tab.length];
		for(int i=0;i<tab.length;i++) {
			
			arcs[i] = new Arc2D.Float(new Rectangle(0, 0, menu.getWidth()-5,menu.getHeight()-5), 90+(360/tab.length)*i, 360/tab.length, Arc2D.PIE);
			
			double midAngle = 90+(360/tab.length)*i+(360/tab.length)/2;
//			System.out.println("i = "+i+" "+Math.cos(midAngle*Math.PI/180)+" "+Math.sin(midAngle*Math.PI/180));
			tab[i].setLocation((int)Math.round(arcs[i].getCenterX()+Math.cos(midAngle*Math.PI/180)*(menu.getWidth()/4))-(tab[i].getWidth()/2), (int)Math.round(arcs[i].getCenterY() + -Math.sin(midAngle*Math.PI/180)*(menu.getHeight()/4)-(tab[i].getHeight()/2)));
			
			if(activate==i)
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.CYAN);
			g2.fill(arcs[i]);
			
			g2.setColor(Color.BLACK);
			g2.draw(arcs[i]);
		}
		
		
		
		//TODO set the menu for the item 9 and 10
	}
	
	
	
	public Arc2D[] getArcs() {
		return this.arcs;
	}

	public void setActivate(int activate) {
		this.activate = activate;
	}
	
}

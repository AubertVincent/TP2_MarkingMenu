import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.ToolBarUI;

public class MarkingMenu extends PanelUI{
	private Arc2D arcs[];
	private Color colors[];
	
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
			
			g2.setColor(Color.BLUE);
			g2.fill(arcs[i]);
		}
		
		
		
		//TODO set the menu for the item 9 and 10
	}
	
	public Arc2D[] getArcs() {
		return this.arcs;
	}
	
}

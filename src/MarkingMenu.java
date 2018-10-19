import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.ToolBarUI;

public class MarkingMenu extends PanelUI{
	public MarkingMenu() {
		super();
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO Auto-generated method stub
//		super.paint(g, c);
		
		JPanel toolbar = (JPanel) c;
		
//		for(Component comp : toolbar.getComponents()) {
//			comp.setLocation(0, 0);
//		}
		float[] coefx = new float[]{ 1 , (float) 0.25, (float) -0.25, -1, -1,(float)-0.25,(float)0.25 };
		float[] coefy = new float[] {1 , 1 , 1,1,(float)-1.5,(float)-1.5,(float)-1.5};
		toolbar.getComponent(0).setLocation(100,100);
		Component tab[] = toolbar.getComponents();
		for(int i=1;i<tab.length && i<8;i++) {
			tab[i].setLocation(tab[i-1].getX() + Math.round((float) (tab[i-1].getWidth())*coefx[i-1]), tab[i-1].getY()+ Math.round((float)(tab[i-1].getHeight())*coefy[i-1])+5);			
		}

		
		//TODO set the menu for the item 9 and 10
	}
}

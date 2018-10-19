//////////////////////////////////////////////////////////////////////////////
// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/* paint *******************************************************************/

class Paint extends JFrame {
	Vector<Shape> shapes = new Vector<Shape>();
	HashMap<Shape, Color> color = new HashMap<Shape, Color>();

	Color currentColor = Color.BLACK;

	class Tool extends AbstractAction implements MouseInputListener {
		Point o;
		Shape shape;

		public Tool(String name) {
			super(name);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			o = e.getPoint();
		}

		public void mouseReleased(MouseEvent e) {
			shape = null;
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	Tool tools[] = { new Tool("pen") {
		public void mouseDragged(MouseEvent e) {
			Path2D.Double path = (Path2D.Double) shape;
			if (path == null) {
				path = new Path2D.Double();
				path.moveTo(o.getX(), o.getY());
				shapes.add(shape = path);
			}
			path.lineTo(e.getX(), e.getY());
			color.put(path, currentColor);
			panel.repaint();
		}
	}, new Tool("rect") {
		public void mouseDragged(MouseEvent e) {
			Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null) {
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				shapes.add(shape = rect);
			}
			rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			color.put(rect, currentColor);
			panel.repaint();
		}
	}, new Tool("eli") {
		public void mouseDragged(MouseEvent e) {
			Ellipse2D.Double eli = (Ellipse2D.Double) shape;
			if (eli == null) {
				eli = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
				shapes.add(shape = eli);
			}
			eli.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			color.put(eli, currentColor);
			panel.repaint();
		}
	} };
	Tool tool;

	JPanel panel;

	public Paint(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		JToolBar toolbar = new JToolBar() {
			{
				for (AbstractAction tool : tools) {
					add(tool);
				} // , new Tool("color") {
					// public void mouseClicked(MouseEvent e) {
					// String[] items = {"item1", "item2"};
					// JComboBox LeNomDeTaComboBox = new JComboBox(items);
					// currentColor = Color.RED;
					// }
				String[] items = { "black", "red", "green", "yellow", "blue" };
				JComboBox<String> color = new JComboBox<String>(items);
				color.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						String currentColor = (String) e.getItem();
						switch (currentColor) {
						case "red":
							Paint.this.currentColor = Color.RED;
							break;
						case "green":
							Paint.this.currentColor = Color.GREEN;
							break;
						case "yellow":
							Paint.this.currentColor = Color.YELLOW;
							;
							break;
						case "blue":
							Paint.this.currentColor = Color.BLUE;
							;
							break;
						default:
							Paint.this.currentColor = Color.BLACK;
						}

					}
				});
				add(color);

				String currentColor = (String) color.getSelectedItem();
				switch (currentColor) {
				case "red":
					Paint.this.currentColor = Color.RED;
					break;
				case "green":
					Paint.this.currentColor = Color.GREEN;
					break;
				case "yellow":
					Paint.this.currentColor = Color.YELLOW;
					;
					break;
				case "blue":
					Paint.this.currentColor = Color.BLUE;
					;
					break;
				default:
					Paint.this.currentColor = Color.BLACK;
				}
			}
		};
		// toolbar.setUI(new MarkingMenu());

		add(toolbar, BorderLayout.PAGE_START);
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());

				g2.setColor(Color.BLACK);
				for (Shape shape : shapes) {
					g2.setColor(color.get(shape));
					g2.draw(shape);
				}
			}
		};
		JPanel panel2 = new JPanel() {
			{
				setOpaque(false);
			}
		};

		panel.setLayout(null);
		JLabel label = new JLabel("Salut1");
		label.setPreferredSize(new Dimension(50,20));
		JLabel label2 = new JLabel("Salut2");
		label2.setPreferredSize(new Dimension(50,20));
		JLabel label3 = new JLabel("Salut3");
		label3.setPreferredSize(new Dimension(50,20));
		JLabel label4 = new JLabel("Salut4");
		label4.setPreferredSize(new Dimension(50,20));
		JLabel label5 = new JLabel("Salut5");
		label5.setPreferredSize(new Dimension(50,20));
		JLabel label6 = new JLabel("Salut6");
		label6.setPreferredSize(new Dimension(50,20));
		JLabel label7 = new JLabel("Salut7");
		label7.setPreferredSize(new Dimension(50,20));
		JLabel label8 = new JLabel("Salut8");
		label8.setPreferredSize(new Dimension(50,20));
		panel2.add(label);
		panel2.add(label2);
		panel2.add(label3);
		panel2.add(label4);
		panel2.add(label5);
		panel2.add(label6);
		panel2.add(label7);
		panel2.add(label8);
		panel2.setUI(new MarkingMenu());
		panel2.setBounds(0,0,200,200);
		panel.add(panel2);		
		panel2.setVisible(false);

		MouseInputAdapter listener = new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if (panel2.isVisible()) {

					panel2.setVisible(false);
				} else {
					panel2.setLocation(p.x-panel2.getWidth()/2,p.y-panel2.getHeight()/2);
					panel2.setVisible(true);
				}
			}
		};
		panel.addMouseListener(listener);
		add(panel);
		

		pack();
		setVisible(true);
	}

	/* main *********************************************************************/

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Paint paint = new Paint("paint");
			}
		});
	}
}
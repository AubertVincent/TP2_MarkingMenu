package vue;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import controleur.Controleur;

public class Vue extends JFrame {
	Vector<Shape> shapes = new Vector<Shape>();
	HashMap<Shape, Color> color = new HashMap<Shape, Color>();

	public Color currentColor;
	Controleur control;
	MarkingMenu menu;
	JPanel panel;

	class Tool extends AbstractAction implements MouseInputListener {
		Point o;
		Shape shape;
		private String name;
		int button;

		public Tool(String name) {
			super(name);
			this.name = name;
		}

		public String getName() {
			return name;
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
			button=e.getButton();
		}

		public void mouseReleased(MouseEvent e) {
			shape = null;
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	Color colors[] = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN };

	Tool tools[] = { new Tool("pen") {
		public void mouseDragged(MouseEvent e) {
			if(button==MouseEvent.BUTTON1) {
				Path2D.Double path = (Path2D.Double) shape;
				if (path == null) {
					path = new Path2D.Double();
					path.moveTo(o.getX(), o.getY());
					control.addShape(shape = path);
				}
				path.lineTo(e.getX(), e.getY());
				control.addColorToShape(path, currentColor);
				panel.repaint();
			}
			
		}
	}, new Tool("rect") {
		public void mouseDragged(MouseEvent e) {
			if(button==MouseEvent.BUTTON1) {
				Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null) {
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				control.addShape(shape = rect);
			}
			rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			control.addColorToShape(rect, currentColor);
			panel.repaint();
			}
			
		}
	}, new Tool("eli") {
		public void mouseDragged(MouseEvent e) {
			if(button==MouseEvent.BUTTON1) {
				Ellipse2D.Double eli = (Ellipse2D.Double) shape;
			if (eli == null) {
				eli = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
				control.addShape(shape = eli);
			}
			eli.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			control.addColorToShape(eli, currentColor);
			panel.repaint();
			}
			
		}
	} };
	Tool tool;


	public Vue(String title, Controleur control) {
		super(title);
		this.control = control;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		JToolBar toolbar = new JToolBar() {
			{
				for (AbstractAction tool : tools) {
					add(tool);
				}
				String[] items = { "black", "red", "green", "yellow", "blue" };
				JComboBox<String> color = new JComboBox<String>(items);
				color.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						String currentColor = (String) e.getItem();
						switch (currentColor) {
						case "red":
							Vue.this.control.changeCurrentColor(Color.RED);
							break;
						case "green":
							Vue.this.control.changeCurrentColor(Color.GREEN);
							break;
						case "yellow":
							Vue.this.control.changeCurrentColor(Color.YELLOW);
							break;
						case "blue":
							Vue.this.control.changeCurrentColor(Color.BLUE);
							break;
						default:
							Vue.this.control.changeCurrentColor(Color.BLACK);
						}

					}
				});
				add(color);

				String currentColor = (String) color.getSelectedItem();
			}
		};

		add(toolbar, BorderLayout.PAGE_START);
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());

				for (Shape shape : shapes) {
					g2.setColor(color.get(shape));
					g2.draw(shape);
				}
			}
		};

		// menu.setVisible(false);

		MouseInputAdapter listener = new MouseInputAdapter() {
			Point origin;
			int button;
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				Point p = e.getPoint();
//				// if (menu.isVisible()) {
//				//
//				// menu.setVisible(false);
//				// } else {
//				// menu.setLocation(p.x-menu.getWidth()/2,p.y-menu.getHeight()/2);
//				// menu.setVisible(true);
//				// menu.repaint();
//				// }
//				if (menu == null) {
//					menu = createMenu();
//					panel.add(menu);
//					menu.setBounds(p.x - menu.getWidth() / 2, p.y - menu.getHeight() / 2, 200, 200);
//				} else {
//					System.out.println("CLOSING MENU");
//					panel.remove(menu);
//					panel.repaint();
//					menu = null;
//				}
//
//			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				origin = e.getPoint();
				button=e.getButton();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(button==MouseEvent.BUTTON3) {
					if (menu == null) {
						menu = createMenu();
						panel.add(menu);
						menu.setBounds(origin.x - menu.getWidth() / 2, origin.y - menu.getHeight() / 2, 200, 200);
						
					}else if(isOutOfMenu(e.getPoint())) {
						switch(menu.getCurrentState()) {
						case CHOICE:
							if(menu.getActivate()==0) {
								menu.setCurrentState(StateMenu.TOOLS);
								menu.setBounds(e.getX()- menu.getWidth() / 2,e.getY()- menu.getHeight() / 2,200,200);
								menu.repaint();
							}else {
								menu.setCurrentState(StateMenu.COLORS);
								menu.setBounds(e.getX()- menu.getWidth() / 2,e.getY()- menu.getHeight() / 2,200,200);
								menu.repaint();
							}
							break;
						default:
							break;
						}
					}
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if (menu != null) {
					switch(menu.getCurrentState()) {
					case TOOLS:
						panel.removeMouseListener(tool);
						panel.removeMouseMotionListener(tool);
						tool = tools[menu.getActivate()];
						panel.addMouseListener(tool);
						panel.addMouseMotionListener(tool);
						menu.setCurrentState(StateMenu.CHOICE);
						System.out.println("CLOSING MENU");
						panel.remove(menu);
						panel.repaint();
						resetMenu();
						break;
					case COLORS:
						control.changeCurrentColor(colors[menu.getActivate()]);
					default:
						System.out.println("CLOSING MENU");
						panel.remove(menu);
						panel.repaint();
						resetMenu();
						break;
					}
					
					
				}
			}

		};

		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);
		add(panel);

		pack();
		setVisible(true);
	}
	

	public boolean isOutOfMenu(Point p) {
		
		return (p.x<menu.getX() || p.x>menu.getX()+menu.getWidth() || p.y <menu.getY() ||p.y>menu.getY()+menu.getHeight() );
	}
	
	public MarkingMenu createMenu() {
		MarkingMenu menu = new MarkingMenu(200,200) {
			{
				setOpaque(false);
			}
		};
		menu.setBounds(0, 0, 200, 200);

		panel.setLayout(null);

		for (int i = 0; i < colors.length; i++) {
			final int j = i;
			menu.addColor(colors[j]);

			MouseInputAdapter menulistener = new MouseInputAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (menu.getCurrentState() == StateMenu.COLORS
							&& menu.getArcsColors().get(j).contains(new Point(e.getPoint().x-menu.getX(),e.getPoint().y-menu.getY()))) {
						menu.setActivate(j);
						menu.repaint();
					}
				}
			};
			panel.addMouseMotionListener(menulistener);
			panel.addMouseListener(menulistener);
		}

		// MOUSE LISTENER OUTILS
		for (int i = 0; i < tools.length; i++) {
			final int j = i;
			menu.addToolLabel(tools[j].getName());

			MouseInputAdapter menulistener = new MouseInputAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (menu.getCurrentState() == StateMenu.TOOLS
							&& menu.getArcsTools().get(j).contains(new Point(e.getPoint().x-menu.getX(),e.getPoint().y-menu.getY()))) {
						menu.setActivate(j);
						menu.repaint();
					}
				}
			};
			panel.addMouseMotionListener(menulistener);
			panel.addMouseListener(menulistener);
		}
		
		//MOUSE LISTENER CHOICES
		menu.addChoiceLabel("Outils");

		MouseInputAdapter menulistener = new MouseInputAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (menu.getCurrentState() == StateMenu.CHOICE
						&& menu.getArcsChoices().get(0).contains(new Point(e.getPoint().x-menu.getX(),e.getPoint().y-menu.getY()))) {
					menu.setActivate(0);
					menu.repaint();
				}
			}
		};

		panel.addMouseMotionListener(menulistener);
		panel.addMouseListener(menulistener);

		menu.addChoiceLabel("Couleurs");

		menulistener = new MouseInputAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (menu.getCurrentState() == StateMenu.CHOICE
						&& menu.getArcsChoices().get(1).contains(new Point(e.getPoint().x-menu.getX(),e.getPoint().y-menu.getY()))) {
					menu.setActivate(1);
					menu.repaint();
				}
			}
		};

		panel.addMouseMotionListener(menulistener);
		panel.addMouseListener(menulistener);

		return menu;

	}

	public void updateShapes(Vector<Shape> shapes) {
		this.shapes = shapes;
	}

	public void updateColors(HashMap<Shape, Color> color) {
		this.color = color;
	}

	public void updateCurrentColor(Color c) {
		this.currentColor = c;
	}
	
	public void resetMenu() {
		menu=null;
	}

}
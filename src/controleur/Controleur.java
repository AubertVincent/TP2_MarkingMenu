package controleur;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import modele.Modele;

public class Controleur {
	private Modele modele;
	private Shape shape;
	private Color color;
	private ArrayList<Color> listColor;
	
	public Controleur(Modele modele) {
		this.modele = modele;
		listColor = new ArrayList<Color>();
		listColor.add(Color.BLACK);
		listColor.add(Color.RED);
		listColor.add(Color.GREEN);
		listColor.add(Color.YELLOW);
		listColor.add(Color.BLUE);
	}
	
	public void addShape(Shape shape) {
		this.shape = shape;
		controlShape();
	}
	
	public void addColorToShape(Shape shape,Color color) {
		this.shape = shape;
		this.color = color;
		controlColorToShape();
	}

	private void controlColorToShape() {
		if(this.listColor.contains(this.color)) {
			modele.addColorToShape(this.shape,this.color);
			System.out.println("Adding new color"+this.color);
		}
		this.color=null;
		this.shape=null;
	}

	private void controlShape() {
		modele.addShape(shape);
		shape=null;
	}
	
}

package modele;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import vue.Vue;

public class Modele {
	Vector<Shape> shapes;
	HashMap<Shape, Color> color;
	Color currentColor;
	ArrayList<Vue> views;

	public Modele() {
		shapes = new Vector<Shape>();
		color = new HashMap<Shape, Color>();
		views = new ArrayList<Vue>();
		currentColor = Color.BLACK;
	}

	public void addView(Vue view) {
		views.add(view);
	}

	public void notifyViews() {
		for (Vue view : views) {
			view.updateShapes(shapes);
			view.updateColors(color);
			view.updateCurrentColor(currentColor);
		}

	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
		notifyViews();
	}

	public void addColorToShape(Shape shape, Color color2) {
		this.color.put(shape, color2);
		notifyViews();
	}
}

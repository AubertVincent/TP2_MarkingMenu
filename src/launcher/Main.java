package launcher;

import controleur.Controleur;
import modele.Modele;
import vue.Vue;

public class Main {
	public static void main(String argv[]) {
		Modele modele = new Modele();
		Controleur control = new Controleur(modele);
		Vue paint = new Vue("paint",control);
		
		modele.addView(paint);
	}
}

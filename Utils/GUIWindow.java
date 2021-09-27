package Utils;

public abstract class GUIWindow {
	
	
	public abstract void hide();
	public abstract void show();
	
	public abstract void spielerWechseln(int spieler);
	public abstract int displayResult(String[] names, int[] score);
	
}

import processing.core.PApplet;

/**
 * Main class to run game
 * @author Preston Wong and Jacky Ho
 *
 */
class Main {
	  	public static void main(String[] args) {

    	String[] processingArgs = {"ChristmasInvaders"};
		ChristmasInvaders mySketch = new ChristmasInvaders();
	    PApplet.runSketch(processingArgs, mySketch);
		PApplet.main("ChristmasInvaders");
	}
}
  

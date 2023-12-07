import processing.core.PApplet;
import processing.core.PImage;

public class ChristmasInvaders extends PApplet {   
    
    int[][][] enemies; // 2D array to store enemies' positions
    int numCols = 10; // Number of columns
    int numRows = 5; // Number of rows
    int enemyRadius = 10; // Radius of enemies
    int spacingX = 50; // Horizontal spacing between enemies
    int spacingY = 50; // Vertical spacing between enemies


    
    /**
     * Sets the size of the window.
     */
    public void settings() {
        size(1280, 720);
        initializeEnemies();
    }

    /**
     * Initializes the game state and loads necessary resources.
     */
    public void load() {
        
    }

    public void draw() {
        background(255);
        drawEnemies();
    }

    public void drawEnemies() {
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                ellipse(enemies[i][j][0], enemies[i][j][1], enemyRadius * 2, enemyRadius * 2);
            }
        }
        
        // Move enemies down every few frames
        if (frameCount % (60 * 2) == 0) { // Adjust the frameCount value based on your desired frequency
            moveEnemiesDown();
        }
    }

    public void initializeEnemies() {
        enemies = new int[numCols][numRows][2];
  
        // Initialize enemies' positions in a grid
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int x = i * spacingX + enemyRadius * 10; // Adjust the starting X position
                int y = j * spacingY + enemyRadius; // Adjust the starting Y position
                enemies[i][j][0] = x;
                enemies[i][j][1] = y;
            }
        }
    }

    public void moveEnemiesDown() {
    // Move enemies down in their rows
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
                enemies[i][j][1] += spacingY;
            }
        }   
    }
}

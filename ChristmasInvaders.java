import processing.core.PApplet;
import processing.core.PImage;

public class ChristmasInvaders extends PApplet {

    /**
     * Enemy Variables
     */
    int[][][] Enemies; // 2D array to store enemies' 
    int Wave = 1;
    int enemyKilled = 0;
    int numCols = 15; // Number of columns
    int numRows = 5; // Number of rows
    int enemyRadius = 10; // Radius of enemies
    int spacingX = 50; // Horizontal spacing between enemies
    int spacingY = 50; // Vertical spacing between enemies

    /**
     * Player Variables
     */
    int playerX; // X position of the player
    int playerY; // Y position of the player
    int playerSpeed = 5; // Speed of the player

    /**
     * Projectile Variables
     */
    int[][] projectiles; // Array to store projectile positions
    int maxProjectiles = 0; // Maximum number of projectiles
    int projectileSpeed = 8; // Speed of the projectiles
    int projectileSize = 10; // Size of the projectiles
    int nextProjectileIndex = 0; // Index to track the next available slot in the projectiles array

    boolean keySpace = false;
    boolean gameOver = false; // Checks if the game is over

    PImage projectile;
    PImage player;

    public void settings() {
        size(1200, 675);
    }

    public void setup() {
        frameRate(60);
        initializeEnemies();
        initializePlayer(); 
        player = loadImage("Player.png");
        player.resize(72, 39);
        projectile = loadImage("Projectile.png");
        
    }

    public void draw() {
        if (!gameOver) {
            background(255);
            drawEnemies();
            storeProjectiles();  
            moveProjectiles();
            drawPlayer();
            if (enemyKilled % 75 == 0) {
                storeEnemies();
            }
            fill (0);
            text(enemyKilled, width / 2, height / 2);
        } else {
            displayGameOver();
        }
    }

    public void drawEnemies() {
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                ellipse(Enemies[i][j][0], Enemies[i][j][1], enemyRadius * 2, enemyRadius * 2);
            }
        }

        // Move enemies down every few frames
        if (frameCount % (60 * 2) == 0) {
            moveEnemiesDown(); 
        }
    }   

    public void storeEnemies() {
        // Create a new temporary array for the new set of enemies
        int[][][] newEnemies = new int[numCols][numRows][2];

        // Initialize enemies' positions in a grid
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int x = i * spacingX + enemyRadius * 10;
                int y = j * spacingY + enemyRadius;
                newEnemies[i][j][0] = x;
                newEnemies[i][j][1] = y;
            }
        }

        // Update enemies to point to the new array
        Enemies = newEnemies;
    }

    public void moveEnemiesDown() {
        
        // Move enemies down in their rows
        for (int j = 0; j < numRows; j++) {
            for (int k = 0; k < numCols; k++) {
                Enemies[k][j][1] += 50;
                if (Enemies[k][j][1] + enemyRadius >= height - enemyRadius) {
                    gameOver = true;
                    break; // Exit the function if game-over
                }
            }
        }
    }

    private void displayGameOver() {
        textAlign(CENTER, CENTER);
        textSize(32);
        fill(255, 0, 0);
        text("Game Over", width / 2, height / 2);
    }


    public void initializeEnemies() {
        Enemies = new int[numCols][numRows][2];

        // Initialize enemies' positions in a grid
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int x = i * spacingX + enemyRadius * 10; // Adjust the starting X position
                int y = j * spacingY + enemyRadius; // Adjust the starting Y position
                Enemies[i][j][0] = x;
                Enemies[i][j][1] = y;
            }
        }
    }

    public void initializePlayer() {
        // Initialize player position
        playerX = width / 2;
        playerY = height - 48;
    }

    public void drawPlayer() {
        // Draw player
        image(player, playerX, playerY);
    }

    public void storeProjectiles() {
        // Create a new temporary array with increased capacity
        int[][] newProjectiles = new int[nextProjectileIndex + 15][2];

        // Copy existing projectiles to the new array
        for (int i = 0; i < nextProjectileIndex; i++) {
            newProjectiles[i][0] = projectiles[i][0];
            newProjectiles[i][1] = projectiles[i][1];
        }

        // Update projectiles to point to the new array
        projectiles = newProjectiles;

        // Update maxProjectiles to the new capacity
        maxProjectiles = nextProjectileIndex + 15;
    }

    public void keyPressed() {
        // Move the player left or right on key press
        if (keyCode == LEFT && playerX - player.width / 2 > 0) {
            playerX -= playerSpeed * 5;
        } else if (keyCode == RIGHT && playerX + player.width / 2 < width) {
            playerX += playerSpeed * 5;
        } else if (key == ' ' && keySpace == false) {
            // Shoot a projectile when the spacebar is pressed
            if (nextProjectileIndex < maxProjectiles) {
                projectiles[nextProjectileIndex][0] = playerX + player.width / 2;
                projectiles[nextProjectileIndex][1] = playerY;
                nextProjectileIndex++;
                keySpace = true;
            }
        }
    }

    public void keyReleased() {
        if (key == ' ') {
            keySpace = false;
        }
    }

    private void moveProjectiles() {
        for (int i = 0; i < nextProjectileIndex; i++) {
            image(projectile, projectiles[i][0], projectiles[i][1]);
            projectiles[i][1] -= projectileSpeed;

            // Check for collision with enemies
            for (int j = 0; j < numCols; j++) {
                for (int k = 0; k < numRows; k++) {
                    float d = dist(projectiles[i][0], projectiles[i][1], Enemies[j][k][0] - 10, Enemies[j][k][1]);
                    if (d < enemyRadius + projectileSize / 2) {
                        // Reset projectile position on collision to off screen, different coordinates to not affect collision
                        projectiles[i][0] = -200;
                        projectiles[i][1] = -200;
                        Enemies[j][k][0] = -100; 
                        Enemies[j][k][1] = -100;
                        enemyKilled++;
                    } 
                }
            }
        }
    }
}

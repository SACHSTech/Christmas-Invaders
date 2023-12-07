import processing.core.PApplet;

public class ChristmasInvaders extends PApplet {

    /**
     * Enemy Variables
     */
    int[][][] enemies; // 2D array to store enemies' positions
    int numCols = 10; // Number of columns
    int numRows = 5; // Number of rows
    int enemyRadius = 10; // Radius of enemies
    int spacingX = 50; // Horizontal spacing between enemies
    int spacingY = 50; // Vertical spacing between enemies

    /**
     * Player Variables
     */
    int playerX; // X position of the player
    int playerY; // Y position of the player
    int playerWidth = 50; // Width of the player
    int playerSpeed = 5; // Speed of the player

    /**
     * Projectile Variables
     */
    int[][] projectiles; // Array to store projectile positions
    int maxProjectiles = 100; // Maximum number of projectiles
    int projectileSpeed = 8; // Speed of the projectiles
    int projectileSize = 10; // Size of the projectiles
    int nextProjectileIndex = 0; // Index to track the next available slot in the projectiles array

    public void settings() {
        size(1280, 720);
        initializeEnemies();
        initializePlayer();
        initializeProjectiles();
    }

    public void draw() {
        background(255);
        drawEnemies();
        drawPlayer();
        moveProjectiles();
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

    public void initializePlayer() {
        // Initialize player position
        playerX = width / 2;
        playerY = height - 50;
    }

    public void drawPlayer() {
        // Draw player
        rect(playerX - playerWidth / 2, playerY, playerWidth, 10);
    }

    public void initializeProjectiles() {
        projectiles = new int[maxProjectiles][2];
    }

    public void keyPressed() {
        // Move the player left or right on key press
        if (keyCode == LEFT && playerX - playerWidth / 2 > 0) {
            playerX -= playerSpeed*5;
        } else if (keyCode == RIGHT && playerX + playerWidth / 2 < width) {
            playerX += playerSpeed*5;
        } else if (key == ' ') {
            // Shoot a projectile when the spacebar is pressed
            if (nextProjectileIndex < maxProjectiles) {
                projectiles[nextProjectileIndex][0] = playerX;
                projectiles[nextProjectileIndex][1] = playerY;
                nextProjectileIndex++;
            }
        }
    }

    private void moveProjectiles() {
        for (int i = 0; i < nextProjectileIndex; i++) {
            ellipse(projectiles[i][0], projectiles[i][1], projectileSize, projectileSize);
            projectiles[i][1] -= projectileSpeed;

            // Check for collision with enemies
            for (int j = 0; j < numCols; j++) {
                for (int k = 0; k < numRows; k++) {
                    float d = dist(projectiles[i][0], projectiles[i][1], enemies[j][k][0], enemies[j][k][1]);
                    if (d < enemyRadius + projectileSize / 2) {
                        // Reset projectile position on collision
                        projectiles[i][0] = -1;
                        projectiles[i][1] = -1;
                        enemies[j][k][0] = -1; // Set enemy position off-screen
                        enemies[j][k][1] = -1;
                    }
                }
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

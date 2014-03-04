/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
//import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author liambrockley
 */
class SnakeEnvironment extends Environment implements CellLocationValidator {

    private GameState gameState = GameState.RUNNING;
    private RegionGrid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> jewels;
    private ArrayList<Point> bomb;
    private ArrayList<Point> portal;
    private double speed = 0;
    private double moveCounter = speed;
    private ArrayList<Enemy> enemies;
    private Boolean hasJewel = false;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {

        AudioPlayer.play("/resources/masala.wav");
        this.grid = new RegionGrid();

        this.setBackground(ResourceTools.loadImageFromResource("resources/Maya_Calendar_finalone.jpg"));
        //    this.setBackgroundImagePosition();

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(5, 2));

        //  this.grid.setColor(Color.GRAY);
        this.grid.setColor(new Color(10, 10, 10, 110));
        this.grid.setInteriorColor(new Color(0, 0, 0, 0));
        this.grid.setColumns(65);
        this.grid.setRows(42);
        this.grid.setCellHeight(14);
        this.grid.setCellWidth(14);
        this.grid.setPosition(new Point(0, 0));

        this.grid.setBottomOffset(12);
        this.grid.setLeftOffset(this.grid.getBottomOffset() + 10);
        this.grid.setRightOffset(this.grid.getBottomOffset() + 10);
        this.grid.setTopOffset(this.grid.getBottomOffset());



        this.jewels = new ArrayList<Point>();
        for (int i = 0; i < 3; i++) {
            this.jewels.add(getRandomExteriorGridLocation());
            //make it unable to spawn on enemy or in interior grid
        }

//        this.bomb = new ArrayList<Point>();
//        this.bomb.add(getRandomGridLocation());
//        this.bomb.add(getRandomGridLocation());



        this.enemies = new ArrayList<Enemy>();
        
        enemies.add(new Enemy(new Point(this.grid.getColumns()-10, this.grid.getRows()-10), this));
        enemies.add(new Enemy(new Point (this.grid.getColumns() -30, this.grid.getRows() - 10), this));
        enemies.add(new Enemy(new Point(this.grid.getColumns()-50, this.grid.getRows() - 10), this));
        




        this.portal = new ArrayList<Point>();
        this.portal.add(new Point(32, 20));

    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
    }

    private Point getRandomExteriorGridLocation() {
        Point newPoint = new Point();
        boolean invalid = true;
        
        do {
            newPoint.setLocation(getRandomGridLocation());
            invalid = grid.isInteriorCellLocation(newPoint);
        } while (invalid);
        
        return newPoint;
    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == gameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    snake.move();

                    for (Enemy enemy : enemies) {
                        enemy.move();
                    }

                    moveCounter = speed;
                    checkSnakeIntersection();

                    if (snake.getDirection() == Direction.RIGHT) {
                        if (snake.getHead().x >= this.grid.getColumns()) {
                            snake.getHead().x = 0;
                        }
                    } else if (snake.getDirection() == Direction.LEFT) {
                        if (snake.getHead().x <= -1) {
                            snake.getHead().x = this.grid.getColumns() - 1;
                        }
                    } else if (snake.getDirection() == Direction.DOWN) {
                        if (snake.getHead().y >= this.grid.getRows()) {
                            snake.getHead().y = 0;
                        }
                    } else if (snake.getDirection() == Direction.UP) {
                        if (snake.getHead().y <= -1) {
                            snake.getHead().y = this.grid.getRows() - 1;
                        }
                    }
                } else {
                    moveCounter--;
                }
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;

            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            if (this.jewels != null){
                for (int i = 0; i < this.jewels.size(); i++) {
                    //GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.jewels.get(i)), this.grid.getCellSize());

                    //graphics.fillOval(this.jewels.size(), this.jewels.size(),this.grid.getCellWidth(), this.grid.getCellHeight());//( this.grid.getCellPosition(this.jewels.get(i)), this.grid.getCellSize(), Color.GREEN);
                    GraphicsPalette.drawDiamond(graphics, this.grid.getCellPosition(this.jewels.get(i)), this.grid.getCellSize(), Color.GREEN);//graphics.fillOval(this.jewels.size(), this.jewels.size(),this.grid.getCellWidth(), this.grid.getCellHeight());//( this.grid.getCellPosition(this.jewels.get(i)), this.grid.getCellSize(), Color.GREEN);
                }
            }

            if (this.bomb != null) {
                for (int i = 0; i < this.bomb.size(); i++) {
                    GraphicsPalette.drawBomb(graphics, this.grid.getCellPosition(this.bomb.get(i)), this.grid.getCellSize(), Color.BLACK);

                }
            }

            if (this.portal != null) {
                for (int i = 0; i < this.portal.size(); i++) {
                    GraphicsPalette.enterPortal(graphics, this.grid.getCellPosition(this.portal.get(i)), this.grid.getCellSize(), Color.BLACK);
                }
            }
//            grid.getCellPosition(null);

            Point cellLocation;
            graphics.setColor(Color.BLACK);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                    graphics.setColor(Color.RED);

                }
            }
            //  GraphicsPalette.enterPortal(graphics, new Point (100,100), new Point(50, 50), Color.YELLOW);

            for (Enemy enemy : enemies) {
                enemy.paint(graphics, this.grid.getCellPosition(enemy.getCellLocation()), this.grid.getCellSize());
            }


            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Copperplate", Font.BOLD, 60));
            graphics.drawString("Score: " + this.score, 50, 50);


            if (gameState == gameState.ENDED) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Copperplate", Font.BOLD, 100));
                graphics.drawString("Game Over", 200, 300);

            }
            if (gameState == gameState.PAUSED) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Copperplate", Font.BOLD, 100));
                graphics.drawString("Paused", 250, 300);

            }


        }
    }

//    @SuppressWarnings("empty-statement")
    private void checkSnakeIntersection() {

        if (snake.checkSelfHit()) {
            this.gameState = GameState.ENDED;
            AudioPlayer.play("/resources/goddamnit.wav");
        }


        for (int i = 0; i < this.jewels.size(); i++) {
            if (snake.getHead().equals(this.jewels.get(i))) {
                this.jewels.get(i).setLocation 
                        //in front of head
                        (getRandomExteriorGridLocation());
                this.hasJewel = true;
                
                AudioPlayer.play("/resources/coin_flip.wav");
                System.out.println("Ate JEWEL");
            }
        }

//        for (int i = 0; i < this.bomb.size(); i++) {
//            if (snake.getHead().equals(this.bomb.get(i))) {
//                this.score = this.score - 2;
//                AudioPlayer.play("/resources/goddamnit.wav");
//                //   this.speed--;
//                this.bomb.get(i).x = (int) (Math.random() * this.grid.getColumns());
//                this.bomb.get(i).y = (int) (Math.random() * this.grid.getRows());
//            }
     //   }
        
        for (int j = 0; j < this.portal.size(); j++) {
            if ((snake.getHead().equals(this.portal.get(j)) && (hasJewel))){ 
                this.hasJewel = false;
                snake.grow(2);
                System.out.println("DROP JEWEL");
                this.score = this.score + 1;
                AudioPlayer.play("/resources/coin2.wav");
            }
            
       
//            if (this.hasJewel = true) {
//                System.out.println("yes jewel!");
//                //draw apple in front of snake head 
//            }



            for (int enemy = 0; enemy < enemies.size(); enemy++) {
                for (int snakePart = 0; snakePart < snake.getBody().size(); snakePart++) {
                    if (enemies.get(enemy).getCellLocation().equals(snake.getBody().get(snakePart))) {
                        System.out.println("Enemy crash");
                        this.enemies.get(enemy).setCellLocation(getRandomExteriorGridLocation());
                        //  this.gameState = GameState.ENDED;
                        AudioPlayer.play("/resources/goddamnit.wav");
                        this.score = this.score - 1;
                        this.snake.setBody(this.snake.getBody());

                    }
                }
            }

            if (this.score <= -10) {
                this.gameState = gameState.ENDED;
                AudioPlayer.play("/resources/goddamnit.wav");

            }
        }
    }

    @Override
    public boolean validate(Point cellLocation) {

        if (grid != null) {
            //check if outside grid...
            if ((cellLocation.y >= this.grid.getRows()
                    || (cellLocation.y <= -1)
                    || (cellLocation.x >= this.grid.getColumns())
                    || (cellLocation.x <= -1))) {
                return false;
            } else { // check if inside safety section
                return !(grid.isInteriorCellLocation(cellLocation));
            }
        }
        return true;
    }
}

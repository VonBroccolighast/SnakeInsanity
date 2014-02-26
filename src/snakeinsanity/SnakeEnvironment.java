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
    private RegionGrid gridTwo;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> poisonBottle;
    private ArrayList<Point> portal;
    private double speed = 0;
    private double moveCounter = speed;
    private ArrayList<Enemy> enemies;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {

        AudioPlayer.play("/resources/masala.wav");
        this.grid = new RegionGrid();

        this.setBackground(ResourceTools.loadImageFromResource("resources/Maya_Calendar_finalone.jpg"));

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(5, 2));

      //  this.grid.setColor(Color.GRAY);
        this.grid.setColor(new Color (160, 140, 140, 128));
        this.grid.setInteriorColor(new Color(0, 0, 0, 0));
        this.grid.setColumns(55);
        this.grid.setRows(33);
        this.grid.setCellHeight(15);
        this.grid.setCellWidth(15);
        this.grid.setPosition(new Point(50, 75));

        this.grid.setBottomOffset(5);

        this.apples = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            this.apples.add(getRandomGridLocation());
        }

        this.poisonBottle = new ArrayList<Point>();
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());



        this.enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(new Point(0, 0), this));
        enemies.add(new Enemy(new Point(this.grid.getColumns() - 1, this.grid.getRows() - 1), this));


        this.portal = new ArrayList<Point>();
        this.portal.add(new Point(18, 20));

    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
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

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    //GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());

                    //graphics.fillOval(this.apples.size(), this.apples.size(),this.grid.getCellWidth(), this.grid.getCellHeight());//( this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize(), Color.GREEN);
                    GraphicsPalette.drawDiamond(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize(), Color.GREEN);//graphics.fillOval(this.apples.size(), this.apples.size(),this.grid.getCellWidth(), this.grid.getCellHeight());//( this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize(), Color.GREEN);
                }
            }

            if (this.poisonBottle != null) {
                for (int i = 0; i < this.poisonBottle.size(); i++) {
                    GraphicsPalette.drawBomb(graphics, this.grid.getCellPosition(this.poisonBottle.get(i)), this.grid.getCellSize(), Color.BLACK);

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


            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Comic Sans", Font.ITALIC, 60));
            graphics.drawString("Score: " + this.score, 50, 50);


            if (gameState == gameState.ENDED) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Chalkboard", Font.BOLD, 100));
                graphics.drawString("Game Over", 200, 300);

            }
            if (gameState == gameState.PAUSED) {
                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Chalkboard", Font.BOLD, 100));
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


        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                this.score++;
                AudioPlayer.play("/resources/coin_flip.wav");
                snake.grow(2);
                this.apples.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.apples.get(i).y = (int) (Math.random() * this.grid.getRows());

            }
        }

        for (int i = 0; i < this.poisonBottle.size(); i++) {
            if (snake.getHead().equals(this.poisonBottle.get(i))) {
                this.score = this.score - 2;
                AudioPlayer.play("/resources/goddamnit.wav");
                //   this.speed--;
                this.poisonBottle.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.poisonBottle.get(i).y = (int) (Math.random() * this.grid.getRows());
            }
        }

        for (int enemy = 0; enemy < enemies.size(); enemy++) {
            for (int snakePart = 0; snakePart < snake.getBody().size(); snakePart++) {
                if (enemies.get(enemy).getCellLocation().equals(snake.getBody().get(snakePart))) {
                    System.out.println("Enemy crash");
                    this.gameState = GameState.ENDED;
                }
            }
        }

//            for (enemies.contains(snake.getHead())) {
//                for (this.snake.getBody()   ) {
//
//                    if (snake.getBody().equals(this.enemies)) {
//                        AudioPlayer.play("/resources/goddamnit.wav");
//                        this.gameState = gameState.ENDED;
//
//                    }
//
//                }
//
//                if (this.score <= -10) {
//                    this.gameState = gameState.ENDED;
//                    AudioPlayer.play("/resources/goddamnit.wav");
//
//                }
//            }
////
    }

    //       public boolean intersects(Point location, ArrayList<Point> locations) {
    //  for (int i = 0; i < locations.size(); i++) {
    //    if (location.equals(locations.get(i))) {
    //      return true;
    //   }
    //}
    //    return false;
//    }
//}
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

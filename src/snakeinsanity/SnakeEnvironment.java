/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
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
class SnakeEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> poisonBottle;
    private ArrayList<Point> portal;
    private double speed = 0;
    private double moveCounter = speed;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.grid = new Grid();
        //this.setBackground (ResourceTools.loadImageFromResource("resources/Jungle.jpg"));
        this.setBackground(Color.BLACK);


        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(5, 2));

        this.grid.setColor(Color.GREEN);
        this.grid.setColumns(55);
        this.grid.setRows(33);
        this.grid.setCellHeight(15);
        this.grid.setCellWidth(15);
        this.grid.setPosition(new Point(50, 75));
//        this.grid.se

        this.apples = new ArrayList<Point>();
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());

        this.poisonBottle = new ArrayList<Point>();
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());
        this.poisonBottle.add(getRandomGridLocation());


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
                        if (snake.getHead().y < -1) {
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
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;

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
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
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
            graphics.setColor(Color.GREEN);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());

                }
            }
        }
        //  GraphicsPalette.enterPortal(graphics, new Point (100,100), new Point(50, 50), Color.YELLOW);

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Comic Sans", Font.ITALIC, 60));
        graphics.drawString("Score: " + this.score, 50, 50);


        if (gameState == gameState.ENDED) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Chalkboard", Font.BOLD, 100));
            graphics.drawString("Game Over", 200, 300);

        }

    }

    private void checkSnakeIntersection() {

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                this.score++;
                snake.grow(2);
                this.apples.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.apples.get(i).y = (int) (Math.random() * this.grid.getRows());
                System.out.println("Apple eaten!");
            }
        }

        for (int i = 0; i < this.poisonBottle.size(); i++) {
            if (snake.getHead().equals(this.poisonBottle.get(i))) {
                String bombDeath = JOptionPane.showInputDialog("BOOOOOMMMMM!!!!");
                this.score--;
                this.poisonBottle.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.poisonBottle.get(i).y = (int) (Math.random() * this.grid.getRows());

            }
        }
    }
}

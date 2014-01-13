/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import snakeinsanity.Direction;
import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



/**
 *
 * @author liambrockley
 */
class SnakeEnvironment extends Environment {
private Grid grid;
private int score =0;
private Snake snake;

private int delay = 4;
private int moveCounter= delay;
    
public SnakeEnvironment() {
        
    }

    @Override
    public void initializeEnvironment() {
        this.grid= new Grid();
        this.setBackground (ResourceTools.loadImageFromResource("resources/Jungle.jpg"));
        
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5,5));
        this.snake.getBody().add(new Point(5,4));
        this.snake.getBody().add(new Point(5,3));
        this.snake.getBody().add(new Point(5,2));


    }

    @Override
    public void timerTaskHandler() {
        if (snake != null){
        snake.move();
        if (moveCounter <= 0){
            snake.move();
            moveCounter = delay;
            
            }else{ 
            
            moveCounter--;
        
        }
        
        
        }
    
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            this.score += 1;
        }else if (e.getKeyCode() == KeyEvent.VK_W){
            snake.setDirection(Direction.UP);
            snake.move();
        }else if (e.getKeyCode() == KeyEvent.VK_S){
            snake.setDirection(Direction.DOWN);
            snake.move();
        }else if (e.getKeyCode() == KeyEvent.VK_A){
            snake.setDirection(Direction.LEFT);
            snake.move();
        }else if (e.getKeyCode() == KeyEvent.VK_D){
            snake.setDirection(Direction.RIGHT);
            snake.move();
        
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
        if (this.grid !=null){
            this.grid.paintComponent(graphics);
            this.grid.setColor(Color.magenta);
            this.grid.setColumns(20);
            this.grid.setRows(20);
            this.grid.setCellHeight(20);
            this.grid.setCellWidth(20);
            this.grid.setPosition(new Point(250,100));
//            grid.getCellPosition(null);
            
            Point cellLocation;
            graphics.setColor(Color.red);
            if (snake != null){
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x,cellLocation.y, grid.getCellWidth(), grid.getCellHeight());   
              
                } 
                
            }
        }
        GraphicsPalette.enterPortal(graphics, new Point (100,100), new Point(50, 50), Color.YELLOW);
                
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font ("Comic Sans", Font.ITALIC, 60));
        graphics.drawString("Score: " + this.score, 50, 50);
    }

    
}

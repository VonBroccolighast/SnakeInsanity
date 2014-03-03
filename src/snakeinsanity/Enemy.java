/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author liambrockley
 */
public class Enemy {
    
    public void move(){
        double value = Math.random();
        Point tempCellLocation = (Point) cellLocation.clone();
        
        if (value <= .2) {
            tempCellLocation.x += 1;
        } else if ((value > .2) && (value <= .4)) {
            tempCellLocation.x -= 1;
        } else if ((value > .4) && (value <= .6)) {
            tempCellLocation.y += 1;
        } else if ((value > .6) && (value <= .8)) {
            tempCellLocation.y -= 1;
        }
        
        if (validator != null) {
            if (validator.validate(tempCellLocation)) {
                cellLocation.setLocation(tempCellLocation);
            }
        }        
    }
    
    public void paint(Graphics graphics, Point systemLocation, Point size){
       graphics.fillOval(systemLocation.x, systemLocation.y, size.x, size.y);
        //graphics.drawImage("/resources/conquistador-armed.jpg", systemLocation.x, systemLocation.y, null);
    }
    
    
    public Enemy(Point cellLocation, CellLocationValidator validator){
        this.cellLocation = cellLocation;
        this.validator = validator;
    }
    
    private CellLocationValidator validator;
    private Point cellLocation;

    /**
     * @return the cellLocation
     */
    public Point getCellLocation() {
        return cellLocation;
    }

    /**
     * @param cellLocation the cellLocation to set
     */
    public void setCellLocation(Point cellLocation) {
        this.cellLocation = cellLocation;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import environment.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author liambrockley
 */
public class RegionGrid extends Grid {

//    public void paint(Graphics graphics, Point position, int columns, int rows, int cellWidth, int cellHeight, Color color) {
    @Override
    public void paintComponent(Graphics graphics) {

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {

                if (isInteriorCellLocation(new Point(column, row))) {
                    graphics.setColor(getInteriorColor());
                } else {
                    graphics.setColor(getColor());
                }
                
                graphics.fill3DRect(getPosition().x + (column * getCellWidth()),
                        getPosition().y + (row * getCellHeight()),
                        getCellWidth(), getCellHeight(), true);
//                graphics.drawRect(getPosition().x + (column * getCellWidth()),
//                        getPosition().y + (row * getCellHeight()),
//                        getCellWidth(), getCellHeight());
            }
        }
    }

    public boolean isInteriorCellLocation(Point cellLocation) {
        return ((cellLocation.x >= leftOffset) && (cellLocation.x < this.getColumns() - rightOffset)
                && (cellLocation.y >= topOffset) && (cellLocation.y < this.getRows() - bottomOffset));
    }
    
    private int topOffset = 3;
    private int leftOffset = 3;
    private int rightOffset = 3;
    private int bottomOffset = 3;
    private Color interiorColor = Color.PINK;

    /**
     * @return the topOffset
     */
    public int getTopOffset() {
        return topOffset;
    }

    /**
     * @param topOffset the topOffset to set
     */
    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    /**
     * @return the leftOffset
     */
    public int getLeftOffset() {
        return leftOffset;
    }

    /**
     * @param leftOffset the leftOffset to set
     */
    public void setLeftOffset(int leftOffset) {
        this.leftOffset = leftOffset;
    }

    /**
     * @return the rightOffset
     */
    public int getRightOffset() {
        return rightOffset;
    }

    /**
     * @param rightOffset the rightOffset to set
     */
    public void setRightOffset(int rightOffset) {
        this.rightOffset = rightOffset;
    }

    /**
     * @return the bottomOffset
     */
    public int getBottomOffset() {
        return bottomOffset;
    }

    /**
     * @param bottomOffset the bottomOffset to set
     */
    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    /**
     * @return the interiorColor
     */
    public Color getInteriorColor() {
        return interiorColor;
    }

    /**
     * @param interiorColor the interiorColor to set
     */
    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
    }
}

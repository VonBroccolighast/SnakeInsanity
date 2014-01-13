/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeinsanity;

import environment.ApplicationStarter;

/**
 *
 * @author liambrockley
 */
public class SnakeInsanity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
        
    }

    private static void start() {
       ApplicationStarter.run("Snake Insanity!!!", new SnakeEnvironment());
    }
}

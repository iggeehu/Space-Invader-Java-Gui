package com.game.SpaceInvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class SpaceInvaders  {


    public static void main(String[] args){
        //prepare to initialize new game
        EnemyRow topTier = new EnemyRow(new Invader(3, 30), 11, 1 );
        EnemyRow secondTier = new EnemyRow(new Invader(3, 20), 11, 1);
        EnemyRow thirdTier = new EnemyRow(new Invader(3, 10), 11, 1);
        EnemyRow[] formation = new EnemyRow[5];
        formation[0] = topTier;
        for(int i=0; i<2; i++)
        {formation[i]= secondTier;}
        for(int i=2; i<5; i++)
        {formation[i] = thirdTier;}
        Player player = new Player();

        Game newRound = new Game(formation, player, 1);

        KeyListener myListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==91) {
                    newRound.player.relativeLoc--;
                }
                if(e.getKeyCode()==93) {
                    newRound.player.relativeLoc++;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        newRound.window.addKeyListener(myListener);

        newRound.renderGrid();
        newRound.start();
        
    }  
}

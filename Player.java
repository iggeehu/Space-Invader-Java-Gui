package com.game.SpaceInvaders;

public class Player {
    int score;
    int lives;
    //if player is 1 space to the left of the center, then relativeLoc is -1,
    int relativeLoc;
    //number of grid travelled per second
    int bulletSpeed;

    public Player()
    {
        this.score = 0;
        this.lives = 3;
        this.relativeLoc = 0;
        this.bulletSpeed = 2;
    }


    public void setLoc(int i){
        this.relativeLoc = i;
    }

}

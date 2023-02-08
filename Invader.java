package com.game.SpaceInvaders;

public class Invader {
    //the grid space each invader takes
    int length;
    //the points for each killed invader
    int points;

    public Invader(int length, int points)
    {
        this.length = length;
        this.points = points;
    }
}

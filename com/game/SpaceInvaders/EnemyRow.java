package com.game.SpaceInvaders;

import java.util.Arrays;
import java.util.HashSet;

public class EnemyRow {
    Invader invader;
    public int numOfInvaders;
    public int interval;


    //both arrayRepresentation & arrayLen are derivative from invader, interval, and numOfInvaders definition
    public int[] arrayRep;
    public int arrayLen;
    //[0, 0, 1, 1] means the first two enemies of this row is dead
    public HashSet<Integer> deadEnemiesIndex = new HashSet();



    public EnemyRow(Invader invader, int numOfInvaders, int interval) {
        this.invader = invader;
        this.numOfInvaders = numOfInvaders;
        this.interval = interval;
        this.arrayLen = numOfInvaders * this.invader.length + interval * (this.numOfInvaders - 1);
        this.arrayRep = new int[this.arrayLen];
    }


    //turn the row of enemies into isolated array representation
    public void renderRowToArray() {
        int pointer = 0;
        while (pointer < arrayLen) {
            //iterate over each invader in the row, if it is dead, fill the space w zero
            for (int j = 0; j < invader.length; j++) {
                if(deadEnemiesIndex.contains(j))
                {
                    arrayRep[pointer]=0;
                }
                else{arrayRep[pointer] = invader.points;}
                pointer++;
            }
            if(pointer>=arrayLen){break;}
            //fill in the interval between invaders
            for (int j = 0; j < interval; j++) {
                arrayRep[pointer] = 0;
                pointer++;
            }
        }
    }
}
package com.game.SpaceInvaders;

import javax.swing.*;

import static java.util.concurrent.TimeUnit.*;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
//import javax.management.monitor.Monitor.scheduler;


public class Game{
    static int gridRows = 12;
    static int gridColumns = 70;
    //least amount of distance between player and enemy to make the game playable
    static int minWhiteSpace = 5;
    static int playerRows = 2;

    EnemyRow[] formation;
    Player player;

    //attributes related to location n speed of enemy block
    int enemySpeed;

    // -1 = enemy is 1 step left of center
    public int relativeLocX = 0;
    // 1 = enemy is 1 step down from the top
    public int relativeLocY = 0;
    //current direction of enemny formation
    boolean goLeft;
    boolean justArrived=false;
    //init-ed from gridRows and gridColumns
    int[][] grid;
    GUI window;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    //the constructor inits the grid with only the player, the render() method renders the enemies
    //based on the deadEnemiesIndex state/attribute of this class' EnemyRow[] formation property.
    public Game(EnemyRow[] formation, Player player, int enemyInitialSpeed) {
        //find out the widest row within offered formation parameter, to check against screen width
        int maxRowLen = formation[0].arrayLen;
        for (int i = 1; i < formation.length; i++) {
            maxRowLen = Math.max(maxRowLen, formation[i].arrayLen);
        }

        if (gridRows - formation.length - playerRows < minWhiteSpace) {
            throw new IllegalArgumentException("not enough distance between enemy and player");}
        if (maxRowLen > gridColumns) {
            throw new IllegalArgumentException("there's one row of enemy that is too wide for the screen");}
        int sideMargin = (gridColumns - maxRowLen) / 2;
        if (sideMargin / enemyInitialSpeed < 5) {
            throw new IllegalArgumentException("the initial speed is too fast for optimal gameplay");}

        this.formation = formation;
        this.player = player;
        this.enemySpeed = enemyInitialSpeed;


        //initialize grid or game interface
        this.grid=new int[gridRows][gridColumns];
        //initialize the two bottom rows (player zone) as 1s
        for(int i=gridRows-playerRows; i<gridRows;i++)
        {
            this.grid[i][0]=1;
            Arrays.fill(this.grid[i], 1);}
        //place the player at the center
        this.grid[gridRows - 1][gridColumns / 2] = 2;
        this.window = new GUI(this.grid);
    }

    //update game state
    public void renderGrid() {
        //make anything above the top row of enemies 0;
        for(int i=0; i<this.relativeLocY; i++)
        {Arrays.fill(this.grid[i],0);}

        //iterate over each enemy row
        //mapping their arrayRep onto the grid based on relativelocX and relativelocY
        for (int i = 0; i < this.formation.length; i++) {
          EnemyRow currRow = this.formation[i];
          currRow.renderRowToArray();
          //calculate padding to add on both sides of isolated row array representation
          int sideMargins = (gridColumns - currRow.arrayLen) / 2;
          int leftMargin = sideMargins + this.relativeLocX;
          //where currRow will be on the grid
          int YPosition = i + this.relativeLocY;

            Arrays.fill(grid[YPosition],0);
          //modify the grid's row based on currRow
          for(int j=0; j<currRow.arrayLen; j++){
              this.grid[YPosition][j+leftMargin]=currRow.arrayRep[j];
          }
        }
        //render player
//        System.out.println(this.player.relativeLoc);
        Arrays.fill(this.grid[gridRows-1], 1);
        this.grid[gridRows-1][gridColumns/2+this.player.relativeLoc]=2;
        //rerender the UI
        this.window.repaint(this.grid);
    }

    //player loses if he has no more lives left or any enemy reached the player zone
    private boolean enemyWin(){
        for(int i=0; i<gridColumns; i++)
        {   //top row of player zone is not all 1s anymore
            if(grid[gridRows-playerRows][i]!=1){return true;}}
        return this.player.lives==0;
    }

    private boolean playerWin(){
        //make sure the rows where enemy formation is located are all 0s, otherwise return false
        for(int i=relativeLocY; i<relativeLocY+this.formation.length; i++)
        {
            for(int j=0; j<gridColumns; j++)
            {if(this.grid[i][j]!=0) return false;}
        }
        return true;
    }

    private boolean reachedWall(){
        for(int i=this.relativeLocY; i<this.relativeLocY+this.formation.length; i++)
        {
           if(grid[i][gridColumns-1]!=0||grid[i][0]!=0)
           {
               return true;}
        }
        return false;
    }

    //moveFormation by one speed unit;
    private void formationMove(){
        if(goLeft)
        {this.relativeLocX-=this.enemySpeed;}
        else{this.relativeLocX+=this.enemySpeed;}
    }

    private void formationDown(){
        this.relativeLocY++;
    }

    //movement of the enemy per unit of time
    void runnableBySecond(){
        System.out.println("runnable");
        if(this.reachedWall()&&!justArrived)
        {
            this.goLeft = !this.goLeft;
            this.formationDown();
            this.justArrived=true;
        }
        else{this.formationMove();
            if(this.reachedWall()){justArrived=false;}
        }
        this.renderGrid();
    }

    public void start(){
        System.out.println("started");
        final Runnable task = new Runnable() {
            public void run(){ runnableBySecond(); }
        };

        final ScheduledFuture<?> taskHandle =
                    this.scheduler.scheduleAtFixedRate(task, 3, 1, SECONDS);

        if(enemyWin()||playerWin()){
            if(playerWin()) System.out.println("playersWIn");
            taskHandle.cancel(true);}
    }


}



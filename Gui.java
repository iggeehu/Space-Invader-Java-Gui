package com.game.SpaceInvaders;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;


public class GUI extends JFrame{
    int[][] grid;

    JPanel panel;
    //graphic slots corresponding to grid
    JButton[][] buttons;

    public GUI(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;

        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
        this.grid = grid;
        this.panel = new JPanel(new GridLayout(r, c, 1, 1));
        this.buttons = new JButton[r][c];
        panel.setBorder(BorderFactory.createCompoundBorder());

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if(grid[i][j]==0 || grid[i][j]==1)
                {
                    this.buttons[i][j]=new JButton("", null);
                }
                else if(grid[i][j]==2)
                {this.buttons[i][j] = new JButton("M", null);}
                else{
                    this.buttons[i][j] = new JButton("$", null);
                }
                this.buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                this.buttons[i][j].setFont(this.buttons[i][j].getFont().deriveFont(8f));
                panel.add(this.buttons[i][j]);
            }
        }
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Space Invaders");
        pack();
        setVisible(true);
    }

    public void repaint(int[][] grid)
    {
        remove(this.panel);
        this.panel = new JPanel(new GridLayout(grid.length, grid[0].length, 1, 1));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j]==1||grid[i][j]==0)
                {
                    this.buttons[i][j] = new JButton("", null);
                }
                else if(grid[i][j]==2)
                {this.buttons[i][j] = new JButton("M", null);}
                else{
                    this.buttons[i][j] = new JButton("$", null);
                }
                //JLabel l = new JLabel(new ImageIcon("image_file.png"), JLabel.CENTER);
                this.buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                this.buttons[i][j].setFont(this.buttons[i][j].getFont().deriveFont(8f));
                this.panel.add(this.buttons[i][j]);
            }
        }
        add(this.panel);
        revalidate();
        repaint();

    }
}

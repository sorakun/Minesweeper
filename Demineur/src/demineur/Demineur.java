/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import MineButton.MineButton;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author sora
 */
public class Demineur extends JFrame implements ActionListener{
 
    static int WIN = 1;
    static int LOSE = 2;
    
    private JPanel pnlTop, pnlGrid, pnlBot;
    JToggleButton btnDiscover, btnMarkDangerous, btnMarkMine;
    JLabel status;
    MineButton [][] btnMatrix;
    
    int sizeX, sizeY;
    
    MineButton [][] genMatrix(int x, int y){
        int [][] mat = new int[x][];
        MineButton [][] mineMat = new MineButton[y][];
        
        for(int i = 0; i < x; i++){
            mat[i] = new int[y];
            mineMat[i] = new MineButton[y];
            
            for(int j = 0; j < y; j++){
                // bomb or nothing?
                if (Math.random() > 0.85)
                    mat[i][j] = -1;
                else
                    mat[i][j] = 0;
            }
        }
        
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if (mat[i][j] == -1){
                    try{
                        if (mat[i-1][j-1] != -1)
                            mat[i-1][j-1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    try{
                        if (mat[i-1][j] != -1)
                            mat[i-1][j]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i-1][j+1] != -1)
                            mat[i-1][j+1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i][j-1] != -1)
                            mat[i][j-1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i][j+1] != -1)
                            mat[i][j+1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i+1][j-1] != -1)
                            mat[i+1][j-1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i+1][j] != -1)
                            mat[i+1][j]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                    try{
                        if (mat[i+1][j+1] != -1)
                            mat[i+1][j+1]++;
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Skip.");
                    }
                    
                }
            }
        }
        
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                System.out.print("\t "+mat[i][j]);
            }
            System.out.print("\n");
        }
        
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(mat[i][j] == -1)
                   mineMat[i][j] = new MineButton(MineButton.MINE, -1);
                else
                   mineMat[i][j] = new MineButton(MineButton.EMPTY, mat[i][j]);
            }
        }
        
        return mineMat;
    }
    
    public Demineur(){
        super("Demineur");
        sizeX = 10;
        sizeY = 10;
        
        btnMatrix = genMatrix(sizeX, sizeY);
        // Components init
        pnlTop = new JPanel();
        pnlGrid = new JPanel();
        pnlBot = new JPanel();
        
        btnDiscover = new JToggleButton(new ImageIcon("resources/marcher_on.png"));
        btnMarkDangerous = new JToggleButton(new ImageIcon("resources/case_marquee_douteuse.png"));
        btnMarkMine = new JToggleButton(new ImageIcon("resources/case_marquee_minee.png"));
        
        btnDiscover.addActionListener(this);
        btnMarkDangerous.addActionListener(this);
        btnMarkMine.addActionListener(this);
        
        status = new JLabel("Jouez ...", new ImageIcon("resources/partie_en-cours.png"), JLabel.CENTER);
        
        // Panel Top setup
        pnlTop.setLayout(new FlowLayout());
        pnlTop.add(btnDiscover);
        pnlTop.add(btnMarkDangerous);
        pnlTop.add(btnMarkMine);
        
        // Panel Grid
        pnlGrid.setLayout(new GridLayout(sizeX, sizeY));
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                pnlGrid.add(btnMatrix[i][j]);
                btnMatrix[i][j].addActionListener(this);
            }
        }
        
        // PanelBot
        pnlBot.setLayout(new FlowLayout());
        status.setSize(new Dimension(200, 200));
        pnlBot.add(status);
        
        // Main Interface setup
        getContentPane().setLayout(new BorderLayout());
        
        getContentPane().add(pnlTop, BorderLayout.NORTH);
        getContentPane().add(pnlGrid, BorderLayout.CENTER);
        getContentPane().add(pnlBot, BorderLayout.SOUTH);
        
        btnDiscover.setSelected(true);
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MineButton){
            MineButton btn = (MineButton)e.getSource();            
            if (btnDiscover.isSelected())
            {
                if(btn.getType() == MineButton.MINE)
                    endGame(LOSE);
                else{                    
                    for(int i = 0; i < btnMatrix.length; i++){
                        for(int j = 0; j < btnMatrix[i].length; j++)
                        {
                            if (btnMatrix[i][j] == btn)
                            clearArea(i, j);
                        }
                    }
                    btn.show();
                    
                    checkVictory();
                    
                }
            }
            else if (btnMarkDangerous.isSelected())
            {
                if (! btn.getDisplayed() )
                    btn.setStatus(MineButton.DANGEROUS);
            }
            else if (btnMarkMine.isSelected())
            {
                if (! btn.getDisplayed() )
                    btn.setStatus(MineButton.MINED);
            }
                
            
        }
        else if (e.getSource() instanceof JToggleButton){
            JToggleButton btn = (JToggleButton)e.getSource();
            if (btn == btnDiscover)
            {
                btnMarkDangerous.setSelected(false);
                btnMarkMine.setSelected(false);
                btnDiscover.setSelected(true);
            }
            else if (btn == btnMarkDangerous)
            {
                btnMarkDangerous.setSelected(true);
                btnMarkMine.setSelected(false);
                btnDiscover.setSelected(false);
            }
            else if (btn == btnMarkMine)
            {
                btnMarkDangerous.setSelected(false);
                btnMarkMine.setSelected(true);
                btnDiscover.setSelected(false);
            }
        }
    }
    
    public void clearArea(int i, int j){
        if (i < 0)
            return;
        if (j < 0)
            return;
        if (i >= btnMatrix.length)
            return;
        if (j >= btnMatrix[i].length)
            return;
 
        if (btnMatrix[i][j].getDisplayed())
            return;
        
        btnMatrix[i][j].show();
        
        if (btnMatrix[i][j].getValue() != 0){
            return;
        }
        
        clearArea(i-1, j-1);
        clearArea(i-1, j);
        clearArea(i-1, j+1);
        
        clearArea(i, j-1);
        clearArea(i, j+1);
        
        clearArea(i+1, j-1);
        clearArea(i+1, j);
        clearArea(i+1, j+1);

    }
    
    public void endGame(int stat){
        for(int i = 0; i < btnMatrix.length; i++){
            for(int j = 0; j < btnMatrix[i].length; j++){
                btnMatrix[i][j].show();
            }
        }
        if (stat == LOSE){
            status.setText("Désoler, vous avez perdus.");
            status.setIcon(new ImageIcon("resources/partie_perdue.png"));
        }else{
            status.setText("Félicitation, vous avez gnagné.");
            status.setIcon(new ImageIcon("resources/partie_gagnee.png"));
        }
    }
    
    public void checkVictory(){
        boolean win = true;
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                if ((btnMatrix[i][j].getValue() != -1) && (!btnMatrix[i][j].getDisplayed()))
                    return;
            }
        }
        endGame(WIN);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new Demineur();
    }   
}

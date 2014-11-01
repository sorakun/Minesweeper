/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineButton;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author sora
 */
public class MineButton extends JButton {
    
    public static int EMPTY = 1;
    public static int MINE = 2;
    
    public static int HIDE = 1;
    static int SHOW = 1;
    
    public static int UNKNOWN = 1;
    public static int DANGEROUS = 2;
    public static int MINED = 3;
    
    private boolean displayed = false;
    
    private int type;
    private int status;
    private int value;
    
    public MineButton(int type, int value){
        super();
        this.type = type;
        this.value = value;
        mark(UNKNOWN);
    }
    
    public void show(){
        if (getType() == EMPTY)
            setIcon(new ImageIcon("resources/case_"+getValue()+".png"));
        else
            setIcon(new ImageIcon("resources/case_mine_explosee.png"));  
        
        displayed = (true);
    }

    public void mark(int status){
        if (status == UNKNOWN)
            setIcon(new ImageIcon("resources/case_normale.png"));
        else if (status == DANGEROUS)
            setIcon(new ImageIcon("resources/case_marquee_douteuse.png"));
        else if (status == MINED)
            setIcon(new ImageIcon("resources/case_marquee_minee.png"));
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
        mark(status);
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the displayed
     */
    public boolean getDisplayed() {
        return displayed;
    }

    /**
     * @param displayed the displayed to set
     */
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
    
    
}

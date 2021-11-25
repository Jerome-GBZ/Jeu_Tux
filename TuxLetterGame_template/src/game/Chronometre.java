/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

public class Chronometre {
    private long begin;
    private long end;
    private long current;
    private int limite;

    public Chronometre(int limite) {
        //intialisation
        this.limite = limite;
        begin = 0;
        current = 0;
    }
    
    public void start(){
        begin = System.currentTimeMillis(); 
    }
 
    public void stop(){
        end = begin + (limite*1000); 
    }
 
    public long getTime() {
        return end-begin;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public int getSeconds() {
        return (int)(getMilliseconds() / 1000.0);
    }
 
    public double getMinutes() {
        return getMilliseconds() / 60000.0;
    }
 
    public double getHours() {
        return getMilliseconds() / 3600000.0;
    }
    
    /**
    * Method to know if it remains time.
    */
    public boolean remainsTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((end - current)/1000.0);
        if(timeSpent > 0)
             return true;
        return false;
    }
     
}


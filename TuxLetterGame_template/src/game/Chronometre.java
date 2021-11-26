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
        this.limite = limite;//Seconds
        begin = 0;
        current = 0;
    }
    
    public void start(){
        stop();
        begin = System.currentTimeMillis(); 
        System.out.println("begin = "+ begin);
    }
 
    public void stop(){
        end = System.currentTimeMillis() + (limite*1000);
    }
 
    public long getTime() {
        getCurrentTime();
        return end-current;
    }
 
    public long getMilliseconds() {
        getCurrentTime();
        return (current-begin);
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
    
    public void getCurrentTime(){
        current = System.currentTimeMillis();
    }
    
    /**
    * Method to know if it remains time.
    */
    public boolean remainsTime() {
        if(getSeconds() < limite){
            System.out.println(getSeconds() + "s");
             return true;
        }
        return false;
    }
     
}


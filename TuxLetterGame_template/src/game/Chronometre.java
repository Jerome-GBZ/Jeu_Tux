package game;

/**
 * Class Chronometre :
 *    @attribute : long begin    : Date minute-seconde à la qu'elle à commencer le chrono
 *                 long end      : Date minute-seconde à la qu'elle le chrono doit se terminer = begin + limite
 *                 long current  : Date minute-seconde actuelle
 *                 int limite    : nombre de seconde que toi durée le chronomètre
 **/
public class Chronometre {
    private long begin;
    private long end;
    private long current;
    private int limite;

    /**
     * @param limite int
     * @initialise toutes les variables dont begin et current à 0
     */
    public Chronometre(int limite) {
        this.limite = limite;//Seconds
        begin = 0;
        current = 0;
    }
    
    /**
     * Lancer le chronomètre : initialiser dans combien de temps le timer doit se finir stop() et la variable begin
     */
    public void start() {
        stop();
        begin = System.currentTimeMillis(); 
    }
 
    /**
     * Initialiser la variable de fin pour dire dans combien de seconde ou minute le timer doit s'arreter
     */
    public void stop() {
        end = System.currentTimeMillis() + (limite*1000);
    }
 

    /**
     * GETTER ATTRIBUTES
     */
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
    * @return true  : quand le temps restant est inférieur au temps limite
              false : quand le temps restant est supérieur au temps limite
    */
    public boolean remainsTime() {
        if(getSeconds() < limite){
             return true;
        } else {
            return false;
        }        
    }
}


package game;


// import org.lwjgl.input.Keyboard;
// import net.java.games.input.Keyboard;

public class JeuDevineLeMotOrdre extends Jeu{
    private int nbLettresRestantes;
    private Chronometre chrono;
    private static int TIME = 30;

    public JeuDevineLeMotOrdre(){
        super();
        nbLettresRestantes = super.lettres.size();
    }

    private boolean tuxTrouveLettre() {
        if(super.collision(super.lettres.get(0))) {
            return true;
        }
        
        return false;
    }

    /**
        private boolean tuxTrouveLettre() {
            if(super.collision(super.lettres.get(0))) {
                return true;
                } else {
                int nbLettre = 1;
                while(nbLettre < super.lettres.size()) {
                    if(super.collision(super.lettres.get(nbLettre))) {
                        if(Character.compare(super.lettres.get(0).getLetter(), super.lettres.get(nbLettre).getLetter()) == 0) {
                            System.out.println("je suis sur meme lettre ! ");
                        }
                    }

                    nbLettre++;
                }
            }
            
            return false;
        }   
     */

    public void setNbLettresRestantes(int nbLettresRestantes) {
        this.nbLettresRestantes = nbLettresRestantes;
    }

    @Override
    protected void démarrePartie(Partie partie) {
       chrono = new Chronometre(TIME);
       chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        if(tuxTrouveLettre()) {
            setNbLettresRestantes(nbLettresRestantes-1);
        }
    }


    // @Override
    // protected void terminePartie(Partie partie);
    
    @Override
    protected boolean appliqueTemps(OnJeuCallback callback) {
        if(chrono.remainsTime()) {
            callback.onTimeSpentListener(chrono.getSeconds());
            return false;
        } else {
            return true;
        }
    }
}

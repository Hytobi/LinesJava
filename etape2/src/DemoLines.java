import guilines.Lines;
import guilines.IJeuDesBilles;

public class DemoLines{
    public static void main(String[] args){
        IJeuDesBilles monJeu = new MonJeu();
        // creer la fenetre - on utilise l’interface graphique implementee dans guiline
        Lines fenetre = new Lines("LILines",monJeu);
    }
}// DemoLines
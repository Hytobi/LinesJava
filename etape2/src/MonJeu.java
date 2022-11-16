import guilines.IJeuDesBilles;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MonJeu implements IJeuDesBilles{
    public static final int DEBUT = 5;
    public static final int COULEUR = 8;
    public static final int AJOUTE = 3;
    public static final int LIG = 9;
    public static final int COL = 9;
    private int[][] tab = new int[LIG][COL];
    private int score = 0;
    private Random random = new Random();

    public MonJeu(){
        reinit();
        this.tab = tab; 
        this.score = score;
    }

    public List<Point> deplace(int ligD, int colD, int ligA, int colA){
        List<Point> maListe = new ArrayList<Point>();
        Point depart = new Point(ligD,colD);
        Point arrive = new Point(ligA,colA);
        tab[ligA][colA]=tab[ligD][colD];
        tab[ligD][colD]=-1;
        
        maListe.add(depart);
        maListe.add(arrive);
        //maListe.add(arrive);

        return maListe;
    }
    public int getCouleur(int lig, int col){
        return tab[lig][col];
    }
    public int getNbBallesAjoutees(){
        return AJOUTE;
    }
    public int getNbColonnes(){
        return COL;
    }
    public int getNbCouleurs(){
        return COULEUR;
    }
    public int getNbLignes(){
        return LIG;
    }
    public int[] getNouvellesCouleurs(){
        int[] array = new int[AJOUTE];
        for (int i=0;i<AJOUTE;i++){
            array[i] = random.nextInt(COULEUR);
        }
        return array;
    }

    public int getScore(){
        return score;
    }
    public boolean partieFinie(){
        return false;
    }
    public void reinit(){
        int i,j,k=0;
        for (i=0;i<LIG;i++){
            for (j=0;j<COL;j++){
                tab[i][j] = -1;
            }
        }
        while (k<DEBUT){
            int l = random.nextInt(LIG);
            int c = random.nextInt(COL);
            if (tab[l][c] == -1){
                tab[l][c] = random.nextInt(COULEUR);
                k++;
            }
        }
    }
}

//pour copier : cp -r etape1/etape2
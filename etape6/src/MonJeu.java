import guilines.IJeuDesBilles;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/** Ajout de la condition d'arret de jeu en ligne 74
 * creation de methode pour reduire le code de la methode deplace ligne143 et ligne171
 * creation de la classe bille
 */
public class MonJeu implements IJeuDesBilles{
    public static final int DEBUT = 5;
    public static final int COULEUR = 8;
    public static final int AJOUTE = 3;
    public static final int LIG = 9;
    public static final int COL = 9;
    public static final int ALIGNE = 5;
    private Bille[][] tab = new Bille[LIG][COL];
    private int score=0;
    private Random random = new Random();
    private int[] newCoul = new int[AJOUTE];
    private int nbBille;

    /**Constructeur  ou on ne prend que les valeur par defaut
     */
    public MonJeu(){
        reinit();
        this.tab = tab; 
        this.score = score;
    }

    /**Methode qui donne la couleur de la balle a la case lig,col
     * @param lig : la ligne de la balle
     * @param col : la colonne de la balle
     * @return : l'entier qui represente la couleur de la balle
     */
    public int getCouleur(int lig, int col){
        return tab[lig][col].getCouleur();
    }

    /**Methode d'acces au nombre de balle que l'on ajoute */
    public int getNbBallesAjoutees(){
        return AJOUTE;
    }

    /**Methode d'acces au nombre de colonne de la grille de jeu */
    public int getNbColonnes(){
        return COL;
    }

    /**Methode d'acces a la quantite de balle de couleur differentes disponible */
    public int getNbCouleurs(){
        return COULEUR;
    }

    /**Methode d'acces au nombre de ligne de la grille de jeu */
    public int getNbLignes(){
        return LIG;
    }

    /**Methode qui donne une liste des balles qui vont tomber au prochain tour */
    public int[] getNouvellesCouleurs(){
        for (int i=0;i<AJOUTE;i++){
            newCoul[i] = random.nextInt(COULEUR);
        }
        return newCoul;
    }

    /**Methode d'acces sur le score de jeu */
    public int getScore(){
        return score;
    }

    /**Methode qui dit si la partie est fini
     * @return : false si elle ne l'est pas 
     */
    public boolean partieFinie(){
        if (nbBille+AJOUTE<LIG*COL){
            return false;
        }
        return true;
    }

    /**Methode qui initialise la grille de jeu et place les 5 balles du debut */
    public void reinit(){
        int i,j,k=0;
        //Crée des billes vide dans chaque case du tableau
        for (i=0;i<LIG;i++){
            for (j=0;j<COL;j++){
                tab[i][j] = new Bille(i,j,-1);
            }
        }

        //Initialise les 2,3 ou 4 voisins de chaque Bille
        for (i = 0; i < LIG; i++) {
			for (j = 0; j < COL; j++) {
				chercheVoisin(i, j);
			}
		}

        //Crée les Bille du début du jeu
        nbBille=0;
        while (k<DEBUT){
            int l = random.nextInt(LIG);
            int c = random.nextInt(COL);
            if (tab[l][c].getCouleur() == -1){
                tab[l][c].setCouleur(random.nextInt(COULEUR));
                nbBille++;
                k++;
            }
        }
    }

    /**Methode qui deplace une balle et vérifie si elle est aligné aux autres
     * @param ligD : ligne de la balle de depart
     * @param colD : colonne de la balle de depart
     * @param ligA : ligne de la balle ou l'on veut la deplacer
     * @param colD : ligne de la balle ou l'on veut la deplacer
     * @return : la liste de point sur la grille
     */
    public List<Point> deplace(int ligD, int colD, int ligA, int colA){
        List<Point> maListe = new ArrayList<Point>();
        if (trouverChemin(ligD, colD, ligA, colA)){
            //Créetion des points
            Point depart = new Point(ligD,colD);
            Point arrive = new Point(ligA,colA);

            //On deplace la bille
            tab[ligA][colA].setCouleur(tab[ligD][colD].getCouleur());
            tab[ligD][colD].setCouleur(-1);
            maListe.add(depart);
            maListe.add(arrive);

            //On regard si elles sont alignées
            int coul = tab[ligA][colA].getCouleur();
            List<Point> pointsAlignes = laLigne(ligA, colA,coul);
            maListe.addAll(pointsAlignes);
            // si ce n'est pas aligné, on ajoute les billes
            if (pointsAlignes.isEmpty() || grilleEstVide()){
                int i=0;
                while (i<newCoul.length){
                    int l = random.nextInt(LIG);
                    int c = random.nextInt(COL);
                    if (tab[l][c].getCouleur() == -1){
                        tab[l][c].setCouleur(newCoul[i]);
                        maListe.add(new Point(l,c));
                        nbBille++;
                        laLigne(l,c,newCoul[i]);
                        i++;
                    }
                }
            }
        }
        return maListe;
    }

    public List<Point> laLigne(int lig, int col,int coul){
        List<Point> pointsAlignes = new ArrayList<Point>();
        List<Point> maListeDG = demiLigne(lig,col,1,0,coul);
        maListeDG.addAll(demiLigne(lig,col,-1,0,coul));
        List<Point> maListeHB = demiLigne(lig,col,0,1,coul);
        maListeHB.addAll(demiLigne(lig,col,0,-1,coul));

        if (maListeDG.size() + 1 == ALIGNE){
            pointsAlignes.addAll(maListeDG);
        }
        if (maListeHB.size() + 1 == ALIGNE){
            pointsAlignes.addAll(maListeHB);
        }
        //Le point de depart
        if (!pointsAlignes.isEmpty()){
            pointsAlignes.add(new Point(lig,col));
        }
        //On supp les billes
        for (Point p : pointsAlignes){
            tab[p.x][p.y].setCouleur(-1);
            nbBille--;
        }
        score += pointsAlignes.size() *2;
        
        return pointsAlignes;

    }

    public List<Point> demiLigne(int depX, int depY, int direcX, int direcY,int coul){
        List<Point> maListeInter = new ArrayList<Point>();
        int lig = depX + direcY;
        int col = depY + direcX;
        while((lig<LIG) && (lig>=0) && (col<COL) && (col>=0) && getCouleur(lig, col)==coul){
            maListeInter.add(new Point(lig,col));
            lig+=direcY;
            col+=direcX;
        }
        return maListeInter;
    }

    /**Methode qui verifie si la grille est vide
     * @return : false si elle n'est pas remplie
     */
    public boolean grilleEstVide(){
        for (int i=0;i<LIG;i++){
            for (int j=0;j<COL;j++){
                if (getCouleur(i,j) != -1){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean coordValide(int x, int y){
        if ((x<LIG) && (x>=0) && (y<COL) && (y>=0)){
            return true;
        } 
        return false;
    }
    public void chercheVoisin(int l, int c){
        Bille[] lesVoisins = new Bille[4];
        if (coordValide(l+1,c)){
            lesVoisins[0] = tab[l+1][c];
        }
        if (coordValide(l,c+1)){
            lesVoisins[1] = tab[l][c+1];
        }
        if (coordValide(l-1,c)){
            lesVoisins[2] = tab[l-1][c];
        }
        if (coordValide(l,c-1)){
            lesVoisins[3] = tab[l][c-1];
        }
        tab[l][c].setVoisins(lesVoisins);
    }

    
    public void resetVu() {
		for (int i = 0; i < LIG; i++) {
			for (int j = 0; j < COL; j++) {
				tab[i][j].setVuFaux();
			}
		}
	}
    private boolean trouverChemin(int ligD, int colD, int ligA, int colA) {
		if (getCouleur(ligD, colD) == -1 || getCouleur(ligA, colA) != -1){
			return false;
        }
		else {
			boolean res = tab[ligD][colD].trouverChemin(ligA, colA);
			resetVu();
			return res;
		}
	}
}


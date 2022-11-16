import guilines.IJeuDesBilles;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**Classe modélisant le jeu LILINES
 * @author PLOUVIN Patrice
 */
public class MonJeu implements IJeuDesBilles{
    //Les constantes pour le jeu par defaut
    public static final int LIG = 9;
    public static final int COL = 9;
    public static final int NB_COULEUR = 4;
    public static final int NB_COULEUR_MAX = 8;
    public static final int NB_BILLE_AJOUTE = 3;
    public static final int NB_BILLE_ALIGNE = 5;
    public static final int NB_BILLE_DEBUT = 5;
    
    //Les valeurs qu'on peut choisir
    private final int nbLig;
	private final int nbCol;
	private final int nbCouleur;
	private final int nbBilleAjoute;
	private final int nbBilleAligne;
    private final int nbBilleDebut;

    private Bille[][] tab;
    private int score;
    private Random random = new Random();
    private int[] newCoul;
    private int nbBille;

    /** Constructeur ou on choisie tous les parametres de jeu */
    public MonJeu(int nbLig, int nbCol, int nbCouleur, int nbBilleAjoute,int nbBilleAligne,int nbBilleDebut) {
		this.nbLig = nbLig;
		this.nbCol = nbCol;
		if (nbCouleur > NB_COULEUR_MAX){
			nbCouleur = NB_COULEUR_MAX;
        }
		this.nbCouleur = nbCouleur;
		this.nbBilleAjoute = nbBilleAjoute;
		this.nbBilleAligne = nbBilleAligne;
        this.nbBilleDebut = nbBilleDebut;

		this.tab = new Bille[nbLig][nbCol];
		this.newCoul = new int[nbBilleAjoute];

		reinit();
	}

    /** Constructeur ou on ne choisie que les dimenssions du jeu */
	public MonJeu(int nbLig, int nbCol) {
		this(nbLig, nbCol, NB_COULEUR, NB_BILLE_AJOUTE, NB_BILLE_ALIGNE,NB_BILLE_DEBUT);
	}

    /** Constructeur ou on ne prend que les valeur par defaut */
	public MonJeu() {
		this(LIG, COL, NB_COULEUR, NB_BILLE_AJOUTE, NB_BILLE_ALIGNE,NB_BILLE_DEBUT);
	}

    /**Methode qui donne la couleur de la balle a la case lig,col
     * @param lig : la ligne de la balle
     * @param col : la colonne de la balle
     * @return : l'entier qui represente la couleur de la balle
     */
    public int getCouleur(int lig, int col){
        return tab[lig][col].getCouleur();
    }

    /**Methode d'acces au nombre de balle que l'on ajoute 
     * @return : le nombre de bille qu'on ajoute chaque tour
    */
    public int getNbBallesAjoutees(){
        return nbBilleAjoute;
    }

    /**Methode d'acces au nombre de colonne de la grille de jeu 
     * @return : le nombre de colonne de jeu
    */
    public int getNbColonnes(){
        return nbCol;
    }

    /**Methode d'acces a la quantite de balle de couleur differentes disponible
     * @return : le nombre de couleur dans le jeu
     */
    public int getNbCouleurs(){
        return nbCouleur;
    }

    /**Methode d'acces au nombre de ligne de la grille de jeu 
     * @return : le nombre de ligne du jeu
    */
    public int getNbLignes(){
        return nbLig;
    }

    /**Methode qui donne une liste des balles qui vont tomber au prochain tour 
     * @return : la liste des billes qui vont tomber
    */
    public int[] getNouvellesCouleurs(){
        for (int i=0;i<nbBilleAjoute;i++){
            newCoul[i] = random.nextInt(nbCouleur);
        }
        return newCoul;
    }

    /**Methode d'acces sur le score de jeu 
     * @return : le score du joueur
    */
    public int getScore(){
        return score;
    }

    /**Methode qui dit si la partie est fini
     * @return : false si elle ne l'est pas, true si elle l'est
     */
    public boolean partieFinie(){
        if (nbBille+nbBilleAjoute<nbLig*nbCol){
            return false;
        }
        return true;
    }

    /**Methode qui initialise la grille de jeu et place les 5 balles du debut */
    public void reinit(){
        //Met le score a O
        this.score = 0;
        int i,j,k=0;
        //Crée des billes vide dans chaque case du tableau
        for (i=0;i<nbLig;i++){
            for (j=0;j<nbCol;j++){
                tab[i][j] = new Bille(i,j,-1);
            }
        }

        //Initialise les 2,3 ou 4 voisins de chaque Bille
        for (i = 0; i < nbLig; i++) {
			for (j = 0; j < nbCol; j++) {
				chercheVoisin(i, j);
			}
		}

        //Crée les Bille du début du jeu
        nbBille=0;
        while (k<nbBilleDebut){
            int l = random.nextInt(nbLig);
            int c = random.nextInt(nbCol);
            if (tab[l][c].getCouleur() == -1){
                tab[l][c].setCouleur(random.nextInt(nbCouleur));
                nbBille++;
                k++;
            }
        }
    }

    /**Methode qui deplace la bille selectionné et vérifie si elle est aligné aux autres
     * @param ligD : ligne de la bille de depart
     * @param colD : colonne de la bille de depart
     * @param ligA : ligne de la bille ou l'on veut la deplacer
     * @param colD : colonne de la bille ou l'on veut la deplacer
     * @return : la liste de point sur la grille
     */
    public List<Point> deplace(int ligD, int colD, int ligA, int colA){
        List<Point> maListe = new ArrayList<Point>();
        if (trouverChemin(ligD, colD, ligA, colA)){
            //Création des points
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
                    int l = random.nextInt(nbLig);
                    int c = random.nextInt(nbCol);
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

    /** Méthode intermediaire de deplace, elle cherche les bille de meme couleur que celle en (lig,col)
     * @param lig : la ligne de la bille
     * @param col : la colonne de la bille
     * @param coul : la couleur de cette bille
     * @return : une liste de point qui sont aligné
     */
    public List<Point> laLigne(int lig, int col,int coul){
        List<Point> pointsAlignes = new ArrayList<Point>();
        List<Point> maListeDG = demiLigne(lig,col,1,0,coul);
        maListeDG.addAll(demiLigne(lig,col,-1,0,coul));
        List<Point> maListeHB = demiLigne(lig,col,0,1,coul);
        maListeHB.addAll(demiLigne(lig,col,0,-1,coul));

        if (maListeDG.size() + 1 >= nbBilleAligne){
            pointsAlignes.addAll(maListeDG);
        }
        if (maListeHB.size() + 1 >= nbBilleAligne){
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
        score += pointsAlignes.size() * pointsAlignes.size() * 2;
        
        return pointsAlignes;
    }

    /** Méthode intermediaire de laLigne, elle cherche les bille de meme couleur que celle en (lig,col)
     * sur des demi ligne
     * @param depX : la ligne de la bille
     * @param depY : la colonne de la bille
     * @param direcX : decal la colonne 
     * @param direcY : decal la ligne
     * @param coul : la couleur de cette bille
     * @return : une liste de point qui sont aligné
     */
    public List<Point> demiLigne(int depX, int depY, int direcX, int direcY,int coul){
        List<Point> maListeInter = new ArrayList<Point>();
        int lig = depX + direcY;
        int col = depY + direcX;
        while((lig<nbLig) && (lig>=0) && (col<nbCol) && (col>=0) && getCouleur(lig, col)==coul){
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
        for (int i=0;i<nbLig;i++){
            for (int j=0;j<nbCol;j++){
                if (getCouleur(i,j) != -1){
                    return false;
                }
            }
        }
        return true;
    }

    /** Méthode qui regard si les coordonnées (x,y) de la bille sont valides
     * @param x : la ligne de la bille
     * @param y : la colonne de la bille
     * @return : vrai si les coordonnées sont valides, faux sinon
     */
    public boolean coordValide(int x, int y){
        if ((x<nbLig) && (x>=0) && (y<nbCol) && (y>=0)){
            return true;
        } 
        return false;
    }

    /** Méthode qui repertorie les voisins de la bille au coordonnée (l,c)
     * @param l : la ligne de la bille
     * @param c : la colonne de la bille
     */
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

    /**Methode qui remet la vu des bille en false pour un nouveau tour de jeu */
    public void resetVu() {
		for (int i = 0; i < nbLig; i++) {
			for (int j = 0; j < nbCol; j++) {
				tab[i][j].setVuFaux();
			}
		}
	}

    /**Methode qui cherche un chemain pour aller d'une bille a une autre
     * @param ligD : la ligne de la bille de depart
     * @param colD : la colonne de la bille de depart
     * @param ligA : la ligne de la bille d'arrivé
     * @param colA : la colonnes de la bille d'arrivé
     * 
     */
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


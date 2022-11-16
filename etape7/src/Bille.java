/**Classe modélisant une Bille
 * @author PLOUVIN Patrice
 */

public class Bille {
    private final int ligne;
    private final int colonne;
    private int couleur;
    private boolean vu;
	private Bille[] voisins;

    /**Constructeur de la classe */
    public Bille(int ligne, int colonne,int couleur){
        this.couleur=couleur;
        this.ligne=ligne;
        this.colonne=colonne;
    }

    /**Méthode qui retourne la coordonné ligne de la Bille 
     * @return la ligne de la bille
    */
    public int getLigne(){
        return ligne;
    }

    /**Méthode qui retourne la coordonné colonne de la Bille 
     * @return : la colonne de la bille
    */
    public int getColonne(){
        return colonne;
    }

    /**Méthode qui retourne la couleur de la Bille 
     * @return : la couleur de la bille
    */
    public int getCouleur(){
        return couleur;
    }

    /**Méthode qui change la couleur de la Bille 
     * @param coul : la nouvelle couleur
    */
    public void setCouleur(int coul){
        couleur = coul;
    }

    /**Méthode qui retourn la valeur vu de la Bille
     * @return : un boolean caracterisant vu
    */
    public boolean getVu(){
        return vu;
    }
    /**Méthode qui change la valeur vu de la Bille en vrai */
    public void setVuVrai(){
        vu = true;
    }
    /**Méthode qui change la valeur vu de la Bille en faux */
    public void setVuFaux(){
        vu = false;
    }

    /**Méthode qui donne la liste des voisins de la Bille
     * @return : la liste des voisins de la bille
     */
    public Bille[] getVoisins() {
		return voisins;
	}

    /**Méthode qui change la liste des voisins de la Bille */
	public void setVoisins(Bille[] voisins) {
		this.voisins = voisins;
	}

    /**Méthode qui trouve si un chemin libre existe entre la bille et la bille aux coordonnées (ligA, colA).
	 * @param ligA : La ligne de la bille d'arrivée
	 * @param colA : La colonne de la bille d'arrivée
	 * @return : vrai si un chemin existe, faux sinon
	 */
	public boolean trouverChemin(int ligA, int colA) {
		if (ligne == ligA && colonne == colA){
			return true;
        }
		vu = true;
		for (Bille bille : voisins) {
			if (bille != null) {
				if ((bille.couleur == -1) && (!bille.vu) && (bille.trouverChemin(ligA, colA))){
					return true;
                }
				bille.setVuVrai();
			}
		}
		return false;
	}
}

package com.france.PROJET;

public class SeanceTheatre extends Seance
{
    protected SalleTheatre salleTheatre;
    protected int nbFauteuilsVendus;

    public SeanceTheatre(SalleTheatre salleTheatre, int nbFauteuilsVendus, int jour, Heure horaire, int nbPlacesVenduesTN )
    {
        super(jour, horaire, nbPlacesVenduesTN);
        this.salleTheatre = salleTheatre;
        this.nbFauteuilsVendus = nbFauteuilsVendus;

    }

    public int nbFauteuilsDispo()
    {
        return salleTheatre.getNbFauteuils()-nbFauteuilsVendus;
    }

    public void vendrePlacesFauteuil(int nbre)
    {
        if ((nbFauteuilsVendus + nbPlacesVenduesTN + nbre) > salleTheatre.capacite)
        {
            System.out.println ("La capacité est remplie");
        }
        else nbFauteuilsVendus += nbre;
    }

    public void vendrePlacesTN(int nbre)
    {
        if ((nbPlacesVenduesTN + nbFauteuilsVendus + nbre) > salleTheatre.capacite)
        {
            System.out.println ("La capacité est remplie");
        }
        else {nbPlacesVenduesTN += nbre;};
    }

    public int nbPlacesDispo()
    {
        return salleTheatre.getCapacite() - nbFauteuilsVendus - nbPlacesVenduesTN;
    }

    public int totalVendu()
    {
        return nbFauteuilsVendus + nbPlacesVenduesTN;
    }

    public double tauxRemplissage()
    {
        return totalVendu()*100.0/salleTheatre.getCapacite();
    }

    @Override
    public boolean equals(Object o)
    {
        SeanceCinema sc = (SeanceCinema) o;
        if (super.equals (sc) && (this.salleTheatre.equals (sc))) return true;
        return false;
    }

    @Override
    public String toString() {
        return  "Seance théâtre {" + super.toString()+ ", salle " + salleTheatre.toString() +
                ", nombre de fauteuils vendus: " + nbFauteuilsVendus + "}";
    }

}

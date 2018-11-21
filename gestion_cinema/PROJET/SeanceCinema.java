package com.france.PROJET;

import java.util.Objects;

public class SeanceCinema extends Seance
{
    protected Salle salle;
    protected  int nbPlacesVenduesTR = 0;

    public SeanceCinema(Salle salle, int nbPlacesVenduesTR, int jour, Heure horaire, int nbPlacesVenduesTN )
    {
        super(jour, horaire, nbPlacesVenduesTN);
        this.salle = salle;
        this.nbPlacesVenduesTR = nbPlacesVenduesTR;

    }

    public void vendrePlacesTN(int nbre)
    {
        if ((nbPlacesVenduesTN + nbPlacesVenduesTR + nbre) > salle.capacite)
        {
            System.out.println ("La capacité est remplie");
        }
        else {nbPlacesVenduesTN += nbre;};
    }

    public void vendrePlacesTR(int nbre)
    {
        if ((nbPlacesVenduesTR + nbPlacesVenduesTN+nbre) > salle.capacite)
        {
            System.out.println ("La capacité est remplie");
        }
        else {nbPlacesVenduesTR += nbre;};
    }

    public int nbPlacesDispo()
    {
        return salle.capacite - nbPlacesVenduesTR - nbPlacesVenduesTN;
    }

    public int totalVendu()
    {
        return nbPlacesVenduesTR + nbPlacesVenduesTN;
    }

    public double tauxRemplissage()
    {
        return totalVendu()*100.0/salle.capacite;
    }

    @Override
    public boolean equals(Object o)
    {
        SeanceCinema sc = (SeanceCinema) o;
        if (super.equals (sc) && (this.salle.equals (sc))) return true;
        return false;
    }

    @Override
    public String toString() {
        return  "Seance cinema {" + super.toString()+ ", salle " + salle.toString() +
                ", nombre de places vendues par tarif réduit: " + nbPlacesVenduesTR + "}";
    }
}

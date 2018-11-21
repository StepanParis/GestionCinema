package com.france.PROJET;

import java.util.Objects;

public abstract class Seance {
    protected int jour;
    protected Heure horaire;
    protected int nbPlacesVenduesTN = 0;

    public Seance(int jour, Heure horaire, int nbPlacesVenduesTN)
    {
        this.jour = jour;
        this.horaire = horaire;
        this.nbPlacesVenduesTN = nbPlacesVenduesTN;
    }

    public abstract int nbPlacesDispo();

    public abstract int totalVendu();

    public abstract double tauxRemplissage();

    public abstract void vendrePlacesTN(int nbre);

    @Override
    public boolean equals(Object o)
    {
        Seance s = (Seance) o;
        if (this.jour == s.jour && (this.horaire.getHoraire().compareTo (s.horaire.getHoraire ()))==0) return true;
        return false;
    }

    @Override
    public String toString()
    {
        return " jour: " + jour +", horaire est: " + this.horaire.getHoraire() +", nombre de places vendues par tarif normale: " + nbPlacesVenduesTN +"}";
    }
}

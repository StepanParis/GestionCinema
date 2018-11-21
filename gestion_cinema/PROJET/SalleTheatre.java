package com.france.PROJET;

public class SalleTheatre extends Salle
{
    private int nbFauteuils;
    protected double prixFateuil;
    protected int nbBalcon;

    public SalleTheatre(String nomSalle, double tarif, int nbFauteuils, double prixFateuil, int nbBalcon)
    {
        super(nomSalle,nbFauteuils + nbBalcon , tarif);
        this.nbFauteuils = nbFauteuils;
        this.prixFateuil = prixFateuil;
        this.nbBalcon = nbBalcon;
    }

    public int getNbFauteuils()
    {
        return nbFauteuils;
    }
    public int getCapacite()
    {
        return nbFauteuils + nbBalcon;
    }



    @Override
    public boolean equals(Object o)
    {
        return super.equals (o);
    }

    @Override
    public String toString()
    {
        return "{ nom de salle: " + nomSalle  + ", capacité: " + nbFauteuils + nbBalcon  + ", tarif: " + tarif +
                ", nombre de fauteuil: " + nbFauteuils + ", nombre de balcons: " + nbBalcon + "}";
    }
}


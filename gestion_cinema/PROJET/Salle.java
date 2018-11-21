package com.france.PROJET;

import java.util.Objects;

public class Salle
{
    protected String nomSalle;
    protected int capacite;
    protected double tarif;

    public Salle(String nomSalle, int capacite, double tarif)
    {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.tarif = tarif;
    }

    @Override
    public boolean equals(Object o)
    {
        Salle s = (Salle) o;
        if (this.nomSalle.compareTo(s.nomSalle)==0) return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "{ nom de salle: " + nomSalle  + ", capacité: " + capacite + ", tarif: " + tarif;
    }
}


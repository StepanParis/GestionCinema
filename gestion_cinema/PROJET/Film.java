package com.france.PROJET;

import java.util.List;
import java.util.Objects;

public class Film extends Spectacle
{
    protected String realisateur;
    protected Heure duree;

    public Film(String titre, List<String> interpretes, String realisateur, Heure duree)
    {
        super(titre, interpretes);
        this.realisateur = realisateur;
        this.duree = duree;
    }


    @Override
    public boolean equals(Object o)
    {
        Spectacle f = (Spectacle) o;
        if (super.equals (f)) return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "Film {" + super.toString () + " realisateur: " + realisateur  + ", duree: " + duree.getDuree ();
    }
}

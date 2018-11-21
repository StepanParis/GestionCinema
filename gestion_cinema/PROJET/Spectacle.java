package com.france.PROJET;

import java.util.List;
import java.util.Objects;

public abstract class Spectacle
{
    private   String titre;
    private List<String> interpretes;

    public Spectacle(String titre, List<String> interpretes)
    {
        this.titre = titre;
        this.interpretes = interpretes;
    }
    public String  getListeInterpretes()
    {
        String s = "";
        for (String liste: interpretes)
        {
           s += liste + ", ";
        }
        return  s.substring (0,s.length ()-2);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString()
    {
        return "titre: '" + titre + ", interpretes: " + this.getListeInterpretes ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spectacle)) return false;
        Spectacle spectacle = (Spectacle) o;
        return Objects.equals (titre, spectacle.titre) ;
    }
}

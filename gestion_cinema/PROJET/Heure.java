package com.france.PROJET;

public class Heure
{
    protected int heures;
    protected int minutes;
//verifier
    public Heure(int minutes)
    {
        this.minutes = minutes%60;
        this.heures = minutes/60;
    }

    public  Heure(int heures, int minutes)
    {
        this.heures = heures;
        this.minutes = minutes;
    }

    public int getDuree()
    {
        return heures*60+minutes;
    }

    public String getHoraire()
    {

        return String.valueOf (heures) + ":" + String.valueOf (minutes);
    }

    @Override
    public boolean equals(Object o)
    {
        if ((this.heures == ((Heure) o).heures)&&(this.minutes == ((Heure) o).minutes )) return true;
        return false;
    }

    @Override
    public String toString()
    {
        return heures+" heures, "+minutes+" minutes.";
    }


}

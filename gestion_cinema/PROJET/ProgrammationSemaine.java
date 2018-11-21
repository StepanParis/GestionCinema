package com.france.PROJET;

import java.util.*;

public class ProgrammationSemaine
{
    protected int semaine;

    public ProgrammationSemaine(int semaine)
    {
        this.semaine=semaine;
    }

    static Comparator<Seance> comparator = new Comparator<Seance>()
    {
        public int compare(Seance o1, Seance o2)
        {
            if (o1.jour>o2.jour)
            { return 1;}
            else if (o1.jour<o2.jour)
            {return -1;}
            else
            {
                if (o1.horaire.heures>o2.horaire.heures)
                { return 1;}
                else if (o1.horaire.heures<o2.horaire.heures)
                {return -1;}
                else
                {return 0;}
            }
        }
    };
    protected Map<Film, Set<Seance> > storeSeanceCinema = new HashMap<>();

    public void addSeanceCinema(Film f,Seance s)
    {
        if(storeSeanceCinema.containsKey (f))
        {
            storeSeanceCinema.get(f).add(s);
        }
        else
        {
            Set<Seance> sortedSeanceCinema = new TreeSet<>(comparator);
            sortedSeanceCinema.add(s);
            storeSeanceCinema.put(f, sortedSeanceCinema);
        }
    }

    public Set<Seance> getSeancesCinema (Film f)
    {

        if (storeSeanceCinema.containsKey (f))
        {
            return storeSeanceCinema.get (f);
        }
        else
        {
            System.out.println ("Il n'y a pas ce film");
            return null;
        }
    }

    public Set<Seance> getSeancesCinemaJour (Film f, int jour)
    {
        Set<Seance> seancesCinemaJour = new TreeSet<>(comparator);

        for(Seance seance: storeSeanceCinema.get (f))
        {
            if (seance.jour==jour)
            {
                seancesCinemaJour.add (seance);
            }

        }
        return seancesCinemaJour;
    }

    public Film getFilm (String titre)
    {
        for(Film film: storeSeanceCinema.keySet())
        {
            if (film.getTitre().equals (titre)) {
                return film;
            }
        }  return null;
    }

    public Set<Film> getSetFilms ()
    {
        return storeSeanceCinema.keySet ();
    }

    public int nbFilms ()
    {
        return storeSeanceCinema.size ();
    }

    public boolean isFilm (Film f)
    {
        return  storeSeanceCinema.containsKey (f);
    }

    public void suprimerSeancePourFilm(Film f, Seance s )
    {
        storeSeanceCinema.get (f).remove (s);
    }

    public Seance getSeanceHoraire(Film f, int jour, Heure horaire)
    {
        Set<Seance> seanceSet= storeSeanceCinema.get (f);
        for(Seance s: seanceSet)
        {
            if(s.jour==jour && s.horaire==horaire) return s;
        }return null;
    }

    protected Map<PieceTheatre, Set<Seance> > storeSeanceTheatre = new HashMap<>();

    public void addSeanceTheatre(PieceTheatre pt,Seance s)
    {
        if(storeSeanceTheatre.containsKey (pt))
        {
            storeSeanceTheatre.get(pt).add(s);
        }
        else
        {
            Set<Seance> sortedSeanceTheatre = new TreeSet<>(comparator);
            sortedSeanceTheatre.add(s);
            storeSeanceTheatre.put(pt,sortedSeanceTheatre);
        }
    }

    public Set<Seance> getSeancesTheatre (PieceTheatre pt)
    {
        if (storeSeanceTheatre.containsKey (pt))
        {
            return storeSeanceTheatre.get (pt);
        }
        else
        {
            System.out.println ("Il n'y a pas cette pièce");
            return null;
        }
    }

    public Seance getSeanceJour (PieceTheatre pt, int jour)
    {
        if (storeSeanceTheatre.containsKey (pt))
        {
            for(Seance s: storeSeanceTheatre.get (pt))
            {
                if(s.jour==jour) return s;
            }
            return null;
        }
        return null;
    }

    public PieceTheatre getPiece (String titre)
    {
        for(PieceTheatre pt: storeSeanceTheatre.keySet())
        {
            if (pt.getTitre().equals (titre)) {
                return pt;
            }
        }  return null;
    }

    public Set<PieceTheatre> getSetPieces ()
    {
        return storeSeanceTheatre.keySet ();
    }

    public int nbPieces ()
    {
        return storeSeanceTheatre.size ();
    }

    public boolean isPiece (PieceTheatre pt)
    {
        return  storeSeanceTheatre.containsKey (pt);
    }

    public void suprimerSeancePourPiece(PieceTheatre pt, Seance s )
    {
        Set<Seance> seanceSet = storeSeanceTheatre.get (pt);
        seanceSet.remove (s);
    }

    public double chiffreFilm(Film f)
    {
        double chiffre = 0;
        for (Seance s: storeSeanceCinema.get(f))
        {
            SeanceCinema seanceCinema = (SeanceCinema)s;
            chiffre +=( seanceCinema.nbPlacesVenduesTR*60/100+seanceCinema.nbPlacesVenduesTN)*seanceCinema.salle.tarif;
        }
        return chiffre;
    }

    public double remplissageFilm(Film f) {
        double remplissage = 0;
        for (Seance s : storeSeanceCinema.get (f)) {
            SeanceCinema seanceCinema = (SeanceCinema) s;
            remplissage += seanceCinema.tauxRemplissage ();
        }
        return remplissage / (storeSeanceCinema.get (f).size ());
    }

    public double chiffrePieceTheatre(PieceTheatre pt)
    {
        double chiffre = 0;
        for (Seance s: storeSeanceTheatre.get(pt))
        {
            SeanceTheatre seancePiece = (SeanceTheatre) s;
            chiffre += seancePiece.nbFauteuilsVendus*seancePiece.salleTheatre.prixFateuil
                    + seancePiece.salleTheatre.nbBalcon*seancePiece.salleTheatre.tarif;
        }
        return chiffre;
    }

    public double remplissagePiece(PieceTheatre pt) {
        double remplissage = 0;
        for (Seance s : storeSeanceTheatre.get (pt)) {
            SeanceTheatre seancePiece = (SeanceTheatre) s;
            remplissage += seancePiece.tauxRemplissage ();
        }
        return remplissage / (storeSeanceTheatre.get (pt).size ());
    }




}

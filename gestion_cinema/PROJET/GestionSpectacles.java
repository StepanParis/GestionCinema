package com.france.PROJET;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GestionSpectacles
{
    static Set<Salle> ensembleSallesCinema = new HashSet<> ();
    static Set<Salle> ensembleSallesTheatre = new HashSet<> ();

    public static void main(String[] args)
    {
        try (FileReader fr = new FileReader ("C:\\Users\\Sony\\Desktop\\Projet2017\\Projet2017\\SallesCinema.csv");
             BufferedReader br = new BufferedReader (fr))
        {
            String s = br.readLine ();
            while ((s = br.readLine ()) != null)
            {
                //On ramplace une virgule par un point pour noter les décimaux
                s = s.replace (',', '.');
                s = s.substring (6, s.length ());
                String[] subStr;

                String delimeter = ";"; // Délimiteur
                subStr = s.split (delimeter);
                ensembleSallesCinema.add (new Salle (subStr[0], Integer.parseInt (subStr[1]), Double.parseDouble (subStr[2])));
                //System.out.println(subStr[0]);
            }
            /*for (Salle salle : ensembleSallesCinema )
            {
                System.out.println(salle.nomSalle + " " + salle.capacite + " " + salle.tarif);
            }*/
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage ());
        }
        catch (Exception e)
        {
            System.out.println ("Le format des données est ilégal");
        }

        try (FileReader fr = new FileReader ("C:\\Users\\Sony\\Desktop\\Projet2017\\Projet2017\\SallesTheatre.csv");
             BufferedReader br = new BufferedReader (fr)) {
            //чтение построчно
            String s = br.readLine ();
            while ((s = br.readLine ()) != null) {
                s = s.replace (',', '.');
                s = s.substring (6, s.length ());
                String[] subStr;

                String delimeter = ";"; // Разделитель
                subStr = s.split (delimeter);
                //Мы ожидаем кол-во балконов, но в данных только capacite et nbFauteuils, поэтому вычисляю
                ensembleSallesTheatre.add (new SalleTheatre (subStr[0], Double.parseDouble (subStr[2]),
                        Integer.parseInt (subStr[3]), Double.parseDouble (subStr[4]),
                        Integer.parseInt (subStr[1]) - Integer.parseInt (subStr[3])));
            }
            /*
            for (Salle salle : ensembleSallesTheatre) {
                SalleTheatre st = (SalleTheatre) salle;
                System.out.println (st.nomSalle + " " + st.capacite + " " + st.tarif + " " + st.getNbFauteuils ()
                                    + " " + st.prixFateuil);
            }*/
        }
        catch (IOException ex)
        {
            System.out.println (ex.getMessage ());
        }
        catch (Exception e)
        {
            System.out.println ("Le format des données est ilégal");
        }

        List<ProgrammationSemaine> lesProgrammations = new ArrayList<> ();
        for (int i=0; i <= 52; i++)
        {
            lesProgrammations.add ( new ProgrammationSemaine (i));
        }



        try (BufferedReader br = new BufferedReader (new InputStreamReader (System.in))) {
            // Creeation programmation de la semaine suivante
            man ();
            String commande = br.readLine ();
            do {

                switch (commande.toLowerCase ()) {
                    case "créer":
                        creation (br, lesProgrammations);
                        break;
                    case "modifier":
                        modification(br, lesProgrammations);
                        break;
                    case "vendre":
                        vente(br, lesProgrammations);
                        break;
                    default:
                        man ();
                        break;
                }
            }
            while (!(commande = br.readLine ()).equals ("exit"));

            //créer la programmation de la semaine suivante

        } catch (IOException ex) {
            System.out.println (ex.getMessage ());
        }
    }
    private static void creation (BufferedReader br, List<ProgrammationSemaine> lesProgrammations) throws IOException
    {
        System.out.println ("Pour quoi voulez vous créer une programmation: film ou pièce? ");
        Film f;
        Set<Seance> sortedSeanceCinema;

        PieceTheatre pt;
        Set<Seance> sortedSeancePieceTheatre;

        String command=br.readLine();
        if (command.equals ("film"))
        {
           f =  creerCinema(br);
           sortedSeanceCinema = creerEnsembleSeancesCinema(br);
           int numeroSemaine = 55;
           while (numeroSemaine>52) {
               System.out.println ("Saisissez le numéro de la semaine jusqu'à 52");
               try {
                   numeroSemaine = Integer.parseInt (br.readLine ());
               } catch (Exception e) {
                   System.out.println ("Il faut saisir un entier");
               }
           }
           ProgrammationSemaine ps =null;
           if( lesProgrammations.size ()!=0) {
               for (ProgrammationSemaine tempPS : lesProgrammations) {
                   if (tempPS.semaine == numeroSemaine) {
                       ps = tempPS;
                       break;
                   }
               }
           }
           if(ps==null)
           {
               ps = new ProgrammationSemaine (numeroSemaine);
               lesProgrammations.add (numeroSemaine, ps);
           }
           for(Seance seance : sortedSeanceCinema)
           {
               ps.addSeanceCinema (f, seance);
           }
        }
        else if (command.equals ("pièce"))
        {
            pt = creerPieceTheatre(br);
            sortedSeancePieceTheatre = creerEnsembleSeancesPieceTheatre (br);

            int numeroSemaine = 55;
            while (numeroSemaine>52)
            {
                System.out.println ("Saisissez le numéro de la semaine jusqu'à 52");
                try
                {
                    numeroSemaine = Integer.parseInt (br.readLine ());
                }
                catch (Exception e)
                {
                    System.out.println ("Il faut saisir un entier");
                }
            }
            ProgrammationSemaine ps =null;

            for (ProgrammationSemaine tempPS : lesProgrammations)
            {
                if (tempPS.semaine==numeroSemaine)
                {
                    ps=tempPS;
                    break;
                }
            }
            if(ps==null)
            {
                ps = new ProgrammationSemaine (numeroSemaine);
                lesProgrammations.add (numeroSemaine, ps);
            }
            for(Seance seance : sortedSeancePieceTheatre)
            {
                ps.addSeanceTheatre (pt, seance);
            }
        }
        else {man();}
        System.out.println ("Vous avez créé un film ");
        man();
    }
    private static Film creerCinema (BufferedReader br) throws IOException
    {
        System.out.println ("Saisissez le titre d'un film");
        String titre = br.readLine();
        System.out.println ("Saisissez une liste des interpretes  par virgule");
        //Не можем поместить все сразу элементы в лист, поэтому делаем с помощью массива
        String[] strings = br.readLine ().split (",");
        List<String> listeInterpretes = new ArrayList<String>();
        for(String str : strings)
        {
            listeInterpretes.add (str);
        }

        System.out.println ("Saisissez un realisateur");
        String realisateur = br.readLine();

        System.out.println ("Saisissez une durée en minutes");
        Heure duree = new Heure (Integer.parseInt (br.readLine ()));

        return new Film (titre, listeInterpretes, realisateur, duree);
    }
    private static  Set<Seance> creerEnsembleSeancesCinema (BufferedReader br) throws IOException {

        Set<Seance> sortedSeanceCinema = new TreeSet<>(ProgrammationSemaine.comparator);

        String nom ="tempVariable";
        while(!(nom).equals ("stop"))
        {
            Salle locSalle = null;
            System.out.println ("Saisissez le nom d'une salle");
            nom = br.readLine ();
            for (Salle salle : ensembleSallesCinema) {
                if (salle.nomSalle.equals (nom)) {
                    locSalle = salle;
                    break;
                }
            }
            if (locSalle == null)
            {
                System.out.println ("Saisissez la capacité d'une salle");
                int capacite = Integer.parseInt (br.readLine ());
                System.out.println ("Saisissez le tarif d'une salle au format DOUBLE");
                Double tarif = Double.parseDouble (br.readLine ());

                locSalle = new Salle (nom, capacite, tarif);
            }
            System.out.println ("Saisissez le nombre de places vendues par tarif réduit");
            int nbPlacesVenduesTR = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez le numéro du jour de la semaine");
            int jour = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez l'horaire au format hh:mm");
            String[] time = br.readLine().split(":");
            int heures  = Integer.parseInt (time[0]);
            int minutes = Integer.parseInt (time[1]);
            Heure horaire = new Heure(heures, minutes);

            System.out.println ("Saisissez le nombre de places vendues par tarif normale");
            int nbPlacesVenduesTN =  Integer.parseInt (br.readLine ());

            sortedSeanceCinema.add ( new SeanceCinema (locSalle,nbPlacesVenduesTR,jour,horaire,nbPlacesVenduesTN));
            System.out.println ("Si vous avez fini saisissez 'stop'");
            nom = br.readLine ();

        }
        return sortedSeanceCinema;
    }
    private static PieceTheatre creerPieceTheatre(BufferedReader br) throws IOException
    {
        System.out.println ("Saisissez le titre d'une pièce théâtre");
        String titre = br.readLine();

        System.out.println ("Saisissez une liste des interpretes  par virgule");
        String[] strings = br.readLine ().split (",");
        List<String> listeInterpretes = new ArrayList<String>();
        for(String str : strings)
        {
            listeInterpretes.add (str);
        }

        System.out.println ("Saisissez un metteur en scene");
        String metteurEnScene = br.readLine();

        System.out.println ("Saisissez le nombre d'entractes");
        int nbEntractes = Integer.parseInt (br.readLine ());

        return new PieceTheatre (titre, listeInterpretes, metteurEnScene, nbEntractes);

    }
    private static  Set<Seance> creerEnsembleSeancesPieceTheatre (BufferedReader br) throws IOException {

        Set<Seance> sortedSeancePieceTheatre = new TreeSet<>(ProgrammationSemaine.comparator);
        String nom ="tempVariable";
        while(!(nom).equals ("stop"))
        {
            SalleTheatre locSalle = null;
            System.out.println ("Saisissez le nom d'une salle");
            nom = br.readLine ();

            for (Salle salle : ensembleSallesTheatre)
            {
                if (salle.nomSalle.equals (nom)) {
                    locSalle = (SalleTheatre)salle;
                    break;
                }
            }
            if (locSalle == null)
            {

                System.out.println ("Saisissez le tarif d'une salle au format DOUBLE");
                Double tarif = Double.parseDouble (br.readLine ());

                System.out.println ("Saisissez le nombre de fauteuils d'une salle");
                int nbFauteuils = Integer.parseInt (br.readLine ());


                System.out.println ("Saisissez le prix de fauteuil d'une salle au format DOUBLE");
                Double prixFateuil = Double.parseDouble (br.readLine ());

                System.out.println ("Saisissez le nombre de balcons d'une salle");
                int nbBalcon = Integer.parseInt (br.readLine ());

                locSalle = new SalleTheatre (nom, tarif, nbFauteuils, prixFateuil, nbBalcon);
            }

            System.out.println ("Saisissez le nombre de fauteuils vendus");
            int nbFauteuilsVendus = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez le jour");
            int jour = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez l'horaire en minutes, le délimiteur soit ':'");
            String[] time = br.readLine().split(":");
            int heures  = Integer.parseInt (time[0]);
            int minutes = Integer.parseInt (time[1]);
            Heure heure = new Heure(heures, minutes);

            System.out.println ("Saisissez le nombre de places vendues par tarif normale");
            int nbPlacesVenduesTN =  Integer.parseInt (br.readLine ());

            sortedSeancePieceTheatre.add ( new SeanceTheatre (locSalle, nbFauteuilsVendus, jour, heure, nbPlacesVenduesTN));
            System.out.println ("Si vous avez fini saisissez 'stop'");
            nom = br.readLine ();
        }
        return sortedSeancePieceTheatre;
    }
    private static void modification (BufferedReader br, List<ProgrammationSemaine> lesProgrammations) throws IOException
    {
        int numeroSemaine = 55;
        while (numeroSemaine>52)
        {
            System.out.println ("Saisissez le numéro de la semaine jusqu'à 52");
            try
            {
                numeroSemaine = Integer.parseInt (br.readLine ());
            }
            catch (Exception e) {
                System.out.println ("Il faut saisir un entier");
            }
        }

        System.out.println ("Choisissez ce que vous voulez faire: ajouter ou supprimer");
        String choix = br.readLine();
        if (choix.compareToIgnoreCase ("ajouter")==0)  {ajouter(br, numeroSemaine, lesProgrammations);}
        if (choix.compareToIgnoreCase ("supprimer")==0){supprimer(br, numeroSemaine, lesProgrammations);}
    }
    private static void ajouter (BufferedReader br, int numSemaine, List<ProgrammationSemaine> lesProgrammations) throws IOException {

        System.out.println ("Vous voulez ajouter une séance pour un film ou une pièce?");
        String choix = br.readLine();
        System.out.println ("Saisissez titre?");
        String titre = br.readLine();

        if (choix.compareToIgnoreCase ("film")==0) {
            System.out.println ("Saisissez le nom d'une salle");
            String nom = br.readLine ();
            Salle locSalle = null;
            for (Salle salle : ensembleSallesCinema) {
                if (salle.nomSalle.equals (nom)) {
                    locSalle = salle;
                    break;
                }
            }
            if (locSalle == null)
            {
                System.out.println ("Saisissez la capacité d'une salle");
                int capacite = Integer.parseInt (br.readLine ());
                System.out.println ("Saisissez le tarif d'une salle au format DOUBLE");
                Double tarif = Double.parseDouble (br.readLine ());

                locSalle = new Salle (nom, capacite, tarif);
            }
            System.out.println ("Saisissez le nombre de places vendues par tarif réduit");
            int nbPlacesVenduesTR = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez le numéro du jour de la semaine");
            int jour = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez l'horaire avec delimiteur ':'");
            String[] time = br.readLine().split(":");
            int heures  = Integer.parseInt (time[0]);
            int minutes = Integer.parseInt (time[1]);
            Heure horaire = new Heure (heures, minutes);

            System.out.println ("Saisissez le nombre de places vendues par tarif normale");
            int nbPlacesVenduesTN =  Integer.parseInt (br.readLine ());

            Seance seanceCinema= new SeanceCinema (locSalle,nbPlacesVenduesTR,jour,horaire,nbPlacesVenduesTN);
            for (ProgrammationSemaine ps : lesProgrammations) {
                if (ps.semaine == numSemaine) {
                    ps.addSeanceCinema (ps.getFilm (titre), seanceCinema);
                    break;
                }
            }
        }

        else if(choix.compareToIgnoreCase ("pièce")==0)
        {

            System.out.println ("Saisissez le nom d'une salle");
            String nom = br.readLine ();
            SalleTheatre locSalle = null;
            for (Salle salle : ensembleSallesTheatre)
            {
                if (salle.nomSalle.equals (nom)) {
                    locSalle = (SalleTheatre)salle;
                    break;
                }
            }
            if (locSalle == null)
            {

                System.out.println ("Saisissez le tarif d'une salle au formant DOUBLE");
                Double tarif = Double.parseDouble (br.readLine ());

                System.out.println ("Saisissez le nombre de fauteuils d'une salle");
                int nbFauteuils = Integer.parseInt (br.readLine ());

                System.out.println ("Saisissez le prix de fauteuil d'une salle au formant DOUBLE");
                Double prixFateuil = Double.parseDouble (br.readLine ());

                System.out.println ("Saisissez le nombre de balcons d'une salle");
                int nbBalcon = Integer.parseInt (br.readLine ());

                locSalle = new SalleTheatre (nom, tarif, nbFauteuils, prixFateuil, nbBalcon);
            }

            System.out.println ("Saisissez le nombre de fauteuils vendus");
            int nbFauteuilsVendus = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez le numéro du jour de la semaine");
            int jour = Integer.parseInt (br.readLine ());

            System.out.println ("Saisissez l'horaire, le delimeteur ':'");
            String[] time = br.readLine().split(":");
            int heures  = Integer.parseInt (time[0]);
            int minutes = Integer.parseInt (time[1]);
            Heure horaire = new Heure (heures, minutes);


            System.out.println ("Saisissez le nombre de places vendues par tarif normale");
            int nbPlacesVenduesTN =  Integer.parseInt (br.readLine ());

            Seance seanceTheatre = new SeanceTheatre (locSalle, nbFauteuilsVendus, jour, horaire, nbPlacesVenduesTN);
            for (ProgrammationSemaine ps : lesProgrammations) {
                if (ps.semaine == numSemaine) {
                    ps.addSeanceTheatre (ps.getPiece (titre), seanceTheatre);
                    break;
                }
            }
        }else{
            System.out.println ("Wrong command try again!");
            return;
        }
        System.out.println ("Vous aves ajouté une séance");
        man();

    }
    private static void supprimer (BufferedReader br, int numSemaine, List<ProgrammationSemaine> lesProgrammations/*, Seance s, String titre*/) throws IOException {
        ProgrammationSemaine ps = null;
        try {
            ps = lesProgrammations.get (numSemaine);
        }
        catch (Exception e)
        {
            System.out.println ("Il n'y a pas la programmation pour cette semaine ");
            return;
        }
        System.out.println ("Vous voulez supprimer une séance du film ou de la pièce?");
        String choix = br.readLine();
        System.out.println ("Le nom du film ou de la pièce");
        String titre = br.readLine ();

        System.out.println ("L'horaire de seance");
        String[] time = br.readLine().split(":");
        int heures  = Integer.parseInt (time[0]);
        int minutes = Integer.parseInt (time[1]);
        Heure horaire = new Heure (heures, minutes);

        if (choix.compareToIgnoreCase ("film")==0)
        {
            Film film = null;
            for (Film f: ps.getSetFilms ())
            {
                if (f.getTitre ().equals (titre))
                {
                    film = f;
                }
            }
            if (film==null)
            {
                Film f = new Film (titre, null, null, null);
            }
            Set<Seance> seanceSet = ps.getSeancesCinema (film);

            for (Seance s : seanceSet) {
                if (s.horaire.getHoraire().equals (horaire.getHoraire ()))
                {
                    seanceSet.remove (s);
                }
            }
            System.out.println ("Vous aves supprimé une séance");
            man();
        }
        else if(choix.compareToIgnoreCase ("pièce")==0)
        {
            PieceTheatre pt = new PieceTheatre (titre, null, null, 0);
            Set<Seance> seanceSetT = ps.storeSeanceTheatre.get (pt);
            for (Seance s : seanceSetT) {
                if (s.horaire.equals (horaire)) {
                    seanceSetT.remove (s);
                }
            }
            System.out.println ("Vous aves supprimé une séance");
            man();
        }
        else
        {
            System.out.println ("Wrong command try again!");
        }
    }
    private static void vente(BufferedReader br, List<ProgrammationSemaine> lesProgrammations) throws IOException
    {
        System.out.println ("Voulez vous vendre des places pour un film ou une pièce?");
        String choix = br.readLine ();

        System.out.println ("Saisissez le numéro de semaine");
        int numSemaine = Integer.parseInt (br.readLine ());

        //Si on a choisi le film on le cherche
        //Avant on cherche touts les film pour semaine choisie
        if (choix.equals ("film"))
        {
            vendreFilm(br, lesProgrammations, numSemaine);
        }
        if (choix.equals ("pièce"))
        {
            vendreTheatre(br, lesProgrammations, numSemaine);
        }
    }
    private static void vendreFilm(BufferedReader br, List<ProgrammationSemaine> lesProgrammations, int numSemaine)
            throws IOException
    {
        Set<Film> setFilms = null;
        ProgrammationSemaine prs = null;
        for (ProgrammationSemaine ps: lesProgrammations)
        {
            if (ps.semaine==numSemaine)
            {
                setFilms = ps.getSetFilms ();
                prs = ps;
            }
        }
        //On affiche touts les films du set de films
        if (setFilms==null)
        {
            System.out.println ("Il n'y a pas de programmation pour cette semaine");
            return;
        }

        System.out.println ("Choisissez le film qui vous interrese");
        for (Film f: setFilms) { System.out.println (f.getTitre()); }

        //On cherche set de séances de film choisi
        String titre = br.readLine ();
        Set<Seance> setSeancesFilm = null;
        setSeancesFilm = prs.getSeancesCinema (prs.getFilm (titre));

        //On affiche toute l'information de toutes les séances de ce film
        for (Seance s: setSeancesFilm) { System.out.println (s.toString ()); }

        System.out.println ("Choisissez le jour du film qui vous interrese");
        int jour = Integer.parseInt (br.readLine ());

        System.out.println ("Choisissez l'horaire du film qui vous interrese");
        String[] time = br.readLine().split(":");
        int heures  = Integer.parseInt (time[0]);
        int minutes = Integer.parseInt (time[1]);
        Heure horaire = new Heure (heures, minutes);

        for (Seance s: setSeancesFilm)
        {
            if ((s.jour == jour) && (s.horaire.getHoraire().compareTo(horaire.getHoraire ()))==0)
            {
                System.out.print ("Le nombre de places disponibles: ");
                System.out.println (s.nbPlacesDispo ());
                System.out.println ("Saisissez le nombre de places que vous voulez vendre");
                int nbPlacesAVendre = Integer.parseInt(br.readLine());
                s.vendrePlacesTN (nbPlacesAVendre);
            }
        }
        System.out.println ("Succès");
    }
    private static void vendreTheatre(BufferedReader br, List<ProgrammationSemaine> lesProgrammations, int numSemaine)
            throws IOException
    {
        Set<PieceTheatre> setPieces = null;
        ProgrammationSemaine prs = null;
        for (ProgrammationSemaine ps: lesProgrammations)
        {
            if (ps.semaine==numSemaine)
            {
                setPieces = ps.getSetPieces ();
                prs = ps;
            }
        }
        if (setPieces==null)
        {
            System.out.println ("Il n'y a pas de programmation pour cette semaine");
            return;
        }

        System.out.println ("Choisissez la pièce qui vous interrese");
        //On affiche touts les pièces du set de pieces
        for (PieceTheatre pt: setPieces) { System.out.println (pt.getTitre()); }

        //On cherche set de séances de pièce choisi
        String titre = br.readLine ();
        Set<Seance> setSeancesTheatre = null;
        setSeancesTheatre = prs.getSeancesTheatre (prs.getPiece (titre));
        //On affiche toute l'information de toutes les séances de cette pièce
        for (Seance s: setSeancesTheatre) { System.out.println (s.toString ()); }

        System.out.println ("Choisissez le jour de la pièce qui vous interrese");
        int jour = Integer.parseInt (br.readLine ());

        System.out.println ("Choisissez l'horaire du pièce qui vous interrese");
        String[] time = br.readLine().split(":");
        int heures  = Integer.parseInt (time[0]);
        int minutes = Integer.parseInt (time[1]);
        Heure horaire = new Heure (heures,minutes);

        for (Seance s: setSeancesTheatre)
        {
            if ((s.jour == jour) && (s.horaire.getHoraire().compareTo(horaire.getHoraire ()))==0)
            {
                System.out.print ("Le nombre de places disponibles: ");
                System.out.println (s.nbPlacesDispo ());
                System.out.println ("Saisissez le nombre de places que vous voulez vendre");
                int nbPlacesAVendre = Integer.parseInt(br.readLine());
                s.vendrePlacesTN (nbPlacesAVendre);
            }
        }
        System.out.println ("Succès");
    }
    private static void man()
    {
        System.out.println ("Qu'est que vous voulez faire?");
        System.out.println (" -créer    -- créer la programmation de la semaine suivante ");
        System.out.println (" -modifier -- modifier une programmation de semaine existante");
        System.out.println (" -vendre   -- vendre des places");
    }
}

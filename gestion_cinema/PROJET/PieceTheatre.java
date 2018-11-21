package com.france.PROJET;

import java.util.List;
import java.util.Objects;

public class PieceTheatre extends Spectacle {
    protected String metteurEnScene;
    protected int nbEntractes;

    public PieceTheatre(String titre, List<String> interpretes, String metteurEnScene, int nbEntractes) {
        super (titre, interpretes);
        this.metteurEnScene = metteurEnScene;
        this.nbEntractes = nbEntractes;
    }

    @Override
    public boolean equals(Object o) {
        PieceTheatre pt = (PieceTheatre) o;
        if (super.equals (pt) && (this.metteurEnScene.compareTo (pt.metteurEnScene)) == 0) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Piéce théatre {" + super.toString () + "metteur en scene: " + metteurEnScene + ", nombre entractes: " + nbEntractes;
    }
}

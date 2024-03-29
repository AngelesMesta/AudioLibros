package com.mesta.audiolibros;

import android.app.Application;
import java.util.Vector;

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros;
    private AdaptadorLibros adaptador;

    @Override
    public void onCreate() {
        super.onCreate(); //esto lo puse porque marcaba un error
        vectorLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibros(this, vectorLibros);
    }

    public AdaptadorLibros getAdaptador() {
        return adaptador;
    }

    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }
}

package com.mesta.audiolibros.fragments;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mesta.audiolibros.AdaptadorLibros;
import com.mesta.audiolibros.Aplicacion;
import com.mesta.audiolibros.Libro;
import com.mesta.audiolibros.MainActivity;
import com.mesta.audiolibros.R;


import java.util.Vector;

public class SelectorFragment {
    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibros adaptador;
    private Vector<Libro> vectorLibros;

    @Override
    public void onAttach(Context contexto) {
        super.onAttach(contexto);
        if (contexto instanceof Activity) {
            this.actividad = (Activity) contexto;
            Aplicacion app = (Aplicacion) actividad.getApplication();
            adaptador = app.getAdaptador();
            vectorLibros = app.getVectorLibros();
        }
    }

    @Override
        public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_viewdos);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad,2));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ((MainActivity) actividad).mostrarDetalle(recyclerView.getChildAdapterPosition(v));
            }         });
        return vista;


        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = { "Compartir", "Borrar ", "Insertar" };
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0: //Compartir
                                        Libro libro = vectorLibros.elementAt(id);
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");
                                        i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                        i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                        startActivity(Intent.createChooser(i, "Compartir"));
                                        break;
                                        case 1: //Borrar
                                                vectorLibros.remove(id);
                                                adaptador.notifyDataSetChanged();
                                                break;
                                                case 2: //Insertar
                                                 vectorLibros.add(vectorLibros.elementAt(id));
                                                 adaptador.notifyDataSetChanged();
                                                 break;
                        }             }         });
                menu.create().show();
                return true;     } });
    }

    }

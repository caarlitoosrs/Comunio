package com.romero.proyectofinalprueba.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.romero.proyectofinalprueba.R;
import com.romero.proyectofinalprueba.models.DAOEscudos;
import com.romero.proyectofinalprueba.models.Jugador;
import com.romero.proyectofinalprueba.models.MercadoAdapter;

import java.util.ArrayList;

public class FragmentMercado extends Fragment {

    private ListView lista;
    private ArrayList<Jugador> jugadores;
    DAOEscudos daoEscudos;
    private MercadoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mercado, container, false);

        lista=view.findViewById(R.id.listaMercado);

        daoEscudos = new DAOEscudos();
        jugadores = daoEscudos.obtenerJugadores();

        adapter = new MercadoAdapter(getContext(), jugadores);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador jugadorSeleccionado = jugadores.get(position);

                new AlertDialog.Builder(view.getContext()).setTitle("Quiere comprar a "+jugadorSeleccionado.getNombre()+"?")
                        .setMessage("VALOR: "+jugadorSeleccionado.getMonedas()+"M"+"\n"+"MEDIA: "+jugadorSeleccionado.getMedia()+"GRL"+"\n"+ "Posicion: "+jugadorSeleccionado.getPosicion())
                        .setPositiveButton("Cerrar", null).setNegativeButton("Comprar",null).show();
            }
        });

        return view;
    }
}
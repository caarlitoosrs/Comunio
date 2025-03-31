package com.romero.proyectofinalprueba.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.romero.proyectofinalprueba.R;
import com.romero.proyectofinalprueba.models.DAOEscudos;
import com.romero.proyectofinalprueba.models.Jugador;
import com.romero.proyectofinalprueba.models.MercadoAdapter;
import com.romero.proyectofinalprueba.models.ui.main.SharedViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FragmentMercado extends Fragment {

    private ListView lista;
    private ArrayList<Jugador> jugadores;
    private MercadoAdapter adapter;
    private DAOEscudos daoEscudos;
    private SharedViewModel viewModel;
    private TextView saldoTextView;
    private int saldoActual;
    private Map<String, Jugador> jugadoresComprados; // Almacena jugadores por posición

    public FragmentMercado(TextView saldoTextView) {
        this.saldoTextView = saldoTextView;
        this.jugadoresComprados = new HashMap<>(); // Almacena jugadores comprados
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mercado, container, false);

        lista = view.findViewById(R.id.listaMercado);
        daoEscudos = new DAOEscudos();
        jugadores = daoEscudos.obtenerJugadores();
        adapter = new MercadoAdapter(getContext(), jugadores);
        lista.setAdapter(adapter);

        saldoActual = Integer.parseInt(saldoTextView.getText().toString().replace("Saldo: ", "").replace("M", "").trim());

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getSaldoActual().observe(getViewLifecycleOwner(), saldo -> {
            saldoTextView.setText("Saldo: " + saldo + "M");
        });

        lista.setOnItemClickListener((parent, view1, position, id) -> {
            Jugador jugadorSeleccionado = jugadores.get(position);

            //PARA QUE NO SE REPITA UNA POSICIÓN QUE YA TIENES
          /*  if (viewModel.getJugadoresComprados().getValue().containsKey(jugadorSeleccionado.getPosicion())) {
                new AlertDialog.Builder(view1.getContext())
                        .setTitle("Jugador ya comprado")
                        .setMessage("Ya tienes un " + jugadorSeleccionado.getPosicion() + " en tu equipo.")
                        .setPositiveButton("Aceptar", null)
                        .show();
                return;
            }*/

            new AlertDialog.Builder(view1.getContext())
                    .setTitle("¿Quieres comprar a " + jugadorSeleccionado.getNombre() + "?")
                    .setMessage("VALOR: " + jugadorSeleccionado.getMonedas() + "M" + "\n" +
                            "MEDIA: " + jugadorSeleccionado.getMedia() + "GRL" + "\n" +
                            "Posición: " + jugadorSeleccionado.getPosicion())
                    .setPositiveButton("Cerrar", null)
                    .setNegativeButton("Comprar", (dialog, which) -> {
                        if (saldoActual >= jugadorSeleccionado.getMonedas()) {
                            int nuevoSaldo = saldoActual - jugadorSeleccionado.getMonedas();
                           viewModel.actualizarSaldo(nuevoSaldo);

                            viewModel.agregarJugador(jugadorSeleccionado.getPosicion(), jugadorSeleccionado);
                        } else {
                            new AlertDialog.Builder(view1.getContext())
                                    .setTitle("Saldo insuficiente")
                                    .setMessage("No tienes suficiente dinero para comprar a este jugador.")
                                    .setPositiveButton("Aceptar", null)
                                    .show();
                        }
                    })
                    .show();
        });

        return view;
    }
}
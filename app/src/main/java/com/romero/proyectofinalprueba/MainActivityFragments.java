package com.romero.proyectofinalprueba;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.romero.proyectofinalprueba.fragments.FragmentMercado;
import com.romero.proyectofinalprueba.fragments.FragmentPlantilla;

public class MainActivityFragments extends AppCompatActivity {

    private ImageView imagen, btnMercado, btnPlantilla, btnEquipo;
    private TextView nombreEquipo, tvSaldo;
    private int saldo = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragments);

        btnEquipo=findViewById(R.id.btnEquipo);
        btnMercado=findViewById(R.id.btnMercado);
        btnPlantilla=findViewById(R.id.btnPlantilla);
        imagen=findViewById(R.id.imgEquipoEscogido);
        nombreEquipo=findViewById(R.id.nombreEquipoEscogido);
        tvSaldo=findViewById(R.id.saldoInicial);

        tvSaldo.setText("Saldo: " + saldo + "M");
        int img  = getIntent().getIntExtra("imgEquipo", 0);
        String nombre = getIntent().getStringExtra("teamName");

        imagen.setImageResource(img);
        nombreEquipo.setText(nombre);

    btnEquipo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });

    btnMercado.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imagen.setVisibility(View.GONE);
            nombreEquipo.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_bottom, new FragmentMercado(tvSaldo)).commit();
        }
    });

    btnPlantilla.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imagen.setVisibility(View.GONE);
            nombreEquipo.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_bottom, new FragmentPlantilla()).commit();

        }
    });




    }

    //--------------MENÚ----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.itemCerrar){
            finish();
        }else if(item.getItemId()==R.id.itemTexto){
            //Aqui tenemos que elegir el tamaño de las fuentes, color, etc.
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.financas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.financas.fragments.DespesasFragment;
import com.financas.fragments.InicioFragment;
import com.financas.fragments.ParcelamentosFragment;
import com.financas.fragments.ReceitasFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView icInicio = findViewById(R.id.ic_inicio);
        ImageView icReceitas = findViewById(R.id.ic_receita);
        ImageView icDespesas = findViewById(R.id.ic_despesas);
        ImageView icParcelamentos = findViewById(R.id.ic_parcelamentos);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new InicioFragment())
                    .commit();
        }

        icInicio.setOnClickListener(v ->
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new InicioFragment())
                        .commit());

        icReceitas.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new ReceitasFragment())
                .addToBackStack(null)
                .commit());

        icDespesas.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new DespesasFragment())
                .addToBackStack(null)
                .commit());

        icParcelamentos.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new ParcelamentosFragment())
                .addToBackStack(null)
                .commit());
    }
}
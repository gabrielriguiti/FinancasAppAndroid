package com.financas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.financas.R;
import com.financas.fragments.DespesasFragment;
import com.financas.fragments.InicioFragment;
import com.financas.fragments.ParcelamentosFragment;
import com.financas.fragments.ReceitasFragment;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout clRootLayout;
    private ImageView ivReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clRootLayout = findViewById(R.id.root_layout);
        ivReceitas = findViewById(R.id.ic_receitas);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,  // Especifica a orientação do degradê
                new int[]{Color.parseColor("#2196F3"), Color.WHITE}  // Especifica as cores do degradê
        );

        clRootLayout.setBackground(gradientDrawable);

        ivReceitas.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReceitasActivity.class);
            startActivity(intent);
        });
    }
}
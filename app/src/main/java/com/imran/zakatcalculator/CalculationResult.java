package com.imran.zakatcalculator;

import static com.imran.zakatcalculator.CalculateZakat.zakatPayable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculationResult extends AppCompatActivity {

    TextView zakatAmount;
    Button calculateAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTheme(R.style.Theme_ZakatCalculator);
        setContentView(R.layout.calculation_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calculationResult), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        zakatAmount = findViewById(R.id.zakatAmount);
        calculateAgain = findViewById(R.id.calculateAgain);

        if (zakatPayable>0) {
            zakatAmount.setText( String.format("%.2f", zakatPayable) + " Taka");
        } else {
            zakatAmount.setText("You don't need to pay Zakat");
        }

        calculateAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculationResult.this, CalculateZakat.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
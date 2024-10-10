package com.imran.zakatcalculator;

import static com.imran.zakatcalculator.CalculateZakat.zakatPayable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CalculationResult extends AppCompatActivity {

    TextView zakatAmount;
    Button calculateAgain;

    OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();

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

        //Assigning variables to the views

        zakatAmount = findViewById(R.id.zakatAmount);
        calculateAgain = findViewById(R.id.calculateAgain);

        //Checking if the zakat is payable or not
        if (zakatPayable>0) {
            zakatAmount.setText( String.format("%.2f", zakatPayable) + " Taka");
        } else {
            zakatAmount.setText("You don't need to pay Zakat");
        }

        //Calculate again button
        calculateAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculationResult.this, CalculateZakat.class);
                startActivity(intent);
                finish();
            }
        });

        //onBackPressedDispatcher for exit confirmation
        getDispatcher(dispatcher);
    }

    //onBackPressedDispatcher for exit confirmation

    public void getDispatcher(OnBackPressedDispatcher dispatcher) {
        this.dispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(CalculationResult.this)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Confirm Exit")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                ActivityCompat.finishAffinity(CalculationResult.this);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}
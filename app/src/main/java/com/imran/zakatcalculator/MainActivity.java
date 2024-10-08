package com.imran.zakatcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    LinearLayout blog1, blog2, blog3;
    Button goldRate, silverRate, calculateZakat, faqButton;

    public OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ZakatCalculator);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        blog1 = findViewById(R.id.blog1);
        blog2 = findViewById(R.id.blog2);
        blog3 = findViewById(R.id.blog3);
        goldRate = findViewById(R.id.goldRate);
        silverRate = findViewById(R.id.silverRate);
        calculateZakat = findViewById(R.id.calculateZakat);
        faqButton = findViewById(R.id.faqButton);

        blog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebLoader.class);
                intent.putExtra("url", "https://www.muslimaid.org/media-centre/blog/zakat-the-third-pillar-of-islam/");
                startActivity(intent);
            }
        });

        blog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebLoader.class);
                intent.putExtra("url", "https://charitymeals.org/news/what-are-the-zakat-rules-and-who-is-eligible-to-pay-it/");
                startActivity(intent);
            }
        });

        blog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebLoader.class);
                intent.putExtra("url", "https://www.riamoneytransfer.com/en/blog/zakat-vs-sadaqah-ramadan-islam/");
                startActivity(intent);
            }
        });

        goldRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebLoader.class);
                intent.putExtra("url", "https://www.alaminjewellers.com/gold-price/");
                startActivity(intent);
            }
        });

        silverRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebLoader.class);
                intent.putExtra("url", "https://www.livepriceofgold.com/silver-price/bangladesh.html");
                startActivity(intent);
            }
        });

        calculateZakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculateZakat.class);
                startActivity(intent);
            }
        });

        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Faq.class);
                startActivity(intent);
            }
        });

        //onBackPressedDispatcher for exit confirmation
        setDispatcher(dispatcher);

    }

    //onBackPressedDispatcher for exit confirmation

    public void setDispatcher(OnBackPressedDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        dispatcher.addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Confirm Exit")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finishAndRemoveTask();
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
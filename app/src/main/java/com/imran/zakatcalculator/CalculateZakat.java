package com.imran.zakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculateZakat extends AppCompatActivity {

    //Declare variables
    TextView continueToClick, continueToClick2, continueToClick3;
    RadioGroup nisabStandardGroup;
    LinearLayout standardSelection, goldSilverPrice, assetInfo, liabilityInfo;
    EditText goldPriceInput, silverPriceInput, gold18kInput, gold21kInput, gold22kInput, goldTraditionalInput, silver18kInput, silver21kInput, silver22kInput, silverTraditionalInput, cashInput, businessInput, moneyOwedInput, otherAssetsInput, goodsInput, debtInput, expenseInput, otherExpenseInput;
    Button checkGoldSilverPrice, calculateButton;
    boolean isGoldSelected = false;
    boolean isSilverSelected = false;
    boolean isNisabRequirementMet;
    float goldPrice, silverPrice, goldQuantity18k, goldQuantity21k, goldQuantity22k, goldQuantityTraditional, silverQuantity18k, silverQuantity21k, silverQuantity22k, silverQuantityTraditional;
    public static float zakatPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculate_zakat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calculateLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign variables
        continueToClick = findViewById(R.id.continueToCalc);
        continueToClick2 = findViewById(R.id.continueToCalc2);
        continueToClick3 = findViewById(R.id.continueToCalc3);

        nisabStandardGroup = findViewById(R.id.nisab_standard_group);

        standardSelection = findViewById(R.id.standardSelection);
        goldSilverPrice = findViewById(R.id.gold_silver_price);
        assetInfo = findViewById(R.id.assetInfo);
        liabilityInfo = findViewById(R.id.liabilityInfo);

        goldPriceInput = findViewById(R.id.gold_price_input);
        silverPriceInput = findViewById(R.id.sliver_price_input);
        gold18kInput = findViewById(R.id.gold_18k_input);
        gold21kInput = findViewById(R.id.gold_21k_input);
        gold22kInput = findViewById(R.id.gold_22k_input);
        goldTraditionalInput = findViewById(R.id.gold_traditional_input);
        silver18kInput = findViewById(R.id.silver_18K_input);
        silver21kInput = findViewById(R.id.silver_21k_input);
        silver22kInput = findViewById(R.id.silver_22K_input);
        silverTraditionalInput = findViewById(R.id.silver_traditional_input);
        cashInput = findViewById(R.id.cash_input);
        businessInput = findViewById(R.id.business_input);
        moneyOwedInput = findViewById(R.id.money_owed_input);
        otherAssetsInput = findViewById(R.id.other_assets_input);
        goodsInput = findViewById(R.id.goods_input);
        debtInput = findViewById(R.id.debts_input);
        expenseInput = findViewById(R.id.expense_input);
        otherExpenseInput = findViewById(R.id.other_expense_input);

        checkGoldSilverPrice = findViewById(R.id.gs_price_check);
        calculateButton = findViewById(R.id.calculate_button);


        //--------------------------------------------------------------------------//
        // continue button functionality
        //--------------------------------------------------------------------------//
        continueToClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected RadioButton
                int selectedId = nisabStandardGroup.getCheckedRadioButtonId();

                // If a RadioButton is selected
                if (selectedId != -1) {
                    // Find the RadioButton by its ID
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    // Get the text of the selected RadioButton
                    String selectedOption = selectedRadioButton.getText().toString();

                    // Make Visible next section and gone current section
                    standardSelection.setVisibility(View.GONE);
                    goldSilverPrice.setVisibility(View.VISIBLE);

                    // Store the selected option in a variable
                    if (selectedOption.equals("Gold")) {
                        isGoldSelected = true;
                    } else if (selectedOption.equals("Silver")) {
                        isSilverSelected = true;
                    }

                } else {
                    // Show a message if no RadioButton is selected
                    Toast.makeText(CalculateZakat.this, "Please Select a Standard!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        continueToClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input values
                String goldPriceStr = goldPriceInput.getText().toString();
                String silverPriceStr = silverPriceInput.getText().toString();
                // Check if the input values are valid
                if (!goldPriceStr.isEmpty() && !silverPriceStr.isEmpty()) {
                    // Convert the input values to float
                    goldPrice = Float.parseFloat(goldPriceStr);
                    silverPrice = Float.parseFloat(silverPriceStr);
                    //Make visible next section and gone current section
                    goldSilverPrice.setVisibility(View.GONE);
                    assetInfo.setVisibility(View.VISIBLE);
                } else {
                    // Show error
                    if (goldPriceStr.isEmpty() && silverPriceStr.isEmpty()) {
                        goldPriceInput.setError("Please enter Gold Price");
                        silverPriceInput.setError("Please enter Silver Price");
                    } else if (silverPriceStr.isEmpty()) {
                        silverPriceInput.setError("Please enter Silver Price");
                    } else {
                        goldPriceInput.setError("Please enter Gold Price");
                    }
                }
            }
        });
        continueToClick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make visible next section and gone current section
                liabilityInfo.setVisibility(View.VISIBLE);
                assetInfo.setVisibility(View.GONE);
            }
        });
        //------------------------------------------------------------------------------------------------//
        // Gold Silver Price Check button functionality
        //------------------------------------------------------------------------------------------------//
        checkGoldSilverPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculateZakat.this, WebLoader.class);
                intent.putExtra("url", "https://www.bajus.org/gold-price");
                startActivity(intent);
            }
        });
        //-----------------------------------------------------------------------------------------------//
        // Calculate button functionality
        //-----------------------------------------------------------------------------------------------//
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // User's gold Input
                String sGold18k = gold18kInput.getText().toString();
                String sGold21k = gold21kInput.getText().toString();
                String sGold22k = gold22kInput.getText().toString();
                String sGoldTraditional = goldTraditionalInput.getText().toString();
                // User's silver Input
                String sSilver18k = silver18kInput.getText().toString();
                String sSilver21k = silver21kInput.getText().toString();
                String sSilver22k = silver22kInput.getText().toString();
                String sSilverTraditional = silverTraditionalInput.getText().toString();
                //User's other assets Input
                String sCash = cashInput.getText().toString();
                String sBusiness = businessInput.getText().toString();
                String sMoneyOwed = moneyOwedInput.getText().toString();
                String sOtherAssets = otherAssetsInput.getText().toString();
                String sGoods = goodsInput.getText().toString();
                //User's liabilities Input
                String sDebt = debtInput.getText().toString();
                String sExpense = expenseInput.getText().toString();
                String sOtherExpense = otherExpenseInput.getText().toString();

                // Helper function to parse input or return 0 if empty
                goldQuantity18k = parseInput(sGold18k);
                goldQuantity21k = parseInput(sGold21k);
                goldQuantity22k = parseInput(sGold22k);
                goldQuantityTraditional = parseInput(sGoldTraditional);
                silverQuantity18k = parseInput(sSilver18k);
                silverQuantity21k = parseInput(sSilver21k);
                silverQuantity22k = parseInput(sSilver22k);
                silverQuantityTraditional = parseInput(sSilverTraditional);
                float cash = parseInput(sCash);
                float business = parseInput(sBusiness);
                float moneyOwed = parseInput(sMoneyOwed);
                float otherAssets = parseInput(sOtherAssets);
                float goods = parseInput(sGoods);
                float debt = parseInput(sDebt);
                float expense = parseInput(sExpense);
                float otherExpense = parseInput(sOtherExpense);

                // Calculate the total gold and silver value
                float totalGoldValue = calculateTotalValue(goldPrice, goldQuantity18k, goldQuantity21k, goldQuantity22k, goldQuantityTraditional);
                float totalSilverValue = calculateTotalValue(silverPrice, silverQuantity18k, silverQuantity21k, silverQuantity22k, silverQuantityTraditional);

                //calculate total asset value
                float totalAssetValue = cash + business + moneyOwed + otherAssets + goods + totalGoldValue + totalSilverValue;

                //calculate total liability value
                float totalLiabilityValue = debt + expense + otherExpense;

                //calculate total zakat value
                float netWealth = totalAssetValue - totalLiabilityValue;

                //calculate Nisab Threshold
                float[] nisabThreshold = calculateNisab(goldPrice, silverPrice);
                float goldNisabThreshold = nisabThreshold[0];
                float silverNisabThreshold = nisabThreshold[1];

                //Check Nisab requirement
                if (isGoldSelected) {
                    if (netWealth > goldNisabThreshold) {
                        isNisabRequirementMet = true;
                    } else if (netWealth < goldNisabThreshold) {
                        isNisabRequirementMet = false;
                    }
                } else if (isSilverSelected) {
                    if (netWealth > silverNisabThreshold) {
                        isNisabRequirementMet = true;
                    } else if (netWealth < silverNisabThreshold) {
                        isNisabRequirementMet = false;
                    }
                }

                //Calculate Zakat Payable
                if (isNisabRequirementMet) {
                    zakatPayable = netWealth * 0.02f;
                } else {
                    zakatPayable = 0.00f;
                }

                Intent myIntent = new Intent(CalculateZakat.this, CalculationResult.class);
                startActivity(myIntent);
            }
        });

        //--------------------------------------------------------------------------------------//
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent home = new Intent(CalculateZakat.this, MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                finish();
            }
        });

    }

    // Helper function to parse input or return 0 if empty
    private float parseInput(String input) {
        if (input.isEmpty()) {
            return 0;
        } else {
            return Float.parseFloat(input);
        }
    }

    // Function to calculate total gold/silver value based on karat quantities and price
    private float calculateTotalValue(float price, float quantity18k, float quantity21k, float quantity22k, float quantityTraditional) {
        // Pure price (24k) calculation, assuming the given price is for 22K
        float purePrice = price / 0.9167f; // For 22K

        // Calculate values for different karats
        float value22k = quantity22k * 11.66667f * price; // Assuming price is 22K price
        float value18k = quantity18k * 11.66667f * ((purePrice * 18) / 24);
        float value21k = quantity21k * 11.66667f * ((purePrice * 21) / 24);
        float valueTraditional = (float) (quantityTraditional * 11.66667f * ((purePrice * 14.75) / 24)); // Use the provided traditional karat value

        // Return the total value
        return value22k + value18k + value21k + valueTraditional;
    }

    // Function to calculate Nisab threshold in pure gold and pure silver
    public float[] calculateNisab(float goldPrice22k, float silverPrice22k) {
        // Convert 22K gold price to 24K (pure) gold price
        float pureGoldPrice = goldPrice22k / (22.0f / 24.0f);

        // Convert 22K silver price to 24K (pure) silver price
        float pureSilverPrice = silverPrice22k / (22.0f / 24.0f);

        // Nisab threshold in grams
        final float nisabGoldInGrams = 85.0f;  // 85 grams of pure gold
        final float nisabSilverInGrams = 595.0f;  // 595 grams of pure silver

        // Calculate Nisab thresholds for pure gold and silver
        float nisabGold = nisabGoldInGrams * pureGoldPrice;  // Nisab in gold
        float nisabSilver = nisabSilverInGrams * pureSilverPrice;  // Nisab in silver

        // Return both Nisab thresholds
        return new float[]{nisabGold, nisabSilver};
    }
}
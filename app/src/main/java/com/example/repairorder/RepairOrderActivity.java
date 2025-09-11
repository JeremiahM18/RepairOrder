package com.example.repairorder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class RepairOrderActivity extends AppCompatActivity {

    private static final double TAX_RATE = 0.08;

    private TextView totalTextView;
    private TextView subtotalTextView;
    private TextView taxTextView;
    //EditText orderEditText;
    private EditText paintEditText;
    private EditText laborEditText;
    private EditText partsEditText;
    private EditText inspectorEditText;
    private EditText technicianEditText;

    private Button submitButton;
    private Spinner orderSpinner;
    private String selectedOrderType = null;   // Spinner state
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repair_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views
        totalTextView = findViewById(R.id.total_price);
        subtotalTextView = findViewById(R.id.subtotalPrice);
        taxTextView = findViewById(R.id.taxPrice);

        paintEditText = findViewById(R.id.paintInput);
        laborEditText = findViewById(R.id.laborInput);
        partsEditText = findViewById(R.id.partsInput);
        inspectorEditText = findViewById(R.id.inspectorInput);
        technicianEditText = findViewById(R.id.technicianInput);

        orderSpinner = findViewById(R.id.orderTypeSpinner);
        submitButton = findViewById(R.id.submit);

        // Spinner adapter from string resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.order_type_categories,           // strings.xml
                android.R.layout.simple_spinner_item);   // selected row layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);

        /*
        Populate With ArrayList instead

        ArrayList<String> orderTypes = new ArrayList<>();
        orderTypes.add("Select order type:");
        orderTypes.add("Diagnostics");
        orderTypes.add("Repairs");
        orderTypes.add("Installation");
        orderTypes.add("Maintenance");
        orderTypes.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);
         */


        // Listen for selections
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOrderType = null;
            }
        });

        // Submit button
        submitButton.setOnClickListener(v -> {
            if (selectedOrderType == null) {
                Toast.makeText(this, "Please select an order type", Toast.LENGTH_SHORT).show();
                orderSpinner.requestFocus();
                return;
            }
            updateTotals();
        });

        // Initialize totals
        setMoney(subtotalTextView, 0);
        setMoney(taxTextView, 0);
        setMoney(totalTextView, 0);

    }

    // Parse a money field safely
    private double getMoney(EditText editText) {
        String value = editText.getText() == null ? "" : editText.getText().toString().trim();
        if (value.isEmpty()) {
            return 0.0;
        }
        try {
            value = value.replaceAll("[,$]","").replace("$","");
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            editText.setError("Enter a valid number");
            return 0.0;
        }
    }

    // Format and set currency: $0.00
    private void setMoney(TextView textView, double value) {
        textView.setText(currency.format(value));
    }

    // Read inputs, compute totals
    private void updateTotals() {
        double paint = getMoney(paintEditText);
        double labor = getMoney(laborEditText);
        double parts = getMoney(partsEditText);

        double subtotal = paint + labor + parts;
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        setMoney(subtotalTextView, subtotal);
        setMoney(taxTextView, tax);
        setMoney(totalTextView, total);
    }
}
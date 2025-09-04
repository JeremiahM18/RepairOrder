package com.example.repairorder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class RepairOrderActivity extends AppCompatActivity {

    TextView totalTextView;
    TextView subtotalTextView;
    TextView taxTextView;
    EditText orderEditText;
    EditText paintEditText;
    EditText laborEditText;
    EditText partsEditText;
    EditText inspectorEditText;
    Button submitButton;

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Random generator = new Random();

            double number = generator.nextDouble();
            String v1 = "$ " + number;
            //subtotalTextView.setText(v1);

            String orderTypeV = orderEditText.getText().toString();
            String laborV = laborEditText.getText().toString();
            String partsV = partsEditText.getText().toString();

            Double ot = Double.parseDouble(orderTypeV);
            Double l = Double.parseDouble(laborV);
            Double p = Double.parseDouble(partsV);

            double sum = ot + l + p;
            String v2 = "$ " + sum;
            subtotalTextView.setText(v2);

            double tax = sum * 0.08;
            String v4 = "$ " + tax;
            taxTextView.setText(v4);

            String v3 = "$ " + (tax + sum);
            totalTextView.setText(v3);


            Log.i("TEST", "Button Clicked");
        }
    };

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



        totalTextView = findViewById(R.id.total_price);
        subtotalTextView = findViewById(R.id.subtotalPrice);
        taxTextView = findViewById(R.id.taxPrice);
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(buttonListener); //Registering the listener to the button
        orderEditText = findViewById(R.id.orderInput);
        paintEditText = findViewById(R.id.technicianInput);
        laborEditText = findViewById(R.id.laborInput);
        partsEditText = findViewById(R.id.partsInput);
        inspectorEditText = findViewById(R.id.paintInput);


    }
}
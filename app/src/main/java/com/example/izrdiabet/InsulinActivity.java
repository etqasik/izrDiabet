package com.example.izrdiabet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class InsulinActivity extends AppCompatActivity {

    private TextInputEditText etXE, etUK, etCurrentGlucose, etTargetGlucose, etSensitivityFactor;
    private TextView tvResult;
    private ImageView ivCalculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insulin);

        etXE = findViewById(R.id.editTextbe);
        etUK = findViewById(R.id.editText3);
        etCurrentGlucose = findViewById(R.id.textInputEditText);
        etTargetGlucose = findViewById(R.id.editText2);
        etSensitivityFactor = findViewById(R.id.editText4);
        tvResult = findViewById(R.id.textView7);
        ivCalculate = findViewById(R.id.imageView9);

        ivCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInsulinDose();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });



    }
    private void calculateInsulinDose() {
        // Получаем значения из полей ввода
        double xe = Double.parseDouble(etXE.getText().toString());
        double uk = Double.parseDouble(etUK.getText().toString());
        double currentGlucose = Double.parseDouble(etCurrentGlucose.getText().toString());
        double targetGlucose = Double.parseDouble(etTargetGlucose.getText().toString());
        double sensitivityFactor = Double.parseDouble(etSensitivityFactor.getText().toString());

        // Расчет дозы инсулина
        double doseForFood = xe * uk; // Доза на еду
        double correctionDose = (currentGlucose - targetGlucose) / sensitivityFactor; // Коррекционная доза
        double totalDose = doseForFood + correctionDose; // Общая доза

        // Вывод результата
        tvResult.setText(String.format("Доза инсулина: %.2f ЕД", totalDose));
        }
}




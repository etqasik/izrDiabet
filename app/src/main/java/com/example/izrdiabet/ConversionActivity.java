package com.example.izrdiabet;

import android.content.Intent;
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

import java.text.DecimalFormat;

public class ConversionActivity extends AppCompatActivity {

    private TextInputEditText esHE, ves, etUK;
    private TextView tvResult, tvResult2;
    private ImageView ivCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversion);

        esHE = findViewById(R.id.editTextt);
        etUK = findViewById(R.id.editText5);
        ves = findViewById(R.id.editText);
        tvResult = findViewById(R.id.textView11);
        tvResult2 = findViewById(R.id.textView12);
        ivCalculate = findViewById(R.id.imageView12);

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
        double xe = Double.parseDouble(esHE.getText().toString());
        double uk = Double.parseDouble(etUK.getText().toString());
        double vess = Double.parseDouble(ves.getText().toString());


        // Расчет дозы инсулина

        double heInFood = xe / 12;
        double doseForFood = heInFood  * uk; // Доза на еду

        // Форматирование
        DecimalFormat df = new DecimalFormat("#.###");
        String foramttedDoseForFood = df.format(doseForFood);
        String foramttedHeInFood = df.format(heInFood);

        // Вывод результата
        tvResult.setText(String.format(foramttedHeInFood + " ХЕ в " + vess + " граммах"));
        tvResult2.setText(String.format(foramttedDoseForFood + " ЕД"));
    }

    public void openMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
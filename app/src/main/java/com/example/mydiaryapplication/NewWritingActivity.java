package com.example.mydiaryapplication;
import java.time.LocalDate;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NewWritingActivity extends AppCompatActivity {
    ImageButton btnback;
    ImageButton btnSend;
    EditText editText;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_writing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.new_writing), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt = findViewById(R.id.view3);
        LocalDate today = LocalDate.now();
        txt.setText("Date: " + today.toString());
        editText = findViewById(R.id.editText);
        btnback = findViewById(R.id.buttonBack3);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewWritingActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        btnSend = findViewById(R.id.imageButtonSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                String date = today.format(formatter);
                Writing writing = new Writing(editText.getText().toString(),today,-1 );
                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewWritingActivity.this);
                List<Writing> writings = dataBaseHelper.getAllWritings();
                List<String> dates = new ArrayList<>();
                for (Writing w: writings) {
                    dates.add(w.date.toString());
                }
                if(dates.contains(today.toString())){
                    Toast.makeText(NewWritingActivity.this, "You already wrote for today!", Toast.LENGTH_SHORT).show();
                }
                else{
                    dataBaseHelper.add(writing);
                    Intent intent = new Intent(NewWritingActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(NewWritingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
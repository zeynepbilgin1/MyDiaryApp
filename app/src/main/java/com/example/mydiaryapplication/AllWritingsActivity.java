package com.example.mydiaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AllWritingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton btnback;
    AllWritingsRecyclerViewAdapter adapter;
    List<Writing> writings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_writings);

        recyclerView = findViewById(R.id.recyclerView);
        btnback = findViewById(R.id.buttonBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllWritingsActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        writings = dataBaseHelper.getAllWritings();
//        writings.add(new Writing("Başlık 1", LocalDate.of(2025, 6, 2),1));

        adapter = new AllWritingsRecyclerViewAdapter(this, writings, this, new AllWritingsRecyclerViewAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(Writing writing) {
                boolean success = dataBaseHelper.deleteWriting(writing.id);
                if (success) {
                    writings.clear();
                    writings.addAll(dataBaseHelper.getAllWritings());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AllWritingsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AllWritingsActivity.this, "Didn't delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);

    }
}
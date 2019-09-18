package com.android.pixabaysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    ImageView ivSearch;
    ProgressBar topProgressBar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();

    }

    public void initializeView(){
        etSearch = findViewById(R.id.etSearchbar);
        ivSearch = findViewById(R.id.ivSearch);
        topProgressBar = findViewById(R.id.topProgressBar);
        recyclerView = findViewById(R.id.recyclerView);
    }
}

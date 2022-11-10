package uz.gita.puzzle;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);

        findViewById(R.id.back_button).setOnClickListener(v -> {
            onBackPressed();
        });
    }
}
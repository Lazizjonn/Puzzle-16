package uz.gita.puzzle;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button start_game = findViewById(R.id.start_game);
        Button statistics = findViewById(R.id.statistics);
        Button about_game = findViewById(R.id.about_game);
        Button exit = findViewById(R.id.exit);

        start_game.setOnClickListener(v -> {
            MainActivity.intentMainActivity(this, "Going to from start activity");
        });

        // Done
        statistics.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });

        // Done
        about_game.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, AboutGameActivity.class);
            startActivity(intent);
        });

        // Done
        findViewById(R.id.exit).setOnClickListener(v -> {
            // dialog
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Do you want to exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        System.exit(0);
                    })
                    .setNegativeButton("No", (d, position) -> {
                    })
                    .create();
            dialog.show();
        });
    }

    public static void intentStartActivity(Activity activity, String message) {
        Intent intent = new Intent(activity, StartActivity.class);
        intent.putExtra("StartActivity", message);
        activity.startActivity(intent);
    }
}

package uz.gita.puzzle;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.local.SharePref;

public class MainActivity extends AppCompatActivity {
    private TextView[][] cell = new TextView[4][4];
    private ArrayList<Integer> numbers = new ArrayList<>(15);
    private ConstraintLayout parent;
    private int x = 3;
    private int y = 3;
    private int count = 0;
    private AppCompatTextView textOfCounter;
    Chronometer timer;
    SharePref pref;
    MediaPlayer mp;
    long extraTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPreference
        pref = SharePref.getPref(this);
        AppCompatImageView sound_button = findViewById(R.id.mute_button);

        // Mediaplayer
        mp = MediaPlayer.create(this, R.raw.bethoowen);

        if (pref.isPlaying()) {
            mp.start();
            sound_button.setImageResource(R.drawable.volume);
        } else {
            sound_button.setImageResource(R.drawable.unvolume);
        }

        //Constraintdan obyekt olish
        parent = new ConstraintLayout(this);
        parent = findViewById(R.id.table);

        //Countni 0 qilib o'rnatish
        textOfCounter = findViewById(R.id.count_text);
        textOfCounter.setText("" + count);

        // Chronometrni topib olish
        timer = findViewById(R.id.timer);

        generateNumbers();
        startGame();
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        findViewById(R.id.restart_button).setOnClickListener(view -> {
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i).getVisibility() == View.INVISIBLE) {
                    parent.getChildAt(i).setVisibility(View.VISIBLE);
                    parent.getChildAt(i).setClickable(true);
                }
            }
            timer.setBase(SystemClock.elapsedRealtime());
            count = 0;
            textOfCounter.setText("" + count);
            generateSolvableNumbers();
            loadDataToViews();
        });

        findViewById(R.id.home_button).setOnClickListener(view -> onBackPressed());

        sound_button.setOnClickListener(view ->

        {
            boolean bool = pref.isPlaying();
            if (bool) {
                pref.savePlaying(!bool);
                mp.pause();
                sound_button.setImageResource(R.drawable.unvolume);
            } else {
                pref.savePlaying(!bool);
                mp.start();
                sound_button.setImageResource(R.drawable.volume);
            }
        });

    } // On Create last //

    @Override
    protected void onPause() {
        mp.pause();
        extraTime = timer.getBase() - SystemClock.elapsedRealtime();
        timer.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.setBase(SystemClock.elapsedRealtime() + extraTime);
        timer.start();
        boolean bool = pref.isPlaying();
        if (bool) {
            mp.start();
        }
    }

    @Override
    protected void onDestroy() {
        mp.stop();
        mp = null;
        super.onDestroy();
    }

    private void generateNumbers() {
        numbers.clear();
        for (int i = 0; i <= 15; i++) {
            numbers.add(i + 1);
        }
    }

    private void startGame() {
        generateSolvableNumbers();
        loadViews();
        Log.d("LOG", numbers.toString());
        loadDataToViews();

    }

    private void generateSolvableNumbers() {
        do {
            shuffle();
        }
        while (!isSolvable(numbers));
    }

    private void shuffle() {
        Collections.shuffle(numbers);
    }

    private boolean isSolvable(List<Integer> list) {
        int counter = 0;
        for (int i = 0; i < 16; i++) {
            // if metodi bo'sh katakni ustunini aniqlaydi, shunda (0;0) kordinatada bo'sh button bo'lsa counter bir qo'shadi;
            if (list.get(i) == 16) {
                counter += i / 4 + 1;
                continue;
            }
            for (int j = i + 1; j < 16; j++) {
                if (list.get(i) > list.get(j)) {
                    counter++;
                }
            }
        }
        Log.d("CCC", counter + "");
        return counter % 2 == 0;
    }

    private void loadViews() {
        for (int i = 0; i < parent.getChildCount(); i++) {
            cell[i / 4][i % 4] = (TextView) parent.getChildAt(i);
            cell[i / 4][i % 4].setTag(i);
            cell[i / 4][i % 4].setOnClickListener(view -> {
                int amount = (Integer) view.getTag();
                move(amount / 4, amount % 4);
            });
        }
    }

    private void move(int i, int j) {
        if ((Math.abs(x - i) == 1 && Math.abs(y - j) == 0) || (Math.abs(x - i) == 0 && Math.abs(y - j) == 1)) {
            cell[x][y].setText(cell[i][j].getText());
            cell[x][y].setVisibility(View.VISIBLE);
            cell[i][j].setVisibility(View.INVISIBLE);
            cell[x][y].setClickable(true);
            cell[i][j].setClickable(false);
            x = i;
            y = j;
            count++;
            check();
            textOfCounter.setText("" + count);

        }
    }

    private void loadDataToViews() {
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (numbers.get(i) == 16) {
                x = i / 4;
                y = i % 4;
                cell[x][y].setClickable(false);
                cell[x][y].setText("");
                parent.getChildAt(i).setVisibility(View.INVISIBLE);
            } else cell[i / 4][i % 4].setText(String.valueOf(numbers.get(i)));
        }
    }

    private void check() {
        boolean winner = true;
        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            TextView text = (TextView) parent.getChildAt(i);
            if (!text.getText().equals((i + 1 + ""))) {
                winner = false;
                i = parent.getChildCount();
            }

        }
        goToWinnerActivity(winner);
    }

    private void goToWinnerActivity(boolean gameWin) {
        if (gameWin) {
            //time
            timer.stop();
            int time = (int) (SystemClock.elapsedRealtime() - timer.getBase()) / 1000;
            Log.d("EEE", time + "");
            // count
            int winnerCounts = count;
            count = 0;

            pref.putInt(winnerCounts);
            Intent intent = new Intent(MainActivity.this, WinnerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("WinnerCount", winnerCounts);
            bundle.putInt("WinnerTime", time);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    public static void intentMainActivity(Activity activity, String message) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("MainActivity", message);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Do you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    super.onBackPressed();
                })
                .setNegativeButton("No", (d, position) -> {
                })
                .create();
        dialog.show();
    }
}



package uz.gita.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SharePref {
    Context context;
    private SharedPreferences pref;

    private SharePref(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("Sample", Context.MODE_PRIVATE);
    }

    private static SharePref sharePref;

    public static SharePref getPref(Context context) {
        if (sharePref == null) {
            sharePref = new SharePref(context);
        }
        return sharePref;
    }

    public void putString(String value) {
        pref.edit().putString("string", value).apply();
    }

    public void putInt(int value) {
        pref.edit().putInt("int", value).apply();
    }

    public void putLong(long value) {
        pref.edit().putLong("long", value).apply();
    }

    public String getString(String defaultValue) {
        return pref.getString("string", defaultValue);
    }

    public int getInt(int defaultValue) {
        return pref.getInt("int", defaultValue);
    }

    public long getLong(long defaultValue) {
        return pref.getLong("long", defaultValue);
    }

    public void saveResults(int count) {
        List<Integer> list = new ArrayList<>();
        list.add(count);
        list.add(getFirst());
        list.add(getSecond());
        list.add(getThird());
        list.add(getFourth());
        list.add(getFifth());
        Collections.sort(list);
        /*if (list.get(5)!=null) */
        list.remove(5);
        saveFirst(list.get(0));
        saveSecond(list.get(1));
        saveThird(list.get(2));
        saveFourth(list.get(3));
        saveFifth(list.get(4));
    }

    public int getFirst() {
        return pref.getInt("First", Integer.MAX_VALUE);
    }

    public int getSecond() {
        return pref.getInt("Second", Integer.MAX_VALUE);
    }

    public int getThird() {
        return pref.getInt("Third", Integer.MAX_VALUE);
    }

    public int getFourth() {
        return pref.getInt("Fourth", Integer.MAX_VALUE);
    }

    public int getFifth() {
        return pref.getInt("Fifth", Integer.MAX_VALUE);
    }

    private void saveFirst(int count) {
        pref.edit().putInt("First", count).apply();
    }

    private void saveSecond(int count) {
        pref.edit().putInt("Second", count).apply();
    }

    private void saveThird(int count) {
        pref.edit().putInt("Third", count).apply();
    }

    private void saveFourth(int count) {
        pref.edit().putInt("Fourth", count).apply();
    }

    private void saveFifth(int count) {
        pref.edit().putInt("Fifth", count).apply();
    }

    public void savePlaying(boolean bool) {
        pref.edit().putBoolean("music", bool).apply();
    }

    public boolean isPlaying() {
        return pref.getBoolean("music", true);
    }

}

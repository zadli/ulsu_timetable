package ru.zadli.ulsu_collaborating.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import ru.zadli.ulsu_collaborating.timetable.adapters.CoupleInfoRVAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.LaunchRVAdapter;

public class CoupleActivity extends AppCompatActivity {
    Intent get_data;
    String couple_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple);
        get_data = getIntent();
        couple_name = get_data.getStringExtra("couple_name");
        setTitle(couple_name);
        RecyclerView rv = findViewById(R.id.rv_grid_couple);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        String[] time = get_data.getStringArrayExtra("couple_time");
        CoupleInfoRVAdapter coupleInfoRVAdapter = new CoupleInfoRVAdapter(this, couple_name,  time[0].split(":", 2));
        rv.setAdapter(coupleInfoRVAdapter);
    }

    public void alarm_action(View view) {

    }

    public void hometask_action(View view) {
        Snackbar.make(view, "Hello Darkness my old friend", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
    }
}
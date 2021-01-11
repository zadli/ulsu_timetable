package ru.zadli.ulsu_collaborating.timetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.CoupleInfoRVAdapter;

public class CoupleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple);

        Intent get_data = getIntent();
        setTitle(get_data.getStringExtra("couple_name"));

        RecyclerView rv = findViewById(R.id.rv_grid_couple);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        String[] time = get_data.getStringArrayExtra("couple_time");

        CoupleInfoRVAdapter coupleInfoRVAdapter = new CoupleInfoRVAdapter(this,
                get_data.getStringExtra("couple_name"),
                Objects.requireNonNull(time)[0].split(":", 2));
        rv.setAdapter(coupleInfoRVAdapter);
    }
}
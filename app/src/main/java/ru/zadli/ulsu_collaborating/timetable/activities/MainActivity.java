package ru.zadli.ulsu_collaborating.timetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.PageAdapters.ContactsPageAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.PageAdapters.SettingsPageAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.PageAdapters.WeatherPageAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.PageAdapters.WeekPageAdapter;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {
    DataSnapshot days;
    DataSnapshot students_contacts;
    JSONArray audios;
    ViewPager pager;
    ProgressBar loading_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainTheme);
        setContentView(R.layout.activity_main);
        Intent get_position = getIntent();
        loading_bar = findViewById(R.id.loading_bar);
        pager = findViewById(R.id.pager);
        parse_data(get_position.getIntExtra("position", 0));
    }

    public void parse_data(final int position) {
        FirebaseDatabase timetable_db = FirebaseDatabase.getInstance();
        DatabaseReference timetable_dbReference = timetable_db.getReference();
        if (position == 0 || position == 1) {
            timetable_dbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    loading_bar.setVisibility(View.GONE);
                    if (position == 0) {
                        days = snapshot.child("moais").child("first_week");
                    } else {
                        days = snapshot.child("moais").child("second_week");
                    }
                    pager.setPageMargin(1000);
                    pager.setAdapter(new WeekPageAdapter(getSupportFragmentManager(), WeekPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
                    Calendar calendar = Calendar.getInstance();
                    String[] days_view = getResources().getStringArray(R.array.days_view);
                    for (int i = 1; i < 6; i++) {
                        int j = i + 1;
                        if (j == calendar.get(Calendar.DAY_OF_WEEK)) {
                            pager.setCurrentItem(i);
                            if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                setTitle("Сегодня " + days_view[i] + " - 1ая неделя");
                            } else {
                                setTitle("Сегодня " + days_view[i] + " - 2ая неделя");
                            }
                        } else if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK) || Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                            pager.setCurrentItem(0);
                            setTitle("Сегодня выходные");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                }
            });
        } else if (position == 3) {
            loading_bar.setVisibility(View.GONE);
            setTitle("Настройки");
            pager.setPageMargin(1000);
            pager.setAdapter(new SettingsPageAdapter(getSupportFragmentManager(), SettingsPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        } else if (position == 2) {
            loading_bar.setVisibility(View.GONE);
            setTitle("Погода");
            pager.setPageMargin(1000);
            pager.setAdapter(new WeatherPageAdapter(getSupportFragmentManager(), WeatherPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        } else if (position == 4) {
            timetable_dbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    loading_bar.setVisibility(View.GONE);
                    students_contacts = snapshot.child("moais").child("students");
                    setTitle("Контакты");
                    pager.setPageMargin(1000);
                    pager.setAdapter(new ContactsPageAdapter(getSupportFragmentManager(), ContactsPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public DataSnapshot get_days() {
        return days;
    }

    public DataSnapshot get_students_contacts() {
        return students_contacts;
    }

    public JSONArray get_audios() {
        return audios;
    }
}

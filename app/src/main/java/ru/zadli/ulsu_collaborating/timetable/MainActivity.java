package ru.zadli.ulsu_collaborating.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.adapters.SettingsPageAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.WeekPageAdapter;

public class MainActivity extends AppCompatActivity {
    DataSnapshot days;
    ViewPager pager;
    TextView loading_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainTheme);
        setContentView(R.layout.activity_main);
        Intent get_position = getIntent();
        loading_text = findViewById(R.id.loading_text);
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
                    loading_text.setVisibility(View.GONE);
                    Calendar calendar = Calendar.getInstance();
                    if (position == 0) {
                        days = snapshot.child("moais").child("first_week");
                    } else {
                        days = snapshot.child("moais").child("second_week");
                    }
                    pager.setPageMargin(1000);
                    pager.setAdapter(new WeekPageAdapter(getSupportFragmentManager(), WeekPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
                    if (Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(1);
                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            setTitle("Сегодня " + getResources().getString(R.string.day_monday) + " - 1ая неделя");
                        } else {
                            setTitle("Сегодня " + getResources().getString(R.string.day_monday) + " - 2ая неделя");
                        }
                    } else if (Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(2);
                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            setTitle("Сегодня " + getResources().getString(R.string.day_tuesday) + " - 1ая неделя");
                        } else {
                            setTitle("Сегодня " + getResources().getString(R.string.day_tuesday) + " - 2ая неделя");
                        }
                    } else if (Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(3);
                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            setTitle("Сегодня " + getResources().getString(R.string.day_wednesday) + " - 1ая неделя");
                        } else {
                            setTitle("Сегодня " + getResources().getString(R.string.day_wednesday) + " - 2ая неделя");
                        }
                    } else if (Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(4);
                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            setTitle("Сегодня " + getResources().getString(R.string.day_thursday) + " - 1ая неделя");
                        } else {
                            setTitle("Сегодня " + getResources().getString(R.string.day_thursday) + " - 2ая неделя");
                        }
                    } else if (Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(5);
                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            setTitle("Сегодня " + getResources().getString(R.string.day_friday) + " - 1ая неделя");
                        } else {
                            setTitle("Сегодня " + getResources().getString(R.string.day_friday) + " - 2ая неделя");
                        }
                    } else if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK) || Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                        pager.setCurrentItem(0);
                        setTitle("Сегодня " + "выходные, иди бухать");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                }
            });
        } else if (position == 3) {
            loading_text.setVisibility(View.GONE);
            setTitle("Настройки");
            pager.setPageMargin(1000);
            pager.setAdapter(new SettingsPageAdapter(getSupportFragmentManager(), SettingsPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        } else if (position == 2) {
            loading_text.setVisibility(View.GONE);
            //Nothing here for now
        }
    }

    public DataSnapshot get_days() {
        return days;
    }
}

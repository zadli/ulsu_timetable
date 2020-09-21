package ru.zadli.ulsu_collaborating.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import ru.zadli.ulsu_collaborating.timetable.adapters.SettingsPageAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.WeekPageAdapter;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity{
    JSONArray first_week;
    JSONArray second_week;
    ViewPager pager;
    JSONArray[] data;
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
    public void parse_data(final int position){
        if (position == 0 || position ==1) {
            Volley.newRequestQueue(this)
                    .add(new JsonObjectRequest(GET,
                            "https://zadli.ru/timetable/study.json",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    ArrayList<String> week_days = new ArrayList<>();
                                    week_days.add("monday");
                                    week_days.add("tuesday");
                                    week_days.add("wednesday");
                                    week_days.add("thursday");
                                    week_days.add("friday");
                                    loading_text.setVisibility(View.GONE);
                                    Calendar calendar = Calendar.getInstance();
                                    if (position == 0) {
                                        try {
                                            first_week = response.getJSONArray("first_week");
                                            JSONObject temp_1 = first_week.getJSONObject(0);
                                            data = new JSONArray[temp_1.length()];
                                            for (int i = 0; i < temp_1.length(); i++) {
                                                data[i] = temp_1.getJSONArray(week_days.get(i));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            second_week = response.getJSONArray("second_week");
                                            JSONObject temp_0 = second_week.getJSONObject(0);
                                            data = new JSONArray[temp_0.length()];
                                            for (int i = 0; i < temp_0.length(); i++) {
                                                data[i] = temp_0.getJSONArray(week_days.get(i));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    pager.setPageMargin(1000);
                                    pager.setAdapter(new WeekPageAdapter(getSupportFragmentManager(), WeekPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
                                    if (Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(0);
                                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_monday) + " - 1ая неделя");
                                        } else {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_monday) + " - 2ая неделя");
                                        }
                                    } else if (Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(1);
                                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_tuesday) + " - 1ая неделя");
                                        } else {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_tuesday) + " - 2ая неделя");
                                        }
                                    } else if (Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(2);
                                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_wednesday) + " - 1ая неделя");
                                        } else {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_wednesday) + " - 2ая неделя");
                                        }
                                    } else if (Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(3);
                                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_thursday) + " - 1ая неделя");
                                        } else {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_thursday) + " - 2ая неделя");
                                        }
                                    } else if (Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(4);
                                        if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_friday) + " - 1ая неделя");
                                        } else {
                                            setTitle("Сегодня " + getResources().getString(R.string.day_friday) + " - 2ая неделя");
                                        }
                                    } else if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK) || Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                                        pager.setCurrentItem(5);
                                        setTitle("Сегодня " + "выходные, иди бухать");
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                        }
                    }
                    ));
        }else if (position == 3) {
            loading_text.setVisibility(View.GONE);
            setTitle("Настройки");
            pager.setPageMargin(1000);
            pager.setAdapter(new SettingsPageAdapter(getSupportFragmentManager(), SettingsPageAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        } else if (position == 2) {
            loading_text.setVisibility(View.GONE);
            //Nothing here for now
        }
    }
    public JSONArray[] get_data() {
        return data;
    }
}

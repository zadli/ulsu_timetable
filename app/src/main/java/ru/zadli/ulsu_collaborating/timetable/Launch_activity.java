package ru.zadli.ulsu_collaborating.timetable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.adapters.ImageGridAdapter;

import static com.android.volley.Request.Method.GET;

public class Launch_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        final SharedPreferences preferences = getSharedPreferences("Student", Context.MODE_PRIVATE);
        RecyclerView rv = findViewById(R.id.rv_grid);
        TextView hello_text = findViewById(R.id.hello_text);
        TextView student_name = findViewById(R.id.student_name);
        ImageView day_image = findViewById(R.id.day_image);
        TextView week_now = findViewById(R.id.week_now);
        final TextView weather_max = findViewById(R.id.temperature_max);
        Calendar calendar = Calendar.getInstance();
        if(!preferences.getBoolean("Logged", false)){
            final AlertDialog.Builder sign_in_dialog = new AlertDialog.Builder(this);
            final View dialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, null);
            sign_in_dialog.setView(dialogView);
            sign_in_dialog.setPositiveButton("Запомнить", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String name = String.valueOf(((EditText) dialogView.findViewById(R.id.name)).getText());
                    SharedPreferences.Editor ed = preferences.edit();
                    if(!Objects.equals(preferences.getString("Name", ""), name)){
                        ed.putBoolean("Logged", true);
                        ed.putString("Name", name);
                        ed.apply();
                    }
                    recreate();

                }
            });
            sign_in_dialog.show();
        }else{
        student_name.setText(preferences.getString("Name", "МОАИС"));
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) > 6 && calendar.get(Calendar.HOUR_OF_DAY) < 20){
            day_image.setImageResource(R.drawable.solnyshko);
            hello_text.setText("Доброе утро,");
        }else{
            day_image.setImageResource(R.drawable.luna);
            hello_text.setText("Доброй ночи,");
        }
        if(calendar.get(Calendar.WEEK_OF_YEAR)%2 == 0){
            week_now.setText("Сейчас 1ая неделя");
        }else{
            week_now.setText("Сейчас 2ая неделя");

        }
        Volley.newRequestQueue(this)
                .add(new JsonObjectRequest(GET,
                        "https://api.openweathermap.org/data/2.5/onecall?lat=54.316667&lon=48.366667&units=metric&appid=7460a393d941c2dcc3666c20ca581223",
                        null,
                        new Response.Listener<JSONObject>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray main = response.getJSONArray("daily");
                                    JSONObject current = response.getJSONObject("current");
                                    JSONObject daily = main.getJSONObject(0);
                                    JSONObject temp = daily.getJSONObject("temp");
                                    weather_max.setText("");
                                    weather_max.setText("На улцие: \n Минимальная: "+ temp.getString("min")+"°C\n Максимальная: "+temp.getString("max") + "°C" + "\n Сейчас: " + current.getString("temp")+ "°C");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                ));
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sglm);

        List<String> imageList = new ArrayList<>();
        imageList.add("https://zadli.ru/img/1.png");
        imageList.add("https://zadli.ru/img/2.png");
        imageList.add("https://zadli.ru/img/3.png");
        imageList.add("https://zadli.ru/img/4.png");

        ImageGridAdapter iga = new ImageGridAdapter(this, imageList);
        rv.setAdapter(iga);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
}
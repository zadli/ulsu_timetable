package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.WeatherRVAdapter;

import static com.android.volley.Request.Method.GET;

public class WeatherFragment extends Fragment {

    public WeatherFragment() {

    }

    public static WeatherFragment newInstance(final int page) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.weather_layout, container, false);
        final RecyclerView weather_rv = result.findViewById(R.id.rv_weather);
        getActivity().setTitle("Погода");
        Volley.newRequestQueue(getActivity()) //Парсим погоду
                .add(new JsonObjectRequest(GET,
                                "https://api.openweathermap.org/data/2.5/onecall?lat=54.316667&lon=48.366667&units=metric&appid=7460a393d941c2dcc3666c20ca581223",
                                null,
                                new Response.Listener<JSONObject>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONArray hourly = response.getJSONArray("hourly");
                                            JSONObject[] hour = new JSONObject[48];
                                            for (int i = 0; i<48;i++){
                                                hour[i] = hourly.getJSONObject(i);
                                            }
                                            weather_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            WeatherRVAdapter adapter = new WeatherRVAdapter(getActivity(), hour);
                                            weather_rv.setAdapter(adapter);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        })
                );
        return result;
    }
    private static class Rectangle extends View {

        public Rectangle(Context context) {
            super(context);
        }
        @Override
        public void onDraw(Canvas canvas) {

        }
    }
}


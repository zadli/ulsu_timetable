package ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import ru.zadli.ulsu_collaborating.timetable.R;

import static com.android.volley.Request.Method.GET;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {

    Context context;
    JSONObject[] hour;

    public WeatherRVAdapter(Context context, JSONObject[] hour) {
        this.context = context;
        this.hour = hour;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_rv_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            Format formatter;
            java.util.Date time = new java.util.Date(Long.parseLong(hour[position].getString("dt"))*1000);
            formatter = new SimpleDateFormat("yyyy-MM-dd,hh:mm", Locale.US);

            holder.time.setText(String.valueOf(formatter.format(time)));

            holder.temp.setText(hour[position].getString("temp"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return hour.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView temp;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.weather_time);
            temp = itemView.findViewById(R.id.weather_temp);
        }
    }
}

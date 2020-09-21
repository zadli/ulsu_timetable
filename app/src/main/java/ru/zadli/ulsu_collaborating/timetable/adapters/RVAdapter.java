package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.zadli.ulsu_collaborating.timetable.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    Context context;
    JSONArray[] couple;
    int size;
    public RVAdapter(final Context context, JSONArray[] couple, int size) {
        this.context = context;
        this.couple = couple;
        this.size = size;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        runEnterAnimation(holder.itemView);

        try {
            JSONObject couple_data = couple[position].getJSONObject(0);
            holder.name.setText(couple_data.getString("name"));
            holder.time.setText(couple_data.getString("time"));
            holder.room.setText(couple_data.getString("room"));
            holder.number.setText(couple_data.getString("id"));
            if (couple_data.getString("group").equals("all")){
                holder.group.setVisibility(View.GONE);
            }else{
                holder.group.setText("Гр." + couple_data.getString("group"));
            }
            holder.prepod.setText(couple_data.getString("prepod"));
            holder.type.setText(couple_data.getString("type"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void runEnterAnimation(View view) {
        view.setTranslationY((float) Resources.getSystem().getDisplayMetrics().heightPixels);
        view.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(3.0f)).setDuration(700).start();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView number;
        TextView time;
        TextView name;
        TextView room;
        TextView prepod;
        TextView group;
        TextView type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number1);
            time = itemView.findViewById(R.id.time1);
            name = itemView.findViewById(R.id.name1);
            room = itemView.findViewById(R.id.room1);
            prepod = itemView.findViewById(R.id.prepod);
            group = itemView.findViewById(R.id.group);
            type = itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Snackbar.make(view, "Hello There, What You Want?", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}

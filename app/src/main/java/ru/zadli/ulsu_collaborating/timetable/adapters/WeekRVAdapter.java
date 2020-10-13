package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.CoupleActivity;
import ru.zadli.ulsu_collaborating.timetable.R;

public class WeekRVAdapter extends RecyclerView.Adapter<WeekRVAdapter.ViewHolder> {
    Context context;
    DataSnapshot day;
    int size;

    public WeekRVAdapter(final Context context, DataSnapshot day, long size) {
        this.context = context;
        this.day = day;
        this.size = (int) size;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.couple_rv_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView);
        position++;

        DataSnapshot couple_data = day.child(String.valueOf(position));
        if (String.valueOf(couple_data.child("double").getValue()).equals("true")) {
            holder.name_2.setText(Objects.requireNonNull(couple_data.child("name_2").getValue()).toString());
            holder.time_2.setText(Objects.requireNonNull(couple_data.child("time_2").getValue()).toString());

            if (Objects.requireNonNull(couple_data.child("group_2").getValue()).toString().equals("all")) {
                holder.group_2.setVisibility(View.GONE);
            } else {
                holder.group_2.setText("Гр." + Objects.requireNonNull(couple_data.child("group_2").getValue()).toString());
            }

            if (Objects.requireNonNull(couple_data.child("remote_2").getValue()).toString().equals("true")) {
                holder.room_2.setTextColor(Color.parseColor("#2D8CFF"));
                holder.room_2.setText("[Д]");
                holder.number.setTextColor(Color.parseColor("#2D8CFF"));
            } else {
                holder.room_2.setText(Objects.requireNonNull(couple_data.child("room_2").getValue()).toString());
            }

            holder.prepod_2.setText(Objects.requireNonNull(couple_data.child("prepod_2").getValue()).toString());
            holder.type_2.setText(Objects.requireNonNull(couple_data.child("type_2").getValue()).toString());
        } else {
            holder.group_2.setVisibility(View.GONE);
            holder.time_2.setVisibility(View.GONE);
            holder.prepod_2.setVisibility(View.GONE);
            holder.name_2.setVisibility(View.GONE);
            holder.type_2.setVisibility(View.GONE);
            holder.room_2.setVisibility(View.GONE);
        }

        holder.name.setText(Objects.requireNonNull(couple_data.child("name").getValue()).toString());
        holder.time.setText(Objects.requireNonNull(couple_data.child("time").getValue()).toString());
        holder.number.setText(Objects.requireNonNull(couple_data.child("id").getValue()).toString());

        if (Objects.requireNonNull(couple_data.child("group").getValue()).toString().equals("all")) {
            holder.group.setVisibility(View.GONE);
        } else {
            holder.group.setText("Гр." + Objects.requireNonNull(couple_data.child("group").getValue()).toString());
        }

        if (Objects.requireNonNull(couple_data.child("remote").getValue()).toString().equals("true")) {
            holder.room.setTextColor(Color.parseColor("#2D8CFF"));
            holder.room.setText("[Д]");
            holder.number.setTextColor(Color.parseColor("#2D8CFF"));
        } else {
            holder.room.setText(Objects.requireNonNull(couple_data.child("room").getValue()).toString());
        }

        holder.prepod.setText(Objects.requireNonNull(couple_data.child("prepod").getValue()).toString());
        holder.type.setText(Objects.requireNonNull(couple_data.child("type").getValue()).toString());
    }

    private void runEnterAnimation(View view) {
        view.setTranslationY((float) Resources.getSystem().getDisplayMetrics().heightPixels);
        view.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(3.0f)).setDuration(700).start();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView time;
        TextView name;
        TextView room;
        TextView prepod;
        TextView group;
        TextView type;
        TextView time_2;
        TextView name_2;
        TextView room_2;
        TextView prepod_2;
        TextView group_2;
        TextView type_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number1);
            time = itemView.findViewById(R.id.time1);
            name = itemView.findViewById(R.id.name1);
            room = itemView.findViewById(R.id.room1);
            prepod = itemView.findViewById(R.id.prepod);
            group = itemView.findViewById(R.id.group);
            type = itemView.findViewById(R.id.type);
            time_2 = itemView.findViewById(R.id.time2);
            name_2 = itemView.findViewById(R.id.name2);
            room_2 = itemView.findViewById(R.id.room2);
            prepod_2 = itemView.findViewById(R.id.prepod2);
            group_2 = itemView.findViewById(R.id.group2);
            type_2 = itemView.findViewById(R.id.type2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    pos++;
                    Intent i = new Intent(context, CoupleActivity.class);
                    DataSnapshot couple_data = day.child(String.valueOf(pos));
                    i.putExtra("couple_name", Objects.requireNonNull(couple_data.child("name").getValue()).toString());
                    String[] time = Objects.requireNonNull(couple_data.child("time").getValue()).toString().split("-");
                    i.putExtra("couple_time", time);
                    context.startActivity(i);
                }
            });
        }
    }
}

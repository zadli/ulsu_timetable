package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.zadli.ulsu_collaborating.timetable.CoupleActivity;
import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;

public class CoupleInfoRVAdapter  extends RecyclerView.Adapter<CoupleInfoRVAdapter.ViewHolder> {
    int[] myImageList = new int[]{R.drawable.set_alarm, R.drawable.home_tasks, R.drawable.alarm_properties, R.drawable.settings,R.drawable.first_week, R.drawable.second_week, R.drawable.events, R.drawable.settings,R.drawable.first_week, R.drawable.second_week, R.drawable.events, R.drawable.settings};
    Context context;
    String couple_name;
    String[] couple_time;

    public CoupleInfoRVAdapter(Context context, String couple_name, String[] couple_time ) {
        this.context = context;
        this.couple_name = couple_name;
        this.couple_time = couple_time;
    }

    @NonNull
    @Override
    public CoupleInfoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoupleInfoRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.couple_grid_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CoupleInfoRVAdapter.ViewHolder holder, int position) {
        holder.picture.setImageResource(myImageList[position]);
    }

    @Override
    public int getItemCount() {
        return myImageList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.rv_grid_couple_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 0){
                        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, couple_name);
                        i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(couple_time[0]));
                        i.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(couple_time[1].replaceFirst(".$", "")));
                        context.startActivity(i);

                    }
                }
            });
        }
    }
}

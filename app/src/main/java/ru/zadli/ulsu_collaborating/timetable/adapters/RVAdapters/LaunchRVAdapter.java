package ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.zadli.ulsu_collaborating.timetable.activities.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;

public class LaunchRVAdapter extends RecyclerView.Adapter<LaunchRVAdapter.ViewHolder> {
    int[] myImageList = new int[]{R.drawable.first_week, R.drawable.second_week, R.drawable.weather, R.drawable.settings, R.drawable.studens_phone_numbers};
    Context context;
    int anim = 0;

    public LaunchRVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaunchRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.launch_rv_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        coolAnimation1(holder.itemView);
        coolAnimation2(holder.itemView);
        holder.picture.setImageResource(myImageList[position]);
    }
    private void coolAnimation1(View viewToAnimate) {
        if (anim == 0) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            anim = 1;
        } else if(anim == 1) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            viewToAnimate.startAnimation(animation);
            anim = 0;
        }
    }
    private void coolAnimation2(View view) {
        view.setTranslationY((float) Resources.getSystem().getDisplayMetrics().heightPixels);
        view.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(4.0f)).setDuration(700).start();
    }

    @Override
    public int getItemCount() {
        return myImageList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.rv_grid_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent start_main = new Intent(context, MainActivity.class);
                    start_main.putExtra("position", getAdapterPosition());
                    context.startActivity(start_main);
                }
            });
        }
    }

}

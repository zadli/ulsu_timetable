package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.activities.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.WeekRVAdapter;

public class WeekFragment extends Fragment {

    private int pageNumber;

    public WeekFragment() {
    }

    public static WeekFragment newInstance(final int page) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        DataSnapshot days = Objects.requireNonNull(activity).get_days();
        View result = inflater.inflate(R.layout.week_layout, container, false);
        RecyclerView couple = result.findViewById(R.id.rv_week);
        TextView day_text = result.findViewById(R.id.week_text);
        ImageView weekend = result.findViewById(R.id.weekend);
        couple.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] days_array = getResources().getStringArray(R.array.days);
        String[] days_view_array = getResources().getStringArray(R.array.days_view);
        for (int i = 0; i < 6; i++){
            if (pageNumber == i){
                if(pageNumber == 0){
                    day_text.setText(days_view_array[i]);
                    weekend.setVisibility(View.VISIBLE);
                }else{
                    WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child(days_array[i]), days.child(days_array[i]).getChildrenCount());
                    day_text.setText(days_view_array[i]);
                    couple.setAdapter(adapter);
                }
            }
        }
        return result;
    }
}
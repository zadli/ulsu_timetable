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

import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.WeekRVAdapter;

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
        final View result = inflater.inflate(R.layout.page_layout, container, false);
        final RecyclerView couple = result.findViewById(R.id.rv_monday);
        final TextView day_text = result.findViewById(R.id.monday);
        ImageView weekend = result.findViewById(R.id.weekend);
        couple.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (pageNumber == 1) {
            WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child("monday"), days.child("monday").getChildrenCount());
            day_text.setText(getResources().getString(R.string.day_monday));
            couple.setAdapter(adapter);
        } else if (pageNumber == 2) {
            WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child("tuesday"), days.child("tuesday").getChildrenCount());
            day_text.setText(getResources().getString(R.string.day_tuesday));
            couple.setAdapter(adapter);
        } else if (pageNumber == 3) {
            WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child("wednesday"), days.child("wednesday").getChildrenCount());
            day_text.setText(getResources().getString(R.string.day_wednesday));
            couple.setAdapter(adapter);
        } else if (pageNumber == 4) {
            WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child("thursday"), days.child("thursday").getChildrenCount());
            day_text.setText(getResources().getString(R.string.day_thursday));
            couple.setAdapter(adapter);
        } else if (pageNumber == 5) {
            WeekRVAdapter adapter = new WeekRVAdapter(getActivity(), days.child("friday"), days.child("friday").getChildrenCount());
            day_text.setText(getResources().getString(R.string.day_friday));
            couple.setAdapter(adapter);
        } else if (pageNumber == 0) {
            day_text.setText("Ну и нахера ты сюда зашел(шла), иди бухай, будь студентом");
            weekend.setVisibility(View.VISIBLE);
        }
        return result;
    }
}
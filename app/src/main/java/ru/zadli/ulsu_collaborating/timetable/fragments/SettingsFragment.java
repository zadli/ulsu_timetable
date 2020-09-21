package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import java.util.Objects;
import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.SettingsRVAdapter;

public class SettingsFragment extends Fragment {

    private int pageNumber;

    public static SettingsFragment newInstance(final int page) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result=inflater.inflate(R.layout.settings_layout, container, false);
        final RecyclerView settings_rv = result.findViewById(R.id.rv_settings);
        settings_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (pageNumber == 0){
            SettingsRVAdapter adapter = new SettingsRVAdapter(
                    getActivity());
            settings_rv.setAdapter(adapter);
        }
        return result;
    }
}
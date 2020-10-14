package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.SettingsRVAdapter;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(final int page) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.settings_layout, container, false);
        final RecyclerView settings_rv = result.findViewById(R.id.rv_settings);
        settings_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        SettingsRVAdapter adapter = new SettingsRVAdapter(getActivity());
        settings_rv.setAdapter(adapter);

        return result;
    }
}
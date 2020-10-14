package ru.zadli.ulsu_collaborating.timetable.adapters.PageAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.zadli.ulsu_collaborating.timetable.fragments.ContactsFragment;
import ru.zadli.ulsu_collaborating.timetable.fragments.WeatherFragment;

public class WeatherPageAdapter extends FragmentPagerAdapter {

    public WeatherPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return (WeatherFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return 1;
    }
}

package id.co.myproject.madefinal.adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import id.co.myproject.madefinal.view.FavoriteMovieFragment;
import id.co.myproject.madefinal.view.FavoriteTvFragment;

public class FavoriteViewPager extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public FavoriteViewPager(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new FavoriteMovieFragment());
        fragments.add(new FavoriteTvFragment());

        titles = new ArrayList<>();
        titles.add("Movie");
        titles.add("Tv Show");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

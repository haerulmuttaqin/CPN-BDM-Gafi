package co.id.cpn.bdmgafi.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerCustAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList();
    private final List<String> title = new ArrayList();

    public ViewPagerCustAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    public int getCount() {
        return this.fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        this.fragmentList.add(fragment);
        this.title.add(title);
    }

    public CharSequence getPageTitle(int position) {
        return this.title.get(position);
    }
}

package mx.ait.crro.kuwindasearchapp.adapter;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.fragment.CategoriesFragment;
import mx.ait.crro.kuwindasearchapp.fragment.CreateCategoryFragment;
import mx.ait.crro.kuwindasearchapp.fragment.SearchFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainMenuFragmentAdapter extends FragmentPagerAdapter {

	public static final int NUM_ITEMS = 3;
	private ArrayList<Fragment> _fragments;
	
	public MainMenuFragmentAdapter(FragmentManager fm) {
		super(fm);
		_fragments = new ArrayList<Fragment>();
		_fragments.add(new CreateCategoryFragment());
		_fragments.add(new SearchFragment());
		_fragments.add(new CategoriesFragment());
	}


	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return _fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _fragments.size();
	}
	
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return KuwindaConstants.CREATCAT_FRAGMENT_TAG;
		case 1:
			return KuwindaConstants.SEARCH_FRAGMENT_TAG;
		case 2:
			return KuwindaConstants.CATEGORIES_FRAGMENT_TAG;
		default:
			return "unknown";
		}
	}

}

package mx.ait.crro.kuwindasearchapp.fragment;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.WebsitesActivity;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateCategoryFragment extends Fragment {

	private EditText _etTitle, _etKeyword;
	private Button _chooseBtn, _doneBtn;
	private CategoriesLoader _catLoader;
	private ArrayList<WebsiteModel> _checkedWebsites;
	private LocalBroadcastManager _lbm;
	private TextView _numWebsitesSelected;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = View.inflate(getActivity(), R.layout.create_category_fragment, null);
		
		_catLoader = KuwindaApplication.getCategoriesLoader();
		
		_etTitle = (EditText) v.findViewById(R.id.etTitle);
		_etKeyword = (EditText) v.findViewById(R.id.etKeyword);
		
		
		_chooseBtn = (Button) v.findViewById(R.id.chooseBtn);
		_numWebsitesSelected = (TextView) v.findViewById(R.id.numWebsitesSelected);
		
		//Restoring the previous state
		if (savedInstanceState != null) {
			_checkedWebsites = savedInstanceState.getParcelableArrayList(KuwindaConstants.GET_WEBSITES);
			if (_checkedWebsites == null) {
				_checkedWebsites = new ArrayList<WebsiteModel>();
			} else {
				_numWebsitesSelected.setVisibility(View.VISIBLE);
				int selected = 0;
				for (WebsiteModel wm : _checkedWebsites) {
					if (wm.getChecked()) {
						selected++;
					}
				}
				if (selected == 1) {
					_numWebsitesSelected.setText(selected + " website selected.");
				} else {
					_numWebsitesSelected.setText(selected + " websites selected.");
				}
			}
		} else {
			_checkedWebsites = new ArrayList<WebsiteModel>();
		}

		_chooseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getActivity(), WebsitesActivity.class);
				startActivityForResult(i, KuwindaConstants.REQUEST_CODE_WEBSITE);
			}
		});
		_doneBtn = (Button) v.findViewById(R.id.doneBtn);
		
		_doneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (_etTitle.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "Please enter a title", Toast.LENGTH_LONG).show();
					return;
				}
				//Check if no websites are selected
				if (_checkedWebsites.size() == 0) {
					Toast.makeText(getActivity(), "Please select at least one website", Toast.LENGTH_LONG).show();
					return;
				}
				int size = _checkedWebsites.size();
				String[] web = new String[size];
				for (int i = 0; i < size; i++) {
					if (_checkedWebsites.get(i).getChecked()) {
						web[i] = _checkedWebsites.get(i).getName();
					}
				}
				Category category = new Category(_etTitle.getText().toString(), web, _etKeyword.getText().toString());
				_catLoader.createCategory(category);
				
				//We send a message to the database to update
				Intent updateIntent = new Intent();
				updateIntent.setAction(KuwindaConstants.ACTION_CATEGORIES_CHANGED);
				_lbm.sendBroadcast(updateIntent);
				
				Toast.makeText(getActivity(), "New category created", Toast.LENGTH_SHORT).show();
				_numWebsitesSelected.setVisibility(View.GONE);
				_etTitle.setText("");
				_checkedWebsites = new ArrayList<WebsiteModel>();
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case KuwindaConstants.REQUEST_CODE_WEBSITE:
				if (resultCode == KuwindaConstants.RESULT_WEBSITES_SELECTED) {
					_checkedWebsites = data.getParcelableArrayListExtra(KuwindaConstants.GET_WEBSITES);
					
					_numWebsitesSelected.setVisibility(View.VISIBLE);
					int selected = 0;
					for (WebsiteModel wm : _checkedWebsites) {
						if (wm.getChecked()) {
							selected++;
						}
					}
					if (selected == 1) {
						_numWebsitesSelected.setText(selected + " website selected.");
					} else {
						_numWebsitesSelected.setText(selected + " websites selected.");
					}
					
				}
				break;
	
			default:
				break;
			}
	}
	
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList(KuwindaConstants.GET_WEBSITES, _checkedWebsites);
		
	}
}

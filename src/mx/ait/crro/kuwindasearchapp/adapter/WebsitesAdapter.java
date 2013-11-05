package mx.ait.crro.kuwindasearchapp.adapter;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import mx.ait.crro.kuwindasearchapp.datastorage.DbConstants;
import mx.ait.crro.kuwindasearchapp.datastorage.WebsiteLoader;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class WebsitesAdapter extends CursorAdapter {
	//TODO: Implement the holder pattern for this adapter
	/*
	 * This class is a great example, after a very long time, of how to correctly implement a list with a checkbox.
	 * It has to keep track of the actual state of the chBox based on the position of the cursor stored in an array.
	 * Then to actually have the elements there was a correspondance with an array lsit that contained the list of elements and
	 * mofified their inner isChecked variable. The problem with not keeping track of the elements, instead of saving them on the 
	 * go is that they get added and removes as the list element recycle. Having the elements and changing the boolean value ensures
	 * the preservation of the checked or unchecked state.
	 */
	
	ArrayList<WebsiteModel> _checkedWebsites; 
	WebsiteLoader _wbLoader;
	boolean[] _checkedPositions;
	
	
	public WebsitesAdapter(Context context, Cursor c) {
		super(context, c, false);
		_checkedWebsites = new ArrayList<WebsiteModel>();
		_wbLoader = KuwindaApplication.getWebsiteLoader();
		
		//we need an array with all the elements and then we loop thourhg them and return the ones that are checked
		if (c.moveToFirst()) {
			do{
				_checkedWebsites.add(WebsiteLoader.getWebsiteModelByCursor(c));
			} while (c.moveToNext());
		}
		_checkedPositions = new boolean[(int) _wbLoader.size()];
	}

	//Load data to UI elements
	public void bindView(View view, Context context, Cursor cursor) {
		//find reference
		CheckBox chBox =  (CheckBox) view.findViewById(R.id.checkBox);
		
		int posInArray = this.getIndexInArrayList(WebsiteLoader.getWebsiteModelByCursor(cursor));
		WebsiteModel wbModel = _checkedWebsites.get(posInArray);
		
		int position = cursor.getPosition();
		wbModel.setPosition(position);
		
		TextView wbName = (TextView) view.findViewById(R.id.websiteName);
		wbName.setText(wbModel.getName());
		
		//We first set the change listener for the new checkbox
		chBox.setTag(wbModel);
		chBox.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		
		//Then we check to see if this was supposed to be checked already
		if (_checkedPositions[position]) {
			chBox.setChecked(true);
		} else {
			chBox.setChecked(false);
		}
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		View row = inflater.inflate(R.layout.websitesrow, null);
		bindView(row, context, cursor);
		return row;
	}
	
	public ArrayList<WebsiteModel> getCheckedWebsites() {
		return _checkedWebsites;
	}
	
	private class MyOnCheckedChangeListener implements OnCheckedChangeListener {
		private int _pos;

		public MyOnCheckedChangeListener() {
			super();
//			_pos = position;
		}
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int posInArray = getIndexInArrayList((WebsiteModel) buttonView.getTag());
			WebsiteModel wm = _checkedWebsites.get(posInArray);
			if (isChecked) {
				wm.setChecked(true);
				_checkedPositions[wm.getPosition()] = true;
			} else {
				//We remove the element from the list and this will always happen after you checked it in the first place
				wm.setChecked(false);
				_checkedPositions[wm.getPosition()] = false;
			}
		}
		
	}
	
	public int getIndexInArrayList(WebsiteModel wm) {
		int size = _checkedWebsites.size();
		for (int i = 0; i < size; i++) {
			if (wm.getExecutionCode() == _checkedWebsites.get(i).getExecutionCode()) {
				return i;
			} 
		}
		return -1;
	}
	public boolean areEqual(WebsiteModel w1, WebsiteModel w2) {
		if (w1.getExecutionCode() == w2.getExecutionCode()) {
			return true;
		} else {
			return false;
		}
	}

}

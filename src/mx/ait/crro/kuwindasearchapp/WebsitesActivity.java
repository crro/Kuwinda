package mx.ait.crro.kuwindasearchapp;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.adapter.WebsitesAdapter;
import mx.ait.crro.kuwindasearchapp.application.KuwindaApplication;
import mx.ait.crro.kuwindasearchapp.data.websites.WebsiteModel;
import mx.ait.crro.kuwindasearchapp.datastorage.WebsiteLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class WebsitesActivity extends Activity {
	private WebsitesAdapter _wa;
	private WebsiteLoader _wbLoader;
	private ListView _lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.websites);
		Button doneBtn = (Button) findViewById(R.id.btnDoneWebsite);
		_wbLoader = KuwindaApplication.getWebsiteLoader();
		_lv = (ListView) findViewById(R.id.websitesList);
		_wa = new WebsitesAdapter(this, _wbLoader.fetchAll());
		_lv.setAdapter(_wa);
		
		_lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				System.out.println("We are clicking");
				CheckBox cBox = (CheckBox) view.findViewById(R.id.checkBox);
				cBox.toggle();
			}
		});
		
		doneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<WebsiteModel> wmArray = _wa.getCheckedWebsites();
				
				Intent resultIntent = new Intent();
				resultIntent.putParcelableArrayListExtra(KuwindaConstants.GET_WEBSITES, wmArray);
				setResult(KuwindaConstants.RESULT_WEBSITES_SELECTED, resultIntent);
				try {
					finish();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}	
	
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		// Close the cursor if there is any assigned to the adapter
		if (_wa != null && _wa.getCursor() != null) {
			_wa.getCursor().close();
		}
	}

}

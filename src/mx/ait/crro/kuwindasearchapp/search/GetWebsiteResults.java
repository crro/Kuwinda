package mx.ait.crro.kuwindasearchapp.search;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.data.websites.Website;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class GetWebsiteResults extends AsyncTask<Website, Void, Website> {
	private ListView _lv;
	private Context _ctx;
	private LocalBroadcastManager _lbm;
	private Website _website;
	
	public GetWebsiteResults(ListView lv, Context c) {
		_lv = lv;
		_ctx = c;
		_lbm = LocalBroadcastManager.getInstance(_ctx);
	}

	@Override
	protected Website doInBackground(Website... params) {
		_website = params[0];
		boolean success = false;
		while (!success) {
			success = _website.search();
			if (isCancelled()) {
				return null;
			}
			if (!success) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		return _website;
		
	}
	
	@Override
	protected void onPostExecute(Website result) {
		super.onPostExecute(result);
		Intent resultIntent = new Intent(KuwindaConstants.ACTION_WEBSITES_CHANGED);
		
		
		if (result == null) {
			return;
		} 
		LayoutInflater inf = LayoutInflater.from(_ctx);
		View v = inf.inflate(R.layout.results, null);
		
		TextView webName = (TextView) v.findViewById(R.id.tvWebsiteName);
		webName.setText(result.getName());
		result.setReady(true);
		//v.setTag(R.string.websites_tag, result);
		_lv.addFooterView(v);
		
		_lbm.sendBroadcast(resultIntent);
		
		System.out.println("The thread finished");
//		}
		
	}
	
}

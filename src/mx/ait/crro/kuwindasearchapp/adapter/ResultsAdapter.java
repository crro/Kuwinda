package mx.ait.crro.kuwindasearchapp.adapter;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.data.websites.Website;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultsAdapter extends BaseAdapter{
	private ArrayList<Website> _websites;
	private Context _context;
	
	public ResultsAdapter(ArrayList<Website> wb, Context c) {
		_websites = wb;
		_context = c;
	}
	
	@Override
	public int getCount() {
		return _websites.size();
	}

	@Override
	public Object getItem(int arg0) {
		return _websites.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public static class ViewHolder {
		TextView websiteTitle;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.results, null);
			
			holder.websiteTitle = (TextView) convertView.findViewById(R.id.tvWebsiteName);
			
			convertView.setTag(R.string.view_holder_tag, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.string.view_holder_tag);
		}
		Website wb = _websites.get(position);
		boolean ready = wb.isReady();
		
		convertView.setTag(R.string.websites_tag, wb);
		holder.websiteTitle.setText(_websites.get(position).getName());
		if (ready) {
			holder.websiteTitle.setTextColor(Color.BLACK);
		} else {
			holder.websiteTitle.setTextColor(Color.GRAY);
		}
		
		return convertView;
	}

	
}

package mx.ait.crro.kuwindasearchapp.adapter;

import java.util.ArrayList;

import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.search.Link;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LinksAdapter extends BaseAdapter {
	private ArrayList<Link> _links;
	private Context _context;
	
	public LinksAdapter(ArrayList<Link> l, Context ctx) {
		_links = l;
		_context = ctx;
	}
	
	@Override
	public int getCount() {
		return _links.size();
	}

	@Override
	public Object getItem(int position) {
		return _links.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static class ViewHolder {
		TextView linkTitle;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.linkrow, null);
			
			holder = new ViewHolder();
			holder.linkTitle = (TextView) convertView.findViewById(R.id.tvLinkTitle);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.linkTitle.setText(_links.get(position).getTitle());
		return convertView;
	}

}

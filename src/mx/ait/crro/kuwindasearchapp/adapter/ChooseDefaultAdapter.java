package mx.ait.crro.kuwindasearchapp.adapter;

import mx.ait.crro.kuwindasearchapp.R;
import mx.ait.crro.kuwindasearchapp.data.categories.Category;
import mx.ait.crro.kuwindasearchapp.datastorage.CategoriesLoader;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChooseDefaultAdapter extends CursorAdapter {

	public ChooseDefaultAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	public void bindView(View view,Context context, Cursor cursor) {
		TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
		Category cat = CategoriesLoader.getCategoryByCursor(cursor);
		categoryName.setText(cat.getName());
	}

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View row = inflater.inflate(R.layout.categoriesrow, null);
		bindView(row, context, cursor);
		return row;
	}

}

package mx.ait.crro.kuwindasearchapp.search;

import android.os.Parcel;
import android.os.Parcelable;

public class Link implements Parcelable{
	
	private String _title;
	private String _url;

	public Link(String title, String url) {
		setTitle(title);
		setUrl(url);
	}
	
	public Link(Parcel in) {
		_title = in.readString();
		_url = in.readString();
	}
	
	public static final Parcelable.Creator<Link> CREATOR = new Parcelable.Creator<Link>() {

		public Link createFromParcel(Parcel source) {
			return new Link(source);
		}

		public Link[] newArray(int size) {
			return new Link[size];
		}
	};

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(_title);
		dest.writeString(_url);
	}
}

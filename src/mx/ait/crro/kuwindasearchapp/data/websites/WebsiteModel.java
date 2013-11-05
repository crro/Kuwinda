package mx.ait.crro.kuwindasearchapp.data.websites;


import org.jsoup.nodes.Document;

import android.os.Parcel;
import android.os.Parcelable;

public class WebsiteModel implements Parcelable{
	private String _name;
	private int _executionCode;
	private String _url;
	private boolean _isChecked;
	private int _position;

	public WebsiteModel(String name, int exCode, String url) {
		_name = name;
		_executionCode = exCode;
		_url = url;
		_isChecked = false;
	}
	
	public WebsiteModel(Parcel in) {
		_name = in.readString();
		_executionCode = in.readInt();
		_url = in.readString();
		boolean[] boolR = new boolean[1];
		in.readBooleanArray(boolR);
		_isChecked = boolR[0];
	}
	
	public static final Parcelable.Creator<WebsiteModel> CREATOR = new Parcelable.Creator<WebsiteModel>() {
		public WebsiteModel createFromParcel(Parcel in) {
			return new WebsiteModel(in);
		}
		public WebsiteModel[] newArray(int size) {
			return new WebsiteModel[size];
		}
	};

	public boolean getChecked() {
		return _isChecked;
	}

	public void setChecked(boolean isChecked) {
		_isChecked = isChecked;
	}

	public String getURL() {
		return _url;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public int getExecutionCode() {
		return _executionCode;
	}

	public void setExecutionCode(int executionCode) {
		this._executionCode = executionCode;
	}

	public void setURL(String url) {
		this._url = url;
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(_name);
		dest.writeInt(_executionCode);
		dest.writeString(_url);
		boolean[] bool = new boolean[] {_isChecked};
		dest.writeBooleanArray(bool);
	}

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

}

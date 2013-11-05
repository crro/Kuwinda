package mx.ait.crro.kuwindasearchapp.fragment;

import mx.ait.crro.kuwindasearchapp.KuwindaConstants;
import mx.ait.crro.kuwindasearchapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {
	
	public static WebFragment newInstance(String url) {
		WebFragment result = new WebFragment();
		
		Bundle args = new Bundle();
		args.putString(KuwindaConstants.URL_BUNDLE_KEY, url);
		
		result.setArguments(args);
		
		return result;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.webview_fragment, null);
		
		final WebView webView = (WebView) v.findViewById(R.id.myWebView);
		
		//webView.getSettings().setBuiltInZoomControls(true);
		//webView.getSettings().setJavaScriptEnabled(true);//enables the javascript 
		
		Bundle args = getArguments();
		String url = args.getString(KuwindaConstants.URL_BUNDLE_KEY);
		
		webView.setWebViewClient(new WebViewClient(){
		    public boolean shouldOverrideUrlLoading(WebView view, String url){
		      webView.loadUrl(url);
		      //returning true means it does not go to the system. 
		      return true;
		    }
		    //onLoadResource you can catch the urls of the images and decide whether or not to display it on screen
		});
		
		webView.loadUrl(url);
		
		return v;
	}
}

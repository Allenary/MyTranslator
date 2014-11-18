package com.example.enrutrans;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class NegotiationTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {
		String url = "http://api.mymemory.translated.net/get?q=";
		String langFrom = "&langpair=en";
		String langTo = "it";
		String q = "cat";

		String result = "noResult";
		try {
			String safeUrl = URLEncoder.encode("|", "UTF-8");
			String finalUrl = url + q + langFrom + safeUrl + langTo;
			Log.d("FINAL_URL", finalUrl);
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient
					.execute(new HttpGet(finalUrl));

			HttpEntity ent = httpResponse.getEntity();
			String strJson = EntityUtils.toString(ent);
			JSONObject json = new JSONObject(strJson);
			Log.d("JSON", json.toString());
			String responseData = json.getString("responseData");
			JSONObject json2 = new JSONObject(responseData);
			String translatedText = json2.getString("translatedText");
			Log.d("TRANSLATION", translatedText);

		} catch (Exception e) {
			Log.d("InputStream", e.toString());
			result = "exception";
		}
		Log.d("FINAL RESULT", result);
		return null;
	}

}

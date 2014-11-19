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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	EditText searchResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchResult = (EditText) findViewById(R.id.searchResult);
		new NegotiationTask().execute("prophecy");
		// searchResult.setText(getTranslation("hello"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class NegotiationTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String url = "http://api.mymemory.translated.net/get?q=";
			String langFrom = "&langpair=en";
			String langTo = "ru";
			String q = params[0];

			String result = "noResult";
			try {
				String safeUrl = URLEncoder.encode("|", "UTF-8");
				String finalUrl = url + q + langFrom + safeUrl + langTo;
				Log.d("FINAL_URL", finalUrl);
				// create HttpClient
				HttpClient httpclient = new DefaultHttpClient();

				// make GET request to the given URL
				HttpResponse httpResponse = httpclient.execute(new HttpGet(
						finalUrl));

				HttpEntity ent = httpResponse.getEntity();
				String strJson = EntityUtils.toString(ent);
				JSONObject json = new JSONObject(strJson);

				Log.d("JSON", json.toString());
				String responseData = json.getString("responseData");
				JSONObject json2 = new JSONObject(responseData);
				String translatedText = json2.getString("translatedText");
				Log.d("TRANSLATION", translatedText);
				result = translatedText;

				// byte[] utf8 = translatedText.getBytes("UTF-8");
				//
				// // Convert from UTF-8 to Unicode
				// String translationResult = new String(utf8, "UTF-8");
				// Log.d("TRANSLATION FULL", translationResult);

			} catch (Exception e) {
				Log.d("InputStream", e.toString());
				result = "exception";
			}
			Log.d("FINAL RESULT", result);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			searchResult.setText(result);
		}
	}
}
package com.example.enrutrans;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	EditText searchWord;
	EditText searchResult;

	public void translate(View view) {
		String searchedWord = searchWord.getText().toString();
		new NegotiationTask().execute(searchedWord);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchResult = (EditText) findViewById(R.id.searchResult);
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

			String result = "";
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
				JSONArray responseData = json.getJSONArray("matches");
				for (int i = 0; i < responseData.length(); i++) {
					result += responseData.getJSONObject(i).getString(
							"translation")
							+ " ";
				}

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
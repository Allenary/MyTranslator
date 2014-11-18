package com.example.enrutrans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class NegotiationTask extends AsyncTask<Void,Void,Void >{

	@Override
	protected Void doInBackground(Void... params) {
		String url="http://api.mymemory.translated.net/get?q=";
    	String langFrom="&langpair=en";
    	String langTo="ru";
    	String q="cat";
    	
    	InputStream inputStream = null;
        String result = "noResult";
        try {
        	String safeUrl = URLEncoder.encode("|", "UTF-8");
        	String finalUrl=url+q+langFrom+safeUrl+langTo;
        	Log.d("FINAL_URL",finalUrl );
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(finalUrl));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = "result="+convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.toString());
            result="exception";
        }
       Log.d("FINAL RESULT", result);
	return null;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }

}

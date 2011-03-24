package com.lotharios.phototour;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class SearchRandomActivity extends Activity {
	
	//private static final int SELECT_IMAGE = 0;
	
	protected Bitmap bm; 
	protected String[] flickrUrlList;	
	
	enum size{
		_s,_t,_z,_m
	};
	
	Drawable image;
	
    /** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_gallery);
        Log.d(getString(R.string.debugTag), "line 52");
		((Gallery) findViewById(R.id.rgallery)).setAdapter(new ImageAdapter(this));
    }
    
    public void getMoreFlickrHandler(View v) {
    	((Gallery) findViewById(R.id.rgallery)).setAdapter(new ImageAdapter(this));
    }
              
	public class ImageAdapter extends BaseAdapter {
		/** The parent context */
		private Context myContext;
		
		/** Simple Constructor saving the 'parent' context. */
		public ImageAdapter(Context c) { 
			this.myContext = c; 
		};
						
		public int getCount() {			
			return 5;
		}
		
		
		/** Returns the size (0.0f to 1.0f) of the views
		* depending on the 'offset' to the center. 
		*/
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset)));		
		}

	    private void flickrApi(String searchPattern, int limit) throws IOException, JSONException {
	    	URL url = new URL(getString(R.string.apiBaseUri) + "flickr.photos.search&text=" + searchPattern + "&api_key=" + getString(R.string.apiKey) + "&per_page=" + limit + "&format=json");
	    	String imageUrl;
	    	Log.d(getString(R.string.debugTag), url.toString());
	    	URLConnection connection = url.openConnection();
	    	
	    	int cnt = 0;	    	
	    	connection.connect();
	    	    	
	    	String line;
	    	StringBuilder builder = new StringBuilder();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	
	    	
	    	while((line=reader.readLine()) != null) {
	    		builder.append(line);
	    	}	    	
	    	
	    	
	    	
	    	int start = builder.toString().indexOf("(") + 1;
	    	int end = builder.toString().length() -1;
	    	
	    	String jSONString = builder.toString().substring(start, end);
	    	
	    	
	    	JSONObject jSONObject = new JSONObject(jSONString);
	    	JSONObject jSONObjectInner = jSONObject.getJSONObject("photos");
	    	
	    	JSONArray photoArray = jSONObjectInner.getJSONArray("photo");
	    	
	    	cnt = photoArray.length();
	    	flickrUrlList = new String[cnt];
	    	for(int i = 0; i<cnt; i++) {
	    		JSONObject photo = photoArray.getJSONObject(i);
		    	imageUrl = constructFlickrImgUrl(photo, size._m);
		    	Log.d(getString(R.string.debugTag), imageUrl);
		    	flickrUrlList[i] = imageUrl;	    		
	    	}
	    	
	    }
	    
	    
	    private String constructFlickrImgUrl(JSONObject input, Enum size) throws JSONException {
	    	String FARMID = input.getString("farm");
	    	String SERVERID = input.getString("server");
	    	String SECRET = input.getString("secret");
	    	String ID = input.getString("id");
	    	
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("http://farm");
	    	sb.append(FARMID);
	    	sb.append(".static.flickr.com/");
	    	sb.append(SERVERID);
	    	sb.append("/");
	    	sb.append(ID);
	    	sb.append("_");
	    	sb.append(SECRET);
	    	sb.append(size.toString());
	    	sb.append(".jpg");
	    	
	    	return sb.toString();
	    }
	    
	    private String randomizer(int length) {
	    	char i[] = new char[length];
	    	for (int j=0; j<length; j++) {
	    		i[j] = (char) ((int)5*Math.random()+(int)'a');
	    	}
	    	
	    	return new String(i);
	    }

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/** Returns a new ImageView to 
		* be displayed, depending on 
		* the position passed. 
		*/
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(this.myContext);
			URL url;
			
			try {
				flickrApi(randomizer(3), 5);
				url = new URL(flickrUrlList[position]);
		    	bm = BitmapFactory.decodeStream(url.openStream());
				
				/* Apply the Bitmap to the ImageView that will be returned. */
				i.setImageBitmap(bm);
			} catch (IOException e) {				
				e.printStackTrace();
				Log.d(getString(R.string.debugTag), "line 176");
				Log.e(getString(R.string.debugTag), e.getMessage());
			} catch (JSONException je) {
				je.printStackTrace();
			}
				
			/* Image should be scaled as width/height are set. */
			i.setScaleType(ImageView.ScaleType.FIT_XY);
		
			/* Set the Width/Height of the ImageView. */
			i.setLayoutParams(new Gallery.LayoutParams(250, 250));
			return i;
		}

    } 
    
    
}
package com.example.k2;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
 
/**
 * 
 * @author Nishanth Prasad
 *
 */
public class MainActivity extends Activity {
 
    // url to make request
    private static String url = "http://www.kaverisoft.com/careers/assignments/android/a1.php";
 
    // JSON Node names
    public static final String TAG_CAMERA = "camera";
    public static final String TAG_picture = "picture";
    public static final String TAG_NAME = "model";
    public static final String TAG_cammake = "make";
    public static final String TAG_camprice = "price";
    
    public static final String TAG_music = "music";
    public static final String TAG_ARTIST = "artist";
    public static final String TAG_ALBUM = "album";
    public static final String TAG_GENRE = "genre";
    public static final String TAG_TITLE = "title";
    
    public static final String TAG_book = "book";
    public static final String TAG_description = "description";
    public static final String TAG_authors = "authors";
    public static final String TAG_price = "price";
    public static final String TAG_title = "title";
    public static final String TAG_id = "id";
    private SeparatedListAdapter adapter;
    ListView lstView;
    Button ref;
    private ProgressDialog pd;
    final Handler mHandler = new Handler();
    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
      public void run() {
        UpdateDisplay();
      }
    };
    final Runnable mUpdateResults2 = new Runnable() {
        public void run() {
          updateList();
        }
      };
 // SectionHeaders
	private final static String[] type = new String[]{"book", "music", "camera"};
    // contacts JSONArray
    JSONObject camera = null;
    JSONObject music = null;
    JSONObject book = null;
    JSONArray json;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> cameraList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> musicList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> bookList = new ArrayList<HashMap<String, String>>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	lstView = (ListView) findViewById(R.id.list1);
    	ref = (Button) findViewById(R.id.refresh);
    	final SQLITEHelper sqlite=new SQLITEHelper(getApplicationContext());
    	showProgressDialog("Please Wait...");
    	// Creating JSON Parser instance
    	ref.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			showProgressDialog("Please Wait...");
    			new Thread() {
    				public void run() {
    					try{
    						sqlite.deleteTable();
    						JSONParser jParser = new JSONParser();
    						json = jParser.getJSONFromUrl(url);
    						mHandler.post(mUpdateResults);
    					}
    					catch (Exception e) {
    						Log.e("tag", e.getMessage());
    					}
    					finally{
    						dismissProgressDialog();
    					}
    				}
    			}.start();
    		}
    	});
    	new Thread() {
    		public void run() {
    			try{
    				bookList= sqlite.getBooks();
    				musicList=  sqlite.getMusics();
    				cameraList= sqlite.getCameras();
    				if(bookList!=null && bookList.size()>0 && musicList!=null && musicList.size()>0 && cameraList!=null && cameraList.size()>0){
    					mHandler.post(mUpdateResults2);
    				}
    				else{
    					JSONParser jParser = new JSONParser();
    					json = jParser.getJSONFromUrl(url);
    					mHandler.post(mUpdateResults);
    				}
    			} catch (Exception e) {
    				Log.e("tag", e.getMessage());
    			}
    			finally{
    				dismissProgressDialog();
    			}
    		}
    	}.start();

    }
    private void UpdateDisplay()
    {
    	try {
    		if(json!=null && json.length()>0){
    			cameraList=new ArrayList<HashMap<String,String>>();
    			musicList=new ArrayList<HashMap<String,String>>();
    			bookList=new ArrayList<HashMap<String,String>>();
    			for(int i=0;i<json.length();i++){
    				//System.out.println(json.getJSONObject(i).names().get(0));
    				if(json.getJSONObject(i).names().get(0).equals(TAG_CAMERA)){
    					JSONObject c = json.getJSONObject(i);
    					// Storing each json item in variable
    					//System.out.println(c.getJSONObject(TAG_CAMERA).getString(TAG_picture));
    					String id = c.getJSONObject(TAG_CAMERA).getString(TAG_picture);
    					String name = c.getJSONObject(TAG_CAMERA).getString(TAG_NAME);
    					String price = c.getJSONObject(TAG_CAMERA).getString(TAG_camprice);
    					String make = c.getJSONObject(TAG_CAMERA).getString(TAG_cammake);

    					HashMap<String, String> map = new HashMap<String, String>();
    					map.put(TAG_picture, id);
    					map.put(TAG_NAME, name);
    					map.put(TAG_camprice, price);
    					map.put(TAG_cammake, make);

    					// adding HashList to ArrayList
    					cameraList.add(map);
    					
    				}
    				else if(json.getJSONObject(i).names().get(0).equals(TAG_music)){
    					JSONObject c = json.getJSONObject(i);
    					// Storing each json item in variable
    					//System.out.println(c.getJSONObject(TAG_music).getString(TAG_ARTIST));
    					String artist = c.getJSONObject(TAG_music).getString(TAG_ARTIST);
    					String album = c.getJSONObject(TAG_music).getString(TAG_ALBUM);
    					String genre = c.getJSONObject(TAG_music).getString(TAG_GENRE);
    					String musictitle = c.getJSONObject(TAG_music).getString(TAG_TITLE);

    					HashMap<String, String> map = new HashMap<String, String>();
    					map.put(TAG_ARTIST, artist);
    					map.put(TAG_ALBUM, album);
    					map.put(TAG_GENRE, genre);
    					map.put(TAG_TITLE, musictitle);

    					// adding HashList to ArrayList
    					musicList.add(map);
    				}
    				else if(json.getJSONObject(i).names().get(0).equals(TAG_book)){
    					JSONObject c = json.getJSONObject(i);
    					// Storing each json item in variable
    					//System.out.println(c.getJSONObject(TAG_book).getString(TAG_description));
    					String description = c.getJSONObject(TAG_book).getString(TAG_description);
    					String author = c.getJSONObject(TAG_book).getString(TAG_authors);
    					String price = c.getJSONObject(TAG_book).getString(TAG_price);
    					String title = c.getJSONObject(TAG_book).getString(TAG_title);
    					String id = c.getJSONObject(TAG_book).getString(TAG_id);

    					HashMap<String, String> map = new HashMap<String, String>();
    					map.put(TAG_description, description);
    					map.put(TAG_authors, author);
    					map.put(TAG_price, price);
    					map.put(TAG_title, title);
    					map.put(TAG_id, id);
    					// adding HashList to ArrayList
    					bookList.add(map);
    				}
    			}
    			updateList();
    			SQLITEHelper sqlite=new SQLITEHelper(getBaseContext());
    			if(bookList!=null && bookList.size()>0){
    				//truncate
    				sqlite.insertbook(bookList);
    			}
    			if(musicList!=null && musicList.size()>0){
    				//truncate
    				sqlite.insertmusic(musicList);
    			}
    			if(cameraList!=null && cameraList.size()>0){
    				//truncate
    				sqlite.insertcamera(cameraList);
    			}
    		}
    	} catch (JSONException e) {
    		e.printStackTrace();
    	}
    }
    private void updateList(){
    	adapter = new SeparatedListAdapter(this);
		// Add Sections
		if(bookList!=null && bookList.size()>0){
			adapter.addSection(type[0], new com.example.k2.ListAdapter(MainActivity.this,bookList));
		}
		if(musicList!=null && musicList.size()>0){
			adapter.addSection(type[1], new com.example.k2.ListAdapter2(MainActivity.this,musicList));
		}
		if(cameraList!=null && cameraList.size()>0){
			adapter.addSection(type[2], new com.example.k2.ListAdapter3(MainActivity.this,cameraList));
		}
		lstView.setAdapter(adapter);
		/*lstView.setAdapter(new com.example.k2.ListAdapter(MainActivity.this,
				bookList));*/
		
		// Listen for Click events
		lstView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
			{
					int type =adapter.getItemViewType(position);
					if(type==1){
						//book
						HashMap<String, String> map=(HashMap<String, String>)parent.getAdapter().getItem(position);
						Intent itemintent = new Intent(MainActivity.this,ShowDescription.class);
					    itemintent.putExtra("details","TITLE : "+map.get(TAG_title)+"\n\n"+"AUTHOR : "+map.get(TAG_authors)+"\n\n"+"PRICE : "+map.get(TAG_price)+"\n\n"+"DESCRIPTION : "+map.get(TAG_description));
					    startActivity(itemintent);
					}
					else if(type==2){
					    		//music			
						HashMap<String, String> map=(HashMap<String, String>)parent.getAdapter().getItem(position);
						Intent itemintent = new Intent(MainActivity.this,ShowDescription.class);
					    itemintent.putExtra("details","TITLE : "+map.get(TAG_TITLE)+"\n\n"+"ALBUM : "+map.get(TAG_ALBUM)+"\n\n"+"ARTIST : "+map.get(TAG_ARTIST)+"\n\n"+"GENRE : "+map.get(TAG_GENRE));
					    startActivity(itemintent);
					}
					else if(type==3){
						//camera
						HashMap<String, String> map=(HashMap<String, String>)parent.getAdapter().getItem(position);
						Intent itemintent = new Intent(MainActivity.this,ShowDescription.class);
					    itemintent.putExtra("details","CAMERA NAME : "+map.get(TAG_NAME)+"\n\n"+"MAKE : "+map.get(TAG_cammake)+"\n\n"+"PRICE : "+map.get(TAG_camprice));
					    startActivity(itemintent);
					}
						
			}
		});
    }
    
    public void showProgressDialog(String dialogText) {
        pd = ProgressDialog.show(MainActivity.this, "", dialogText,true);
        pd.setCancelable(true);
        pd.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
      }

      public void dismissProgressDialog() {
        try {
          pd.dismiss();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }
      }
}

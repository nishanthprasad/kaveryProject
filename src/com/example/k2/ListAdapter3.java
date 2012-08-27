package com.example.k2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter3 extends BaseAdapter {
	String transData;
	private static ArrayList<HashMap<String,String>> searchArrayList;
	Context mycontext;
	private LayoutInflater mInflater;

	public ListAdapter3(Context context, ArrayList<HashMap<String,String>> results) {
		//System.out.println("results=="+results);
		searchArrayList = results;
		mycontext = context;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		
		if (searchArrayList != null)
			return searchArrayList.size();
		return 0;
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cameralist, null);
			holder = new ViewHolder();
			holder.image_url = (ImageView) convertView
					.findViewById(R.id.feed_image);
			holder.modelname = (TextView) convertView.findViewById(R.id.modelname);
			holder.price = (TextView) convertView
					.findViewById(R.id.price);
			holder.make = (TextView) convertView.findViewById(R.id.make);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		try {
			URL feedImage;
			feedImage = new URL(searchArrayList.get(position).get(MainActivity.TAG_picture));
			HttpURLConnection conn= (HttpURLConnection)feedImage.openConnection();
	        InputStream is = conn.getInputStream();
	        Bitmap img = BitmapFactory.decodeStream(is);
	        holder.image_url.setImageBitmap(img);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		holder.modelname.setText(searchArrayList.get(position).get(MainActivity.TAG_NAME));
		holder.price.setText(searchArrayList.get(position).get(MainActivity.TAG_camprice));
		holder.make.setText(searchArrayList.get(position).get(MainActivity.TAG_cammake));
		return convertView;
	}

	static class ViewHolder {
		ImageView image_url;
		TextView modelname;
		TextView price;
		TextView make;
	}

}

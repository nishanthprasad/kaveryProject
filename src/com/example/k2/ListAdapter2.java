package com.example.k2;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter2 extends BaseAdapter {
	String transData;
	private static ArrayList<HashMap<String,String>> searchArrayList;
	Context mycontext;
	private LayoutInflater mInflater;

	public ListAdapter2(Context context, ArrayList<HashMap<String,String>> results) {
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
			convertView = mInflater.inflate(R.layout.musiclist, null);
			holder = new ViewHolder();
			holder.music_title = (TextView) convertView
					.findViewById(R.id.musictitle);
			holder.album = (TextView) convertView.findViewById(R.id.album);
			holder.artist = (TextView) convertView
					.findViewById(R.id.artist);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.music_title.setText(searchArrayList.get(position).get(MainActivity.TAG_TITLE));
		holder.album.setText(searchArrayList.get(position).get(MainActivity.TAG_ALBUM));
		holder.artist.setText(searchArrayList.get(position).get(MainActivity.TAG_ARTIST));
		return convertView;
	}

	static class ViewHolder {
		TextView music_title;
		TextView album;
		TextView artist;
	}

}

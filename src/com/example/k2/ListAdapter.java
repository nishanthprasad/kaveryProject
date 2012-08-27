package com.example.k2;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	String transData;
	private static ArrayList<HashMap<String,String>> searchArrayList;
	Context mycontext;
	private LayoutInflater mInflater;

	public ListAdapter(Context context, ArrayList<HashMap<String,String>> results) {
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
			convertView = mInflater.inflate(R.layout.booklist, null);
			holder = new ViewHolder();
			holder.book_title = (TextView) convertView
					.findViewById(R.id.booktitle);
			holder.author = (TextView) convertView.findViewById(R.id.bookauthor);
			holder.price = (TextView) convertView
					.findViewById(R.id.bookprice);
			holder.description = (TextView) convertView.findViewById(R.id.bookdesc);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.book_title.setText(searchArrayList.get(position).get(MainActivity.TAG_title));
		//System.out.println(searchArrayList.get(position).get(MainActivity.TAG_authors));
		holder.author.setText(searchArrayList.get(position).get(MainActivity.TAG_authors));
		holder.price.setText(searchArrayList.get(position).get(MainActivity.TAG_price));
		//System.out.println(searchArrayList.get(position).get(MainActivity.TAG_price));
		if(searchArrayList.get(position).get(MainActivity.TAG_description).split(" ").length>20){
			String s[]=searchArrayList.get(position).get(MainActivity.TAG_description).split(" ");
			String s2="";
			for(int i=0;i<20;i++)
				s2=s2+" "+s[i];
			holder.description.setText(s2);
		}
		else
			holder.description.setText(searchArrayList.get(position).get(MainActivity.TAG_description));
		//System.out.println(searchArrayList.get(position).get(MainActivity.TAG_description));
		return convertView;
	}

	static class ViewHolder {
		TextView book_title;
		TextView author;
		TextView price;
		TextView description;
	}

}

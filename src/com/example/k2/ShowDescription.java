package com.example.k2;



import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 
 * @author Nishanth Prasad
 *
 */
public class ShowDescription extends Activity
{
  String theStory ="Information Not Found.";
  TextView db;
  
  public void onCreate(Bundle icicle) 
  {
    super.onCreate(icicle);
    System.out.println("on create");
    setContentView(R.layout.showdescription);
    TextView detailsview=(TextView)findViewById(R.id.storybox);
    Intent startingIntent = this.getIntent();
    String text=startingIntent.getExtras().getString("details");
    if(text.length()==0)
    	text=theStory;
    detailsview.setText(text);
    Button backbutton = (Button) findViewById(R.id.back);
    backbutton.setOnClickListener(new Button.OnClickListener() 
    {
      public void onClick(View v) 
      {
        finish();
      }
    });     
  }
@Override
  protected void onStart() {
    super.onStart();
  }
    
  @Override
  protected void onResume() {
    super.onResume();
  }
  @Override
  protected void onPause() {
    super.onPause();
  }
  @Override
    protected void onDestroy() {
      super.onDestroy();
    }
  @Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}

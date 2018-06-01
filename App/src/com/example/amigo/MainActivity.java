package com.example.amigo;



import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	public static TextView nDate,nName;
	public static String ip;
	//public static Button bJoin,bLogin;
	public static int curYear, curMonth, curDay, curHour, curMinute, curNoon, curSecond;

	Date curMillis;
	public static TimerTask second;
	Calendar c;
	String noon = "";

	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start();
		nDate = (TextView) findViewById(R.id.nDate);
		nName = (TextView) findViewById(R.id.naV);
		Button bJoin= (Button) findViewById(R.id.Join);		
	//	Button bLogin = (Button) findViewById(R.id.Login);;
		if(Input.na!=null)
		nName.setText(Input.na);
		bJoin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				

				Toast.makeText(MainActivity.this, "Hello World!!",
						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(MainActivity.this,Input.class);
				startActivity(intent);
			}
		});
	
	/*	bLogin.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {

			
			Toast.makeText(getApplicationContext(),"Connect Bt plz",Toast.LENGTH_SHORT).show();
			//Intent intent = new Intent(MainActivity.this,mServer.class); //서버 활용
			Intent intent = new Intent(MainActivity.this,BluetoothChat.class); // 블루투스 활용
			startActivity(intent);
	
		}
	});
	*/


	}
	private void start()
	 {
		 second = new TimerTask()
		 {
			 @Override

			 public void run()
			 {
				 Update();       
			 }
		 };     
		 Timer timer = new Timer();
		 timer.schedule(second, 0, 1000);
	 }

	 protected void Update()
	 {  
		 c = Calendar.getInstance();
		 curMillis = c.getTime();
		 curYear = c.get(Calendar.YEAR);
		 curMonth = c.get(Calendar.MONTH)+1;
		 curDay = c.get(Calendar.DAY_OF_MONTH); 
		 curHour = c.get(Calendar.HOUR_OF_DAY);
		 curNoon = c.get(Calendar.AM_PM);
		 if(curNoon == 0)
		 {
			 noon = "AM";
		 }
		 else
		 {
			 noon = "PM";
			 curHour -= 12;
		 }
		 curMinute = c.get(Calendar.MINUTE);  
		 curSecond = c.get(Calendar.SECOND);

		 Runnable updater = new Runnable()
		 {        
			 @Override
			public void run()
			 {     
				 nDate.setText(curYear+" . "+curMonth+" . "+curDay+" . "+     
						 noon+"  "+curHour+" : "+curMinute+" : "+curSecond);   

			 }

		 };

		 handler.post(updater);
	 }
	
	
}






package com.example.amigo;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Input extends Activity implements OnClickListener {
	
 	public static Button sB,rB;
 	public static ImageButton bM,bW;
    public static EditText naE,agE,heE,weE;
    public static String na,ag,he,we,se,msg;
    public static CheckBox cM,cW;
    public static SQLiteUserListHelper dbHelper;	
    public static List<User> userList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);

		 
		
		naE = (EditText) findViewById(R.id.edt4);
		agE = (EditText) findViewById(R.id.edt3);
		heE = (EditText) findViewById(R.id.edt2);
		weE = (EditText) findViewById(R.id.edt1);
		bM = (ImageButton) findViewById(R.id.ibM);
		bW = (ImageButton) findViewById(R.id.ibW);
		cM = (CheckBox) findViewById(R.id.cM);
		cW = (CheckBox) findViewById(R.id.cW);


		sB = (Button) findViewById(R.id.sb);
		rB = (Button) findViewById(R.id.rese);
			
		bM.setOnClickListener(this);
		bW.setOnClickListener(this);
		
		sB.setOnClickListener(this); 
		rB.setOnClickListener(this); 

		//mDbOpenHelper.close();

	
			    
      
	}



	@Override
	public void onClick(View v) {

		
		switch(v.getId())
		{
		case R.id.ibM:
			se = "Man";
			cM.setChecked(true);
			cW.setChecked(false);
			bM.setClickable(false);
			bW.setClickable(false);
			break;
	
		case R.id.ibW:
			se = "Woman";
			cW.setChecked(true);
			cM.setChecked(false);
			bW.setClickable(false);
			bM.setClickable(false);
			break;	
			
		case R.id.rese:
			naE.setText(null);
			agE.setText(null);
			heE.setText(null);
			weE.setText(null);
			
			na = naE.getText().toString();
			ag = agE.getText().toString();
			he= heE.getText().toString();
			we= weE.getText().toString();
			se="";
			
			bW.setClickable(true);
			bM.setClickable(true);
			sB.setClickable(true);
			cM.setChecked(false);
			cW.setChecked(false);
			
			naE.setEnabled(true);
			agE.setEnabled(true);
			heE.setEnabled(true);
			weE.setEnabled(true);

			Log.d("Name",na);
			Log.d("Age",ag);
			Log.d("Sex",se);
			Log.d("Height",he);
			Log.d("Weight",we);

			break;

		case R.id.sb:

			dbHelper = new SQLiteUserListHelper(getApplicationContext());
			na = naE.getText().toString();
			ag = agE.getText().toString();
			he = heE.getText().toString();
			we = weE.getText().toString();	

			naE.setEnabled(false);
			agE.setEnabled(false);
			heE.setEnabled(false);
			weE.setEnabled(false);

			if(na.isEmpty()!=true&ag.isEmpty()!=true&he.isEmpty()!=true&we.isEmpty()!=true){


				Log.i("Name",na);
				Log.i("Age",ag);
				Log.i("Sex",se);
				Log.i("Height",he);
				Log.i("Weight",we);
				msg = "Insert Data...";
		
				Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

				Log.d("destiny", "=======================");


				Log.d("destiny", "Insert Data...");
	
				 dbHelper.insertUser(new User(na,ag,se,he,we));

				sB.setClickable(false);

				userList = dbHelper.selectAllUser();


				for (User user : userList) {
					Log.d("destiny","ID : "+user.getId()+ " Name : " + user.getName() + "  Age : "
							+ user.getAge() + "  Sex : " + user.getSex() + "  Height : " 
							+ user.getHeight() + "  Weight : " + user.getWeight());

				}

				Log.d("destiny", "=======================");

				Intent i = new Intent(Input.this,BluetoothChat.class);
				startActivity(i);
			}
			else
			{
				msg = "Plz Input";

				Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
			}


		}


	}		
}

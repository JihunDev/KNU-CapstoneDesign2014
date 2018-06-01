/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.amigo;

import java.util.List;







import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main Activity that displays the current chat session.
 */
@SuppressLint("NewApi")
public class BluetoothChat extends Activity {
	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;
	private static boolean F = true;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_DEVICE_NAME = 3;
	public static final int MESSAGE_TOAST = 4;
	public static final int MESSAGE_WRITE = 5;

	public static final int ADDMESSAGE_STATE_CHANGE = 1;
	public static final int ADDMESSAGE_READ = 2;
	public static final int ADDMESSAGE_DEVICE_NAME = 3;
	public static final int ADDMESSAGE_TOAST = 4;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";

	public static final String TOAST = "toast";

	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

	//Save
	public static String[] result,Addresult;
	public static String readMessage,readMe,rA,rH,rS,technique,tPrev,AddreadMessage,AddreadMe,Addtechnique,AddtPrev,name_d,Stechnique,PrevS="";
	public static String[] rMe,AddrMe;
	public static int[] re,Addre;
	public static float[] ae,Addae;
	public static int aa,ah,mA,mH,abs,cVal,t,ct,val,acVal;

	public static float mS,as,iScore;
	public static final float alpha = 0.8f;

	//giro


	public static TextView sT,sPt,sJt,sN;
	// Layout Views
	private Button mSendButton;
	private ListView mTec;
	private static ArrayAdapter adapter;


	// Name of the connected device
	private String mConnectedDeviceName = null,AddmConnectedDeviceName=null;
	// Array adapter for the conversation thread
	private ArrayAdapter<String> mConversationArrayAdapter;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BluetoothChatService mChatService = null;
	private ServerAddReceiver mChataddService = null;


	static SQLiteUserListHelper dbHelper;	
	static SQLite_ExInfo dbHell;	
	public static List<ExInfo> userExList;
	//Handler handler,Addhandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	if(D) Log.e(TAG, "+++ ON CREATE +++");

    	// Set up the window layout
    	setContentView(R.layout.btmain);

		GLSurfaceView glSurfaceViewr = (GLSurfaceView) findViewById(R.id.surface_viewr);
		glSurfaceViewr.setRenderer(new LTexturedCubeRenderer(getApplicationContext())); 

		GLSurfaceView glSurfaceViewl = (GLSurfaceView) findViewById(R.id.surface_viewl);
		glSurfaceViewl.setRenderer(new RTexturedCubeRenderer(getApplicationContext())); 

		dbHelper = new SQLiteUserListHelper(getApplicationContext());
		dbHell= new SQLite_ExInfo(getApplicationContext());
		mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);

		sT =(TextView) findViewById(R.id.tsp);
		sJt =(TextView) findViewById(R.id.th);
		sPt =(TextView) findViewById(R.id.tspi);
		sN =(TextView) findViewById(R.id.textView1);
		mTec =(ListView) findViewById(R.id.tlistView);
		sN.setText(Input.na);
		mSendButton = (Button) findViewById(R.id.ssb);
		mSendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("aaa :",""+mS+"/"+mS+"/"+mH );
				val=0;


				dbHell.insertExInfo(new ExInfo(""+sN.getText(),""+mS,""+mA,""+mH,""+Stechnique,""+iScore));
				userExList = dbHell.selectAll();
				for (ExInfo info : userExList) {
					Log.d("destiny","ID : "+info.getId()+ " Name : " + info.getName() + " SPin : "
							+ info.getSpin() + " Speed : " + info.getSpeed() + "Height : " 
							+ info.getHeight());

				}        	
				Intent next = new Intent(BluetoothChat.this,InfoList.class);
				startActivity(next);
				finish();
			}
		});

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		cVal=0; ah=0; re = new int[15]; ae = new float[15]; result = new String[15]; Addresult = new String[15];
		abs =0; as=0; val=0; iScore=0; acVal=0; Addre=new int[15];  Addae = new float[15];
		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;

		}




		// Creating and attaching the renderer.



	}

	@Override
	public void onStart() {
		super.onStart();
		if(D) Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)	setupChat();
		
		
		}

	}


	@Override
	public synchronized void onResume() {
		super.onResume();
		if(D) Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
		if (mChataddService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChataddService.getState() == ServerAddReceiver.STATE_NONE) {
				// Start the Bluetooth chat services
				mChataddService.start();
			}
		}
	}

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		// Initialize the array adapter for the conversation thread
		mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
		mTec.setAdapter(mConversationArrayAdapter);
		// Initialize the compose field with a listener for the return key

		// Initialize the send button with a listener that for click events

		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);
		mChataddService = new ServerAddReceiver(this, AddmHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if(D) Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if(D) Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (mChatService != null) mChatService.stop();
		if (mChataddService != null) mChataddService.stop();

		if(D) Log.e(TAG, "--- ON DESTROY ---");
	}
	// The action listener for the EditText widget, to listen for the return key
	private TextView.OnEditorActionListener mWriteListener =
			new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
			// If the action is a key-up event on the return key, send the message
			if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				//    sendMessage(message);
			}
			if(D) Log.i(TAG, "END onEditorAction");
			return true;
		}
	};
	private void ensureDiscoverable() {
		if(D) Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() !=
				BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}




	@SuppressLint("NewApi")
	private final void setStatus(int resId) {
		final ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(resId);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private final void setStatus(CharSequence subTitle) {
		final ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(subTitle);
	}

	// The Handler that gets information back from the BluetoothChatService
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
			     case BluetoothChatService.STATE_CONNECTED:
			    	 setStatus(name_d);
	                    mConversationArrayAdapter.clear();
	                    break;
				case BluetoothChatService.STATE_CONNECTING:
					setStatus(R.string.title_connecting);
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					setStatus(R.string.title_not_connected);
					break;
				}
				break;

			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;

				readMessage = new String(readBuf, 0, msg.arg1);
				result=readMessage.split(" ");  
				technique="";
				t = MainActivity.curSecond;

				if(cVal==0){
					if(result.length==14){
						break;
					}
					if(readMessage!="-"){
						readMe=readMessage;

					}
					Log.e("v :",readMe);
					cVal++;
				}
				else if(cVal==1){
					if(readMe.toString()!=readMessage.toString()){
						readMe=readMe.toString()+readMessage.toString()+" ";
						result=readMe.split(" ");  




						if(result.length==14){
							Log.e("val :",readMe );


							re[0] = Integer.parseInt(result[0]); //r
							re[1] = Integer.parseInt(result[1]); //p
							re[2] = Integer.parseInt(result[2]); //y
							//a cc
							re[3] = Integer.parseInt(result[3]); //x=y
							re[4] = Integer.parseInt(result[4]); //y=-x
							re[5] = Integer.parseInt(result[5]); //z=-z
							//gro
							re[6] = Integer.parseInt(result[6]);
							re[7] = Integer.parseInt(result[7]);
							re[8] = Integer.parseInt(result[8]);
							// mag
							re[9]  = Integer.parseInt(result[9]);
							re[10] = Integer.parseInt(result[10]);
							re[11] = Integer.parseInt(result[11]);
							//
							re[12] = Integer.parseInt(result[12]);



							// 회전각속도를 계산    



							re[6] = (int) (alpha *re[6] + (1 - alpha) *re[3]);
							re[7] = (int) (alpha *re[7] + (1 - alpha) *re[4]); 
							re[8] = (int) (alpha *re[8] + (1 - alpha) *re[5]);  

							ae[0] = re[3] - re[6];
							ae[1] = re[4] - re[7];
							ae[2] = re[5] - re[8];   		

							Log.d("re :",""+re[3]);


							 aa= (int) Math.sqrt(Math.pow(ae[0], 2) + Math.pow(ae[1], 2));


							rA=Integer.toString(aa);


							if(re[3]<=-5) 	//+왼 -오른쪽
							{
								technique ="in_edge";
								if(tPrev=="out_edge")
								{
									F=false;
								}
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}
							else if(re[3]>=5) 	//+왼 -오른쪽
							{
								technique ="out_edge";
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}

							if(((re[4]>=7)&&(re[4]<=8))&&((re[5]>=2)&&(re[5]<=3)))
							{


								technique = "toe";
								//iScore += 0.4;



							}

						}


						if(ae[2]>ae[4])
						{

							ah=(int) Math.abs(Math.pow(ae[2], 2)/(2*9.8));
						}

						/*
						 * spin
						 */

						abs = (int)Math.abs(ae[5])-Math.abs(re[12]); 


						abs = Math.abs(abs);

						if(abs<=150)
						{
							if(abs>=25)
							{
								as+=0.5;
							}
						}

						if(ae[7]>re[9]+30)
						{

							if(re[3]<=-5) 	//+왼 -오른쪽
							{
								technique ="forward in_edge";
								//	tT.setText(technique);
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}
							else if(re[3]>=5) 	//+왼 -오른쪽
							{
								technique ="forward out_edge";
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}
							else
							{
								technique ="forward";

							}


						}
						else if(ae[7]<re[9]-30)
						{
							technique ="backward";
							if(re[3]<=-5) 	//+왼 -오른쪽
							{
								technique ="backward_in_edge";
								//	tT.setText(technique);
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}
							else if(re[3]>=5) 	//+왼 -오른쪽
							{
								technique ="backward_out_edge";
								Log.d("aaa :",""+mS+"/"+mS+"/"+mH+"/"+technique+"/"+iScore );

							}
						}


						if(mS<as)
						{
							mS=as;
						}
						if(mA<aa)
						{
							mA=aa;
						}
						if(mH<ah)
						{
							mH=ah;
						}
						rH=Integer.toString(ah);
						rS=""+as;

						sT.setText(rA);
						sJt.setText(rH);
						sPt.setText(rS);
						//tT.setText(technique);
						if(((tPrev!=null)&&(tPrev!=""))&&(tPrev!=technique)){
							mConversationArrayAdapter.add("Left : "+tPrev);
						}
						tPrev = technique;

						cVal=0; ae[4]=ae[2]; ae[6]=re[0];  ae[5]=re[12]; ae[7]=re[9] ;


					}
				}
				break;

			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to "
						+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
				name_d = mConnectedDeviceName;
				break;

			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
	// The Handler that gets information back from the BluetoothChatService
		public Handler AddmHandler = new Handler() {
			@Override
			public void handleMessage(Message Addmsg) {
			switch (Addmsg.what) {

			case ADDMESSAGE_STATE_CHANGE:
				if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + Addmsg.arg1);
				switch (Addmsg.arg1) {
				case ServerAddReceiver.STATE_CONNECTED:
					setStatus(name_d);
					mConversationArrayAdapter.clear();
					break;
				case ServerAddReceiver.STATE_CONNECTING:
					setStatus(R.string.title_connecting);
					break;
				case ServerAddReceiver.STATE_LISTEN:
				case ServerAddReceiver.STATE_NONE:
					setStatus(R.string.title_not_connected);
					break;
				}
				break;
				//error
			case ADDMESSAGE_READ:
				byte[] readBuf = (byte[]) Addmsg.obj;

				AddreadMessage = new String(readBuf, 0, Addmsg.arg1);
				Addresult=AddreadMessage.split(" ");  
				Addtechnique="";

				if(acVal==0){
					if(Addresult.length==14){
						break;
					}
					if(AddreadMessage!="-"){
						AddreadMe=AddreadMessage;

					}
					Log.e("va :",AddreadMe);
					acVal++;
				}
				else if(acVal==1){
					if(AddreadMe.toString()!=AddreadMessage.toString()){
						AddreadMe=AddreadMe.toString()+AddreadMessage.toString()+" ";

						Log.e("aa :",AddreadMe );
						Addresult=AddreadMe.split(" ");  
						Log.d("aa :",""+Addresult.length);

						if(Addresult.length==14){
							Log.e("vala :",AddreadMe );


							Addre[0] = Integer.parseInt(Addresult[0]); //r
							Addre[1] = Integer.parseInt(Addresult[1]); //p
							Addre[2] = Integer.parseInt(Addresult[2]); //y
							//a cc
							Addre[3] = Integer.parseInt(Addresult[3]); //x=y
							Addre[4] = Integer.parseInt(Addresult[4]); //y=-x
							Addre[5] = Integer.parseInt(Addresult[5]); //z=-z
							//gro
							Addre[6] = Integer.parseInt(Addresult[6]);
							Addre[7] = Integer.parseInt(Addresult[7]);
							Addre[8] = Integer.parseInt(Addresult[8]);
							// mag
							Addre[9]  = Integer.parseInt(Addresult[9]);
							Addre[10] = Integer.parseInt(Addresult[10]);
							Addre[11] = Integer.parseInt(Addresult[11]);
							//
							Addre[12] = Integer.parseInt(Addresult[12]);







							Log.d("re :",""+re[3]);




							if(Addre[3]>=5) 	//+왼 -오른쪽
							{
								Addtechnique ="in_edge";
								//	tT.setText(technique);

							}
							else if(Addre[3]<=-5) 	//+왼 -오른쪽
							{
								Addtechnique ="out_edge";
							}

							if(((Addre[4]>=7)&&(Addre[4]<=8))&&((Addre[5]>=2)&&(Addre[5]<=3)))
							{


								Addtechnique = "toe";

								if(tPrev=="backward_in_edge")
								{

									if(as==2)
									{
										Stechnique="Duoble_Flip Jump";

									}
									else if(as==3)
									{
										Stechnique="Triple_Flip Jump";

									}
									else
									{
										Stechnique="Flip Jump";
									}

									PrevS = Stechnique;
									if(Stechnique!=null) mConversationArrayAdapter.add(Stechnique);

								}

								if(tPrev=="backward_out_edge")
								{
									if(F==true){


										if(as==2)
										{
											Stechnique="Duoble_Lutz Jump";

										}
										else if(as==3)
										{
											Stechnique="Triple_Lutz Jump";

										}
										else
										{
											Stechnique="Lutz Jump";
										}
									}
									else if(F==false){
										Stechnique="Flutz....";
										iScore = -10;
									}
									PrevS = Stechnique;
									if(Stechnique!=null) mConversationArrayAdapter.add(Stechnique);

								}
							}

							if(Addae[7]>Addre[9]+30)
							{
								Addtechnique ="forward";
								if(Addre[3]>=5) 	//+왼 -오른쪽
								{
									Addtechnique ="forward_in_edge";
									//	tT.setText(technique);

								}
								else if(Addre[3]<=-5) 	//+왼 -오른쪽
								{
									Addtechnique ="forward_out_edge";
								}
							}
							else if(Addae[0]<Addre[9]-30)
							{
								Addtechnique ="backward";
								if(Addre[3]>=5) 	//+왼 -오른쪽
								{
									Addtechnique ="backward_in_edge";
									//	tT.setText(technique);

								}
								else if(Addre[3]<=-5) 	//+왼 -오른쪽
								{
									Addtechnique ="backward_out_edge";
								}
							}
						}





						if(((AddtPrev!=null)&&(AddtPrev!=""))&&(AddtPrev!=Addtechnique)){
							mConversationArrayAdapter.add("Right : "+AddtPrev);
						}
						AddtPrev = Addtechnique;

						PrevS =PrevS+" ";
						acVal=0;  Addae[0]=Addre[9];
					}
				}
				break;

			case ADDMESSAGE_DEVICE_NAME:
				// save the connected device's name
				AddmConnectedDeviceName = Addmsg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to "
						+ AddmConnectedDeviceName, Toast.LENGTH_SHORT).show();
				name_d =name_d+ "  " + AddmConnectedDeviceName;
				break;

			case ADDMESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), Addmsg.getData().getString(TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

	 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
    	 if(secure==true){
    	String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
         mChatService.connect(device);
         Log.d(TAG,"hi");
    	 }
        else if(secure!=true){
        	String address = data.getExtras()
                    .getString(AddDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BluetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                
        	mChataddService.connect(device);
            Log.d(TAG,"hello");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
        case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, AddDeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

    
  
}

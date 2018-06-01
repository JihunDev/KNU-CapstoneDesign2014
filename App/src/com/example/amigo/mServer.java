package com.example.amigo;


/*
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.widget.Button;

public class mServer extends Activity{


	private static final boolean D = true;
	private static final String TAG = "Server";
    public static final String DEVICE_NAME = "device_name";
    public static final String SUBDEVICE_NAME = "device_name";

    public static final int MESSAGE_STATE_CHANGE = 0;
    public static final int MESSAGE_READ = 1;
    protected static final int SUBMESSAGE_READ = 2;
	protected static final int MESSAGE_DEVICE_NAME = 3;
	protected static final int MESSAGE_SUBDEVICE_NAME = 4;
	

    public static String[] result,sresult;
    public static String readMessage,readMe,dName,sName,sreadMessage,sreadMe;
    private ServerReceiver mChatService = null;
    private ServerAddReceiver mAddService= null;

    private Button mSendButton;

	Handler handler = new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		
		  mSendButton = (Button) findViewById(R.id.ssb);
		  GLSurfaceView glSurfaceViewr = (GLSurfaceView) findViewById(R.id.surface_viewr);
	        glSurfaceViewr.setRenderer(new RTexturedCubeRenderer(getApplicationContext())); 
	        
	        GLSurfaceView glSurfaceViewl = (GLSurfaceView) findViewById(R.id.surface_viewl);
	        glSurfaceViewl.setRenderer(new LTexturedCubeRenderer(getApplicationContext())); 


		
	}
	  public void onStart() {
	        super.onStart();
	        if(D) Log.e(TAG, "++ ON START ++");

	        if (mChatService == null) setupChat();
	        
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
	            if (mChatService.getState() == ServerReceiver.STATE_NONE) {
	              // Start the Bluetooth chat services
	              mChatService.start();
	            }
	        }
	        if (mAddService != null) {
	            // Only if the state is STATE_NONE, do we know that we haven't started already
	            if (mAddService.getState() == mAddService.ADDSTATE_NONE) {
	              // Start the Bluetooth chat services
	            	mAddService.start();
	            }
	        }
	    }

	    private void setupChat() {
	        Log.d(TAG, "setupChat()");

	        // Initialize the array adapter for the conversation thread
	       
	        // Initialize the compose field with a listener for the return key
	       
	        // Initialize the send button with a listener that for click events
	      
	        // Initialize the BluetoothChatService to perform bluetooth connections
	        mChatService = new ServerReceiver(mHandler);
	//        mAddService = new ServerAddReceiver(mHandler);

	        // Initialize the buffer for outgoing messages
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
	        if(D) Log.e(TAG, "--- ON DESTROY ---");
	    }
	    
	    @SuppressLint("NewApi")
		private final void setStatus(int resId) {
	        final ActionBar actionBar = getActionBar();
	        actionBar.setSubtitle(resId);
	    }

		@SuppressLint("NewApi")
		private final void setStatus(CharSequence subTitle) {
	        final ActionBar actionBar = getActionBar();
	        actionBar.setSubtitle(subTitle);
	    }

	    // The Handler that gets information back from the BluetoothChatService
	    private final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case MESSAGE_STATE_CHANGE:
	                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
	                switch (msg.arg1) {
	                case ServerReceiver.STATE_CONNECTED:
	                	setStatus("Connect : " + dName +" _ "+ sName);
	                    break;
	                case ServerReceiver.STATE_CONNECTING:
	                    setStatus(R.string.title_connecting);
	                    break;
	                case ServerReceiver.STATE_NONE:
	                    setStatus(R.string.title_not_connected);
	                    break;
	                }
	                break;

	            case MESSAGE_READ:
	            	byte[] readBuf = (byte[]) msg.obj;
                  

	            	// construct a string from the valid bytes in the buffer
	            	readMessage = new String(readBuf, 0, msg.arg1);
	            	result=readMessage.split(" ");  
	            	Log.e(TAG, dName+" "+readMessage);
	            	break;
	            	
	            case SUBMESSAGE_READ:
	            	byte[] sreadBuf = (byte[]) msg.obj;
                  

	            	// construct a string from the valid bytes in the buffer
	            	sreadMessage = new String(sreadBuf, 0, msg.arg1);
	            	sresult=sreadMessage.split(" ");  
	            	Log.d(TAG, sName+" "+sreadMessage);

	            	break;
	            	
            	case MESSAGE_DEVICE_NAME:
            		dName = msg.getData().getString(DEVICE_NAME);
	            	break;
            	case MESSAGE_SUBDEVICE_NAME:
            		sName = msg.getData().getString(SUBDEVICE_NAME);
	            	break;


            	}
            }

        };

	          
	        
	    }
*/
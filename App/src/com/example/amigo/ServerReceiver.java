package com.example.amigo;
/*
import java.io.DataOutputStream;
import java.io.IOException;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServerReceiver {
    private static final boolean D = true;
    private static final String TAG = "Service";
	public static final int dportnum = 9999;

	public static final int STATE_NONE = 0;       // we're doing nothing
	public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
	public static final int STATE_CONNECTED = 2;  // now connected to a remote device
   
	private final Handler mHandler;
	private ServerSocket serverSocket;
    private ConnectedThread mConnectedThread;
    private ConnectThread mConnectThread;
    private String clientAddress;

    public HashMap<InetAddress, InputStream> clients;

    private int mState;

	public  ServerReceiver(Handler handler){
		 mState = STATE_NONE;
	     mHandler = handler;
	}

	    /**
	     * Set the current state of the chat connection
	     * @param state  An integer defining the current connection state
	     */

/*
	    private synchronized void setState(int state) {
	        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
	        mState = state;

	        // Give the new state to the Handler so the UI Activity can update
	        mHandler.obtainMessage(mServer.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
	    }

	    /**
	     * Return the current connection state. */
/*
	    public synchronized int getState() {
	        return mState;
	    }
	    
	    public synchronized void start() {
	        if (D) Log.d(TAG, "start");
	   
	        if(mConnectThread != null)
	        {
	        	mConnectThread = new ConnectThread(dportnum);
		        mConnectThread.start();
	        	
	        }
	        else{
		        mConnectThread = new ConnectThread(dportnum);
		        mConnectThread.start();
	        }

	        setState(STATE_CONNECTING);
	        
	    }
	    
	    public synchronized void connected(Socket socket, String clientAddress) {
	        if (D) Log.d(TAG, "connected, Socket Type:" + clientAddress);

	        // Cancel the thread that completed the connection
	        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}


	        // Start the thread to manage the connection and perform transmissions
	        mConnectedThread = new ConnectedThread(socket, clientAddress);
	        mConnectedThread.start();
			   Log.i(TAG,"서4");


	        // Send the name of the connected device back to the UI Activity
	        Message msg = mHandler.obtainMessage(mServer.MESSAGE_DEVICE_NAME);
	        Bundle bundle = new Bundle();
	       
	        bundle.putString(mServer.DEVICE_NAME,""+clientAddress);
	        
	        msg.setData(bundle);
	        mHandler.sendMessage(msg);
			   Log.i(TAG,"서3");


	        setState(STATE_CONNECTED);
	    }

	    
	    
	    private class ConnectThread extends Thread {
	    	Socket mmSocket;
	    	private final int ptnum;

	        public ConnectThread(int num) {
	        	ptnum = num;
				try {
					serverSocket = new ServerSocket(ptnum);
				   Log.i(TAG,"서버가 시작되었습니다.");
				 
				} catch (IOException e) {
					// TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}
	        }

	        @Override
			public void run() {
	            // Always cancel discovery because it will slow down a connection

	            // Make a connection to the BluetoothSocket
	            try {
	                // This is a blocking call and will only return on a
	                // successful connection or an exception
	            	mmSocket = serverSocket.accept();
	            	  InetSocketAddress isaClient = (InetSocketAddress) mmSocket.getRemoteSocketAddress();
						clientAddress = isaClient.getAddress().getHostAddress();
	 			   Log.i(TAG,"서1");

	            } catch (IOException e) {
	                // Close the socket
	                
	                	try {
	                		mmSocket.close();
						} catch (IOException e1) {
							// TODO 자동 생성된 catch 블록
							e1.printStackTrace();
						}
	
	                return;
	            }

	            // Reset the ConnectThread because we're done
	            synchronized (ServerReceiver.this) {
	                mConnectThread = null;
	            }

	            // Start the connected thread
	            connected(mmSocket, clientAddress);
	        }

	       

			public void cancel() {
	            try {
	            	mmSocket.close();
	            } catch (IOException e) {
	                Log.e(TAG, "close() of connect " + " socket failed", e);
	            }
	        }
	    }
	    
	
	    
	    private class ConnectedThread extends Thread {
	        Socket mmSocket;
	        public final InputStream mmInStream;

	        public ConnectedThread(Socket socket, String clientAddress) {
	            Log.d(TAG, "create ConnectedThread:" + clientAddress);
	            mmSocket = socket;
	            InputStream tmpIn = null;

	            // Get the BluetoothSocket input and output streams
	            try {
	                tmpIn = socket.getInputStream();
	                
	            } catch (IOException e) {
	                Log.e(TAG, "temp sockets not created", e);
	            }

	            mmInStream = tmpIn;
	        }

	        @Override
			public void run() {
	            Log.i(TAG, "BEGIN mConnectedThread");
	            byte[] buffer = new byte[1024];
	            int bytes;
	            
	            // Keep listening to the InputStream while connected
	            while (true) {
	                try {
	                    // Read from the InputStream
	                    bytes = mmInStream.read(buffer);
	                    
	                    // Send the obtained bytes to the UI Activity
	                    mHandler.obtainMessage(mServer.MESSAGE_READ, bytes, -1, buffer)
	                            .sendToTarget();
	                } catch (IOException e) {
	                    Log.e(TAG, "disconnected", e);
	                    // Start the service over to restart listening mode
	                    ServerReceiver.this.start();
	                    break;
	                }
	            }
	        }
*/
	        /**
	         * Write to the connected OutStream.
	         * @param buffer  The bytes to write
	         */
/*	      
	        public void cancel() {
	            try {
	                mmSocket.close();
	            } catch (IOException e) {
	                Log.e(TAG, "close() of connect socket failed", e);
	            }
	        }
	    }

	    public synchronized void stop() {
	        if (D) Log.d(TAG, "stop");

	        if (mConnectThread != null) {
	            mConnectThread.cancel();
	            mConnectThread = null;
	        }

	        if (mConnectedThread != null) {
	            mConnectedThread.cancel();
	            mConnectedThread = null;
	        }
	        setState(STATE_NONE);
	    }

}
*/

package com.example.amigo;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

@SuppressLint("NewApi")
public class InfoList extends Activity implements OnClickListener{

	public static EditText find;
	public static Button del,search;
	public static ListView list_v;
	public static String del_id,ex_id;


	static List<User> searchList;
	static List<User> findList;
	static List<User> aluser;
	static List<ExInfo> ExsearchList; 
	static List<ExInfo> ExList; 

	private ArrayAdapter<ExInfo> mAdapter;
	private String[] str,st_id,sId;

	private static int count,cnt;
	static SQLiteUserListHelper sdbHelper;	
	static SQLite_ExInfo sdb;	

	private static ArrayAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		st_id = new String[24];
		setContentView(R.layout.list);
		del = (Button) findViewById(R.id.del);
		search = (Button) findViewById(R.id.search);
		find =(EditText) findViewById(R.id.fiN);
		list_v=(ListView) findViewById(R.id.mList);
		
		cnt=0;

		sdbHelper = new SQLiteUserListHelper(getApplicationContext());
		sdb = new SQLite_ExInfo(getApplicationContext());

		del.setOnClickListener(this);
		ExList =sdb.selectAll();

		search.setOnClickListener(this);
		list_v.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ex_id =(String) list_v.getItemAtPosition(position);
				st_id=ex_id.split(" ");  
				Log.i("ex",""+ex_id);
				Log.i("ex",""+st_id[2]);
				find.setText(st_id[2]);
				for (ExInfo info : ExList) {
					if (info.getName().equals(""+st_id[2])) {
					ExsearchList = sdb.selectExInfo(info);
					
					sId[0] =("Name : " + info.getName() + " Spin : "
							+ info.getSpin() + " Speed : " + info.getSpeed() + " Height : " 
							+ info.getHeight() + " technique : " + info.getTechnique()+ " score : " + info.getScore());
					}
				}
		        Toast.makeText(InfoList.this, sId[0] , Toast.LENGTH_LONG).show();

				return false;
			}
		});
	}

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onClick(View v) {
		// TODO 자동 생성된 메소드 스텁

		aluser =sdbHelper.selectAllUser();
		str= new String[aluser.size()+1];

		del_id = find.getText().toString();
		switch(v.getId())
		{

		case R.id.del:
			cnt=0;

			if(del_id.isEmpty()!=true){
				searchList = sdbHelper.selectAllUser();
				for (User user : searchList) {
					if (user.getName().equals(""+del_id)) {
						sdbHelper.deleteUser(user);
					}

				}

				Log.d("destiny", "After Delete");
				searchList = sdbHelper.selectAllUser();
				for (User user : searchList) {
					Log.d("destiny","ID : "+user.getId()+ " Name : " + user.getName() + "  Age : "
							+ user.getAge() + "  Sex : " + user.getSex() + "  Height : " 
							+ user.getHeight() + "  Weight : " + user.getWeight());



					str[cnt] =("Name : " + user.getName() + " Age : "
							+ user.getAge() + " Sex : " + user.getSex() + " Height : " 
							+ user.getHeight() + " Weight : " + user.getWeight());
					cnt++;
				}

				adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, str);
				adapter.setNotifyOnChange(true);
							
				
			}
			
			list_v.setAdapter(adapter);
			Log.d("destiny", "=======================");
			find.setText("");
			searchList = sdbHelper.selectAllUser();
			break;
		
	

	case R.id.search:
		cnt=0;
		aluser =sdbHelper.selectAllUser();
		str= new String[aluser.size()+1];
		if(del_id.isEmpty()!=true){
			findList = sdbHelper.selectAllUser();
			for (User user : searchList) {
				if (user.getName().equals(""+del_id)) {
					findList = sdbHelper.selectFindUser(user);
				}
			}

				Log.d("destiny", "=======================");
				Log.d("destiny", "Find "+ del_id+" data");
				for (User user : findList) {
					Log.d("destiny","ID : "+user.getId()+ " Name : " + user.getName() + "  Age : "
							+ user.getAge() + "  Sex : " + user.getSex() + "  Height : " 
							+ user.getHeight() + "  Weight : " + user.getWeight());



					str[cnt] =("Name : " + user.getName() + " Age : "
							+ user.getAge() + "  Sex : " + user.getSex() + " Height : " 
							+ user.getHeight() + " Weight : " + user.getWeight());
					cnt++;
				}
				
				adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1,str);
				adapter.setNotifyOnChange(true);
				

			Log.d("destiny", "=======================");
			list_v.setAdapter(adapter);
			find.setText("");
			break;
		}

		else{
			aluser =sdbHelper.selectAllUser();
			str= new String[aluser.size()];

			searchList = sdbHelper.selectAllUser();
			cnt=aluser.size();

			Log.d("destiny", "=======================");
			Log.d("destiny", "Read all data");

			for (User user : searchList) {
				Log.d("destiny","ID : "+user.getId()+ " Name : " + user.getName() + "  Age : "
						+ user.getAge() + "  Sex : " + user.getSex() + "  Height : " 
						+ user.getHeight() + "  Weight : " + user.getWeight());



				str[cnt-1] =("Name : " + user.getName() + " Age : "
						+ user.getAge() + " Sex : " + user.getSex() + " Height : " 
						+ user.getHeight() + " Weight : " + user.getWeight());
				cnt--;
			}
			
			adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, str);
			



		}
		Log.d("destiny", "=======================");
		list_v.setAdapter(adapter);
		searchList = sdbHelper.selectAllUser();
		find.setText("");
		break;
	}
}
}


/*   private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {


			boolean result = mDbOpenHelper.deleteColumn(position + 1);

			if(result){
				mInfoArray.remove(position);
				mAdapter.setArrayList(mInfoArray);
				mAdapter.notifyDataSetChanged();
			}else {

			}

			return false;
		}
	};


	}


	/**
 * OnClick Button
 * @param v
 */


/*
 * Layout
 */



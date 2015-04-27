package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class mainList extends ActionBarActivity {
	
	private ListView m_List;
	private DrawerLayout m_Drawer;
	private eventDataSource m_DataSource;
	private List<eventData> m_AllEvent;

	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	
	m_AllEvent = new ArrayList<eventData>();
	m_DataSource = new eventDataSource(getApplicationContext());
	
	Thread m = new Thread( new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			m_DataSource.syncAllData();
		}
	});
	m.run();
	
	//UserData mdata = new UserData(0, 3, "OldYoghesh", "10.66565654,13.65465456");
	//m_DataSource.addUser(mdata);
	setContentView(com.example.flareon.R.layout.basedrawer);
	m_Drawer = (DrawerLayout)findViewById(R.id.baseDrawer);
	m_List = (ListView)findViewById(com.example.flareon.R.id.mainList);
	m_List.setOnItemClickListener(new mListClickListener());
	
	newEvent frag = new newEvent(new eventDataAdder(null),m_DataSource.getAllUsers());
	FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.baseFrame,frag);
	transaction.commit();
}

@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

public class mListClickListener implements AdapterView.OnItemClickListener
{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1,  final int position, long arg3) {
		// TODO Auto-generated method stub
		m_Drawer.closeDrawer(m_List);
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	if(position == 0)
            	{
            		newEvent frag = new newEvent(new eventDataAdder(null), m_DataSource.getAllUsers());
            		FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            		transaction.replace(R.id.baseFrame,frag);
            		transaction.commit();
            	}
            	else if (position == 4) {
					FriendsList mFriendList = new FriendsList();
					mFriendList.allFriends(m_DataSource.getAllUsers());
					mFriendList.mAddtoDB = new addFriendToDB();
					FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            		transaction.replace(R.id.baseFrame,mFriendList);
            		transaction.commit();
				}
            	else if(position == 3)
            	{
            		ChangePasswordFragment AccountFrag = new ChangePasswordFragment();
            		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            		transaction.replace(R.id.baseFrame, AccountFrag);
            		transaction.commit();
            	}
            	else
            	{
	
            		m_AllEvent = m_DataSource.getAllEventData();
            		 SlidingTabFragment Slidingfrag = new SlidingTabFragment(new PageAdapter(getSupportFragmentManager()),new eventDataAdder(null));
            		FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
            		transaction.replace(R.id.baseFrame,Slidingfrag);
            		transaction.commit();
            	}
            }
        }, 200);

	}
}

public interface addEventData
{
	public void addDatatoExternalDB(eventData mEventdata);
	public void addDatatoInternalDB(eventData mEventdate);
}

public class eventDataAdder implements addEventData
{
	
	public eventData mData;
	
	eventDataAdder(eventData data)
	{
		mData = data;
	}

	@Override
	public void addDatatoExternalDB(eventData meventData) {
		// TODO Auto-generated method stub
		eventData arr[] = new eventData [1];
		arr[0] = meventData;
		SharedPreferences m_loginPref= getSharedPreferences("Login", Activity.MODE_PRIVATE);
		String username = m_loginPref.getString("name", "");
		
		new AddEventOnline(username).execute(arr);
	}

	@Override
	public void addDatatoInternalDB(eventData meventData) {
		// TODO Auto-generated method stub
		if(meventData.getEid() == 0)
		{m_DataSource.addEvent(meventData);}
		else
		{m_DataSource.updateEvent(meventData.getEid(), meventData);}
		
		return;
	}
	
	}

public class PageAdapter extends FragmentStatePagerAdapter
{

	public List<eventData> alldata;		
	public List<String> tabTexts;
	
	
	public PageAdapter(FragmentManager fm) {
		super(fm);
		alldata = m_DataSource.getAllEventData();
		tabTexts = new ArrayList<String>();
		for(int i = 0; i < alldata.size(); i++)
		{
			tabTexts.add(alldata.get(i).getName());
			//getItem(i);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return (Fragment)new newEvent(new eventDataAdder(alldata.get(arg0)),m_DataSource.getAllUsers());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alldata.size();
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
            
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();

        super.destroyItem(container, position, object);
    }
	
	}

public class addFriendToDB
{
	private String mName;

	public void addTodb(String name)
	{
		mName = name;
		m_DataSource.addUser(new UserData(0, 0, mName, "10,10"));
		SharedPreferences m_loginPref= getSharedPreferences("Login", MODE_PRIVATE);
		String username = m_loginPref.getString("name", "");
		String[] vals = {username,name,"0"};
		
		new AddFriendsOnline().execute(vals);
	}
	
}

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	Intent mStart = new Intent(getApplicationContext(),MainActivity.class);
	mStart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	mStart.putExtra("EXIT", true);
	startActivity(mStart);
	
}

}

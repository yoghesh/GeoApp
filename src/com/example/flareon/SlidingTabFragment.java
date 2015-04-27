package com.example.flareon;

import java.util.ArrayList;
import java.util.List;

import com.example.flareon.mainList.PageAdapter;
import com.example.flareon.mainList.eventDataAdder;

import android.R.color;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SlidingTabFragment extends android.support.v4.app.Fragment {

	private SlidingView mSlidingview;
	private ViewPager mPageview;
	private PageAdapter m_adapter;
	private eventDataAdder m_EventAdder;
	private FragmentActivity m_Active;
	
	SlidingTabFragment(PageAdapter mAdapt, eventDataAdder adder)
	{
		m_adapter = mAdapt;
		m_EventAdder = adder;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		m_Active = (FragmentActivity)activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.sliderfragment, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mSlidingview = (SlidingView)view.findViewById(R.id.SliderTab);
		mPageview = (ViewPager)view.findViewById(R.id.SlideViewPager);
		mPageview.setBackgroundColor(color.black);
		mPageview.setAdapter(m_adapter);
		mSlidingview.setViewPager(mPageview);
		
	}
	
	/*public class tabAdapter extends PagerAdapter
	{

		List<String> tabTexts = new ArrayList<String>();
		public tabAdapter()
		{
			for(int i = 0; i < m_AllEvents.size(); i++)
			{
				tabTexts.add(m_AllEvents.get(i).getName());
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tabTexts.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0 == arg1);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			eventData lData = m_AllEvents.get(position);
			//View v = LayoutInflater.from(container.getContext()).inflate(R.layout.newevent, container,false);
			//v.setBackgroundColor(android.graphics.Color.BLACK);
			//TextView txtEventName = (TextView)v.findViewById(R.id.txtEventNmae);
			//txtEventName.setText(lData.getName());
			//container.addView(v);
			
			
			newEvent frag = new newEvent(m_EventAdder);
    		FragmentTransaction transaction =  m_Active.getSupportFragmentManager().beginTransaction();
    		transaction.replace(R.id.baseFrame,frag);
    		transaction.commit();
			
			return null;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View)object);
		}*/
		
		
		
	//}
}

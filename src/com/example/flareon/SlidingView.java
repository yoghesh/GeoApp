package com.example.flareon;

import com.example.flareon.mainList.PageAdapter;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class SlidingView extends HorizontalScrollView {

	private float mDensity;
	private static int TITLE_OFFSET = 24;
	private static int TAB_VIEW_PADDING = 16;
	private static int TAB_VIEW_TEXT_SIZE = 12;
	private SlidingTab mSlideTab;
	private ViewPager mViewPager;
	private ViewPager.OnPageChangeListener mListener;
	
	public SlidingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setWillNotDraw(false);
		setHorizontalScrollBarEnabled(false);
		setFillViewport(true);
		
		DisplayMetrics mDispMat = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(mDispMat);
		mDensity = mDispMat.density;
		mSlideTab = new SlidingTab(context);
		this.addView(mSlideTab,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
	}

	public void setCustomListener(OnPageChangeListener listener)
	{
		mListener = listener;
	}
	
	public SlidingView(Context context, AttributeSet attrs)
	{this(context,attrs,0);}
	
	public SlidingView(Context context)
	{this(context,null);}
	
	public void setViewPager(ViewPager v)
	{
		mSlideTab.removeAllViews();
		mViewPager = v;
		
		if (v != null)
		{
			mViewPager.setOnPageChangeListener(new pageListener());
			PopulateTabStrip();
		}
	}
	
	public void PopulateTabStrip()
	{
		PagerAdapter myAdapter = mViewPager.getAdapter();
		for(int i =0; i<myAdapter.getCount(); i++)
		{
			TextView tabview = (TextView) createTextView(getContext());
			//tabview.setBackgroundColor(android.graphics.Color.GRAY);
			tabview.setText(((PageAdapter)myAdapter).tabTexts.get(i).toCharArray(),0,((PageAdapter)myAdapter).tabTexts.get(i).toCharArray().length);
			tabview.setTextColor(android.graphics.Color.WHITE);
			tabview.setTag(i);
			tabview.setOnClickListener(new tabOnClickListener());
			mSlideTab.addView(tabview);
		}
	}
	
	public class tabOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView txtV = (TextView)v;
			Integer position = (Integer)(v.getTag());
			mViewPager.setCurrentItem(position);
		}
		
	}
	
	
	public View createTextView(Context context)
	{
		TextView v = new TextView(context);
		v.setGravity(android.view.Gravity.CENTER);
		v.setTextSize(TypedValue.COMPLEX_UNIT_DIP,TAB_VIEW_TEXT_SIZE);
		v.setTypeface(Typeface.DEFAULT_BOLD);
		
		int padding = (int)(TAB_VIEW_PADDING*mDensity);
		v.setPadding(padding, padding, padding, padding);
		
		return v;
	}
	
	public void setTabColorizer(SlidingTab.TabColorizer mColorizer)
	{
		mSlideTab.setCustomTabColorizer(mColorizer);
	}
	
	public void setIndicatorColor(int[] colors)
	{
		mSlideTab.setIndicatorColor(colors);
	}
	
	public void setDividerColor(int[] colors)
	{
		mSlideTab.setDividerColor(colors);
	}
	
	public class pageListener implements ViewPager.OnPageChangeListener
	{

		private int mScrollState;
		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			mScrollState = state;
			if(mListener!=null)
			{mListener.onPageScrollStateChanged(state);}
		}

		@Override
		public void onPageScrolled(int position, float offset, int offsetpixel) {
			// TODO Auto-generated method stub
			int tabCount = mSlideTab.getChildCount();
			if(tabCount == 0 || position <0 || position >= tabCount)
			{return;}
			mSlideTab.onTabViewPageChanged(position, offset);
			
			View SelectedTab = mSlideTab.getChildAt(position);
			int extraOffset = (int)(SelectedTab!=null?SelectedTab.getWidth()*offset:0);
			ScrollToTab(position, extraOffset);
			
			if(mListener!=null)
			{onPageScrolled(position, offset, offsetpixel);}
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if (mScrollState == ViewPager.SCROLL_STATE_IDLE)
			{mSlideTab.onViewerPageChanged(position, 0);
			ScrollToTab(position, 0);}
			if(mListener!=null)
			{mListener.onPageSelected(position);}
		}
		
		
	}
	
	public void ScrollToTab(int position, int offset)
	{
		int count = mSlideTab.getChildCount();
		
		if (count < 0 || position < 0 || position >= count)
		{return;}
		
		View v = mSlideTab.getChildAt(position);
		int scrollAmount = v.getLeft()+offset;
		
		if (count > 0 || offset > 0)
		{
			scrollAmount -= TITLE_OFFSET*mDensity;
		}
		
		scrollTo(scrollAmount, 0);
		
	}
	
}

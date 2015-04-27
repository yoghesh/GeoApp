package com.example.flareon;

import java.util.Currency;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

public class SlidingTab extends LinearLayout {

	private static int DIVIDER_THICKNESS = 1;
	private static int INDICATOR_THICKNESS = 5;
	private static int SEPERATOR_THICKNESS = 2;
	private static float DIVIDER_HEIGHT = 0.5f;
	private static int[] DEFAULT_SEPERATOR_COLOR = {0x19A319, 0x0000FC };
	private static int[] DEFAULT_INDICATOR_COLOR = {0x19A319, 0x0000FC };
	
	private SimpleTabColorizer m_DefaultTabColorizer;
	private float m_Density = 0.0f;
	private int mDividerThickness;
	private int mIndicatorThickness;
	private int mSeperatorThickness;
	private TabColorizer mCustomTabColorizer;
	
	private Paint mDividerPaint;
	private Paint mIndicatorPaint;
	private Paint mSeperatorPaint;
	
	private int mSelectedPosition;
	private float mSelectedOffset;
	
	public SlidingTab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		DisplayMetrics dispMatrices = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dispMatrices);
		m_Density = dispMatrices.density;
		setWillNotDraw(false);
		m_DefaultTabColorizer = new SimpleTabColorizer();
		m_DefaultTabColorizer.setIndicatorColors(DEFAULT_INDICATOR_COLOR);
		m_DefaultTabColorizer.setDividerColors(DEFAULT_SEPERATOR_COLOR);
		
		mDividerThickness = (int)(DIVIDER_THICKNESS*m_Density);
		mIndicatorThickness = (int)(INDICATOR_THICKNESS*m_Density);
		mSeperatorThickness = (int)(SEPERATOR_THICKNESS*m_Density);
		
		mIndicatorPaint = new Paint();
		mDividerPaint = new Paint();
		mDividerPaint.setStrokeWidth(mDividerThickness);
		mSeperatorPaint = new Paint();
		mSeperatorPaint.setColor(getColorFromInt(getColorFromInt(android.graphics.Color.WHITE)));
		this.setVisibility(View.VISIBLE);
		this.setOrientation(HORIZONTAL);
	}
	
	public SlidingTab(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		this(context,attrs,0);
	}

	public SlidingTab(Context context)
	{
		this(context,null);
	}

	
	public interface TabColorizer
	{
		public int getIndicatorColor(int position);
		public int getDividerColor(int position);
	}

	
	public class SimpleTabColorizer implements TabColorizer
	{
		private int[] mIndicatorColor;
		private int[] mDividerColor;

		@Override
		public int getIndicatorColor(int position) {
			// TODO Auto-generated method stub
			return mIndicatorColor[position%mIndicatorColor.length];
		}

		@Override
		public int getDividerColor(int position) {
			// TODO Auto-generated method stub
			return mDividerColor[position%mDividerColor.length];
		}
		
		public void setIndicatorColors(int[] colors)
		{ mIndicatorColor = colors;}
		
		public void setDividerColors(int[] colors)
		{mDividerColor = colors;}
		
	}
	
	public void setCustomTabColorizer(TabColorizer tabColorizer)
	{
		if (tabColorizer != null)
		{
			mCustomTabColorizer = tabColorizer;
			invalidate();
		}
	}
	
	public void setDividerColor(int[] colors)
	{
		mCustomTabColorizer = null;
		m_DefaultTabColorizer.setDividerColors(colors);
		invalidate();
	}
	
	public void setIndicatorColor(int[] colors)
	{
		mCustomTabColorizer = null;
		m_DefaultTabColorizer.setIndicatorColors(colors);
		invalidate();
	}
	
	public void onViewerPageChanged(int position, float offset)
	{
		mSelectedPosition = position;
		mSelectedOffset = offset;
		invalidate();
	}
	
	public int getColorFromInt(int color)
	{
		return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
	}
	
	public int BlendColor(int color1, int color2, float offset)
	{
		float invertOffset = 1f - offset;
		float r = offset*Color.red(color2) + invertOffset*Color.red(color1);
		float g = offset*Color.green(color2) + invertOffset*Color.green(color1);
		float b = offset*Color.blue(color2) + invertOffset*Color.blue(color1);
		
		return Color.rgb((int)r, (int)g, (int)b);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int height = getHeight();
		int mColor;
		int count = getChildCount();
		TabColorizer tabColor;
		if (mCustomTabColorizer != null)
		{
			tabColor = mCustomTabColorizer;
		}
		else
		{
			tabColor = m_DefaultTabColorizer;
		}
		
		if (count > 0)
		{
			View selectedView = getChildAt(mSelectedPosition);
			int left = selectedView.getLeft();
			int right = selectedView.getRight();
			mColor = getColorFromInt(tabColor.getIndicatorColor(mSelectedPosition));
			if (mSelectedOffset > 0f && mSelectedOffset < 1f)
			{
				View nextView = getChildAt(mSelectedPosition+1);
				left = (int)(left + (nextView.getLeft()-left)*mSelectedOffset);
				right = (int)(right + (nextView.getRight()-right)*mSelectedOffset);
				mColor = BlendColor(mColor, tabColor.getIndicatorColor(mSelectedPosition+1), mSelectedOffset);
				
			}
			
			mIndicatorPaint.setColor(mColor);
			canvas.drawRect(left, height - mIndicatorThickness, right, height, mIndicatorPaint);
			
			int dividerGeight = (int)(Math.max(0f, Math.min(DIVIDER_HEIGHT, 1f)) * height);
						
			for (int i = 0; i < getChildCount(); i++)
			{
				mDividerPaint.setColor(getColorFromInt(tabColor.getDividerColor(i)));
				View v = getChildAt(i);
				canvas.drawRect(v.getRight(), v.getHeight()-dividerGeight, v.getRight()+mDividerThickness, height, mDividerPaint);	
			}
			
			
			canvas.drawRect(getLeft(), height-mSeperatorThickness, getRight(), height, mSeperatorPaint);
			
		}
	}
	
	public void onTabViewPageChanged(int position, float offset)
	{
		mSelectedPosition = position;
		mSelectedOffset = offset;
		invalidate();
	}
}

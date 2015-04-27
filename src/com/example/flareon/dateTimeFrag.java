package com.example.flareon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.flareon.newEvent.dateTimeGetter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TimePicker;

public class dateTimeFrag extends android.support.v4.app.DialogFragment {

	private TimePicker mTimepick;
	private CalendarView mCalenderView;
	private Button btnOK;
	private dateTimeGetter mDatetimeGetter;
	private String date;
	private String time;
	
	dateTimeFrag(dateTimeGetter getter)
	{
		mDatetimeGetter = getter;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	View v = inflater.inflate(R.layout.datetime, container, false);
	getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
	mTimepick = (TimePicker)v.findViewById(R.id.timePicker1);
	mCalenderView = (CalendarView)v.findViewById(R.id.calendarView1);
	date = mDatetimeGetter.getdate();
	time = mDatetimeGetter.getTime();
	mCalenderView.setHorizontalScrollBarEnabled(true);
	if (date != null)
	{SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
	try {
		mCalenderView.setDate(sdf.parse(date).getTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(time != null)
	{
		String[] splitTime = time.split(":");
		mTimepick.setCurrentHour(Integer.parseInt(splitTime[0]));
		mTimepick.setCurrentMinute(Integer.parseInt(splitTime[1]));
	}
	}
	mCalenderView.setOnDateChangeListener(new OnDateChangeListener() {
		
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			date = dayOfMonth + "-" + month + "-" + year;
			// TODO Auto-generated method stub
			
		}
	});
	btnOK = (Button)v.findViewById(R.id.btndateok);
	btnOK.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mCalenderView.getDate();
			time = mTimepick.getCurrentHour() + ":" + mTimepick.getCurrentMinute();
			mDatetimeGetter.setDateTime(date, time);
			dismiss();
		}
	});
	return v;
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mCalenderView.getDate();
		time = mTimepick.getCurrentHour() + ":" + mTimepick.getCurrentMinute();
		mDatetimeGetter.setDateTime(date, time);

	}
}

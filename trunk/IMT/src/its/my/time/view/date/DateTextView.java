package its.my.time.view.date;

import its.my.time.util.DateUtil;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class DateTextView extends TextView implements OnDateSetListener, OnClickListener {

	private DatePickerDialog dialog;
	private Calendar date;
	
	
	
	
	public DateTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public DateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(null);
	}

	public DateTextView(Context context) {
		super(context);
		init(null);
	}

	private void init(Calendar initDate) {
		if(initDate!=null) {
			date = initDate;
		} else {
			date = Calendar.getInstance();
		}
		setOnClickListener(this);
		setText(DateUtil.getDay(date));
	}

	@Override
	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		date.set(Calendar.YEAR,year);
		date.set(Calendar.MONTH,month);
		date.set(Calendar.DAY_OF_MONTH,day);
		setText(DateUtil.getDay(date));
	}

	@Override
	public void onClick(View arg0) {
		dialog = new DatePickerDialog(getContext(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		init(date);
	}

	
}
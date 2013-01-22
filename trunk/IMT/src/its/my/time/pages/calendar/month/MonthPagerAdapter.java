package its.my.time.pages.calendar.month;

import its.my.time.pages.calendar.base.BasePagerAdapter;
import its.my.time.util.DateUtil;

import java.util.Calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MonthPagerAdapter extends BasePagerAdapter {

	public MonthPagerAdapter(FragmentManager fm, Calendar cal) {
		super(fm, cal);
	}

	@Override
	protected Fragment getView(int incrementation) {
		final Calendar displayedMonth = (Calendar) getCurrentCalendar().clone();
		displayedMonth.add(Calendar.MONTH, incrementation);
		return new MonthFragment(displayedMonth);
	}

	@Override
	protected String getCustomTitle(int incrementation) {
		final Calendar displayedDay = (Calendar) getCurrentCalendar().clone();
		displayedDay.add(Calendar.MONTH, incrementation);
		return DateUtil.getMonth(displayedDay.get(Calendar.YEAR),
				displayedDay.get(Calendar.MONTH));
	}
}
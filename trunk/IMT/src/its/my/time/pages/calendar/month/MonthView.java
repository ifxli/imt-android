package its.my.time.pages.calendar.month;

import its.my.time.R;
import its.my.time.data.bdd.compte.CompteBean;
import its.my.time.data.bdd.compte.CompteRepository;
import its.my.time.data.bdd.compte.CompteRepository.OnCompteChangedListener;
import its.my.time.data.bdd.events.eventBase.EventBaseBean;
import its.my.time.data.bdd.events.eventBase.EventBaseRepository;
import its.my.time.pages.calendar.base.BaseView;
import its.my.time.util.DateUtil;
import its.my.time.util.IdUtil;
import its.my.time.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.MonthDisplayHelper;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MonthView extends BaseView implements OnCompteChangedListener {

	private final MonthDisplayHelper helper;
	private final OnDayClickListener listener;
	private SparseArray<CompteBean> comptes;
	private SparseArray<List<View>> eventLittleViews;
	private CompteRepository compteRepo;

	public MonthView(Context context, Calendar cal, OnDayClickListener listener) {
		super(context);
		this.helper = new MonthDisplayHelper(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), DateUtil.FIRST_DAY);
		this.listener = listener;
	}

	@Override
	protected View createView() {

		if(compteRepo == null) {
			compteRepo = new CompteRepository(getContext());
		}
		compteRepo.addOnCompteCHangedListener(this);
		List<CompteBean> listComptes = compteRepo.getAllCompteByUid(PreferencesUtil.getCurrentUid(getContext()));

		if(comptes == null) {
			comptes = new SparseArray<CompteBean>();
			eventLittleViews = new SparseArray<List<View>>();
			for (CompteBean compte : listComptes) {
				comptes.put(Integer.valueOf(compte.getId()), compte);
				eventLittleViews.put(compte.getId(), new ArrayList<View>());
			}
		}

		final LinearLayout view = (LinearLayout) inflate(getContext(),R.layout.activity_calendar_month, null);
		createTabDay(view);
		return view;
	}

	@Override
	protected String getTopBarText() {
		return DateUtil.getMonth(this.helper.getYear(), this.helper.getMonth());
	}

	@SuppressWarnings("deprecation")
	private void addStyleToday(View view) {
		Drawable dr = view.getBackground();
		dr = dr.mutate();
		dr.setColorFilter(Color.parseColor("#FFFFCC"), Mode.MULTIPLY);
		view.setBackgroundDrawable(dr);
	}

	private void createTabDay(LinearLayout view) {

		boolean isInMois = false;
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);

		final LinearLayout tabJour = (LinearLayout) view
				.findViewById(R.id.llTabJour);
		LinearLayout ligne;
		RelativeLayout layoutDay;
		for (int i = 0; i < 5; i++) {
			ligne = (LinearLayout) tabJour.getChildAt(i);

			// cellule du num�rio de semaine

			cal.set(this.helper.getYear(), this.helper.getMonth(),
					this.helper.getDayAt(i, 0));

			if (!isInMois) {
				if (i <= 1) {
					cal.add(Calendar.MONTH, -1);
				} else {
					cal.add(Calendar.MONTH, 1);
				}
			}

			final TextView txtVwSem = (TextView) ligne.getChildAt(0);
			txtVwSem.setText(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
			txtVwSem.setId(IdUtil.getWeekId(cal.get(Calendar.YEAR),
					cal.get(Calendar.WEEK_OF_YEAR)));

			// liste des jours
			for (int j = 1; j < 8; j++) {
				// detecte si le jour est dans le mois en cours
				if (this.helper.getDayAt(i, j - 1) == 1) {
					isInMois = !isInMois;
				}

				layoutDay = (RelativeLayout) ligne.getChildAt(j);
				TextView txtVw = (TextView) layoutDay
						.findViewById(R.id.activity_calendar_month_day_label);
				txtVw.setId(IdUtil.getDayId(this.helper.getYear(),
						this.helper.getMonth(), this.helper.getDayAt(i, j - 1)));
				txtVw.setEnabled(isInMois);
				txtVw.setText(String.valueOf(this.helper.getDayAt(i, j - 1)));
				if (!isInMois) {
					txtVw.setTextColor(getResources().getColor(R.color.grey));
				}

				final GregorianCalendar today = new GregorianCalendar();

				boolean isToday = false;
				if (today.get(Calendar.YEAR) == this.helper.getYear()
						&& today.get(Calendar.MONTH) == this.helper.getMonth()
						&& today.get(Calendar.DAY_OF_MONTH) == this.helper
						.getDayAt(i, j - 1) && isInMois) {
					addStyleToday(layoutDay);
					isToday = true;
				}

				final GregorianCalendar calDeb = new GregorianCalendar(
						this.helper.getYear(), this.helper.getMonth(),
						this.helper.getDayAt(i, j - 1), 0, 0, 0);
				final GregorianCalendar calFin = new GregorianCalendar(
						this.helper.getYear(), this.helper.getMonth(),
						this.helper.getDayAt(i, j - 1), 0, 0, 0);
				calFin.add(Calendar.DAY_OF_MONTH, 1);

				List<EventBaseBean> listEventsFinal = new ArrayList<EventBaseBean>();
				listEventsFinal = new EventBaseRepository(getContext()).getAllEvents(calDeb, calFin);

				final LinearLayout layoutListe = (LinearLayout) layoutDay.findViewById(R.id.activity_calendar_month_day_liste);
				if(listEventsFinal.size() > 0) {
					ViewGroup eventLayout;
					TextView textEvent;
					for (int nbEvents = 0; nbEvents < listEventsFinal.size(); nbEvents++) {
						final EventBaseBean eventBaseBean = listEventsFinal.get(nbEvents);
						eventLayout = (ViewGroup) inflate(getContext(),R.layout.activity_calendar_month_day_event, null);
						eventLayout.findViewById(R.id.activity_calendar_month_day_event_frame).setBackgroundColor(compteRepo.getById(eventBaseBean.getCid()).getColor());
						textEvent = (TextView) eventLayout.findViewById(R.id.activity_calendar_month_day_event_text);
						textEvent.setText(eventBaseBean.getTitle());
						if (isToday) {
							textEvent.setBackgroundColor(Color.parseColor("#FFFFCC"));
						}
						CompteBean compte = comptes.get((int)eventBaseBean.getCid());
						if(!compte.isShowed()) {
							eventLayout.setVisibility(INVISIBLE);
						}
						layoutListe.addView(eventLayout);
						eventLittleViews.get((int)eventBaseBean.getCid()).add(eventLayout);
					}
				} else {
					layoutDay.findViewById(R.id.activity_calendar_month_day_liste_scroll).setVisibility(INVISIBLE);
				}
				if (this.listener != null) {
					final GregorianCalendar calListener = (GregorianCalendar) calDeb.clone();
					calListener.set(Calendar.HOUR_OF_DAY, 8);
					calListener.set(Calendar.HOUR_OF_DAY, 10);

					OnClickListener clickListener = new OnClickListener() {
						@Override
						public void onClick(View v) {
							MonthView.this.listener.onDayClickListener(calListener);
						}
					};

					OnLongClickListener longCLickListener = new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							MonthView.this.listener.onDayLongClickListener(calListener);
							return false;
						}
					};
					layoutDay.setOnClickListener(clickListener );
					layoutDay.setOnLongClickListener(longCLickListener);
					layoutListe.setOnClickListener(clickListener );
					layoutListe.setOnLongClickListener(longCLickListener);
				}
			}
		}
	}

	@Override public void onCompteAdded(CompteBean compte) {}
	@Override public void onCompteRemoved(CompteBean compte) {}

	@Override
	public void onCompteUpdated(CompteBean compte) {
		if(compte.isShowed()) {
			for (View v : eventLittleViews.get(compte.getId())) {
				v.setVisibility(VISIBLE);
			}
		} else {
			for (View v : eventLittleViews.get(compte.getId())) {
				v.setVisibility(INVISIBLE);
			}
		}
	}


	public interface OnDayClickListener {
		void onDayClickListener(GregorianCalendar day);

		void onDayLongClickListener(GregorianCalendar day);
	}

}

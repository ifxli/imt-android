package its.my.time.data.ws.events;

import its.my.time.data.bdd.base.BaseRepository;
import its.my.time.data.bdd.events.event.EventBaseBean;
import its.my.time.data.bdd.events.event.EventBaseRepository;
import its.my.time.data.ws.WSPostBase;
import its.my.time.util.DateUtil;
import its.my.time.util.Types;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class WSSendEvent extends WSPostBase<EventBaseBean>{

	public WSSendEvent(Context context, EventBaseBean event, PostCallback<EventBaseBean> callBack) {
		super(context, event, callBack);
	}

	@Override
	public String getUrl() {
		return "api/events";
	}
	
	@Override
	public BaseRepository<EventBaseBean> getRepository() {
		return new EventBaseRepository(getContext());
	}
	
	@Override
	public List<NameValuePair> intitialiseParams(List<NameValuePair> nameValuePairs) {
		EventBaseBean event = getObject();
		
		if(event.getDateSync().equals(DateUtil.createCalendar())) {
	        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_idEvent", String.valueOf(0)));
		} else {
	        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_idEvent", String.valueOf(event.getId())));
		}
		
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_title", event.getTitle()));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_content", event.getDetails()));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_date", String.valueOf(event.gethDeb().getTimeInMillis())));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_dateEnd", String.valueOf(event.gethFin().getTimeInMillis())));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_allDay", String.valueOf(event.isAllDay())));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_account", String.valueOf(event.getCid())));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_importance", "0"));
        nameValuePairs.add(new BasicNameValuePair("imt_event_form_general_type", Types.Event.getLabelById(event.getTypeId())));
		return nameValuePairs;
	}

}

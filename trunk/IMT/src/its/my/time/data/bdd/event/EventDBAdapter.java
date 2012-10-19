package its.my.time.data.bdd.event;

import its.my.time.data.bdd.DBAdapterBase;
import its.my.time.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EventDBAdapter extends DBAdapterBase{

	public static final String KEY_ID = "id";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_HDEB = "hdeb";
	public static final String KEY_HFIN = "hfin";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CID = "cid";
	public static final int KEY_INDEX_ID = 0;
	public static final int KEY_INDEX_DETAILS = 1;
	public static final int KEY_INDEX_HDEB = 2;
	public static final int KEY_INDEX_HFIN = 3;
	public static final int KEY_INDEX_TITLE = 4;
	public static final int KEY_INDEX_CID = 5;


	public static final String DATABASE_TABLE = "event";

	private String[] allAttr = new String[]{KEY_ID, KEY_DETAILS, KEY_HDEB, KEY_HFIN, KEY_TITLE, KEY_CID};

	public EventDBAdapter(Context context) {
		super(context);
	}

	public List<EventBean> ConvertCursorToMapObject(Cursor c) {
		List<EventBean> liste = new ArrayList<EventBean>();
		if (c.getCount() == 0){return liste;}
		c.moveToFirst();
		do {
			EventBean event = ConvertCursorToObject(c);
			liste.add(event);
		} while (c.moveToNext());
		c.close();
		return liste;
	}

	public EventBean ConvertCursorToObject(Cursor c) {
		EventBean event = new EventBean();
		event.setId(c.getInt(KEY_INDEX_ID));
		event.setCid(c.getInt(KEY_INDEX_CID));
		event.setTitle(c.getString(KEY_INDEX_TITLE));
		event.sethDeb(DateUtil.getDateFromISO(c.getString(KEY_INDEX_HDEB)));
		event.sethFin(DateUtil.getDateFromISO(c.getString(KEY_INDEX_HFIN)));
		event.setDetails(c.getString(KEY_INDEX_DETAILS));
		return event;
	}

	public EventBean ConvertCursorToOneObject(Cursor c) {
		if(c.getCount() <= 0) {
			return null;
		}
		c.moveToFirst();
		EventBean event = ConvertCursorToObject(c);
		c.close();
		return event;
	}

	public long insertEvent(EventBean event){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, event.getTitle());
		initialValues.put(KEY_DETAILS, event.getDetails());
		initialValues.put(KEY_HDEB, DateUtil.getTimeInIso(event.gethDeb()));
		initialValues.put(KEY_HFIN, DateUtil.getTimeInIso(event.gethFin()));
		initialValues.put(KEY_CID, event.getTitle());
		open();
		long res = this.db.insert(DATABASE_TABLE, null, initialValues);
		close();
		return res;
	}

	public boolean deleteEvent(long rowId) {
		open();
		boolean res = this.db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
		close();
		return res;
	}

	public Cursor getAllEvent() {
		open();
		Cursor c = this.db.query(DATABASE_TABLE,allAttr, null, null, null, null, null);
		close();
		return c;
	}
	
	public EventBean getById(long id) {
		open();
		Cursor c = this.db.query(DATABASE_TABLE,allAttr, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null);
		EventBean res = ConvertCursorToOneObject(c);
		close();
		return res;
	}
}

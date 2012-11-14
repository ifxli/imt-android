package its.my.time.data.bdd.compte;

import its.my.time.data.bdd.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CompteRepository extends DatabaseHandler{

	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_COLOR = "color";
	public static final String KEY_TYPE = "type";
	public static final String KEY_UID = "uid";

	public static final int KEY_INDEX_ID = 0;
	public static final int KEY_INDEX_TITLE = 1;
	public static final int KEY_INDEX_COLOR = 2;
	public static final int KEY_INDEX_TYPE = 3;
	public static final int KEY_INDEX_UID = 4;


	public static final String DATABASE_TABLE = "compte";

	public static final String CREATE_TABLE =  "create table " + DATABASE_TABLE + "("
			+ KEY_ID + " INTEGER primary key autoincrement,"
			+ KEY_TITLE + " TEXT not null,"
			+ KEY_COLOR + " INTEGER not null,"
			+ KEY_TYPE + " INTEGER not null,"
			+ KEY_UID + " INTEGER not null);";


	private String[] allAttr = new String[]{KEY_ID, KEY_TITLE, KEY_COLOR, KEY_TYPE, KEY_UID};

	public CompteRepository(Context context) {
		super(context);
	}

	public List<CompteBean> convertCursorToListObject(Cursor c) {
		List<CompteBean> liste = new ArrayList<CompteBean>();
		if (c.getCount() == 0){return liste;}
		c.moveToFirst();
		do {
			CompteBean compte = convertCursorToObject(c);
			liste.add(compte);
		} while (c.moveToNext());
		c.close();
		return liste;
	}

	public CompteBean convertCursorToObject(Cursor c) {
		CompteBean compte = new CompteBean();
		compte.setId(c.getInt(KEY_INDEX_ID));
		compte.setTitle(c.getString(KEY_INDEX_TITLE));
		compte.setType(c.getInt(KEY_INDEX_TYPE));
		compte.setColor(c.getInt(KEY_INDEX_COLOR));
		compte.setUid(c.getInt(KEY_INDEX_UID));
		return compte;
	}

	public CompteBean convertCursorToOneObject(Cursor c) {
		if(c.getCount() <= 0) {
			return null;
		}
		c.moveToFirst();
		CompteBean event = convertCursorToObject(c);
		c.close();
		return event;
	}

	public long insertCompte(CompteBean compte){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, compte.getTitle());
		initialValues.put(KEY_COLOR, compte.getColor());
		initialValues.put(KEY_TYPE, compte.getType());
		initialValues.put(KEY_UID, compte.getUid());
		open();
		long res = this.db.insert(DATABASE_TABLE, null, initialValues);
		close();
		return res;
	}

	public boolean deleteCompte(long rowId) {
		open();
		boolean res = this.db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
		close();
		return res;
	}

	public List<CompteBean> getAllCompteByUid(long uid) {
		open();
		Cursor c = this.db.query(DATABASE_TABLE,allAttr, KEY_UID + "=?", new String[] { "" + uid }, null, null, null);
		List<CompteBean> res = convertCursorToListObject(c);
		close();
		return res;
	}

	public CompteBean getById(long id) {
		open();
		Cursor c = this.db.query(DATABASE_TABLE,allAttr, KEY_ID + "=?", new String[] { "" + id }, null, null, null);
		close();
		CompteBean res = convertCursorToOneObject(c);
		return res;
	}
}
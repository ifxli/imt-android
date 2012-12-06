package its.my.time.pages.editable.events.plugins.odj;

import its.my.time.R;
import its.my.time.data.bdd.events.plugins.odj.OdjBean;
import its.my.time.util.DatabaseUtil;
import its.my.time.util.PreferencesUtil;

import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.mobeta.android.dslv.DragSortCursorAdapter;
import com.mobeta.android.dslv.DragSortListView;

public class OdjFragment extends SherlockFragment {

	private int eventId;
	private Button mButtonSend;
	private EditText mTextPdj;
	private DragSortListView mListOdj;
	private EditText mTextOdj;
	
	public OdjFragment(int eventId) {
		super();
		this.eventId = eventId;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		RelativeLayout mView = (RelativeLayout) inflater.inflate(R.layout.activity_event_odj, null);
		mListOdj = (DragSortListView)mView.findViewById(R.id.event_odj_liste);

		mListOdj.setAdapter(new OdjAdapter(getActivity(), eventId));
		
		mTextOdj = (EditText)mView.findViewById(R.id.event_odj_editOdj); 
		
		mButtonSend = (Button)mView.findViewById(R.id.event_odj_save);
		mButtonSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO envoyer via ws
				OdjBean odj = new OdjBean();
				odj.setValue(mTextOdj.getText().toString());
				odj.setEid(eventId);
				long res = DatabaseUtil.Plugins.getOdjRepository(getActivity()).insertOdj(odj);
				if(res < 0) {
					Toast.makeText(getActivity(), "Votre objet du jour n'a pu �tre envoy�.", Toast.LENGTH_SHORT).show();
				}
				mListOdj.setAdapter(new OdjAdapter(getActivity(), eventId));
				mTextOdj.setText("");
			}
		}); 
		
		
		return mView;
	}
}
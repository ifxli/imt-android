package its.my.time.pages.editable.events.meeting.details;

import its.my.time.data.bdd.events.eventBase.EventBaseBean;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class DetailsFragment extends SherlockFragment {

	private DetailsView mDetailsView;
	private EventBaseBean event;

	public DetailsFragment(EventBaseBean event) {
		this.event = event;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDetailsView = new DetailsView(getActivity(), event);
		return mDetailsView;
	}
}
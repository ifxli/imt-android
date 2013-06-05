package its.my.time.sip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IncomingCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		CallManager.loadCall(intent);
	}
}
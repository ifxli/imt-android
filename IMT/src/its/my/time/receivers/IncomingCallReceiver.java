package its.my.time.receivers;

import its.my.time.pages.call.IncomingCallActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IncomingCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//CallManager.loadCall(intent);
		context.startActivity(new Intent(context, IncomingCallActivity.class));
	}
}
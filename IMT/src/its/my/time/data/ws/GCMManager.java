package its.my.time.data.ws;

import its.my.time.Consts;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class GCMManager {

	public static String initGcm(Context context) {
		String gcmId = null;
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		if (GCMRegistrar.isRegistered(context)) {
			gcmId = GCMRegistrar.getRegistrationId(context);
		}
		String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			GCMRegistrar.register(context, Consts.GCM_PROJECT_ID);
			gcmId = GCMRegistrar.getRegistrationId(context);
		} else {
			gcmId = regId;
		}
		return gcmId;
	}


	public static void uploadPicture(Bitmap bitmap, int uid) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		byte [] ba = bao.toByteArray();
		String ba1=Base64.encodeToString(ba,0);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("image",ba1));
		nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(uid)));
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Consts.URL_UPLOAD);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


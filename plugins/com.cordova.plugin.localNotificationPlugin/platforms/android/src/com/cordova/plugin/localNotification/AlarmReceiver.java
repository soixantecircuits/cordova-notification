package com.cordova.plugin.localNotification;

import java.io.IOException;
import java.io.InputStream;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 * @author dvtoever
 */
public class AlarmReceiver extends BroadcastReceiver {

	public static final String TITLE = "ALARM_TITLE";
	public static final String SUBTITLE = "ALARM_SUBTITLE";
	public static final String TICKER_TEXT = "ALARM_TICKER";
	public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
	public static final String ICON = "ALARM_ICON";

	/* Contains time in 24hour format 'HH:mm' e.g. '04:30' or '18:23' */
	public static final String HOUR_OF_DAY = "HOUR_OF_DAY";
	public static final String MINUTE = "MINUTES";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("AlarmReceiver", "AlarmReceiver invoked!");

		Bundle bundle = intent.getExtras();
		NotificationManager notificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// Get notification Id
		int notificationId = 0;
		try {
			notificationId = Integer.parseInt(bundle.getString(NOTIFICATION_ID));
		} catch (Exception e) {
			Log.e(LocalNotification.TAG, "Unable to process alarm with id: " + bundle.getString(NOTIFICATION_ID));
		}

		// Create onClick for toast notification
		Intent onClick = new Intent(context, AlarmHelper.class)
			.putExtra(AlarmReceiver.NOTIFICATION_ID, notificationId);
		// Create pending intent for onClick
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, onClick, PendingIntent.FLAG_CANCEL_CURRENT);

		// Build Notification
		Notification notification=null;
		Resources res = context.getResources();
		int height = (int) res.getDimension(android.R.dimen.notification_large_icon_height);
		int width = (int) res.getDimension(android.R.dimen.notification_large_icon_width);
		
		
		if(!bundle.getBoolean("externalIcon")){
			//Log.d("reciever","in");
			 AssetManager assetManager = context.getAssets();
		        InputStream istr = null;
		        try {
		            istr = assetManager.open("www/icon.png");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        Bitmap bitmap = BitmapFactory.decodeStream(istr);
		        try{
		        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		        }catch(Exception e){}
			notification= new Notification.Builder(context)
			.setSmallIcon(android.R.drawable.dark_header)
			.setContentTitle(bundle.getString(TITLE))
			.setContentText(bundle.getString(SUBTITLE))
			.setTicker(bundle.getString(TICKER_TEXT))
			.setContentIntent(contentIntent)
			//.setVibrate(new long[] { 0, 100, 200, 300 })
			.setWhen(System.currentTimeMillis())
			.setLargeIcon(bitmap)
			.build();
		}else{
			//Log.d("reciever","bit");
			BitmapFactory.Options options = new BitmapFactory.Options();
			  // will results in a much smaller image than the original
			  options.inSampleSize = 1;
			  Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString(ICON), options);
			  try{
		        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false); 
			  }catch(Exception e){
				  AssetManager assetManager = context.getAssets();
			        InputStream istr = null;
			        try {
			            istr = assetManager.open("www/icon.png");
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
			        bitmap = BitmapFactory.decodeStream(istr);
			        try{
			        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
			        }catch(Exception ex2){}
			  }
			  
			//Bitmap bitmap = getBitmapFromURL(bundle.getString(ICON));
			notification= new Notification.Builder(context)
			.setSmallIcon(android.R.drawable.dark_header)
			.setContentTitle(bundle.getString(TITLE))
			.setContentText(bundle.getString(SUBTITLE))
			.setTicker(bundle.getString(TICKER_TEXT))
			.setContentIntent(contentIntent)
			//.setVibrate(new long[] { 0, 100, 200, 300 })
			.setWhen(System.currentTimeMillis())
			.setLargeIcon(bitmap)
			.build();
		}

		Log.d(LocalNotification.TAG, "Notification Instantiated: Title: " + bundle.getString(TITLE) + ", Sub Title: " + bundle.getString(SUBTITLE) + ", Ticker Text: " + bundle.getString(TICKER_TEXT));
		/*
		 * If you want all reminders to stay in the notification bar, you should
		 * generate a random ID. If you want do replace an existing
		 * notification, make sure the ID below matches the ID that you store in
		 * the alarm intent.
		 */
		notificationMgr.notify(notificationId, notification);
		Log.d(LocalNotification.TAG, "Notification created!");

		// NOTE: If the application is closed state, JS gets the notification when the application is opened from tapping the notification
		// see AlarmHelper.java

		// If we are in background state we still have access to Cordova WebView
		if (LocalNotification.getCordovaWebView() != null) {
			// Send JS a message
			LocalNotification.getCordovaWebView().sendJavascript("cordova.fireDocumentEvent('receivedLocalNotification', { active : true, notificationId : " + notificationId + " })");
		}
	}
}

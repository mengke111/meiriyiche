package com.zounikeji.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.zounikeji.constant.ApiConstant;
import com.zounikeji.constant.NotifiConstant;
import com.zounikeji.meiriyiche.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;



public class AppVersionUpdateService extends Service {

	private static final int NOTIFY_DOW_ID = 0;
	private static final int NOTIFY_OK_ID = 1;

	private Context mContext = this;
	private boolean cancelled;
	private NotificationManager mNotificationManager;
	private Notification mNotification;

	private int progress;
	private int fileSize;
	private int readSize;
	private int downSize;
	private File downFile;

	private DownlaodApkBroadcastReciever mReciver;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NotifiConstant.NOTICE_DOWNLOAD_UPDATE:
				RemoteViews contentView = mNotification.contentView;
				contentView.setTextViewText(R.id.rate, msg.arg1 + "%");
				contentView.setProgressBar(R.id.progress, 100, msg.arg1, false);
				mNotificationManager.notify(NOTIFY_DOW_ID, mNotification);
				break;
			case NotifiConstant.NOTICE_DOWNLOAD_DOWNLOADING:
				mNotificationManager.cancel(NOTIFY_DOW_ID);
				createNotification(NOTIFY_OK_ID);
				openFile(downFile);
				break;
			case NotifiConstant.NOTICE_DOWNLOAD_STOP:
				mNotificationManager.cancel(NOTIFY_DOW_ID);
				cancelled = true;
				AppVersionUpdateService.this.stopService(new Intent(AppVersionUpdateService.this, AppVersionUpdateService.class));
				break;
			}
		};
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mReciver = new DownlaodApkBroadcastReciever();
		registerBoradcastReceiver();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	private void startDownload(String download_url) {

		fileSize = 0;
		readSize = 0;
		downSize = 0;
		progress = 0;

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL myURL = new URL(download_url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			fileSize = conn.getContentLength();
			is = conn.getInputStream();

			if (is == null) {
				throw new RuntimeException("stream is null");
			}
			if (is != null) {
				downFile = new File(Environment.getExternalStorageDirectory(), ApiConstant.APP_NAME);
				fos = new FileOutputStream(downFile);
				byte buf[] = new byte[1024 * 1024];

				while ((readSize = is.read(buf)) > 0) {
					if (!cancelled) {
						fos.write(buf, 0, readSize);
						downSize += readSize;
						sendMessage(0);
					} else {
						downFile.delete();
						break;
					}
				}
				if (downFile.exists()) {
					installApk();
				}
				if (cancelled) {
					handler.sendEmptyMessage(NotifiConstant.NOTICE_DOWNLOAD_STOP);
					downFile.delete();
				} else {
					handler.sendEmptyMessage(NotifiConstant.NOTICE_DOWNLOAD_DOWNLOADING);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos)
					fos.close();
				if (null != is)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(int what) {
		int num = (int) ((double) downSize / (double) fileSize * 100);
		if (num > progress) {
			progress = num;
			Message msg0 = new Message();
			msg0.what = what;
			msg0.arg1 = progress;
			handler.sendMessage(msg0);
		}
	}

	private void installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), ApiConstant.APP_NAME)), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		AppVersionUpdateService.this.stopService(new Intent(AppVersionUpdateService.this, AppVersionUpdateService.class));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		cancelled = false;
		createNotification(NOTIFY_DOW_ID);
		new Thread(new Runnable() {
			@Override
			public void run() {
				startDownload(ApiConstant.DOWN_UPDATE_URL);
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	class DownlaodApkBroadcastReciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if (arg1.getAction().equals(NotifiConstant.CANCEL_DOWNLOAD_NOTICE_BAR)) {
				Message msg0 = new Message();
				msg0.what = 3;
				handler.sendMessage(msg0);
			}
		}
	}

	/**
	 * 娉ㄥ唽骞挎挱
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(NotifiConstant.CANCEL_DOWNLOAD_NOTICE_BAR);
		registerReceiver(mReciver, myIntentFilter);
	}

	@SuppressWarnings("deprecation")
	public void createNotification(int notifyId) {
		switch (notifyId) {
		case NOTIFY_DOW_ID:
			int icon = R.drawable.ic_download_logo;
			CharSequence tickerText = "寮�涓嬭浇...";
			long when = System.currentTimeMillis();
			mNotification = new Notification(icon, tickerText, when);
			mNotification.flags = Notification.FLAG_ONGOING_EVENT;
			RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification);
			contentView.setTextViewText(R.id.fileName, "747鍔╂墜 (姝ｅ湪涓嬭浇...)");
			mNotification.contentView = contentView;
			PendingIntent contentIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(NotifiConstant.CANCEL_DOWNLOAD_NOTICE_BAR),
					PendingIntent.FLAG_UPDATE_CURRENT);
			mNotification.contentIntent = contentIntent;
			contentView.setOnClickPendingIntent(NOTIFY_DOW_ID, contentIntent);
			break;
		case NOTIFY_OK_ID:
			int icon2 = R.drawable.ic_download_logo;
			CharSequence tickerText2 = "涓嬭浇瀹屾垚";
			long when2 = System.currentTimeMillis();
			mNotification = new Notification(icon2, tickerText2, when2);
			mNotification.flags = Notification.FLAG_AUTO_CANCEL;
			cancelled = true;
		}
	}

	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}

	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
		if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		if (!end.equals("apk")) {
			type += "/*";
		}
		return type;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.stopService(new Intent(this, AppVersionUpdateService.class));
		if (mReciver != null) {
			unregisterReceiver(mReciver);
		}
	}
}

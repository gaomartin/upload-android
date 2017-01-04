package com.pplive.media.upload.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.pplive.media.upload.bean.UploadInfo;

public class UploadDataBaseManager {

	final static String TAG = UploadDataBaseManager.class.getSimpleName();

	public static final String TABLE_UPLOAD = "table_upload";
	public static final String FIELD_LOCAL_PATH = "local_path";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_TOKEN = "token";
	public static final String FIELD_APITK = "apitk";
	public static final String FIELD_SIZE = "size";
	public static final String FIELD_STATE = "state";
	public static final String FIELD_PPFEATURE = "ppfeature";
	public static final String FIELD_FID = "fid";
	public static final String FIELD_PROGRESS = "progress";
	public static final String FIELD_USER_NAME = "user_name";
	public static final String FIELD_CATEGORY_ID = "categoryId";
	public static final String FIELD_CHANNEL_WEB_ID = "channel_web_id";

	private static UploadDataBaseManager mInstance;

	private UploadDataHelper mDataHelper;

	private SQLiteDatabase mDatabase;

	private UploadDataBaseManager(Context context) {
		mDataHelper = new UploadDataHelper(context);
		mDatabase = mDataHelper.getWritableDatabase();
	}

	public static UploadDataBaseManager getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new UploadDataBaseManager(context);
		}
		return mInstance;
	}

	public void insertUpload(UploadInfo info) {
		mDatabase.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put(FIELD_FID, info.getFid());
			values.put(FIELD_LOCAL_PATH, info.getLocalPath());
			values.put(FIELD_PROGRESS, info.getProgress());
			values.put(FIELD_SIZE, info.getSize());
			values.put(FIELD_STATE, info.getState());
			values.put(FIELD_USER_NAME, info.getUserName());
			values.put(FIELD_APITK, info.getApitk());
			values.put(FIELD_PPFEATURE, info.getPpfeature());
			values.put(FIELD_TOKEN, info.getToken());
			values.put(FIELD_NAME, info.getName());
			values.put(FIELD_CHANNEL_WEB_ID, info.getChannel_web_id());
			values.put(FIELD_CATEGORY_ID, info.getCategoryId());
			mDatabase.insert(TABLE_UPLOAD, null, values);
			mDatabase.setTransactionSuccessful();
		} finally {
			mDatabase.endTransaction();
		}
	}

	public void updateUpload(UploadInfo info) {
		mDatabase.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put(FIELD_FID, info.getFid());
			values.put(FIELD_LOCAL_PATH, info.getLocalPath());
			values.put(FIELD_NAME, info.getName());
			values.put(FIELD_PPFEATURE, info.getPpfeature());
			values.put(FIELD_PROGRESS, info.getProgress());
			values.put(FIELD_SIZE, info.getSize());
			values.put(FIELD_STATE, info.getState());
			values.put(FIELD_USER_NAME, info.getUserName());
			values.put(FIELD_TOKEN, info.getToken());
			values.put(FIELD_APITK, info.getApitk());
			values.put(FIELD_CHANNEL_WEB_ID, info.getChannel_web_id());
			values.put(FIELD_CATEGORY_ID, info.getCategoryId());
			mDatabase.update(TABLE_UPLOAD, values, BaseColumns._ID + "=?",
					new String[] { String.valueOf(info.getId()) });
			mDatabase.setTransactionSuccessful();
		} finally {
			mDatabase.endTransaction();
		}
	}

	public void deleteUploadById(long id) {
		mDatabase.beginTransaction();
		try {
			mDatabase.delete(TABLE_UPLOAD, BaseColumns._ID + "=?", new String[] { String.valueOf(id) });
			mDatabase.setTransactionSuccessful();
		} finally {
			mDatabase.endTransaction();
		}
	}

	public List<UploadInfo> searchAllUploads(String username) {
		List<UploadInfo> result = new ArrayList<UploadInfo>();
		Cursor c = null;
		try {
			c = mDatabase.rawQuery("SELECT * FROM " + TABLE_UPLOAD + " WHERE " + FIELD_USER_NAME + "=?",
					new String[] { username });
			if (c != null) {
				while (c.moveToNext()) {
					UploadInfo info = new UploadInfo();
					info.setFid(c.getString(c.getColumnIndex(FIELD_FID)));
					info.setId(c.getInt(c.getColumnIndex(BaseColumns._ID)));
					info.setLocalPath(c.getString(c.getColumnIndex(FIELD_LOCAL_PATH)));
					info.setName(c.getString(c.getColumnIndex(FIELD_NAME)));
					info.setPpfeature(c.getString(c.getColumnIndex(FIELD_PPFEATURE)));
					info.setProgress(c.getInt(c.getColumnIndex(FIELD_PROGRESS)));
					info.setSize((c.getString(c.getColumnIndex(FIELD_SIZE))));
					info.setApitk((c.getString(c.getColumnIndex(FIELD_APITK))));
					info.setToken((c.getString(c.getColumnIndex(FIELD_TOKEN))));
					info.setState(c.getInt(c.getColumnIndex(FIELD_STATE)));
					info.setChannel_web_id(c.getString(c.getColumnIndex(FIELD_CHANNEL_WEB_ID)));
					info.setUserName(c.getString(c.getColumnIndex(FIELD_USER_NAME)));
					info.setCategoryId(c.getInt(c.getColumnIndex(FIELD_CATEGORY_ID)));
					result.add(info);
				}
			}
			return result;
		} finally {
			c.close();
		}
	}

	public List<String> searchVideoPaths(String username) {
		List<String> pathList = new ArrayList<String>();
		Cursor c = null;
		try {
			c = mDatabase.rawQuery("SELECT * FROM " + TABLE_UPLOAD + " WHERE " + FIELD_USER_NAME + "=?",
					new String[] { username });
			if (c != null) {
				while (c.moveToNext()) {

					pathList.add(c.getString(c.getColumnIndex(FIELD_LOCAL_PATH)));
				}
			}
			return pathList;
		} finally {
			c.close();
		}
	}

	class UploadDataHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "upload.db";
		private static final int DATABASE_VERSION = 1;

		public UploadDataHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(String.format(
					"CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " // BaseColumns._ID
							+ "%s TEXT, " // FIELD_LOCAL_PATH
							+ "%s TEXT, " // FIELD_NAME
							+ "%s TEXT, " // FIELD_TOKEN
							+ "%s TEXT, " // FIELD_APITK
							+ "%s TEXT, " // FIELD_SIZE
							+ "%s INTEGER, " // FIELD_STATE
							+ "%s TEXT, " // FIELD_PPFEATURE
							+ "%s TEXT, " // FIELD_FID
							+ "%s TEXT, "// FIELD_CATEGORY_ID
							+ "%s TEXT, "// FIELD_CHANNEL_WEB_ID
							+ "%s INTEGER, " // FIELD_PROGRESS
							+ "%s TEXT)", // FIELD_USER_NAME
					TABLE_UPLOAD, BaseColumns._ID, FIELD_LOCAL_PATH, FIELD_NAME, FIELD_TOKEN, FIELD_APITK, FIELD_SIZE,
					FIELD_STATE, FIELD_PPFEATURE, FIELD_FID, FIELD_CATEGORY_ID, FIELD_CHANNEL_WEB_ID, FIELD_PROGRESS,
					FIELD_USER_NAME));
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}

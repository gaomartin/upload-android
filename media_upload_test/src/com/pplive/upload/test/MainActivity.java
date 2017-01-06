package com.pplive.upload.test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pplive.media.upload.UpLoadManagerListener;
import com.pplive.media.upload.UploadManager;
import com.pplive.media.upload.bean.UploadInfo;
import com.pplive.media.upload.util.LogUtils;
import com.pplive.upload.test.util.FileUtils;
import com.pplive.upload.test.util.StringUtil;
import com.pplive.upload.test.util.UriUtils;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements UpLoadManagerListener {
	private final int RESULT = 100;
	private final int RECORD_VIDEO = 101;
	private Context mContext;
	private TextView tvPath;
	private UploadManager mUploadManager;
	private List<UploadInfo> mDatas = new ArrayList<UploadInfo>();
	private List<String> videoPaths = new ArrayList<String>();
	private ListView listView;
	private UploadListAdapter adapter;
	private String videoPath;
	private String uptokenUrl = "http://115.231.44.26:8081/uploadtest/uptoken";
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0x1001:
				getUploadListFromDb();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;

		tvPath = (TextView) findViewById(R.id.tv_path);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new UploadListAdapter(mContext);
		listView.setAdapter(adapter);
		mUploadManager = UploadManager.init(mContext, uptokenUrl);
		mUploadManager.setListener(this);
		GetVideoPathTask getVideoPathTask = new GetVideoPathTask();
		getVideoPathTask.execute();
		//
		GetReLunchDataTask getReLunchDataTask = new GetReLunchDataTask();
		getReLunchDataTask.execute();
	}

	private void getUploadListFromDb() {
		GetDataTask getDataTask = new GetDataTask();
		getDataTask.execute();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_seletvideo:
			Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			innerIntent.setType("video/*");
			Intent wrapperIntent = Intent.createChooser(innerIntent, null);
			startActivityForResult(wrapperIntent, RESULT);
			break;
		case R.id.btn_recordvideo:// 录制视频
			Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
			startActivityForResult(mIntent, RECORD_VIDEO);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			if (requestCode == RESULT) {// 本地视频
				Uri uri = data.getData();
				videoPath = UriUtils.getPath(mContext, uri);
				tvPath.setText(videoPath);
				if (!StringUtil.isEmpty(videoPath)) {
					if (FileUtils.getVideoFileSize(videoPath) > 1024 * 1024 * 1) {
						int start = videoPath.lastIndexOf("/");
						String name = videoPath.substring(start + 1, videoPath.length());
						LogUtils.error("videoPaths =" + videoPaths.toString());
						if (videoPaths.contains(videoPath)) {
							LogUtils.error("已添加过 videoPath =" + videoPath.toString());
							Toast.makeText(mContext, "已添加过", 0).show();
						} else {
							LogUtils.error("未添加过 videoPath =" + videoPath.toString());
							mUploadManager.insertVideo(videoPath, name);
						}
					} else {
						Toast.makeText(mContext, "文件小于1M,无法上传", 0).show();
					}
				}
			} else if (requestCode == RECORD_VIDEO) {// 录像
				Uri uri = data.getData();
				String videoPath = UriUtils.getPath(mContext, uri);
				tvPath.setText(videoPath);
				if (!StringUtil.isEmpty(videoPath)) {
					if (FileUtils.getVideoFileSize(videoPath) > 1024 * 1024 * 1) {
						int start = videoPath.lastIndexOf("/");
						String name = videoPath.substring(start + 1, videoPath.length());
						if (videoPaths.contains(videoPath)) {
							Toast.makeText(mContext, "已添加过", 0).show();
						} else {
							mUploadManager.insertVideo(videoPath, name);
						}
					} else {
						Toast.makeText(mContext, "文件小于1M,无法上传", 0).show();
					}
				}
			}
		}
	}

	@Override
	public void onStateChange(UploadInfo info) {
		LogUtils.error("onStateChange fid:" + info.getFid() + "  progress =" + info.getProgress() + " state = "
				+ info.getState());
		for (UploadInfo in : mDatas) {
			if (info.getId() == in.getId()) {
				in.setProgress(info.getProgress());
				break;
			}
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				adapter.refreshData(mDatas);
			}
		});
	}

	@Override
	public void onUploadError(UploadInfo info) {
		LogUtils.error("== onUploadError() ==");
		if (info.getState() == UploadInfo.STATE_UPLOAD_FALI) {
			for (UploadInfo in : mDatas) {
				if (info.getId() == in.getId()) {
					in.setState(UploadInfo.STATE_UPLOAD_FALI);
					LogUtils.error("== onUploadError() ==");
					break;
				}
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					adapter.refreshData(mDatas);
				}
			});
		}
	}

	@Override
	public void onUploadSuccess(UploadInfo info) {
		LogUtils.error("== onUploadSuccess() == info.webid = " + info.getChannel_web_id());
		if (info.getState() == UploadInfo.STATE_HAS_UPLOAD) {
			Toast.makeText(mContext, "已上传过", 0).show();
			return;
		}
		if (info.getState() == UploadInfo.STATE_UPLOAD_SUCCESS) {
			for (UploadInfo in : mDatas) {
				if (info.getId() == in.getId()) {
					in.setState(UploadInfo.STATE_UPLOAD_SUCCESS);
					break;
				}
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					adapter.refreshData(mDatas);
				}
			});
		}
	}

	@Override
	public void onAddUploadTask(boolean isAddSuccess) {
		if (isAddSuccess) {
			Message msg = mHandler.obtainMessage();
			msg.what = 0x1001;
			mHandler.sendMessageDelayed(msg, 2000);
			if (!StringUtil.isEmpty(videoPath)) {
				videoPaths.add(videoPath);
			}
			Toast.makeText(mContext, "添加成功", 0).show();
		} else {
			Toast.makeText(mContext, "添加失败", 0).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		for (UploadInfo info : mDatas) {
			if (info.getState() == UploadInfo.STATE_UPLOADING || info.getState() == UploadInfo.STATE_PAUSE) {
				info.setState(UploadInfo.STATE_PAUSE);
			}
			mUploadManager.updateUpload(info);
		}
		mUploadManager.onDestory();
	}

	class RemoveDataTask extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			if (params != null && params.length > 0) {
				int id = params[0];
				mUploadManager.deleteUploadById(id);
			}
			return null;
		}
	}

	class GetDataTask extends AsyncTask<Object, Integer, List<UploadInfo>> {

		@Override
		protected List<UploadInfo> doInBackground(Object... params) {
			return mUploadManager.searchAllUploads();
		}

		@Override
		protected void onPostExecute(List<UploadInfo> result) {
			super.onPostExecute(result);
			mDatas = result;
			LogUtils.error("mData size = " + mDatas.size());
			if (mDatas != null && mDatas.size() > 0) {
				adapter.refreshData(mDatas);
				for (UploadInfo info : mDatas) {
					if (info.getState() == UploadInfo.STATE_WAIT) {
						info.setState(UploadInfo.STATE_UPLOADING);
						mUploadManager.uploadFile(info);
						mUploadManager.updateUpload(info);
					}
				}
			}
		}
	}

	class GetReLunchDataTask extends AsyncTask<Object, Integer, List<UploadInfo>> {

		@Override
		protected List<UploadInfo> doInBackground(Object... params) {
			return mUploadManager.searchAllUploads();
		}

		@Override
		protected void onPostExecute(List<UploadInfo> result) {
			super.onPostExecute(result);
			mDatas = result;
			LogUtils.error("mData size = " + mDatas.size());
			if (mDatas != null && mDatas.size() > 0) {
				adapter.refreshData(mDatas);
				for (UploadInfo info : mDatas) {
					if (info.getState() == UploadInfo.STATE_UPLOADING) {
						mUploadManager.uploadFile(info);
						mUploadManager.updateUpload(info);
					}
				}
			}
		}
	}

	class GetVideoPathTask extends AsyncTask<Object, Integer, List<String>> {

		@Override
		protected List<String> doInBackground(Object... params) {
			return mUploadManager.searchVideoPaths();
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
			videoPaths = result;
			LogUtils.error("GetVideoPathTask videoPaths =" + videoPaths.toString());
		}
	}

	private void onPlayPauseClicked(UploadInfo info) {
		if (info == null)
			return;
		// 暂停
		if (info.getState() == UploadInfo.STATE_UPLOADING || info.getState() == UploadInfo.STATE_WAIT) {
			pauseUpload(info);
		} else if (info.getState() == UploadInfo.STATE_PAUSE || info.getState() == UploadInfo.STATE_UPLOAD_FALI) {
			startUpload(info);
		}
	}

	private void pauseUpload(UploadInfo info) {
		for (UploadInfo in : mDatas) {
			if (in.getId() == info.getId()) {
				in.setState(UploadInfo.STATE_PAUSE);
				LogUtils.error("pauseUpload fid:" + in.getFid());
				in.setPause(true);
				mUploadManager.updataUploadThread(in);
				mUploadManager.updateUpload(info);
				break;
			}
		}
		adapter.refreshData(mDatas);
	}

	private void startUpload(UploadInfo info) {
		for (UploadInfo in : mDatas) {
			if (in.getId() == info.getId()) {
				in.setState(UploadInfo.STATE_UPLOADING);
				in.setPause(false);
				LogUtils.error("startUpload fid:" + in.getFid());
				mUploadManager.updataUploadThread(in);
				mUploadManager.uploadFile(in);
				mUploadManager.updateUpload(info);
				break;
			}
		}
		adapter.refreshData(mDatas);
	}

	private void onDeleteItem(int position) {
		UploadInfo info = adapter.getItem(position);
		new RemoveDataTask().execute((int) info.getId());
		String path = info.getLocalPath();
		if (videoPaths.contains(path))
			videoPaths.remove(path);
		for (UploadInfo in : mDatas) {
			if (in.getId() == info.getId()) {
				in.setState(UploadInfo.STATE_PAUSE);
				LogUtils.error("pauseUpload fid:" + in.getFid());
				in.setPause(true);
				mUploadManager.updataUploadThread(in);
				break;
			}
		}
		mDatas.remove(info);
		adapter.removeData(position);
	}

	class UploadListAdapter extends BaseAdapter {
		private List<UploadInfo> uploadList = new ArrayList<UploadInfo>();
		private Context mContext;

		public UploadListAdapter(Context context) {
			mContext = context;
		}

		public List<UploadInfo> getDatas() {
			return uploadList;
		}

		public void setDatas(List<UploadInfo> mDatas) {
			this.uploadList = mDatas;
		}

		public void refreshData(List<UploadInfo> info) {
			if (info != null && info.size() > 0) {
				uploadList.clear();
				uploadList.addAll(info);
				notifyDataSetChanged();
			}
		}

		public void appendData(List<UploadInfo> info) {
			if (info != null && info.size() > 0) {
				uploadList.addAll(info);
				notifyDataSetChanged();
			}
		}

		public void removeData(int position) {
			uploadList.remove(position);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return uploadList.size();
		}

		@Override
		public UploadInfo getItem(int position) {
			return uploadList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			final UploadInfo info = getItem(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.upload_list_item, null);
				holder = new ViewHolder();
				holder.progress = (ProgressBar) convertView.findViewById(R.id.progressbar);
				holder.imgDelete = (ImageView) convertView.findViewById(R.id.btn_delete);
				holder.imgPlayPause = (ImageView) convertView.findViewById(R.id.btn_pause);
				holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_upload);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.progress.setMax(Integer.valueOf(info.getSize()));
			holder.imgPlayPause.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onPlayPauseClicked(info);
				}
			});
			holder.imgDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onDeleteItem(position);
				}
			});

			switch (info.getState()) {
			case UploadInfo.STATE_UPLOADING:
				holder.imgPlayPause.setBackgroundResource(R.drawable.upload_play_pause_btn);
				holder.imgPlayPause.setEnabled(true);
				holder.imgPlayPause.setSelected(false);
				holder.progress.setProgress(info.getProgress());
				holder.tvStatus.setText("开始上传");
				break;
			case UploadInfo.STATE_UPLOAD_SUCCESS:
				holder.imgPlayPause.setBackgroundResource(R.drawable.upload_complete_btn);
				holder.imgDelete.setSelected(false);

				holder.progress.setProgress(Integer.valueOf(info.getSize()));
				holder.tvStatus.setText("完成上传");
				break;
			case UploadInfo.STATE_UPLOAD_FALI:
				holder.tvStatus.setText("上传失败");
				break;
			case UploadInfo.STATE_PAUSE:
				holder.imgPlayPause.setBackgroundResource(R.drawable.upload_play_pause_btn);
				holder.imgPlayPause.setEnabled(true);
				holder.imgPlayPause.setSelected(true);
				holder.progress.setProgress(info.getProgress());
				holder.tvStatus.setText("暂停上传");
				break;
			default:
				break;
			}
			return convertView;
		}

		class ViewHolder {
			ProgressBar progress;
			ImageView imgDelete;
			ImageView imgPlayPause;
			TextView tvStatus;
		}
	}
}

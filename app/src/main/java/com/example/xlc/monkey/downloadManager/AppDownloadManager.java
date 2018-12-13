package com.example.xlc.monkey.downloadManager;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.example.xlc.monkey.utils.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author:xlc
 * @date:2018/12/7
 * @descirbe:基于原生的DownloadManager 实现的在线升级
 */
public class AppDownloadManager {

    private WeakReference<Activity> weakReference;
    private final DownloadManager mDownloadManager;
    private final DownloadChangeObserver mDownloadChangeObserver;
    private final DownloadReceiver mDownloadReceiver;
    private OnUpdateListener mUpdateListener;
    private long mReqId;

    public AppDownloadManager(Activity activity) {
        weakReference = new WeakReference<>(activity);
        mDownloadManager = (DownloadManager) weakReference.get().getSystemService(Context.DOWNLOAD_SERVICE);
        mDownloadChangeObserver = new DownloadChangeObserver(new Handler());
        mDownloadReceiver = new DownloadReceiver();
    }

    public void setUpdateListener(OnUpdateListener updateListener) {
        this.mUpdateListener = updateListener;
    }


    public void downloadApk(String apkUrl, String title, String desc) {
        //装不了新版本，在下载之前应该删除已有文件
        File apkFile = new File(weakReference.get().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app_name.apk");
        if (apkFile != null && apkFile.exists()) {
            apkFile.delete();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        //设置title
        request.setTitle(title);
        //设置描述
        request.setDescription(desc);
        //完成后显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(weakReference.get(), Environment.DIRECTORY_DOWNLOADS, "app_name.apk");
        //在手机SD卡上创建一个download文件夹
        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
        //指定下载到SD卡的/download/my/目录下
        // request.setDestinationInExternalPublicDir("/codoon/","codoon_health.apk");
        request.setMimeType("application/vnd.android.package-archive");
        mReqId = mDownloadManager.enqueue(request);
    }

    /**
     * 取消下载
     */
    public void cancel(){
        mDownloadManager.remove(mReqId);
    }

    /**
     * 对应{@link Activity#onResume()} ()}
     */
    public void resume(){
        //设置监听uri。parse("content://downloads/my_downloads)
        weakReference.get().getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"),true,mDownloadChangeObserver);
        //注册广播，监听apk是否下载完成
        weakReference.get().registerReceiver(mDownloadReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 对应{@link Activity#onPause()} ()}
     */
    public void onPause(){
        weakReference.get().getContentResolver().unregisterContentObserver(mDownloadChangeObserver);
        weakReference.get().unregisterReceiver(mDownloadReceiver);
    }


    private void updateView(){
       int[] bytesAndStatus = new int[]{0,0,0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mReqId);
        Cursor c = null;
        try{
            c= mDownloadManager.query(query);
            if (c!=null && c.moveToFirst()) {
                //已经下载的字节数
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //终须下载的字节数
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //状态所在的列索引
                bytesAndStatus[2] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
            }
        }finally {
            if (c!=null) {
                c.close();
            }
        }

        if (mUpdateListener !=null) {
            mUpdateListener.update(bytesAndStatus[0],bytesAndStatus[1]);
        }

    }

    public interface OnUpdateListener {
        void update(int currentByte, int totalByte);
    }

    public interface AndroidInstallPermissionListener {
        void permissionSuccess();

        void permissionFail();
    }


    class DownloadChangeObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateView();
        }
    }


    class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            boolean haveInstallPermission;
            //兼容android8.0的安装未知来源的apk
            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
                haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    AndroidInstallPermissionListener listener = new AndroidInstallPermissionListener() {
                        @Override
                        public void permissionSuccess() {
                            //申请权限成功了
                            installApk(context,intent);
                        }

                        @Override
                        public void permissionFail() {
                            ToastUtil.showLongToast(context,"授权失败，无法安装应用");
                        }
                    };
//                        申请未知来源的安装权限
//                    AndroidPermissionActivity.listener = listener;
//                    Intent intent1 = new Intent(context, AndroidPermissionActivity.class);
//                    context.startActivity(intent1);

                }else{
                    installApk(context,intent);
                }
            }else {
                installApk(context,intent);
            }
        }
    }


    private void installApk(Context context,Intent intent){
        long completeDownLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        Uri uri;
        Intent intentInstall = new Intent();
        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstall.setAction(Intent.ACTION_VIEW);
        if (completeDownLoadId == mReqId) {
            if (Build.VERSION.SDK_INT <Build.VERSION_CODES.M){//6.0一下
                uri = mDownloadManager.getUriForDownloadedFile(completeDownLoadId);
            }else if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){//6.0 - 7.0
                File apkFile = queryDownloadApk(context, completeDownLoadId);
                uri = Uri.fromFile(apkFile);
            }else{
                uri = FileProvider.getUriForFile(context,context.getPackageName()+"fileProvider",new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"app_name.apk"));
                intentInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intentInstall.setDataAndType(uri,"application/vnd.android.package-archive");
            context.startActivity(intentInstall);
        }
    }

    //通过downloadId查询下载的apk，解决6.0以后安装的问题
    private File queryDownloadApk(Context context, long downLoadId) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downLoadId !=-1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downLoadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur!=null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }
}

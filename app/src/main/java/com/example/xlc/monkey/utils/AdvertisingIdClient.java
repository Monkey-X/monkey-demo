package com.example.xlc.monkey.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author:xlc
 * @date:2018/12/11
 * @descirbe:获取GAID(Google Advertising ID)
 */
public class AdvertisingIdClient {

    //该方法耗时，不能在主线程调用
    public static String getGoogleAdId(Context context) throws Exception {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return "Cannot call in the main thread, You must must call in the other thread";
        }
        PackageManager pm = context.getPackageManager();

            pm.getPackageInfo("com.android.vending", 0);
            AdvertisingConnection connection = new AdvertisingConnection();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
        if (context.bindService(intent,connection, Context.BIND_AUTO_CREATE)) {
            try{
                AdvertisingInterface adInterface = new AdvertisingInterface(connection.getBinder());
                return adInterface.getId();
            }finally {
                context.unbindService(connection);
            }
        }
        return "";
    }


    private static final class AdvertisingConnection implements ServiceConnection {

        boolean retrieved = false;
        private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue<>();

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                this.queue.put(service);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        public IBinder getBinder() throws InterruptedException {
            if (this.retrieved) {
                throw new IllegalStateException();
            }
            this.retrieved = true;
            return this.queue.take();
        }
    }

    private static final class AdvertisingInterface implements IInterface {

        private IBinder binder;

        public AdvertisingInterface(IBinder binder) {
            this.binder = binder;
        }

        @Override
        public IBinder asBinder() {
            return binder;
        }

        public String getId() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            String id;
            try {
                data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                binder.transact(1, data, reply, 0);
                reply.readException();
                id = reply.readString();
            } finally {
                data.recycle();
                reply.recycle();
            }
            return id;
        }


        public boolean isLimitAdTrackingEnabled(boolean paramBoolean) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            boolean limitAdTracking;
            try{
                data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                data.writeInt(paramBoolean ? 1 : 0);
                binder.transact(2, data, reply, 0);
                reply.readException();
                limitAdTracking = 0 != reply.readInt();
            }finally {
                data.recycle();
                reply.recycle();
            }
           return limitAdTracking;
        }
    }
}

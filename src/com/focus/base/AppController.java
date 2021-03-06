package com.focus.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.focus.focus.service.UpdateService;
import com.lidroid.xutils.DbUtils;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    //单例线程池
    private ExecutorService mSingleExecutor;  
    //缓存线程池
    private ExecutorService mCachedExecutor; 
    //固定线程池，默认创建个数为手机处理器个数
    private ExecutorService mFixedExecutor;   
    //定时线程池
    private ScheduledExecutorService mScheduleExecutor;
    private  DbUtils db;
    private BasePreferences pref;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSingleExecutor = Executors.newSingleThreadExecutor();
        mCachedExecutor =  Executors.newCachedThreadPool();
        mFixedExecutor = Executors.newFixedThreadPool(2);
        mScheduleExecutor = Executors.newScheduledThreadPool(2);
    
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
         db = DbUtils.create(this);
         pref = new BasePreferences(this,this.getPackageName());
         Intent service = new Intent(this,UpdateService.class);  
         startService(service); 
    }

    public BasePreferences getPref() {
        return pref;
    }

    public DbUtils getDb() {
        return db;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
    
    public ExecutorService getSingleExecutor() {
        return mSingleExecutor;
    }

    public ExecutorService getCachedExecutor() {
        return mCachedExecutor;
    }

    public ExecutorService getFixedExecutor() {
        return mFixedExecutor;
    }

    public ScheduledExecutorService getScheduleExecutor() {
        return mScheduleExecutor;
    }
    
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
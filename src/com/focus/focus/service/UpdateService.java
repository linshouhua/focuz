package com.focus.focus.service;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Intent;
import android.os.IBinder;
import android.webkit.URLUtil;

import com.focus.base.AppController;
import com.focus.base.service.BaseService;
import com.focus.focus.KEY;
import com.focus.focus.Util;
import com.focus.focus.entity.FocusInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;

public class UpdateService extends BaseService {

    @Override
    public void onCreate() {
        super.onCreate();
        final AtomicLong atomicLong = new AtomicLong(0);
        AppController.getInstance().getScheduleExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int freqSecond = NumberUtils.toInt(AppController.getInstance().getPref().getString(KEY.FREQ_SECOND), 0);
                if (freqSecond <= 0) {
                    return;
                }
                if ((atomicLong.get() + (freqSecond * 1000)) < System.currentTimeMillis()) {
                    atomicLong.set(System.currentTimeMillis());
                    exe();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void exe() {
        DbUtils db = AppController.getInstance().getDb();
        List<FocusInfo> list;
        try {
            list = db.findAll(FocusInfo.class);
            int updatedCount = 0;
            if (CollectionUtils.isNotEmpty(list)) {
                for (FocusInfo focusInfo : list) {
                    LogUtils.d(focusInfo.toString());
                    if (URLUtil.isHttpUrl(focusInfo.getUrl()) || URLUtil.isHttpsUrl(focusInfo.getUrl())) {
                        Document doc = Jsoup.parse(new URL(focusInfo.getUrl()), 10000);
                        String title = null;
                        try {
                            title = doc.select("title").first().text();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String html = doc.html();
                        String md5 = Util.md5(html);
                        if (md5.equals(focusInfo.getLastMD5()) == false) {
                            focusInfo.setLastMD5(md5);
                            focusInfo.setUpdateTs(System.currentTimeMillis());
                            focusInfo.setReadFlag(1);
                            focusInfo.setTitle(title);
                            db.saveOrUpdate(focusInfo);
                            updatedCount++;
                        }
                    }
                }
                LogUtils.d("更新数量=" + updatedCount);
                if (updatedCount > 0) {
                    Intent intent = new Intent(KEY.ACTION_UPDATE_FOCUS);
                    intent.setAction(KEY.ACTION_UPDATE_FOCUS);
                    sendBroadcast(intent);
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

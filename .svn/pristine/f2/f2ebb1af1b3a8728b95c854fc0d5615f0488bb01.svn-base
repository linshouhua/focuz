package com.focus.focus.view.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.focus.R;
import com.focus.base.AppController;
import com.focus.base.view.BaseFragment;
import com.focus.focus.KEY;
import com.focus.focus.Util;
import com.focus.focus.adapter.FocusInfoAdapter;
import com.focus.focus.entity.FocusInfo;
import com.focus.focus.view.activity.WebActivity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

@SuppressLint("InflateParams")
public class FocusInfoFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

    private ListView mMainLv;
    private FocusInfoAdapter mAdapter;
    private ImageView mImageView;
    private List<FocusInfo> mList;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_focus;
    }

    @Override
    protected View getHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_home, null);
        mImageView = (ImageView) view.findViewById(R.id.add);
        mImageView.setOnClickListener(this);
        return view;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mMainLv = (ListView) getActivity().findViewById(R.id.mainLv);
        mList = new ArrayList<FocusInfo>();
        setListData();
        mAdapter = new FocusInfoAdapter(getActivity(), mList);
        mMainLv.setAdapter(mAdapter);
        mMainLv.setOnItemClickListener(this);

        IntentFilter intentFilter = new IntentFilter(KEY.ACTION_UPDATE_FOCUS);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.d("接收到一个广播" + intent.getAction());
                setListData();
                mAdapter.notifyDataSetChanged();
            }
        }, intentFilter);
    }

    @SuppressWarnings("unchecked")
    private void setListData() {
        DbUtils db = AppController.getInstance().getDb();
        List<FocusInfo> list = new ArrayList<FocusInfo>();
        try {
            list = db.findAll(FocusInfo.class);
            if (CollectionUtils.isEmpty(list)) {
                list = ListUtils.EMPTY_LIST;
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        }
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        case R.id.add:
            add();
            break;
        default:
            break;
        }
    }

    private void add() {
        final EditText inputServer = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加关注网址").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.d(inputServer.getText().toString());
                inputServer.setText(StringUtils.trimToEmpty(inputServer.getText().toString()).replace(" ", ""));
                if (StringUtils.isNotBlank(inputServer.getText().toString())) {
                    final DbUtils db = AppController.getInstance().getDb();
                    if ("drop".equals(inputServer.getText())) {
                        try {
                            db.dropTable(FocusInfo.class);
                        } catch (Exception e) {
                            LogUtils.e(e.getMessage(), e);
                        }
                        return;
                    }

                    if (inputServer.getText().toString().toLowerCase().indexOf("http") < 0) {
                        inputServer.setText("http://" + inputServer.getText().toString());
                    }
                    FocusInfo focusInfoDb = null;
                    try {
                        focusInfoDb = db.findFirst(Selector.from(FocusInfo.class).where("url", "=", inputServer.getText().toString()));
                    } catch (DbException e2) {
                        e2.printStackTrace();
                    }
                    boolean validUrl = URLUtil.isHttpUrl(inputServer.getText().toString()) || URLUtil.isHttpsUrl(inputServer.getText().toString());
                    if (focusInfoDb != null) {
                        Util.toast("已经存在的网址");
                        return;
                    }
                    if (validUrl == false) {
                        Util.toast("不合法的网址");
                        return;
                    }
                    if (validUrl) {
                        StringRequest strReq = new StringRequest(Method.GET, inputServer.getText().toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    FocusInfo focusInfo = new FocusInfo();
                                    Document doc = null;
                                    String title = null;
                                    String html = null;
                                    doc = Jsoup.parse(response);
                                    title = doc.select("title").first().text();
                                    html = doc.html();
                                    String md5 = Util.md5(html);
                                    focusInfo.setUrl(inputServer.getText().toString());
                                    focusInfo.setTitle(title);
                                    focusInfo.setReadFlag(0);
                                    focusInfo.setLastMD5(md5);
                                    focusInfo.setUpdateTs(System.currentTimeMillis());
                                    db.saveOrUpdate(focusInfo);
                                    Intent intent = new Intent(KEY.ACTION_UPDATE_FOCUS);
                                    intent.setAction(KEY.ACTION_UPDATE_FOCUS);
                                    getActivity().sendBroadcast(intent);
                                    Util.toast("添加成功");
                                } catch (DbException e) {
                                    try {
                                        db.dropTable(FocusInfo.class);
                                    } catch (DbException e1) {
                                        LogUtils.e(e1.getMessage(), e1);
                                    }
                                    LogUtils.e(e.getMessage(), e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.toast("添加失败" + error.getMessage());
                            }
                        });
                        AppController.getInstance().addToRequestQueue(strReq, "TAG");
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    public void onItemClick(AdapterView<? extends Object> parent, View view, int position, long id) {
        if (parent != null) {
            ImageView red = (ImageView) parent.findViewById(R.id.redCircle);
            if (red != null) {
                red.setVisibility(View.INVISIBLE);
            }
        }
        FocusInfo focusInfo = mList.get(position);
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(KEY.URL, focusInfo.getUrl());
        intent.putExtra(KEY.TITLE, focusInfo.getTitle());
        focusInfo.setReadFlag(0);
        try {
            AppController.getInstance().getDb().saveOrUpdate(focusInfo);
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        }
        startActivity(intent);
    }
}

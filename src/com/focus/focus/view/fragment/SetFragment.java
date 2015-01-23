package com.focus.focus.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.focus.R;
import com.focus.base.AppController;
import com.focus.base.view.BaseFragment;
import com.focus.focus.KEY;
import com.focus.focus.view.activity.AboutUsActivity;

@SuppressLint("InflateParams")
public class SetFragment extends BaseFragment implements OnClickListener {
    private TextView mFreqTvSet;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_set;
    }

    @Override
    protected View getHeader() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_set, null);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mFreqTvSet = (TextView) getActivity().findViewById(R.id.freqSet);
        mFreqTvSet.setText(AppController.getInstance().getPref().getString(KEY.FREQ));
        getActivity().findViewById(R.id.freqGroup).setOnClickListener(this);
        getActivity().findViewById(R.id.aboutUsGroup).setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        case R.id.freqGroup:
            freq();
            break;
        case R.id.aboutUsGroup:
            aboutUs();
        default:
            break;
        }
    }

    private void aboutUs() {
            Intent intent = new Intent(getActivity(),AboutUsActivity.class);
            startActivity(intent);
    }

    private void freq() {
        int min = 60;
        final String[] items = { "10秒", "5分钟", "15分钟", "30分钟", "1小时", "6小时" };
        final int[] second = { 10, 5 * min, 15 * min, 30 * min, 60 * min, 60 * 6 * min };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("更新频率");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                String freq = items[position];
                mFreqTvSet.setText(freq);
                AppController.getInstance().getPref().putString(KEY.FREQ, freq);
                AppController.getInstance().getPref().putString(KEY.FREQ_SECOND, "" + second[position]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

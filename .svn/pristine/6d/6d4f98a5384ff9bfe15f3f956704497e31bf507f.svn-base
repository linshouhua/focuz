package com.focus.focus.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.focus.R;
import com.focus.focus.entity.FocusInfo;

public class FocusInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<FocusInfo> mList;

    public FocusInfoAdapter(Context context, List<FocusInfo> list) {
        mContext = context;
        mList = list;
        if (mList == null) {
            mList = new ArrayList<FocusInfo>();
        }
    }

    @Override
    public int getCount() {
        return CollectionUtils.size(mList);
    }

    @Override
    public Object getItem(int arg0) {
        return CollectionUtils.get(mList, arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (null == convertView) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_focus_item, null);
            vHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            vHolder.tvUpdateTime = (TextView) convertView.findViewById(R.id.updateTime);
            vHolder.tvUrl = (TextView) convertView.findViewById(R.id.focusUrl);
            vHolder.ivImageView = (ImageView) convertView.findViewById(R.id.redCircle);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        final FocusInfo data = mList.get(position);
        if (data != null) {
            vHolder.tvTitle.setText(data.getTitle());
            if (data.getUpdateTs() != null) {
                vHolder.tvUpdateTime.setText(DateFormatUtils.format(data.getUpdateTs(), "yyyy-MM-dd HH:mm:ss"));
            }
            vHolder.tvUrl.setText(data.getUrl());
            if (data.getReadFlag() == 1) {
                vHolder.ivImageView.setVisibility(View.VISIBLE);
            } else {
                vHolder.ivImageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView ivImageView;
        TextView tvTitle;
        TextView tvUpdateTime;
        TextView tvUrl;
    }
}

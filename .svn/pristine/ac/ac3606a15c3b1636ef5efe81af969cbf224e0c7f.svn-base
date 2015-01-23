package com.focus.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.focus.R;

public abstract class BaseFragment extends Fragment {
    private ViewGroup mActionBarContainer;
    private ViewGroup mConent;
    private View mHeader;

    protected View getHeader() {
        return null;
    }

    abstract protected int getLayoutResID();

    abstract protected void initView(@Nullable Bundle savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_base, null);
        mActionBarContainer = (ViewGroup) view.findViewById(R.id.base_header_container);
        int layoutResID = getLayoutResID();
        View viewContent = LayoutInflater.from(getActivity()).inflate(layoutResID, null);
        mConent = (ViewGroup) view.findViewById(R.id.base_id_content);
        mConent.addView(viewContent);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHeader = getHeader();
        if (mHeader != null) {
            mHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mActionBarContainer.addView(mHeader);
        }
        initView(savedInstanceState);
    }
}

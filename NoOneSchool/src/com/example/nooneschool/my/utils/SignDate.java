package com.example.nooneschool.my.utils;



import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.SignDateAdapter;
import com.example.nooneschool.my.adapter.SignWeekAdapter;
import com.example.nooneschool.my.inter.OnSignedSuccess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class SignDate extends LinearLayout {

    private TextView tv_year;
    private InnerGridView gv_week;
    private InnerGridView gv_date;
    private SignDateAdapter adapterDate;

    public SignDate(Context context) {
        super(context);
        init();
    }

    public SignDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignDate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(),R.layout.layout_signdate,this);
        tv_year = (TextView) view.findViewById(R.id.signdate_year_textview);
        gv_week = (InnerGridView) view.findViewById(R.id.signdate_week_gridview);
        gv_date = (InnerGridView) view.findViewById(R.id.signdate_date_gridview);
        tv_year.setText(DateUtil.getCurrentYearAndMonth());
        gv_week.setAdapter(new SignWeekAdapter(getContext()));
        adapterDate = new SignDateAdapter(getContext());
        gv_date.setAdapter(adapterDate);
    }

    public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess){
        adapterDate.setOnSignedSuccess(onSignedSuccess);
    }
}

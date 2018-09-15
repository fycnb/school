package com.example.nooneschool.my;



import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.DateAdapter;
import com.example.nooneschool.my.adapter.WeekAdapter;
import com.example.nooneschool.my.inter.OnSignedSuccess;
import com.example.nooneschool.my.utils.DateUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class SignDate extends LinearLayout {

    private TextView tvYear;
    private InnerGridView gvWeek;
    private InnerGridView gvDate;
    private DateAdapter adapterDate;

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
        tvYear = (TextView) view.findViewById(R.id.tvYear);
        gvWeek = (InnerGridView) view.findViewById(R.id.gvWeek);
        gvDate = (InnerGridView) view.findViewById(R.id.gvDate);
        tvYear.setText(DateUtil.getCurrentYearAndMonth());
        gvWeek.setAdapter(new WeekAdapter(getContext()));
        adapterDate = new DateAdapter(getContext());
        gvDate.setAdapter(adapterDate);
    }

    public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess){
        adapterDate.setOnSignedSuccess(onSignedSuccess);
    }
}

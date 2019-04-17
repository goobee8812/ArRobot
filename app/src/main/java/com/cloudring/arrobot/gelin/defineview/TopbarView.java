package com.cloudring.arrobot.gelin.defineview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudring.arrobot.gelin.R;


public class TopbarView extends RelativeLayout {

    private LayoutInflater layoutInflater;
    private View myView;
    private ImageView backIv;
    private TextView titleTv;
    private LinearLayout deleteLl;
    private Button confirmBtn;

    public TopbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutInflater = LayoutInflater.from(context);
        myView = layoutInflater.inflate(R.layout.view_top,null);
        backIv = myView.findViewById(R.id.id_back_iv);
        titleTv = myView.findViewById(R.id.id_top_title_tv);
        deleteLl = myView.findViewById(R.id.id_delete_ll);
        confirmBtn = myView.findViewById(R.id.id_confirm_btn);
        addView(myView);
    }

    public void setTitle(String title){
        titleTv.setText(title);
    }

}

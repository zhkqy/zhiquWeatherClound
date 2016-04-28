package com.yilongweather.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yilongweather.R;

public class SetActivity extends Activity {

	private RelativeLayout mRelativeBack;
	private LinearLayout mLinearSet;

	private RelativeLayout mRelative1;
	private RelativeLayout mRelative2;
	private RelativeLayout mRelative3;
	private RelativeLayout mRelative4;
	private RelativeLayout mRelative5;
	
	private RelativeLayout mRelative6;
	private RelativeLayout mRelative7;
	private RelativeLayout mRelative8;
	private RelativeLayout mRelative9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set);
		initView();
		mRelativeBack = (RelativeLayout) findViewById(R.id.relative_set);
		mRelativeBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		mRelative1 = (RelativeLayout) findViewById(R.id.in_update);
		TextView textUpdate = (TextView) mRelative1.findViewById(R.id.text_update);
		textUpdate.setText("天气更新");
		TextView textAuto = (TextView) mRelative1.findViewById(R.id.text_auto);
		textAuto.setText("自动更新");
		
		mRelative2 = (RelativeLayout) findViewById(R.id.in_remind);
		TextView text2 = (TextView) mRelative2.findViewById(R.id.text_update);
		text2.setText("预警提醒");
		TextView text22 = (TextView) mRelative2.findViewById(R.id.text_auto);
		text22.setText("开启");
		
		mRelative3 = (RelativeLayout) findViewById(R.id.in_table);
		TextView text3 = (TextView) mRelative3.findViewById(R.id.text_update);
		text3.setText("桌面插件");
		TextView text33 = (TextView) mRelative3.findViewById(R.id.text_auto);
		text33.setText("纸片");
		
		mRelative4 = (RelativeLayout) findViewById(R.id.in_sign);
		TextView text4 = (TextView) mRelative4.findViewById(R.id.text_update);
		text4.setText("星座运势");
		TextView text44 = (TextView) mRelative4.findViewById(R.id.text_auto);
		text44.setText("开启");
		
		mRelative5 = (RelativeLayout) findViewById(R.id.in_activity);
		TextView text5 = (TextView) mRelative5.findViewById(R.id.text_update);
		text5.setText("趣味活动");
		TextView text55 = (TextView) mRelative5.findViewById(R.id.text_auto);
		text55.setText("开启");
		
		mRelative6 = (RelativeLayout) findViewById(R.id.in_suggest);
		TextView text6 = (TextView) mRelative6.findViewById(R.id.text_update);
		text6.setText("产品建议");
		TextView text66 = (TextView) mRelative6.findViewById(R.id.text_auto);
		text66.setVisibility(View.INVISIBLE);
		
		mRelative7 = (RelativeLayout) findViewById(R.id.in_help);
		TextView text7 = (TextView) mRelative7.findViewById(R.id.text_update);
		text7.setText("帮助");
		TextView text77 = (TextView) mRelative7.findViewById(R.id.text_auto);
		text77.setVisibility(View.INVISIBLE);
		
		mRelative8 = (RelativeLayout) findViewById(R.id.in_about);
		TextView text8 = (TextView) mRelative8.findViewById(R.id.text_update);
		text8.setText("关于");
		TextView text88 = (TextView) mRelative8.findViewById(R.id.text_auto);
		text88.setVisibility(View.INVISIBLE);
		
		mRelative9 = (RelativeLayout) findViewById(R.id.in_attention);
		TextView text9 = (TextView) mRelative9.findViewById(R.id.text_update);
		text9.setText("关注知趣新浪微博");
		TextView text99 = (TextView) mRelative9.findViewById(R.id.text_auto);
		text99.setVisibility(View.INVISIBLE);
		
	}

}

package com.hjwordsgame.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int right = getIntent().getIntExtra("right", 0);
		int total = getIntent().getIntExtra("total", 0);
		TextView tv = new TextView(ctx);
		tv.setText("答对题数为："+right+"!(总共"+total+"题)");
		tv.setTextColor(Color.RED);
		tv.setTextSize(25);
		setContentView(tv);
	}
}

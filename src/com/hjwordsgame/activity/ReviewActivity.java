package com.hjwordsgame.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hjwordsgame.R;
import com.hjwordsgame.model.WordBean;

public class ReviewActivity extends BaseActivity implements OnClickListener {
	private TextView tv_review_sentence;
	private TextView tv_review_word;
	private TextView tv_review_chinese;
	private TextView tv_review_phonetic;
	private TextView tv_review_chinesesentence;
	private Button btn_review_goon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		
		initView();
		initData();
		initListener();
	}

	private void initView() {
		tv_review_sentence = (TextView) findViewById(R.id.tv_review_sentence);
		tv_review_word = (TextView) findViewById(R.id.tv_review_word);
		tv_review_chinese = (TextView) findViewById(R.id.tv_review_chinese);
		tv_review_phonetic = (TextView) findViewById(R.id.tv_review_phonetic);
		tv_review_chinesesentence = (TextView) findViewById(R.id.tv_review_chinesesentence);
		btn_review_goon = (Button) findViewById(R.id.btn_review_goon);
	}

	private void initData() {
		WordBean bean = getIntent().getParcelableExtra("bean");
		tv_review_word.setText(bean.getWord());
		tv_review_sentence.setText(bean.getSentence());
		tv_review_chinese.setText(bean.getChinese());
		tv_review_phonetic.setText(bean.getPhonetic());
		tv_review_chinesesentence.setText(bean.getChineseSentence());
	}

	private void initListener() {
		btn_review_goon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_review_goon:
			finish();
			break;

		default:
			break;
		}
	}
}

package com.hjwordsgame.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjwordsgame.R;
import com.hjwordsgame.adapter.ChooseWordsAdapter;
import com.hjwordsgame.common.GlobalVars;
import com.hjwordsgame.model.TestBean;
import com.hjwordsgame.model.WordBean;
import com.hjwordsgame.model.WordBean4Test;
import com.hjwordsgame.util.MyLogger;

public class ChooseWordsActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
	private TextView tv_cw_howmanychoosed;
	private Button btn_cw_back;
	private Button btn_cw_ppwindow;
	private Button btn_cw_startstudy;
	private Button btn_cw_starttest;
	private ListView lv_cw;
	private List<WordBean> wordBeans = new ArrayList<WordBean>();
	private CheckBox cb_cw_selectall;
	private ChooseWordsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosewords);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		tv_cw_howmanychoosed = (TextView) findViewById(R.id.tv_cw_howmanychoosed);
		btn_cw_back = (Button) findViewById(R.id.btn_cw_back);
		btn_cw_ppwindow = (Button) findViewById(R.id.btn_cw_ppwindow);
		btn_cw_startstudy = (Button) findViewById(R.id.btn_cw_startstudy);
		btn_cw_starttest = (Button) findViewById(R.id.btn_cw_starttest);
		cb_cw_selectall = (CheckBox) findViewById(R.id.cb_cw_selectall);
		lv_cw = (ListView) findViewById(R.id.lv_cw);
		
	}

	private void initData() {
		GlobalVars.sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		WordBean bean1 = new WordBean();
		bean1.setWord("root");
		bean1.setChinese("n. 根，根源");
		bean1.setChineseSentence("这棵树的根很粗。");
		bean1.setSentence("The roots of the tress are very thick.");
		bean1.setPhonetic("[ru:t]");
		bean1.setSentenceVoicePath(R.raw.word);
		bean1.setWordVoicePath(R.raw.word1);
		WordBean bean2 = new WordBean();
		bean2.setWord("angel");
		bean2.setChinese("n. 天使");
		bean2.setChineseSentence("妈妈说了，我是个天使。");
		bean2.setSentence("Mum says I'm an angel.");
		bean2.setPhonetic("['eind3el]");
		bean2.setSentenceVoicePath(R.raw.jiaodizhu);
		bean2.setWordVoicePath(R.raw.word2);
		WordBean bean3 = new WordBean();
		bean3.setWord("mark");
		bean3.setChinese("n. 标志；符号；痕迹");
		bean3.setChineseSentence("她在考试中份数很低");
		bean3.setSentence("She got a low mark in the exam.");
		bean3.setPhonetic("[ma:k]");
		bean3.setSentenceVoicePath(R.raw.sentense);
		bean3.setWordVoicePath(R.raw.word3);
		wordBeans.add(bean1);
		wordBeans.add(bean2);
		wordBeans.add(bean3);
		adapter = new ChooseWordsAdapter(ctx, wordBeans, GlobalVars.sp, tv_cw_howmanychoosed);
		lv_cw.setAdapter(adapter);
		
		//测试单词的数据填充
		ArrayList<WordBean4Test> list1 = new ArrayList<WordBean4Test>();
		list1.add(new WordBean4Test(bean1.getWord(), true, bean1.getChinese()));
		list1.add(new WordBean4Test(bean2.getWord(), false, bean2.getChinese()));
		list1.add(new WordBean4Test(bean3.getWord(), false, bean3.getChinese()));
		TestBean testBean1 = new TestBean();
		testBean1.setList(list1);
		testBean1.setWordBean(bean1);
		
		ArrayList<WordBean4Test> list2 = new ArrayList<WordBean4Test>();
		list2.add(new WordBean4Test(bean2.getWord(), true, bean2.getChinese()));
		list2.add(new WordBean4Test(bean1.getWord(), false, bean1.getChinese()));
		list2.add(new WordBean4Test(bean3.getWord(), false, bean3.getChinese()));
		TestBean testBean2 = new TestBean();
		testBean2.setList(list2);
		testBean2.setWordBean(bean2);
		
		ArrayList<WordBean4Test> list3 = new ArrayList<WordBean4Test>();
		list3.add(new WordBean4Test(bean3.getWord(), true, bean3.getChinese()));
		list3.add(new WordBean4Test(bean1.getWord(), false, bean1.getChinese()));
		list3.add(new WordBean4Test(bean2.getWord(), false, bean2.getChinese()));
		TestBean testBean3 = new TestBean();
		testBean3.setList(list3);
		testBean3.setWordBean(bean3);
		
		GlobalVars.testBeans = new ArrayList<TestBean>();
		GlobalVars.testBeans.add(testBean1);
		GlobalVars.testBeans.add(testBean2);
		GlobalVars.testBeans.add(testBean3);
	}

	private void initListener() {
		btn_cw_back.setOnClickListener(this);
		btn_cw_ppwindow.setOnClickListener(this);
		btn_cw_startstudy.setOnClickListener(this);
		btn_cw_starttest.setOnClickListener(this);
		cb_cw_selectall.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cw_back:
			finish();
			break;
		case R.id.btn_cw_ppwindow:
			Toast.makeText(ctx, "btn_cw_ppwindow", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_cw_startstudy:
			ArrayList<WordBean> toStudyBeans = new ArrayList<WordBean>();
			for (WordBean bean : wordBeans) {
				if(bean.isSelected()){
					toStudyBeans.add(bean);
				}
			}
			if(toStudyBeans.size() == 0){
				Toast.makeText(ctx, "您未选择任何单词！", Toast.LENGTH_SHORT).show();
			}else{
				Intent intent = new Intent(ctx,StudyActivity.class);
				intent.putParcelableArrayListExtra("wordBeans", toStudyBeans);
				startActivity(intent);
			}
			break;
		case R.id.btn_cw_starttest:
			Intent intent = new Intent(ctx,ExamActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(R.id.cb_cw_selectall == buttonView.getId()){
			if(isChecked){
				for (WordBean bean : wordBeans) {
					bean.setSelected(true);
				}
				adapter.notifyDataSetChanged();
			}else{
				for (WordBean bean : wordBeans) {
					bean.setSelected(false);
				}
				adapter.notifyDataSetChanged();
			}
		}
	}
}

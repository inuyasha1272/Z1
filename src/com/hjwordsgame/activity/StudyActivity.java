package com.hjwordsgame.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjwordsgame.R;
import com.hjwordsgame.adapter.StudyPageAdapter;
import com.hjwordsgame.model.WordBean;

public class StudyActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
	private TextView tv_study_count;
	private Button btn_study_auto;
	private Button btn_study_back;
	private Button btn_study_next;
	private Button btn_study_previous;
	private ViewPager vp_study;
	private List<View> viewList = new ArrayList<View>();
	private int totalWords;
	private int currentPage = 0;
	private boolean auto = true;
	private ArrayList<WordBean> beans;
	private int sentenceVoicePath;
	private int wordVoicePath;
	private MediaPlayer mp4word;
	private MediaPlayer mp4sentence;
	private AnimationDrawable smallSoundDrawable;
	private AnimationDrawable soundDrawable;
	private StudyPageAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		tv_study_count = (TextView) findViewById(R.id.tv_study_count);
		btn_study_auto = (Button) findViewById(R.id.btn_study_auto);
		btn_study_back = (Button) findViewById(R.id.btn_study_back);
		btn_study_previous = (Button) findViewById(R.id.btn_study_previous);
		btn_study_next = (Button) findViewById(R.id.btn_study_next);
		vp_study = (ViewPager) findViewById(R.id.vp_study);
	}

	private void initData() {
		Intent intent = getIntent();
		beans = intent.getParcelableArrayListExtra("wordBeans");
		totalWords = beans.size();
		tv_study_count.setText("1/"+totalWords);
		initViewPagerElement(beans);
		initViewPager();
	}

	private void initViewPager() {
		adapter = new StudyPageAdapter(viewList);
		vp_study.setAdapter(adapter);
		
		WordBean bean = beans.get(0);
//		ImageView iv_small = (ImageView) vp_study.findViewWithTag(bean.getWord());
//		ImageView iv_study_sound = (ImageView) vp_study.findViewWithTag(bean.getChinese());
//		soundDrawable = (AnimationDrawable) iv_study_sound.getDrawable();
//		smallSoundDrawable = (AnimationDrawable) iv_small.getDrawable();
		readWord(bean);
	}
	
	private void initViewPagerElement(ArrayList<WordBean> beans) {
		for (int i = 0; i < beans.size(); i++) {
			WordBean bean = beans.get(i);
			View vpitem = getLayoutInflater().inflate(R.layout.vpitem_study, null);
			TextView tv_study_sentence = (TextView) vpitem.findViewById(R.id.tv_study_sentence);
			TextView tv_study_word = (TextView) vpitem.findViewById(R.id.tv_study_word);
			TextView tv_study_chinese = (TextView) vpitem.findViewById(R.id.tv_study_chinese);
			TextView tv_study_phonetic = (TextView) vpitem.findViewById(R.id.tv_study_phonetic);
			TextView tv_study_chinesesentence = (TextView) vpitem.findViewById(R.id.tv_study_chinesesentence);
			ImageView iv_study_smallsound = (ImageView) vpitem.findViewById(R.id.iv_study_smallsound);
			ImageView iv_study_sound = (ImageView) vpitem.findViewById(R.id.iv_study_sound);
			iv_study_smallsound.setTag(bean.getWord());
			iv_study_sound.setTag(bean.getChinese());
			iv_study_smallsound.setImageResource(R.anim.anim_small_sound);
			iv_study_sound.setImageResource(R.anim.anim_sound);
			if(i == 0){
				soundDrawable = (AnimationDrawable) iv_study_sound.getDrawable();
				smallSoundDrawable = (AnimationDrawable) iv_study_smallsound.getDrawable();
			}
			tv_study_word.setText(bean.getWord());
			tv_study_sentence.setText(bean.getSentence());
			tv_study_chinese.setText(bean.getChinese());
			tv_study_phonetic.setText(bean.getPhonetic());
			tv_study_chinesesentence.setText(bean.getChineseSentence());
			viewList.add(vpitem);
		}
		if(beans.size() == 1){
			btn_study_next.setText("开始测试");
		}else{
			btn_study_next.setText("下一个");
		}
	}

	private void initListener() {
		btn_study_back.setOnClickListener(this);
		btn_study_auto.setOnClickListener(this);
		btn_study_next.setOnClickListener(this);
		btn_study_previous.setOnClickListener(this);
		vp_study.setOnPageChangeListener(this);
	}

	public void setText(String text) {
		tv_study_count.setText(text);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_study_auto:
			if(auto)
				btn_study_auto.setText("开启自动发音");
			else
				btn_study_auto.setText("取消自动发音");
			auto = !auto;
			break;
		case R.id.btn_study_back:
			finish();
			break;
		case R.id.btn_study_next:
			stopPlayingAndRelease();
			if(currentPage < totalWords-1)
				currentPage++;
			CharSequence text = btn_study_next.getText();
			if("开始测试".equals(text)){
				Intent intent = new Intent(ctx,ExamActivity.class);
				startActivity(intent);
				finish();
			}else{
				if(currentPage < totalWords-1){
					vp_study.setCurrentItem(currentPage);
				}else{
					vp_study.setCurrentItem(currentPage);
				}
			}
			
			break;
		case R.id.btn_study_previous:
			stopPlayingAndRelease();
			if(currentPage > 0)
				currentPage--;
			vp_study.setCurrentItem(currentPage);
			if(currentPage == 0){
				tv_study_count.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
//		Log.e(TAG, "changed!!!!!!!!!!!!!!!!!");
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Log.e(TAG, "Scrolled!!!!!!!!!!!!!!!!!");
	}

	@Override
	public void onPageSelected(int arg0) {
		Log.e(TAG, "onPageSelected!!!!!!!!!!!!!!!!!  "+arg0);
		WordBean bean = beans.get(arg0);
		ImageView iv_small = (ImageView) vp_study.findViewWithTag(bean.getWord());
		ImageView iv_study_sound = (ImageView) vp_study.findViewWithTag(bean.getChinese());
		soundDrawable = (AnimationDrawable) iv_study_sound.getDrawable();
		smallSoundDrawable = (AnimationDrawable) iv_small.getDrawable();
		stopPlayingAndRelease();
		readWord(bean);
		currentPage  = arg0;
		tv_study_count.setText(++arg0+"/"+totalWords);
		if(currentPage  == 0 && (currentPage != totalWords-1)){
			btn_study_next.setText("下一个");
			btn_study_previous.setVisibility(View.INVISIBLE);
		}else if(currentPage  == totalWords-1){
			btn_study_next.setText("开始测试");
			btn_study_previous.setVisibility(View.VISIBLE);
		}else if(currentPage  == 0 && (currentPage == totalWords-1)){
			btn_study_next.setText("开始测试");
			btn_study_previous.setVisibility(View.INVISIBLE);
		}else{
			btn_study_next.setText("下一个");
			btn_study_previous.setVisibility(View.VISIBLE);
		}
	}
	
	private void readWord(WordBean bean){
		if(auto){
			sentenceVoicePath = bean.getSentenceVoicePath();
			wordVoicePath = bean.getWordVoicePath();
			mp4word = MediaPlayer.create(ctx, wordVoicePath);
			mp4word.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					try {
						if(soundDrawable!=null)
						soundDrawable.stop();
						readSentence();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (NotFoundException e) {
						e.printStackTrace();
					}
				}
			});
			mp4word.start();
			if(soundDrawable!=null)
			soundDrawable.start();
			
		}
	}
	private void readSentence(){
		mp4sentence = MediaPlayer.create(ctx, sentenceVoicePath);
		mp4sentence.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				if(smallSoundDrawable!=null)
				smallSoundDrawable.stop();
				stopPlayingAndRelease();
			}
		});
		mp4sentence.start();
		if(smallSoundDrawable!=null)
		smallSoundDrawable.start();
	}
	
	/**停止音频播放*/
	private void stopPlayingAndRelease(){
		if(mp4word!=null){
			mp4word.stop();
			mp4word.release();
			mp4word = null;
		}
		if(mp4sentence!=null){
			mp4sentence.stop();
			mp4sentence.release();
			mp4sentence = null;
		}
		if(soundDrawable!=null)
			soundDrawable.stop();
		if(smallSoundDrawable!=null)
			smallSoundDrawable.stop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopPlayingAndRelease();
	}
}

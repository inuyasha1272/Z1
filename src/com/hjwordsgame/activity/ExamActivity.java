package com.hjwordsgame.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjwordsgame.R;
import com.hjwordsgame.adapter.ExamListAdapter;
import com.hjwordsgame.adapter.ExamListAdapter.ViewHolder;
import com.hjwordsgame.common.GlobalVars;
import com.hjwordsgame.model.TestBean;
import com.hjwordsgame.model.WordBean4Test;
import com.hjwordsgame.view.ProgressWheel;

public class ExamActivity extends BaseActivity implements OnClickListener {
	protected static final int TIMEOUT = 110;
	private TextView tv_wordOrChinese;
	private Button btn_exam_back;
	private Button btn_exam_auto;
	private Button btn_exam_sure;
	private EditText et_exam;
	private ListView lv;
	private View ll;
	private int index;

	/** mode=0时是选正确的中文翻译，mode=1时是拼写 */
	private int mode = 0;
	private TestBean testBean;
	private ExamListAdapter adapter;
	private ProgressWheel pb;
	/** 总题目数 */
	private int totalCount;
	/** 当前多少题 */
	private int currentCount = 1;
	/** 答对了多少题 */
	private int rightCount = 0;
	
	private boolean stop;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TIMEOUT:
				Toast.makeText(ctx, "超时", 0).show();
				goToReviewAcitivy();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);

		initView();
		initData();
		initListener();
	}

	private void initView() {
		tv_wordOrChinese = (TextView) findViewById(R.id.tv_exam_word);
		btn_exam_back = (Button) findViewById(R.id.btn_exam_back);
		btn_exam_auto = (Button) findViewById(R.id.btn_exam_auto);
		btn_exam_sure = (Button) findViewById(R.id.btn_exam_sure);
		et_exam = (EditText) findViewById(R.id.et_exam);
		lv = (ListView) findViewById(R.id.lv_exam);
		pb = (ProgressWheel) findViewById(R.id.pw_exam);
		ll = findViewById(R.id.ll_exam);
	}

	private void initData() {
		totalCount = GlobalVars.testBeans.size() * 2;
		if (index <= GlobalVars.testBeans.size() - 1) {
			testBean = GlobalVars.testBeans.get(index);
			if (mode == 0) {
				// 选正确的中文意思
				List<WordBean4Test> list = testBean.getList();
				adapter = new ExamListAdapter(ctx, list);
				lv.setAdapter(adapter);
				lv.setVisibility(View.VISIBLE);
				ll.setVisibility(View.GONE);
				tv_wordOrChinese.setText(testBean.getWordBean().getWord());
			} else if (mode == 1) {
				// 拼写
				tv_wordOrChinese.setText(testBean.getWordBean().getChinese());
				lv.setVisibility(View.GONE);
				ll.setVisibility(View.VISIBLE);
			}
			pb.setText(currentCount + "/" + totalCount);
			pb.resetCount();
			new Thread(new Runnable() {

				@Override
				public void run() {
					int progress = 0;
					int y = 360;
					while (progress < y && !stop) {
						pb.incrementProgress();
						progress++;
						try {
							// 10秒走一圈
							Thread.sleep(1000 / 36);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(!stop)
						handler.sendEmptyMessage(TIMEOUT);
				}
			}).start();
		}
	}

	private void initListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				stop = true;
				pb.resetCount();
				WordBean4Test bean = (WordBean4Test) parent
						.getItemAtPosition(position);
				ViewHolder holder = (ViewHolder) view.getTag();
				holder.cb.setChecked(true);
				View iv_right = lv.findViewWithTag(adapter.getRightWord());
				holder.iv.setVisibility(View.VISIBLE);
				iv_right.setVisibility(View.VISIBLE);
				if (bean.getWord().equals(adapter.getRightWord())) {
					Toast.makeText(ctx, "选择正确", Toast.LENGTH_SHORT).show();
					rightCount++;
					caculate();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							nextQuestion();
						}
					}, 1000);
				} else {
					Toast.makeText(ctx, "选择错误", Toast.LENGTH_SHORT).show();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							goToReviewAcitivy();
						}
					}, 1000);
				}
			}
		});
		btn_exam_sure.setOnClickListener(this);
		btn_exam_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_exam_sure:
			String trim = et_exam.getText().toString().trim();
			if (TextUtils.isEmpty(trim)) {
				Toast.makeText(ctx, "您未输入任何单词！", Toast.LENGTH_SHORT).show();
				return;
			}
			stop = true;
			if (trim.equals(testBean.getWordBean().getWord())) {
				Toast.makeText(ctx, "拼写正确！", Toast.LENGTH_SHORT).show();
				et_exam.setText("");
				rightCount++;
				caculate();
				nextQuestion();
			} else {
				Toast.makeText(ctx, "拼写错误！", Toast.LENGTH_SHORT).show();
				et_exam.setText("");
				goToReviewAcitivy();
			}
			break;
		case R.id.btn_exam_auto:

			break;
		case R.id.btn_exam_back:
			finish();
			break;

		default:
			break;
		}
	}

	private void goToReviewAcitivy() {
		Intent intent = new Intent(ctx, ReviewActivity.class);
		intent.putExtra("bean", testBean.getWordBean());
		startActivityForResult(intent, 11);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 11) {
			Toast.makeText(ctx, "复习回来的！", Toast.LENGTH_SHORT).show();
			caculate();
			nextQuestion();
		}
	}
	/** 下一题 */
	private void nextQuestion() {
		stop = false;
		if(currentCount <= totalCount){
			initData();
		}else{
			Toast.makeText(ctx, "测试结束！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ctx, ResultActivity.class);
			intent.putExtra("right", rightCount);
			intent.putExtra("total", totalCount);
			startActivity(intent);
			finish();
		}
	}

	/** 计算下道题的题号 */
	private void caculate() {
		if (mode == 0)
			mode++;
		else {
			mode = 0;
			index++;
		}
		currentCount++;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stop = true;
	}
}

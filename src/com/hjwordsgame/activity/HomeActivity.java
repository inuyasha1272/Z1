package com.hjwordsgame.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjwordsgame.R;
import com.hjwordsgame.util.MyLogger;
import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_home_setting;
	private TextView tv_home_fragmentdemo;
	private TextView tv_home_choosebooks;
	private TextView tv_home_mywords;
	private Button btn_home_level;
	private Button btn_home_recitewords;
	
	private SlidingMenu menu;
	private TextView tv_setting_login;
	private View rl_setting_aboutus;
	private View rl_setting_apprecommend;
	private View rl_setting_appshare;
	private View rl_setting_feedback;
	private int slidingBehindWidth;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initData();
		initView();
		initListeners();
	}

	private void initData() {
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		slidingBehindWidth = width*3/4;
		MyLogger.i(TAG, "屏幕宽高是："+width+","+height+",滑动菜单的宽度是："+slidingBehindWidth);
	}

	private void initView() {
		iv_home_setting = (ImageView) findViewById(R.id.iv_home_setting);
		tv_home_fragmentdemo = (TextView) findViewById(R.id.tv_home_fragmentdemo);
		tv_home_choosebooks = (TextView) findViewById(R.id.tv_home_choosebooks);
		tv_home_mywords = (TextView) findViewById(R.id.tv_home_mywords);
		btn_home_level = (Button) findViewById(R.id.btn_home_level);
		btn_home_recitewords = (Button) findViewById(R.id.btn_home_recitewords);
		initSlidingMenu();
	}

	private void initSlidingMenu() {
		menu = new SlidingMenu(ctx);
		menu.setMode(SlidingMenu.RIGHT); 									// 设置模式为右往左滑
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); 			// 设置滑动的屏幕局限，该设置为全屏区域都可以滑动
		menu.setShadowWidthRes(R.dimen.width_1_80); 						// 设置阴影的宽度
		menu.setShadowDrawable(R.drawable.shadow); 							// 设置阴影
		menu.setBehindOffsetRes(R.dimen.width_1_80); 						// SlidingMenu划出时主页面显示的残剩宽度
		menu.setBehindWidth(slidingBehindWidth); 							// 设置SlidingMenu菜单的宽度
		menu.setFadeDegree(0.35f); 											// SlidingMenu滑动时的渐变程度
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.activity_menu_settting);
		rl_setting_aboutus = menu.findViewById(R.id.rl_setting_aboutus);
		rl_setting_apprecommend = menu.findViewById(R.id.rl_setting_apprecommend);
		rl_setting_appshare = menu.findViewById(R.id.rl_setting_appshare);
		rl_setting_feedback = menu.findViewById(R.id.rl_setting_feedback);
		tv_setting_login = (TextView) menu.findViewById(R.id.tv_setting_login);
	}

	private void initListeners() {
		tv_home_fragmentdemo.setOnClickListener(this);
		tv_home_choosebooks.setOnClickListener(this);
		tv_home_mywords.setOnClickListener(this);
		btn_home_level.setOnClickListener(this);
		btn_home_recitewords.setOnClickListener(this);
		rl_setting_aboutus.setOnClickListener(this);
		rl_setting_apprecommend.setOnClickListener(this);
		rl_setting_appshare.setOnClickListener(this);
		rl_setting_feedback.setOnClickListener(this);
		iv_home_setting.setOnClickListener(this);
		tv_setting_login.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing())
			menu.toggle();
		else
			super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_home_fragmentdemo:
//			Toast.makeText(ctx, "fragmentdemo", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(ctx,MyFragmentActivity.class));
			break;
		case R.id.tv_home_choosebooks:
			Toast.makeText(ctx, "选词书", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_home_mywords:
			Toast.makeText(ctx, "生词本", Toast.LENGTH_SHORT).show();
			break;
		case R.id.iv_home_setting:
			menu.toggle();
			break;
		case R.id.rl_setting_aboutus:
			Toast.makeText(ctx, "关于我们", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting_apprecommend:
			Toast.makeText(ctx, "应用推荐", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting_appshare:
			Toast.makeText(ctx, "应用分享", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting_feedback:
			Toast.makeText(ctx, "意见反馈", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_setting_login:
			Toast.makeText(ctx, "登录", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_home_recitewords:
			Intent intent = new Intent(ctx,ChooseWordsActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_home_level:
			Toast.makeText(ctx, "第2关", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}

package com.hjwordsgame.activity;

import android.app.Activity;

public class BaseActivity extends Activity{
	protected String TAG = this.getClass().getSimpleName();
	protected Activity ctx = this;
}

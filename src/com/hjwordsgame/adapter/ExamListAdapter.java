package com.hjwordsgame.adapter;

import java.util.List;

import android.app.Activity;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjwordsgame.R;
import com.hjwordsgame.model.TestBean;
import com.hjwordsgame.model.WordBean;
import com.hjwordsgame.model.WordBean4Test;

public class ExamListAdapter extends BaseAdapter {

	private static final String TAG = "ExamListAdapter";
	private Activity ctx;
	private List<WordBean4Test> beans;
	private String rightWord;

	public String getRightWord() {
		return rightWord;
	}

	public ExamListAdapter(Activity ctx, List<WordBean4Test> beans) {
		super();
		this.ctx = ctx;
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	/**测试中的选项只有4个，因此这里不需要复用item*/
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		convertView = View.inflate(ctx, R.layout.lvitem_exam, null);
		holder.cb = (CheckBox) convertView.findViewById(R.id.cb_exam_lvitem);
		holder.tv = (TextView) convertView.findViewById(R.id.tv_exam_lvitem);
		holder.iv = (ImageView) convertView
				.findViewById(R.id.iv_exam_lvitem);
		WordBean4Test bean = beans.get(position);
		holder.tv.setText(bean.getChinese());
		if(bean.isRight()){
			holder.iv.setBackgroundResource(R.drawable.pic_right);
			rightWord = bean.getWord();
			holder.iv.setTag(rightWord);
		}
		else
			holder.iv.setBackgroundResource(R.drawable.pic_wrong);
		Log.e(TAG, "当前bean的信息：" + bean.toString());
		convertView.setTag(holder);
		return convertView;
	}

	public static class ViewHolder {
		public ImageView iv;
		public TextView tv;
		public CheckBox cb;
	}
}

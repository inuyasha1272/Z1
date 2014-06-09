package com.hjwordsgame.adapter;

import java.util.List;

import android.app.Activity;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.hjwordsgame.R;
import com.hjwordsgame.model.WordBean;

public class ChooseWordsAdapter extends BaseAdapter {

	private static final String TAG = "ChooseWordsAdapter";
	private Activity ctx;
	private List<WordBean> beans;
	/**当前选中的单词数*/
	private int currentCount;
	/**当前设置的最大单词数*/
	private int totalCount;
	private SoundPool pool;
	private TextView tv_cw_count;
	
	public ChooseWordsAdapter(Activity ctx, List<WordBean> beans, SoundPool pool,TextView tv_cw_count) {
		super();
		this.ctx = ctx;
		this.beans = beans;
		this.pool = pool;
		this.tv_cw_count = tv_cw_count;
		this.totalCount = beans.size();
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public void setTotalCount(int totalCount) {
		this.currentCount = totalCount;
		this.totalCount = totalCount;
		for (WordBean bean : beans) {
			bean.setSelected(true);
		}
	}

	@Override
	public int getCount() {
		return totalCount;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(ctx,
					R.layout.lvitem_choosewords, null);
			holder = new ViewHolder();
			holder.cb_choosewords_lvitem = (CheckBox) convertView
					.findViewById(R.id.cb_choosewords_lvitem);
			holder.tv_word = (TextView) convertView
					.findViewById(R.id.tv_choosewords_lvitem_word);
			holder.tv_chinese = (TextView) convertView
					.findViewById(R.id.tv_choosewords_lvitem_chinese);
			holder.ll_choosewords_lvitem = convertView
					.findViewById(R.id.ll_choosewords_lvitem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final WordBean bean = beans.get(position);
		final int word = pool.load(ctx, bean.getWordVoicePath(), 1);
		holder.tv_word.setText(bean.getWord());
		holder.tv_chinese.setText(bean.getChinese());
		holder.ll_choosewords_lvitem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pool.play(word, 1, 1, 0, 0, 1);
			}
		});
		holder.cb_choosewords_lvitem.setChecked(bean.isSelected());
		holder.cb_choosewords_lvitem.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				bean.setSelected(isChecked);
				if(isChecked){
					tv_cw_count.setText("已选"+(++currentCount)+"个词");
				}else{
					tv_cw_count.setText("已选"+(--currentCount)+"个词");
				}
			}
		});
		return convertView;
	}

	static class ViewHolder {
		View ll_choosewords_lvitem;
		TextView tv_chinese;
		TextView tv_word;
		CheckBox cb_choosewords_lvitem;
	}
}

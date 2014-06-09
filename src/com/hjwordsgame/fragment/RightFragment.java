package com.hjwordsgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwordsgame.R;

public class RightFragment extends Fragment {
	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_right, null, false);
		tv = (TextView) view.findViewById(R.id.tv_fragment_right);
		
		return view;
	}
	
	public void setText(String text){
		tv.setText(text);
	}
}

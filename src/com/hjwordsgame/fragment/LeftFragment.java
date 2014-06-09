package com.hjwordsgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.hjwordsgame.R;

public class LeftFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_left, null, false);
		Button button = (Button) view.findViewById(R.id.btn_fragment_left);
		
		
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RightFragment rightFragment = (RightFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment2);
				rightFragment.setText("内容改变了哦");
				
			}
		});
		return view;
	}
}

package com.sjd.mynote2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyfragmentView extends Fragment {

	private View view;
	private EditText medit_text1;
	private EditText medit_text2;
	private Note mnote;
	private Button button_back;
	private Button button_modify;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.viewnote, container, false);
		Init();
		return view;
	}

	private void Init() {
		Listen_button_back();
		Listen_button_modify();
		show();

	}

	// 展示Note标题和内容
	private void show() {
		mnote = (Note) getArguments().getSerializable("Note");

		medit_text1 = (EditText) view.findViewById(R.id.view_edittext_title);
		medit_text1.setFocusable(false);
		medit_text1.setFocusableInTouchMode(false);
		medit_text1.setText(mnote.getNote_title());

		medit_text2 = (EditText) view.findViewById(R.id.view_edittext_content);
		medit_text2.setFocusable(false);
		medit_text2.setFocusableInTouchMode(false);
		medit_text2.setText(mnote.getNote_content());
	}

	private void Listen_button_back() {
		button_back = (Button) view.findViewById(R.id.view_button_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.fragment_container, new MyfragmentHome());
				ft.commit();

			}
		});
	}

	private void Listen_button_modify() {
		button_modify = (Button) view.findViewById(R.id.view_button_modify);
		button_modify.setTag(1);
		button_modify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((Integer) v.getTag() == 1) {
					// 更改两个输入框为可编辑状态，并设置更改按钮为保存按钮
					medit_text1.setFocusable(true);
					medit_text1.setFocusableInTouchMode(true);

					medit_text2.setFocusable(true);
					medit_text2.setFocusableInTouchMode(true);

					button_modify.setText("保存");
					button_modify.setTag(0);

					Toast.makeText(getActivity(), "进入编辑状态", Toast.LENGTH_SHORT)
							.show();
				} else {
					save();
					clear();
				}
			}
		});
	}

	private void save() {
		
		CloseInput();
		String title = medit_text1.getText().toString();
		String content = medit_text2.getText().toString();
		mnote.setNote_title(title);
		mnote.setNote_content(content);

		SQLiteDatabase db = new MySQLOpenHelper(getActivity())
				.getWritableDatabase();
		String sql = "UPDATE Note SET title = ?,content = ?WHERE id = ?";
		db.execSQL(sql,
				new Object[] { mnote.getNote_title(), mnote.getNote_content(),
						mnote.getNote_id() });
		db.close();
		Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
	}

	// 处理更改恢复到浏览状态的变动
	private void clear() {
		button_modify.setText("更改");
		button_modify.setTag(1);
		medit_text1.setFocusable(false);
		medit_text1.setFocusableInTouchMode(false);
		// 为了能将键盘退出
		medit_text1.setEnabled(false);
		medit_text1.setEnabled(true);

		medit_text2.setFocusable(false);
		medit_text2.setFocusableInTouchMode(false);
		medit_text2.setEnabled(false);
		medit_text2.setEnabled(true);
	}

	//关闭软键盘
	private void CloseInput(){
		getActivity();
		InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(medit_text1.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(medit_text2.getWindowToken(), 0);  
	}
}

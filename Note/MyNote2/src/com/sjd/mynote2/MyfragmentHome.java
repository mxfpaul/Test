package com.sjd.mynote2;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyfragmentHome extends Fragment {

	private Button mbutton_new;
	private Button mbutton_edit;
	private Button mbutton_search;
	private ListView mlistview;
	private ArrayAdapter<String> madapter;
	private ArrayList<String> mdata;
	private ArrayList<Note> mdata2;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.home, container, false);
		Init();
		return view;

	}

	private void Init() {
		// 设置按钮监听
		ListenButton_edit();
		ListenButton_new();
		ListenButton_search();
		// 展示Note
		showNote();

	}

	private void showNote() {

		mlistview = (ListView) view.findViewById(R.id.home_listview_1);
		mdata = new ArrayList<String>();
		mdata2 = new ArrayList<Note>();

		SQLiteDatabase db = new MySQLOpenHelper(getActivity())
				.getWritableDatabase();
		Cursor cursor = db.query("note", new String[] { "id", "title",
				"content" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Note zheNote = new Note();
			zheNote.setNote_id(cursor.getInt(0));
			zheNote.setNote_title(cursor.getString(1));
			zheNote.setNote_content(cursor.getString(2));

			mdata.add(cursor.getString(1));
			mdata2.add(zheNote);
		}
		madapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mdata);
		// 测试listview 每个item的响应事件

		mlistview.setAdapter(madapter);

		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				for (int i = 0; i < mdata2.size(); i++) {
					if (id == i) {
						// 传递该Note
						Bundle bundle = new Bundle();
						bundle.putSerializable("Note", mdata2.get(i));
						MyfragmentView fragment = new MyfragmentView();

						fragment.setArguments(bundle);
						FragmentTransaction ft = getFragmentManager()
								.beginTransaction();

						ft.replace(R.id.fragment_container, fragment);

						ft.addToBackStack(null);
						ft.commit();
					}
				}

			}

		});

		cursor.close();
		db.close();
	}

	private void ListenButton_edit() {
		mbutton_edit = (Button) view.findViewById(R.id.home_button_edit);
		mbutton_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				android.support.v4.app.FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.fragment_container, new MyfragmentDelete());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
	}

	private void ListenButton_new() {
		mbutton_new = (Button) view.findViewById(R.id.home_button_new);
		mbutton_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				android.support.v4.app.FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.fragment_container, new MyfragmentNew());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
	}
	
	private void ListenButton_search() {
		mbutton_search = (Button) view.findViewById(R.id.home_button_search);
		mbutton_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				android.support.v4.app.FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.fragment_container, new MyfragmentSearch());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
	}
	
}

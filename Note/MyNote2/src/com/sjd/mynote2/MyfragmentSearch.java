package com.sjd.mynote2;

import java.util.ArrayList;

import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyfragmentSearch extends Fragment {
	private Button mbutton_search;
	private EditText medit_text1;
	private ListView mlistview;
	private ArrayList<String> mdata;
	private ArrayList<Note> mdata2;
	private ArrayAdapter<String> madapter;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.search, container,false);
		Init();
		return view;
	}

	private void Init(){
		
		ListenButton_search();
		
	}
	//监听并处理
	private void ListenButton_search(){
		mbutton_search = (Button)view.findViewById(R.id.search_button_search);
		mbutton_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					show();
					CloseInput();
				
				
			}
		});
	}
	//获取查询的条件
	private String get_Search_title(){
		medit_text1 = (EditText)view.findViewById(R.id.search_edittext_1);
		String search_item = "%"+medit_text1.getText().toString()+"%";
		return search_item;
	}
	
	//查询该条件
	private void Search_title(){
		mdata = new ArrayList<String>(); 
		mdata2 = new ArrayList<Note>(); 
		
		
		SQLiteDatabase db = new MySQLOpenHelper(getActivity()).getReadableDatabase();
		String sql = "SELECT * FROM Note WHERE title like ? ";
		
		Cursor cursor = db.rawQuery(sql,new String[]{get_Search_title()} );
		
		while(cursor.moveToNext()){
			Note note = new Note();
			note.setNote_id(cursor.getInt(0));
			note.setNote_title(cursor.getString(1));
			note.setNote_content(cursor.getString(2));
			mdata.add(note.getNote_title());
			mdata2.add(note);
		}
		cursor.close();
		db.close();
	}
	
	//展示查询到的Note
	private void show(){
		Search_title();
		mlistview = (ListView)view.findViewById(R.id.search_listview_1);
		madapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mdata);
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
	}

	//关闭虚拟键盘
	private void CloseInput(){
		getActivity();
		InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(medit_text1.getWindowToken(), 0);
		
	}
	
}

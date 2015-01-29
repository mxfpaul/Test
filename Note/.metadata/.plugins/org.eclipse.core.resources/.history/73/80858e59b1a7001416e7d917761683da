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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MyfragmentDelete extends Fragment {
	private Button mbutton_back;
	private Button mbutton_delete;
	private ListView mlistview;
	private ArrayAdapter<String> madapter;
	private ArrayList<String> mdata;//全部Note的title
	private ArrayList<Note> mdata2;//全部的Note
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.deletenote, container, false);
		ListenButton_back();
		ListenButton_delete();
		showNote();

		return view;
	}

	private void showNote() {

		mlistview = (ListView) view.findViewById(R.id.delete_listview_1);
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
				android.R.layout.simple_list_item_multiple_choice, mdata);
		
		// 测试listview 每个item的响应事件

		mlistview.setAdapter(madapter);
		mlistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		mlistview.isItemChecked(1);
		
		
		

		cursor.close();
		db.close();
	}

	private void ListenButton_delete(){
		mbutton_delete = (Button)view.findViewById(R.id.delete_button_delete);
		mbutton_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Delete();
				back();
			}
		});
	}
	
	private void ListenButton_back(){
		mbutton_back = (Button)view.findViewById(R.id.delete_button_back);
		mbutton_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back();
			}
		});
	}
	
	private void Delete(){
		//确认要删除的Note
		ArrayList<Note> note = new ArrayList<Note>();
		for(int i=0;i<mdata2.size();i++){
			if(mlistview.isItemChecked(i)==true){
				note.add(mdata2.get(i));
//				Toast.makeText(getActivity(), ""+mdata2.get(i).getNote_id(), Toast.LENGTH_SHORT).show();
			}
		}
		//删除选中的Note
		SQLiteDatabase db = new MySQLOpenHelper(getActivity()).getWritableDatabase();
		String sql = "DELETE FROM Note WHERE id = ?";
		for(int i=0;i<note.size();i++){
			db.execSQL(sql, new Object[]{note.get(i).getNote_id()});
		}
		db.close();
		Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();

	}
	
	private void back(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, new MyfragmentHome());
		ft.commit();
	}
	
	
}

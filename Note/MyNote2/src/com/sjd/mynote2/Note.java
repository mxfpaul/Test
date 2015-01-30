package com.sjd.mynote2;

import java.io.Serializable;

public class Note implements Serializable{
	

	private static final long serialVersionUID = 6389902511791533923L;
	private String mNote_title;
	private String mNote_content;
	private int mNote_id;
	private String mNote_Createdate;
	
	public String getNote_Createdate(){
		return mNote_Createdate;
	}
	
	public void setNote_Createdate(int month,int monthday,int hour,int min){
		mNote_Createdate = ""+month+"/"+monthday+" "+hour+":"+min;
	}
	
	public int getNote_id() {
		return mNote_id;
	}
	public void setNote_id(int note_id) {
		mNote_id = note_id;
	}
	public String getNote_title() {
		return mNote_title;
	}
	public void setNote_title(String note_title) {
		mNote_title = note_title;
	}
	public String getNote_content() {
		return mNote_content;
	}
	public void setNote_content(String note_content) {
		mNote_content = note_content;
	}
	
	
}

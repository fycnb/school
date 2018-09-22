package com.example.nooneschool.my;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity {
//	 private MyPublishAdapter mMyPublishAdapter;
//	 private ListView mMyFinish;
//	 private List<MyNeed> mMyNeeds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		init();
		getdata();
	}
	
	private void init(){
		
	}
	
	private void getdata(){
		
	}
//    new Thread(){
//        public void run(){
//            final String result = MyFinishService.MyFinishByPost(Userid);
//
//            if (result != null) {
//                
//                try {
//
//                    JSONArray ja = new JSONArray(result);
//                    mMyNeeds = new ArrayList<>();
//
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject j = (JSONObject) ja.get(i);
//                        String Needid = j.getString("Needid");
//                      
//                        mMyNeeds.add(new MyNeed());
//                        mMyPublishAdapter = new MyPublishAdapter(MyFinishActivity.this,mMyNeeds);
//
//                    }
//
//                   runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                        	mMyFinish = (ListView) findViewById(R.id.myfinish_list_view);
//                        	mMyFinish.setAdapter(mMyPublishAdapter);
//                        	mMyFinish.setOnItemClickListener(new OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                	final String needid =  ((MyNeed) mMyPublishAdapter.getItem(position)).getNeedid();
//                                	new Thread(){
//                                        public void run(){
//                                        	final String result = MyPublishItemService.MyPublishItemByPost(needid);
//               					
//             
//                                        	runOnUiThread(new Runnable() {
//                                        		
//                                                @Override
//                                                public void run() {
//                                           	 	 if(result != null) {
//                       						 		 try{
//                       	                            	   JSONObject j = new JSONObject(result);
//                       	                            	   
//                       	                            	   final String Userid = j.getString("Userid");
//                       	                            	   
//                       	                            	   Intent intent = new Intent(MyFinishActivity.this,MyPublishItemActivity.class);
//                       	                            	   intent.putExtra("needid", needid);
//                       	                            	   startActivity(intent);
//                       						 		 }catch (Exception e) {
//                       						 			 e.printStackTrace();
//                                                   }
//                       						 }
//                                                }
//                                            });
//                                        }
//                                    }.start();
//
//                                }
//
//                            });
//
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//              
//
//            }
//        }
//    }.start();
//}
}

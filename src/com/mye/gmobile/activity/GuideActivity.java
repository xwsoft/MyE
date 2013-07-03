package com.mye.gmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mye.gmobile.R;
import com.mye.gmobile.common.component.MyScrollLayout;
import com.mye.gmobile.common.component.OnViewChangeListener;

public class GuideActivity extends Activity implements OnViewChangeListener{
	
	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
  
/*        try {  
            SharedPreferences sharedPreferences = this.getSharedPreferences("MyE",Context.MODE_PRIVATE);
            int userSaveVer = sharedPreferences.getInt("versionCode",0);
            PackageManager pm = this.getPackageManager();  
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            int versionCode = pi.versionCode;
            if(userSaveVer == versionCode){
				Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
				GuideActivity.this.startActivity(intent);
				GuideActivity.this.finish();
				overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
            }
        	Editor editor = sharedPreferences.edit();
        	editor.putInt("versionCode",versionCode);
        	editor.commit();
        } catch (Exception e) {  
            Log.e("VersionInfo", "Exception", e);  
        }  */
        initView();
    }
    
	private void initView() {
		mScrollLayout  = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		animLayout = (LinearLayout) findViewById(R.id.animLayout);
		leftLayout  = (LinearLayout) findViewById(R.id.leftLayout);
		rightLayout  = (LinearLayout) findViewById(R.id.rightLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for(int i = 0; i< count;i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}
	

	@Override
	public void OnViewChange(int position) {
		Log.i("OnViewChange--position",""+position);
		if(position==count){
			mScrollLayout.setVisibility(View.GONE);
			pointLLayout.setVisibility(View.GONE);
			animLayout.setVisibility(View.VISIBLE);
			leftLayout.setVisibility(View.GONE);
			rightLayout.setVisibility(View.GONE);
			Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
			GuideActivity.this.startActivity(intent);
			GuideActivity.this.finish();
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			return;
		}
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		Log.i("setcurrentPoint", position+"");
		if(position < 0 || position > count -1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}
}
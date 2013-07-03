package com.mye.gmobile.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author xwsoft
 * @version date：2013-5-20 下午5:07:37
 * 
 */
public class BorderTextView extends TextView{
	
	private int bg_color = Color.RED;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		// 将边框设为黑色
		paint.setColor(bg_color);
		// 画TextView的4个边
		canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
		canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,this.getHeight() - 1, paint);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,this.getHeight() - 1, paint);
	}

	public BorderTextView(Context context, AttributeSet attrs,int _bg_color) {
		super(context, attrs);
		bg_color = _bg_color;
	}
}

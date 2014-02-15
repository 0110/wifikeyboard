package com.volosyukivan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * 
 * @author ollo
 *
 */
public class WebCursorView extends View {

	private Paint mPaint = new Paint();
	private RectF mCursor = new RectF(20, 40, 10, 15);

	public WebCursorView(Context context) {
		super(context);
		mPaint.setColor(Color.MAGENTA);
	}
	
	protected void onDraw(Canvas canvas) {
		   super.onDraw(canvas);
		   
		   canvas.drawOval(this.mCursor, this.mPaint);
	}

}

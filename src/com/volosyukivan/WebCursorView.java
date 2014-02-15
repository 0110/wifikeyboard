package com.volosyukivan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author ollo
 *
 */
public class WebCursorView extends View {

	private Paint mPaint = new Paint();
	private RectF mCursor = new RectF(20, 40, 15, 15);
	private Point mScreenDimension = null;

	public WebCursorView(Context context, Point screenDimension) {
		super(context);
		mPaint.setColor(Color.CYAN);
		this.mScreenDimension  = screenDimension;
		mCursor.left = mScreenDimension.x / 2;
		mCursor.top = mScreenDimension.y / 2;
		mCursor.right = (mScreenDimension.x / 2) + 15;
		mCursor.bottom = (mScreenDimension.y / 2) + 15;
	}
	
	protected void onDraw(Canvas canvas) {
		   super.onDraw(canvas);
		   if (mScreenDimension != null)
		   {
			   canvas.drawOval(this.mCursor, this.mPaint);			   
		   }
	}

	public void decreaseX() {
		// TODO Tiny test
		mCursor.left -= 5;
		mCursor.right -= 5;
		Log.e("Cursor", "X: " + mCursor.left);
		invalidate();
	}

	public void increaseX() {
		// TODO Tiny test
		mCursor.left += 5;
		mCursor.right += 5;
		Log.e("Cursor", "X: " + mCursor.left);
		invalidate();
	}

}

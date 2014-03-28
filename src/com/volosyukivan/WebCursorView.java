package com.volosyukivan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.input.InputManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
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
	private InputManager im = null;

	public WebCursorView(Context context, Point screenDimension, InputManager im) {
		super(context);
		mPaint.setColor(Color.CYAN);
		this.mScreenDimension  = screenDimension;
		this.im = im; // Needed to send an key event. 
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

	public void setCursor(final int posx, final int posy) {
		mCursor.top = posx;
		mCursor.bottom = posy;
		invalidate();
	}
	
	public void fire() {
		final int posx = (int) mCursor.left;
		final int posy = (int) mCursor.top;
		
		//inject pointer event
		int[] ptrs = { 0 };
		//MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1, ptrs, posx, posy, 0, 1, 1, 0, 0,    InputDevice.SOURCE_TOUCHPAD, 0);
		
		MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, posx, posy, 1);
		this.dispatchTouchEvent(event);
		Log.d("Cursor", "Click: " + posx + " " + posy);
	}
}

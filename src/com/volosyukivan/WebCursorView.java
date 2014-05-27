package com.volosyukivan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.input.InputManager;
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

	public void setCursor(int posx, int posy) {
	    
	    posx *=  (mScreenDimension.y / HttpService.TARGET_HTML_HEIGHT);
	    posy *=  (mScreenDimension.y / HttpService.TARGET_HTML_HEIGHT);
	    
		mCursor.top = posy + 7;
		mCursor.bottom = posy - 7;
		mCursor.left = posx - 7;
		mCursor.right = posx + 7;
		invalidate();
	}
	
	public void fire() {
		final int posx = (int) mCursor.left + 2;
		final int posy = (int) mCursor.top + 2;
        String line;
        
		try {
		    /* Workaround by writing the command into a "shell script" */
		    File dir = getContext().getDir("bash", Context.MODE_PRIVATE);
		    final String f = dir.getAbsolutePath() + "/command.sh";
		    Log.v("Cursor", "Script : " + f);
		    FileWriter fw = new FileWriter(f);
		    fw.write("input touchscreen tap " + posx + " " + posy+"");
		    fw.close();
		    
		    Process ret = Runtime.getRuntime().exec("chmod 755 " + f);
            int retcode = ret.waitFor();
            Log.v("Cursor", "call : chmod 755 " + f + " returned " + retcode);
            
		    final String cmd = "su -c " + f;
            ret = Runtime.getRuntime().exec(cmd);
            
            retcode = ret.waitFor();
            
            Log.d("Cursor", "Click: " + posx + " " + posy + " returned with " + retcode);
            Log.v("Cursor", "call : " + cmd);
            //if (retcode > 0)
            {
                BufferedReader err = new BufferedReader(new InputStreamReader(ret.getErrorStream()));
                while ((line = err.readLine()) != null)
                {
                    Log.d("Cursor", "err: " + line);
                }
                BufferedReader out = new BufferedReader(new InputStreamReader(ret.getInputStream()));
                while ((line = out.readLine()) != null)
                {
                    Log.d("Cursor", "out: " + line);
                }
            }
            
            
        } catch (IOException e) {
            Log.e("Cursor", "Impossible to click " + posx + "x" + posy + " because of " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("Cursor", "Impossible to click " + posx + "x" + posy + " because of " + e.getMessage());
            e.printStackTrace();
        }
	}
}

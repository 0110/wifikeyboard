package com.volosyukivan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
    private String mScript  = null;

	public WebCursorView(Context context, Point screenDimension, InputManager im) {
		super(context);
		mPaint.setColor(Color.CYAN);
		this.mScreenDimension  = screenDimension;
		this.im = im; // Needed to send an key event. 
		mCursor.left = mScreenDimension.x / 2;
		mCursor.top = mScreenDimension.y / 2;
		mCursor.right = (mScreenDimension.x / 2) + 15;
		mCursor.bottom = (mScreenDimension.y / 2) + 15;
		
		/* build script to generate mouse clicks */
        try {
            buildScript();
        } catch (IOException e) {
            Log.e("Cursor", "Impossible to generate shell script, because of " + e.getMessage());
            mScript = null;
        } catch (InterruptedException e) {
            Log.e("Cursor", "Impossible to generate shell script, because of " + e.getMessage());
            mScript = null;
        }
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
		    Process ret;
            int retcode;
            
            if (mScript == null)
                throw new IOException("shell script does not exist!");
            
		    final String cmd = "su -c " + mScript;
            ret = Runtime.getRuntime().exec(cmd);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ret.getOutputStream()));
            bw.write(""+posx + "\n");
            bw.flush();
            bw.write(""+posy + "\n");
            bw.flush();
            
            retcode = ret.waitFor();
            
            Log.d("Cursor", "Click: " + posx + " " + posy + " returned with " + retcode);
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

    private void buildScript() throws IOException, InterruptedException {
        /* Workaround by writing the command into a "shell script" */
        File dir = getContext().getDir("bash", Context.MODE_PRIVATE);
        this.mScript = dir.getAbsolutePath() + "/command.sh";
        Log.v("Cursor", "Script : " + this.mScript);
        FileWriter fw = new FileWriter(this.mScript);
        fw.write("#!/system/bin/sh\n");
        fw.write("read posx\n");
        fw.write("read posy\n");
        fw.write("input touchscreen tap $posx $posy\n");
        fw.close();
        
        Process ret = Runtime.getRuntime().exec("chmod 755 " + this.mScript);
        int retcode = ret.waitFor();
        Log.v("Cursor", "call : chmod 755 " + this.mScript + " returned " + retcode);
    }
}

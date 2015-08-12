package de.andrano.light;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Light {
	
	String TAG = "de.andrano.light";
	
	Camera camera = null;
	Parameters parameter;
	Context context;
	
	public Light(Context cxt) {
		try {
			context = cxt;
			if (camera == null) {
				camera = Camera.open();
			}
			if (camera == null) {
				Toast.makeText(context, R.string.no_camera, Toast.LENGTH_SHORT).show();
			}
			parameter 	= camera.getParameters();
		} catch (Exception e) {
			Log.e(TAG, "Exception (Constructor): "+e.toString());
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		super.finalize();
	}
	
	@SuppressLint("NewApi")
	public Boolean turnOn() {
		//Shut on
		try {
			if (camera == null) {
				camera = Camera.open();
			}
			parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameter);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
				camera.setPreviewTexture(new SurfaceTexture(0));
			}
			camera.startPreview();
		} catch (Exception e) {
			Toast.makeText(context, R.string.no_flash, Toast.LENGTH_SHORT).show();
			Log.e(TAG, "Exception (turn_on): "+e.toString());
			return false;
		}
		return true;
	}
	
	public Boolean turnOff() {
		//Shut off
		try {
			parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(parameter);
			camera.stopPreview();
			camera.release();
			camera = null;
		} catch (Exception e) {
			Log.e(TAG, "Exception (turn_off): "+e.toString());
			return false;
		}
		return true;
	}
	
}
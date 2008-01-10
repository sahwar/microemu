package org.microemu.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.android.device.AndroidDevice;
import org.microemu.android.device.AndroidDeviceDisplay;
import org.microemu.android.device.AndroidFontManager;
import org.microemu.android.device.AndroidInputMethod;
import org.microemu.android.ui.AndroidDisplayComponent;
import org.microemu.android.util.AndroidLoggerAppender;
import org.microemu.android.util.AndroidRecordStoreManager;
import org.microemu.app.Common;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.log.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MicroEmulator extends Activity {
	
	public static final String LOG_TAG = "MicroEmulator";
		
	protected Common common;

	protected EmulatorContext emulatorContext = new EmulatorContext() {

		private InputMethod inputMethod = new AndroidInputMethod();

		private DeviceDisplay deviceDisplay = new AndroidDeviceDisplay(this);
		
		private FontManager fontManager = new AndroidFontManager();

		public DisplayComponent getDisplayComponent() {
			return devicePanel;
		}

		public InputMethod getDeviceInputMethod() {
			return inputMethod;
		}

		public DeviceDisplay getDeviceDisplay() {
			return deviceDisplay;
		}

		public FontManager getDeviceFontManager() {
			return fontManager;
		}

		public InputStream getResourceAsStream(String name) {
			try {
				if (name.startsWith("/")) {
					return MicroEmulator.this.getAssets().open(name.substring(1));
				} else {
					return MicroEmulator.this.getAssets().open(name);
				}
			} catch (IOException e) {
				Logger.debug(e);
				return null;
			}
		}
				
	};
	
	// TODO rename class & field name
	private AndroidDisplayComponent devicePanel;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        Logger.removeAllAppenders();
        Logger.addAppender(new AndroidLoggerAppender());
        
        System.setOut(new PrintStream(new OutputStream() {
        	
        	StringBuffer line = new StringBuffer();

			@Override
			public void write(int oneByte) throws IOException {
				if (((char) oneByte) == '\n') {
					Logger.debug(line.toString());
					line.delete(0, line.length() - 1);
				} else {
					line.append((char) oneByte);
				}
			}
        	
        }));
        
        System.setErr(new PrintStream(new OutputStream() {
        	
        	StringBuffer line = new StringBuffer();

			@Override
			public void write(int oneByte) throws IOException {
				if (((char) oneByte) == '\n') {
					Logger.debug(line.toString());
					line.delete(0, line.length() - 1);
				} else {
					line.append((char) oneByte);
				}
			}
        	
        }));
        
        String midletClassName = getResources().getString(0x7f020001);

        java.util.List params = new ArrayList();
        params.add("--usesystemclassloader");
        params.add(midletClassName);
        
        devicePanel = new AndroidDisplayComponent(this);
        
        ((AndroidDeviceDisplay) emulatorContext.getDeviceDisplay()).displayRectangleWidth = 320;
        ((AndroidDeviceDisplay) emulatorContext.getDeviceDisplay()).displayRectangleHeight = 220;
        
        common = new Common(emulatorContext);
        common.setRecordStoreManager(new AndroidRecordStoreManager(this));
        common.setDevice(new AndroidDevice(emulatorContext, this));        
        common.initParams(params, null, AndroidDevice.class);
               
        setContentView(devicePanel);
        
        System.setProperty("microedition.platform", "microemulator-android");

        common.getLauncher().setSuiteName(midletClassName);
        common.initMIDlet(true);
    }

}
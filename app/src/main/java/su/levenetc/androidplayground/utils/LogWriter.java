package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class LogWriter {

	private static final String TAG = LogWriter.class.getSimpleName();
	private String fileName;
	private Context context;
	private static final int TAG_LENGTH = 10;
	private final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.ENGLISH);
	private boolean enabled = false;

	public LogWriter(String fileName, Context context) {
		this.fileName = fileName;
		this.context = context;
	}

	public void setEnable(boolean value) {
		enabled = value;
	}

	public void log(String tag, String message) {
		if (enabled) {
			try {
				tag = Utils.fixLength(tag, TAG_LENGTH, ' ');
				String time = timeFormat.format(new Date(System.currentTimeMillis()));

				FileOutputStream out = context.openFileOutput(fileName, Context.MODE_APPEND);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
				String logMessage = time + " | " + tag + " | " + message + "\n";
				outputStreamWriter.append(logMessage);
				outputStreamWriter.close();

				Log.i(TAG, logMessage);

			} catch (IOException e) {
				//ignore
			}
		}
	}

	public String getLogs() {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput(fileName);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString;
				StringBuilder sb = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					sb.append(receiveString);
					sb.append("\n");
				}


				inputStream.close();
				ret = sb.toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "File not found: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Can not read file: " + e.toString());
		}

		return ret;
	}

}
package su.levenetc.androidplayground.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static android.content.Intent.EXTRA_SUBJECT;


/**
 * Created by Eugene Levenetc on 16/07/2016.
 */
public class SystemUtils {

    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;

    public static void changeStaticField(Class clazz, String fieldName, String targetValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(clazz, targetValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectedToWiFi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        else return info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected();
    }

    public static void requestLocationPermission(Activity activity) {
        if (isMarshmallow()) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }
    }

    public static void requestReadExternalStoragePermission(Activity activity) {
        if (isMarshmallow()) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, LOCATION_REQUEST);
        }
    }

    public static void requestCameraPermission(Activity activity) {
        if (isMarshmallow()) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, LOCATION_REQUEST);
        }
    }

    public static boolean canLaunchCamera(Context context) {
        if (isMarshmallow()) {
            return (hasPermission(context, Manifest.permission.CAMERA));
        } else {
            return true;
        }
    }

    public static boolean canReadExternalStorage(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 15) {
            return (hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE));
        } else {
            return true;
        }
    }

    public static boolean canAccessLocation(Context context) {
        return (hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public static boolean hasPermission(Context context, String perm) {
        if (isMarshmallow()) {
            return (PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(perm));
        } else {
            return true;
        }
    }

    public static boolean hasSensor(int type, Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {

            if (sensor.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public static void openGooglePlayPage(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * @return 0 if there is no such resource
     */
    public static int getDrawableResIdByName(String resName, Context context) {
        if (resName == null) return 0;
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(resName, "drawable", packageName);
    }

    public static String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * @return 0, 90, 180 or 270. 0 could be returned if there is no data about rotation
     */
    public static int getImageRotation(Context context, Uri imageUri) {
        try {
            ExifInterface exif = new ExifInterface(imageUri.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            if (rotation == ExifInterface.ORIENTATION_UNDEFINED)
                return getRotationFromMediaStore(context, imageUri);
            else return exifToDegrees(rotation);
        } catch (IOException e) {
            return 0;
        }
    }

    public static
    @Nullable
    Point getPhotoDimens(Context context, Uri imageUri) throws IOException {
        final ExifInterface exif = getExif(context, imageUri);
        if (exif != null) {
            final int width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1);
            final int height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1);
            if (width == -1 || height == -1) return null;
            else return new Point(width, height);
        }
        return null;
    }

    public static
    @Nullable
    ExifInterface getExif(Context context, Uri imageUri) throws IOException {
        return new ExifInterface(imageUri.getPath());
    }

    public static int getRotationFromMediaStore(Context context, Uri imageUri) {
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
        if (cursor == null) return 0;

        cursor.moveToFirst();

        int orientationColumnIndex = cursor.getColumnIndex(columns[1]);
        return cursor.getInt(orientationColumnIndex);
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } else if (exifOrientation == ExifInterface.ORIENTATION_NORMAL) {
            return 0;
        }

        return 0;
    }

    public static int getScreenOrientation(Context context) {
        int orientation;
        if (ViewUtils.getScreenWidth(context) < ViewUtils.getScreenHeight(context)) {
            orientation = Configuration.ORIENTATION_PORTRAIT;
        } else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return orientation;
    }

    public static void recycleBitmaps(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) recycleBitmap(bitmap);
    }

    public static void recycleBitmap(@Nullable Bitmap bitmap) {
        if (bitmap != null) bitmap.recycle();
    }

    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshmallow() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @SuppressLint("NewApi")
    public static void increaseWebViewTextSize(WebView webView) {
        WebSettings settings = webView.getSettings();
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        } else {
            settings.setTextZoom(140);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(true);
        }
    }

    @SuppressLint("NewApi")
    public static void copyText(String text, Context context) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public static void openEmailApp(@Nullable Context c, int subject, String mailTo, @StringRes int cantFindEmail) {
        openEmailApp(c, subject, mailTo, null, cantFindEmail);
    }

    public static void openEmailApp(@Nullable Context c, int subject, String mailTo, File attachment, @StringRes int cantFindEmail) {
        if (c == null) return;
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("message/rfc822");
            intent.setData(Uri.parse("mailto:" + mailTo));
            intent.putExtra(EXTRA_SUBJECT, c.getString(subject));
            if (attachment != null) intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));
            c.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(c, cantFindEmail, Toast.LENGTH_LONG).show();
        }
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity, TextView textView) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (textView != null) {
                textView.clearFocus();
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }


    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity, null);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String getDeviceId(Context context) {

        String googleDeviceId = null;
        try {
            googleDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Throwable th) {
            Log.w("getDeviceId", th);
        }

        if (googleDeviceId != null && googleDeviceId.length() > 0)
            return googleDeviceId;

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    public static boolean isHigherThan(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT > apiLevel;
    }

    public static void showKeyboard(EditText view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//		inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
//		((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
//		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//		inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
//		editText.requestFocus();
    }
}
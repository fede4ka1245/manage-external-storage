package com.manageexternalstorage;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import android.os.Build;
import android.os.Environment;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.widget.Toast;
import android.app.Activity;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;

@ReactModule(name = ManageExternalStorageModule.NAME)
public class ManageExternalStorageModule extends ReactContextBaseJavaModule implements ActivityEventListener  {
  private static final int REQUEST_CODE = 228228;

  public static final String NAME = "ManageExternalStorage";
  Promise promise;
  ReactApplicationContext reactContext;

  public ManageExternalStorageModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void checkManagePermission(Promise promise) {
      if (Build.VERSION.SDK_INT >= 30) {
          promise.resolve(Environment.isExternalStorageManager());
      } else {
          int result = ContextCompat.checkSelfPermission(getReactApplicationContext(), READ_EXTERNAL_STORAGE);
          int result1 = ContextCompat.checkSelfPermission(getReactApplicationContext(), WRITE_EXTERNAL_STORAGE);
          promise.resolve(result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED);
      }
  }


  @ReactMethod
  public void requestManagePermission(Promise promise) {
    this.promise = promise;
    if (Build.VERSION.SDK_INT >= 30) {
          try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(String.format("package:%s",getReactApplicationContext().getPackageName())));
            getReactApplicationContext().startActivityForResult(intent, REQUEST_CODE, null);
          } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            getReactApplicationContext().startActivityForResult(intent, REQUEST_CODE, null);
          }
      } else {
          ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, 100);
      }
  }

  @Override
  public void onNewIntent(Intent intent) {
  }


  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
    if (REQUEST_CODE != requestCode) {
      return;
    }

    if (Build.VERSION.SDK_INT >= 30) {
      this.promise.resolve(Environment.isExternalStorageManager());
    } else {
      int result = ContextCompat.checkSelfPermission(getReactApplicationContext(), READ_EXTERNAL_STORAGE);
      int result1 = ContextCompat.checkSelfPermission(getReactApplicationContext(), WRITE_EXTERNAL_STORAGE);
      this.promise.resolve(result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED);
    }
  }
}

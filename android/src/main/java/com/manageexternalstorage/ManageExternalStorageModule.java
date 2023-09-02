package com.manageexternalstorage;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = ManageExternalStorageModule.NAME)
public class ManageExternalStorageModule extends ReactContextBaseJavaModule {
  public static final String NAME = "ManageExternalStorage";

  public ManageExternalStorageModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void checkPermission() {
      if (Build.VERSION.SDK_INT >= 30) {
          return Environment.isExternalStorageManager();
      } else {
          int result = ContextCompat.checkSelfPermission(getReactApplicationContext(), READ_EXTERNAL_STORAGE);
          int result1 = ContextCompat.checkSelfPermission(getReactApplicationContext(), WRITE_EXTERNAL_STORAGE);
          return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
      }
  }

  @ReactMethod
  public void requestPermission() {
      if (Build.VERSION.SDK_INT >= 30) {
          try {
              Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
              intent.addCategory("android.intent.category.DEFAULT");
              intent.setData(Uri.parse(String.format("package:%s",getReactApplicationContext().getPackageName())));
              getCurrentActivity().startActivityForResult(intent, 2296);
          } catch (Exception e) {
              Intent intent = new Intent();
              intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
              getCurrentActivity().startActivityForResult(intent, 2296);
          }
      } else {
          ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, 100);
      }
  }
}

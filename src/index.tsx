import { NativeModules, Platform } from 'react-native';
import * as types from './types';

const ManageExternalStorage =
  NativeModules.ManageExternalStorage as types.ManageExternalStorageInterface;

export function checkManagePermission(): Promise<boolean> {
  if (Platform.OS === 'ios') {
    return new Promise((resolve) => {
      resolve(false);
    });
  }

  return ManageExternalStorage.checkManagePermission();
}

export function requestManagePermission(): Promise<boolean> {
  if (Platform.OS === 'ios') {
    return new Promise((resolve) => {
      resolve(false);
    });
  }

  return ManageExternalStorage.requestManagePermission();
}

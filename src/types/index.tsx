export type requestManagePermission = () => Promise<boolean>;
export type checkManagePermission = () => Promise<boolean>;
export interface ManageExternalStorageInterface {
  checkManagePermission: checkManagePermission;
  requestManagePermission: requestManagePermission;
}

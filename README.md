# manage-external-storage

Plugin to manage all files in external storage. See explanation here: https://developer.android.com/training/data-storage/manage-all-files

## Installation

```sh
npm install manage-external-storage
```

## Android Setup
### 1. Add the following permissions to Android Manifest
```
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

### 2. Add the following line to Android Manifest
```
some code above...
<application
  android:requestLegacyExternalStorage="true" <-- this one
```

## Usage

```js
// check if can manage
checkManagePermission().then((isManagePermitted) => {
  console.log(isManagePermitted);
});

// request rights to manage
requestManagePermission().then((isManagePermitted) => {
  console.log(isManagePermitted);
});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

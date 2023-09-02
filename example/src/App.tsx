import * as React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import {
  checkManagePermission,
  requestManagePermission,
} from 'manage-external-storage';

export default function App() {
  const [result, setResult] = React.useState<boolean>();

  React.useEffect(() => {
    checkManagePermission().then((result) => {
      console.log(result);
      setResult(result);
    });

    requestManagePermission().then((result) => {
      console.log(result);
      setResult(result);
    });
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

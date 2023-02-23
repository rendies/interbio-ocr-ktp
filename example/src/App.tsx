import * as React from 'react';
import { Button } from 'react-native';

import { StyleSheet, View, Text } from 'react-native';
import { loadOcrKtpActivity, isLoading } from 'react-native-interbio-ocr-ktp';

export default function App() {
  const [result, setResult] = React.useState<string>('');
  const [loading, setLoading] = React.useState<boolean>(false);

  React.useEffect(() => {
    // multiply(3, 7).then(setResult);
    setLoading(isLoading());
    // setResult(getOcrKtpResult());
  }, []);

  return (
    <View style={styles.container}>
      <Button
        onPress={async () => {
          setResult(await loadOcrKtpActivity());
        }}
        title="Start example activity"
      />
      <Text>Loading: {loading}</Text>
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

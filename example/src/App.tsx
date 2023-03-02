import * as React from 'react';
import { Button } from 'react-native';
import { Image } from 'react-native';

import { StyleSheet, View, Text } from 'react-native';
import { loadOcrKtpActivity, isLoading } from 'react-native-interbio-ocr-ktp';

export default function App() {
  const [result, setResult] = React.useState<string>('{}');
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
          let resultDt = await loadOcrKtpActivity();

          console.log(JSON.parse(resultDt).ktpImage.substring(0, 100));

          // @ts-ignore
          setResult(resultDt);
        }}
        title="Start example activity"
      />
      <Text>Loading: {loading ? 'Loading...' : 'Finish'}</Text>
      <Text>Result: {JSON.parse(result).demographicData}</Text>
      <Text>
        Is Photocopy:{' '}
        {JSON.parse(result).isPhotocopy ? 'Photo Copy' : 'Original'}
      </Text>
      <Text>Is Photocopy Score: {JSON.parse(result).isPhotocopyScore}</Text>
      <Image
        style={{
          width: 100,
          height: 50,
          borderWidth: 1,
          borderColor: 'red',
        }}
        source={{
          uri: `data:image/png;base64,${JSON.parse(result).ktpImage}`,
        }}
      />
      <Image
        style={{
          width: 100,
          height: 50,
          borderWidth: 1,
          borderColor: 'red',
        }}
        source={{
          uri: `data:image/png;base64,${JSON.parse(result).signature}`,
        }}
      />
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

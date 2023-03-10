import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-interbio-ocr-ktp' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const InterbioOcrKtp = NativeModules.InterbioOcrKtp
  ? NativeModules.InterbioOcrKtp
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function isLoading(): boolean {
  return InterbioOcrKtp.isLoading();
}

export function loadOcrKtpActivity(): Promise<string> {
  return InterbioOcrKtp.loadOcrKtpActivity();
}

export function multiply(a: number, b: number): Promise<number> {
  return InterbioOcrKtp.multiply(a, b);
}

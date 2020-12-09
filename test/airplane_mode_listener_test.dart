import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:airplane_mode_listener/airplane_mode_listener.dart';

void main() {
  const MethodChannel channel = MethodChannel('airplane_mode_listener');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AirplaneModeListener.platformVersion, '42');
  });
}

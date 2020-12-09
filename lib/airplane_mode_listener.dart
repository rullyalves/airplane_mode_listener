import 'dart:async';

import 'package:flutter/services.dart';

class AirplaneModeListener {
  static const _eventChannel =
      const EventChannel('airplane_mode_changed');

   Stream<bool> get airplaneModeStatus =>
      _eventChannel.receiveBroadcastStream().cast<bool>();
}

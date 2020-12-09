import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:airplane_mode_listener/airplane_mode_listener.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _airplaneMode = 'Unknown';
  StreamSubscription _subscription;

  @override
  void initState() {
    super.initState();
  _subscription =  AirplaneModeListener().airplaneModeStatus.listen((event) {
      setState(() {
        _airplaneMode = event ? "Airplane mode ativado" : "Airplane mode desativado";
      });
    });
  }


  @override
  void dispose() {
    _subscription?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_airplaneMode\n'),
        ),
      ),
    );
  }
}

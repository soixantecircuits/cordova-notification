# Cordova Notification

This repo is intended to test the [cordova local notifications plugin](http://plugins.cordova.io/#/package/com.cordova.plugin.localnotificationplugin).

## Installation

Make sure you have `cordova` installed:

```
npm i -g cordova
```
Then, add the platform you want to support:
```
cordova platform add android ios
```

You'll probably want to use a [socket event emitter](https://github.com/hugohil/socketio-event-emitter) to trigger test events

## Usage

```
cordova run [android/ios]
```

### Ionic

You can also use [ionic view](http://view.ionic.io/) to test on device without building on your own machine.

Make sure you have ionic installed, if not: `npm i -g ionic`.

Then:
```
ionic upload
```

> Though it is very helpfull, it can be a little confusing (updating the build on the device may take longer than what the cli printed, manifests may not be updated, etc). It can play you tricks :)

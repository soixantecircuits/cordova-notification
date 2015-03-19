cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "phonegap/plugin/localNotification/localNotification.js",
        "id": "com.cordova.plugin.localNotificationPlugin",
        "clobbers": [
            "window.localNotification"
        ]
    }
]
});
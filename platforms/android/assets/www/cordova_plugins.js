cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/com.cordova.plugin.localNotificationPlugin/www/phonegap/plugin/localNotification/localNotification.js",
        "id": "com.cordova.plugin.localNotificationPlugin.localNotificationPlugin",
        "clobbers": [
            "window.localNotification"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "com.cordova.plugin.localNotificationPlugin": "2.1.1"
}
// BOTTOM OF METADATA
});
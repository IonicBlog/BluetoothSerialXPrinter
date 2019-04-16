var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'ionicXprinter', 'coolMethod', [arg0]);
};

exports.connect = function (macAddress, success, failure) {
    cordova.exec(success, failure, "ionicXprinter", "connect", [macAddress]);
};

exports.list= function (success, failure) {
    cordova.exec(success, failure, "ionicXprinter", "list", []);
};

exports.write =function (data, success, failure) {

    // convert to ArrayBuffer
    if (typeof data === 'string') {
        data = stringToArrayBuffer(data);
    } else if (data instanceof Array) {
        // assuming array of interger
        data = new Uint8Array(data).buffer;
    } else if (data instanceof Uint8Array) {
        data = data.buffer;
    }

    cordova.exec(success, failure, "ionicXprinter", "write", [data]);
};
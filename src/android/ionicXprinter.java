package ionic.cordova.plugin.xprinter;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class ionicXprinter extends CordovaPlugin {

    private static final String LIST = "list";
    private static final String CONNECT = "connect";
    private static final String WRITE = "write";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if (action.equals(LIST)) {
            listBondedDevices(callbackContext);
        } else if (action.equals(CONNECT)) {
            boolean secure = true;
            connect(args, secure, callbackContext);
        }else if (action.equals(WRITE)) {
            byte[] data = args.getArrayBuffer(0);
            bluetoothSerialService.write(data);
            callbackContext.success();

        }
        return false;
    }

    private void listBondedDevices(CallbackContext callbackContext) throws JSONException {
        JSONArray deviceList = new JSONArray();
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : bondedDevices) {
            deviceList.put(deviceToJSON(device));
        }
        callbackContext.success(deviceList);
    }

    private void connect(CordovaArgs args, boolean secure, CallbackContext callbackContext) throws JSONException {
        String macAddress = args.getString(0);
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);

        if (device != null) {
            connectCallback = callbackContext;
            bluetoothSerialService.connect(device, secure);
            buffer.setLength(0);

            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
            result.setKeepCallback(true);
            callbackContext.sendPluginResult(result);

        } else {
            callbackContext.error("Could not connect to " + macAddress);
        }
    }

}

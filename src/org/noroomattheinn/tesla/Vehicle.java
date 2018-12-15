/*
 * Vehicle.java - Copyright(c) 2013 Joe Pasqua
 * Provided under the MIT License. See the LICENSE file for details.
 * Created: Jul 5, 2013
 */

package org.noroomattheinn.tesla;

import java.util.Locale;
import java.util.logging.Level;
import org.apache.commons.codec.digest.DigestUtils;
import org.noroomattheinn.utils.Utils;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

/**
 * Vehicle: This object represents a single Tesla Vehicle. It provides information
 * describing the vehicle, provides query calls to get the current state
 * of various subsystems (eg HVAC), and provides commands to take action on
 * the vehicle (e.g. unlock the doors).
 * <P>
 * A good running description of the overall Tesla REST API is given in this 
 * <a href="http://goo.gl/Z1Lul" target="_blank">Google Doc</a>. More notes and
 * a mockup can also be found at
 * <a href="https://tesla-api.timdorr.com/" target="_blank">tesla-api.timdorr.com</a>.
 * 
 * @author Joe Pasqua <joe at NoRoomAtTheInn dot org>
 */

public class Vehicle {
/*------------------------------------------------------------------------------
 *
 * Constants and Enums
 * 
 *----------------------------------------------------------------------------*/
    public static enum StateType {Charge, Drive, GUI, HVAC, Vehicle};
    public enum PanoCommand {vent, close};

    // The following are effectively constants, but are set in the constructor
    private final String    ChargeEndpoint, DriveEndpoint, GUIEndpoint,
                            HVACEndpoint, VehicleStateEndpoint;
    private final String    HVAC_Start, HVAC_Stop, HVAC_SetTemp;
    private final String    Charge_Start, Charge_Stop, Charge_SetMax,
                            Charge_SetStd, Charge_SetPct;
    private final String    Doors_OpenChargePort, Doors_CloseChargePort, Doors_Unlock,
                            Doors_Lock, Doors_Sunroof, Doors_Trunk;
    private final String    Action_Honk, Action_Flash, Action_Wakeup, Action_RemoteStart;
    private final String    SpeedLimit_Set, SpeedLimit_Enable, SpeedLimit_Disable,
                            SpeedLimit_ClearPin;
    private final String    ValetMode_Enable, ValetMode_ClearPin;
    private final String    Media_Toggle_Playback, Media_Next_Track, Media_Prev_Track,
                            Media_Next_Fav, Media_Prev_Fav, Media_VolumeUp, Media_VolumeDown;
    private final String    Schedule_SWUpdate, Cancel_SWUpdate;
// Need Navigation
    
/*------------------------------------------------------------------------------
 *
 * Internal State
 * 
 *----------------------------------------------------------------------------*/
    private final Tesla         tesla;
    private final Streamer      streamer;

    // Instance variables that describe the Vehicle
    private final String        color;
    private final String        streamingVID;
    private final String        userID;
    private final String        vehicleID;
    private final String        vin;
    private final String        streamingTokens[];
    private final String        status;
    private final Options       options;
    private final String        baseValues;
    private final String        displayName;
    private final String        uuid;
    private final boolean       isInService;
    private final int           apiVersion;
    private final String        vehicleID2;      // Not clear what this represents
    private final String        backSeatToken;
    private final String        backSeatTokenUpdated;

/*==============================================================================
 * -------                                                               -------
 * -------              Public Interface To This Class                   ------- 
 * -------                                                               -------
 *============================================================================*/
    
    public Vehicle(Tesla tesla, JSONObject description) {
        this.tesla = tesla;
        this.baseValues = description.toString();
        
        color = description.optString("color");
        displayName = description.optString("display_name");
        vehicleID = description.optString("id");
        userID = description.optString("user_id");
        streamingVID = description.optString("vehicle_id");
        vin = description.optString("vin");
        uuid = DigestUtils.sha256Hex(vin);
        status = description.optString("state");
        isInService = description.optBoolean("in_service");
        apiVersion = description.optInt("api_version");
        vehicleID2 = description.optString("id_s");
        backSeatToken = description.optString("backseat_token");
        backSeatTokenUpdated = description.optString("backseat_token_updated_at");
        
        // Get the streaming tokens if they exist...
        streamingTokens = new String[2];
        try {
            JSONArray tokens = description.getJSONArray("tokens");
            if (tokens != null && tokens.length() == 2) {
                streamingTokens[0] = tokens.getString(0);
                streamingTokens[1] = tokens.getString(1);
            } 
        } catch (JSONException ex) {
            Tesla.logger.log(Level.SEVERE, null, ex);
        }
                
        // Handle the Options
        options = new Options(description.optString("option_codes"));
        streamer = new Streamer(this);
        
        // Initialize state endpoints
        ChargeEndpoint = tesla.vehicleData(vehicleID, "charge_state");
        DriveEndpoint = tesla.vehicleData(vehicleID, "drive_state");
        GUIEndpoint = tesla.vehicleData(vehicleID, "gui_settings");
        HVACEndpoint = tesla.vehicleData(vehicleID, "climate_state");
        VehicleStateEndpoint = tesla.vehicleData(vehicleID, "vehicle_state");
        // TODO: FETCH VehicleConfig
        
        // Initialize HVAC endpoints
        HVAC_Start = tesla.vehicleCommand(vehicleID, "auto_conditioning_start");
        HVAC_Stop = tesla.vehicleCommand(vehicleID, "auto_conditioning_stop");
        HVAC_SetTemp = tesla.vehicleCommand(vehicleID, "set_temps");
        
        // Initialize Charge endpoints
        Charge_Start = tesla.vehicleCommand(vehicleID, "charge_start");
        Charge_Stop = tesla.vehicleCommand(vehicleID, "charge_stop");
        Charge_SetMax = tesla.vehicleCommand(vehicleID, "charge_max_range");
        Charge_SetStd = tesla.vehicleCommand(vehicleID, "charge_standard");
        Charge_SetPct = tesla.vehicleCommand(vehicleID, "set_charge_limit");
        
        // Initialize Door endpoints
        Doors_OpenChargePort = tesla.vehicleCommand(vehicleID, "charge_port_door_open");
        Doors_CloseChargePort = tesla.vehicleCommand(vehicleID, "charge_port_door_close");
        Doors_Unlock = tesla.vehicleCommand(vehicleID, "door_unlock");
        Doors_Lock = tesla.vehicleCommand(vehicleID, "door_lock");
        Doors_Sunroof = tesla.vehicleCommand(vehicleID, "sun_roof_control");
        Doors_Trunk = tesla.vehicleCommand(vehicleID, "actuate_trunk");
        
        // Initialize Action Endpoints
        Action_Honk = tesla.vehicleCommand(vehicleID, "honk_horn");
        Action_Flash = tesla.vehicleCommand(vehicleID, "flash_lights");
        Action_RemoteStart = tesla.vehicleCommand(vehicleID, "remote_start_drive");
        Action_Wakeup = tesla.vehicleSpecific(vehicleID, "wake_up");
        
        // Initialize Speed Limit Endpoints
        SpeedLimit_Set = tesla.vehicleCommand(vehicleID, "speed_limit_set_limit");
        SpeedLimit_Enable = tesla.vehicleCommand(vehicleID, "speed_limit_activate");
        SpeedLimit_Disable = tesla.vehicleCommand(vehicleID, "speed_limit_deactivate");
        SpeedLimit_ClearPin = tesla.vehicleCommand(vehicleID, "speed_limit_clear_pin");
        
        // Initialize Valet Mode Endpoints
        ValetMode_Enable = tesla.vehicleCommand(vehicleID, "set_valet_mode");
        ValetMode_ClearPin = tesla.vehicleCommand(vehicleID, "reset_valet_pin");
        
        // Initialize Media Endpoints
        Media_Toggle_Playback = tesla.vehicleCommand(vehicleID, "media_toggle_playback");
        Media_Next_Track = tesla.vehicleCommand(vehicleID, "media_next_track");
        Media_Prev_Track = tesla.vehicleCommand(vehicleID, "media_prev_track");
        Media_Next_Fav = tesla.vehicleCommand(vehicleID, "media_next_fav");
        Media_Prev_Fav = tesla.vehicleCommand(vehicleID, "media_prev_fav");
        Media_VolumeUp = tesla.vehicleCommand(vehicleID, "media_volume_up");
        Media_VolumeDown = tesla.vehicleCommand(vehicleID, "media_volume_down");
        
        // Initialize Software Update Endpoints
        Schedule_SWUpdate = tesla.vehicleCommand(vehicleID, "schedule_software_update");
        Cancel_SWUpdate = tesla.vehicleCommand(vehicleID, "cancel_software_update");
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to access basic Vehicle information
 * 
 *----------------------------------------------------------------------------*/
    
    public String   getVIN() { return vin; }
    public String   getVID() { return vehicleID; }
    public String   getUUID() { return uuid; }
    public String   getStreamingVID() { return streamingVID; }
    public String   status() { return status; } // Status can be "asleep", "waking", or "online"
    public Options  getOptions() { return options; }
    public String   getStreamingToken() { return streamingTokens[0]; }
    public String   getDisplayName() { return displayName; }
    public String   getUnderlyingValues() { return baseValues; }
    public boolean  isAsleep() { return !isAwake(); }
    public boolean  mobileEnabled() {
        JSONObject r = tesla.getState(tesla.vehicleSpecific(vehicleID, "mobile_enabled"));
        return r.optBoolean("reponse", false);
    }
    public boolean isAwake() {
        for (Vehicle v : tesla.queryVehicles()) {
            if (vin.equals(v.vin)) {
                return !v.status().equals("asleep");
            }
        }
        return false;
    }

/*------------------------------------------------------------------------------
 *
 * Methods to query various types of Vehicle state
 * 
 *----------------------------------------------------------------------------*/
    
    public BaseState query(StateType which) {
        switch (which) {
            case Charge: return queryCharge();
            case Drive: return queryDrive();
            case GUI: return queryGUI();
            case HVAC: return queryHVAC();
            case Vehicle: return queryVehicle();
            default:
                Tesla.logger.log(Level.SEVERE, "Unexpected query type: {0}", which);
                return null;
        }
    }
    
    public ChargeState queryCharge() {
        return new ChargeState(tesla.getState(ChargeEndpoint));
    }
    public DriveState queryDrive() {
        return new DriveState(tesla.getState(DriveEndpoint));
    }
    public GUIState queryGUI() {
        return new GUIState(tesla.getState(GUIEndpoint));
    }
    public HVACState queryHVAC() {
        return new HVACState(tesla.getState(HVACEndpoint));
    }
    public VehicleState queryVehicle() {
        VehicleState vh = new VehicleState(tesla.getState(VehicleStateEndpoint));
        return vh;
    }
    public Streamer getStreamer() { return streamer; }

/*------------------------------------------------------------------------------
 *
 * Methods to control the HVAC system
 * 
 *----------------------------------------------------------------------------*/
    
    public Result setAC(boolean on) {
        if (on) return startAC();
        else return stopAC();
    }
    
    public Result startAC() {
        return new Result(tesla.invokeCommand(HVAC_Start));
    }

    public Result stopAC() {
        return new Result(tesla.invokeCommand(HVAC_Stop));
    }
    
    public Result setTempC(double driverTemp, double passengerTemp) {
        String tempsPayload = String.format(Locale.US,
                "{'driver_temp' : '%3.1f', 'passenger_temp' : '%3.1f'}",
                driverTemp, passengerTemp);
        return new Result(tesla.invokeCommand(HVAC_SetTemp, tempsPayload));
    }
    
    public Result setTempF(double driverTemp, double passengerTemp) {
        return setTempC(Utils.fToC(driverTemp), Utils.fToC(passengerTemp));
    }

/*------------------------------------------------------------------------------
 *
 * Methods to control the Charging system
 * 
 *----------------------------------------------------------------------------*/
    
    public Result setChargeState(boolean charging) {
        return new Result(tesla.invokeCommand(charging? Charge_Start : Charge_Stop));
    }
    
    public Result startCharging() { return setChargeState(true); }

    public Result stopCharging() { return setChargeState(false); }
    
    public Result setChargeRange(boolean max) {
        return new Result(tesla.invokeCommand(max ? Charge_SetMax : Charge_SetStd));
    }
    
    public Result setChargePercent(int percent) {
        if (percent < 1 || percent > 100)
            return new Result(false, "value out of range");
        JSONObject response = tesla.invokeCommand(
                Charge_SetPct,  String.format("{'percent' : '%d'}", percent));
        if (response.optString("reason").equals("already_set")) {
            try {
                response.put("result", true);
            } catch (JSONException e) {
                Tesla.logger.severe("Can't Happen!");
            }
        }
        return new Result(response);
    }

/*------------------------------------------------------------------------------
 *
 * Methods to control speed limits
 * 
 *----------------------------------------------------------------------------*/
 
    public Result enableSpeedLimiting(int pinCode) {
        if (pinCode < 1000 || pinCode > 9999)
            return new Result(false, "value out of range");         
        JSONObject response = tesla.invokeCommand(SpeedLimit_Enable,
                 String.format("{'pin' : '%d'}", pinCode));

        return new Result(response);
    }

    public Result disableSpeedLimiting(int pinCode) {
        if (pinCode < 1000 || pinCode > 9999)
            return new Result(false, "value out of range");         
        JSONObject response = tesla.invokeCommand(SpeedLimit_Disable,
                 String.format("{'pin' : '%d'}", pinCode));

        return new Result(response);
    }
     
    public Result clearSpeedLimitPin(int pinCode) {
        if (pinCode < 1000 || pinCode > 9999)
            return new Result(false, "value out of range");        
        JSONObject response = tesla.invokeCommand(SpeedLimit_ClearPin,
            String.format("{'pin' : '%d'}", pinCode));
     
        return new Result(response);
    }

    public Result setSpeedLimit(int speedInMph) {
        if (speedInMph < 50 || speedInMph > 90)
            return new Result(false, "value out of range");      
        JSONObject response = tesla.invokeCommand(SpeedLimit_Set,
            String.format("{'limit_mph' : '%d'}", speedInMph));
     
        return new Result(response);
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to control valet mode
 * 
 *----------------------------------------------------------------------------*/
 
    public Result setValetMode(boolean valetEnabled, int pinCode) {
         if (pinCode < 1000 || pinCode > 9999)
            return new Result(false, "value out of range");
         
        return new Result(tesla.invokeCommand(ValetMode_Enable, 
                String.format("{'on' : '%b', 'password' : '%d'}", valetEnabled, pinCode)));
    }
    
    public Result clearValetPin() {
        return new Result(tesla.invokeCommand(ValetMode_ClearPin));
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to control media
 * 
 *----------------------------------------------------------------------------*/
 
    public Result toggleMediaPlayback() {
        return new Result(tesla.invokeCommand(Media_Toggle_Playback));
    }
    
    public Result nextMediaTrack() {
        return new Result(tesla.invokeCommand(Media_Next_Track));
    }
    
    public Result previousMediaTrack() {
        return new Result(tesla.invokeCommand(Media_Prev_Track));
    }
    
    public Result nextMediaFavorite() {
        return new Result(tesla.invokeCommand(Media_Next_Fav));
    }
    
    public Result previousMediaFavorite() {
        return new Result(tesla.invokeCommand(Media_Prev_Fav));
    }
    
    public Result increaseMediaVolume() {
        return new Result(tesla.invokeCommand(Media_VolumeUp));
    }
    
    public Result decreaseMediaVolume() {
        return new Result(tesla.invokeCommand(Media_VolumeDown));
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to schedule software updates
 * 
 *----------------------------------------------------------------------------*/
 
    public Result scheduleSoftwareUpdate(int seconds) {
        return new Result(tesla.invokeCommand(Schedule_SWUpdate, 
                String.format("{'offset_sec' : '%d'}", seconds)));
    }
    
    public Result doSoftwareUpdate() { return scheduleSoftwareUpdate(0); }

    public Result cancelSoftwareUpdate() {
        return new Result(tesla.invokeCommand(Cancel_SWUpdate));
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to control the doors, trunk, frunk, and roof
 * 
 *----------------------------------------------------------------------------*/
    
    public Result setLockState(boolean locked) {
        return new Result(tesla.invokeCommand(locked ? Doors_Lock : Doors_Unlock));
    }
    
    public Result lockDoors() { return setLockState(true); }

    public Result unlockDoors() { return setLockState(false); }
    
    public Result openChargePort() {
        return new Result(tesla.invokeCommand(Doors_OpenChargePort));
    }
    
    public Result closeChargePort() {
        return new Result(tesla.invokeCommand(Doors_CloseChargePort));
    }
    
    public Result openFrunk() { // Requires 6.0 or greater
        return new Result(tesla.invokeCommand(Doors_Trunk, "{'which_trunk' : 'front'}"));
    }
    
    public Result openTrunk() { // Requires 6.0 or greater
        return new Result(tesla.invokeCommand(Doors_Trunk, "{'which_trunk' : 'rear'}"));
    }
    
    public Result setPano(PanoCommand cmd) {
        String payload = String.format("{'state' : '%s'}", cmd.name());
        return new Result(tesla.invokeCommand(Doors_Sunroof, payload));
    }
    
    public Result stopPano() {
        return new Result(tesla.invokeCommand(Doors_Sunroof, "{'state' : 'stop'}"));
    }
    
/*------------------------------------------------------------------------------
 *
 * Methods to perform miscellaneous actions
 * 
 *----------------------------------------------------------------------------*/
    
    public Result honk() {
        return new Result(tesla.invokeCommand(Action_Honk));
    }

    public Result flashLights() {
        return new Result(tesla.invokeCommand(Action_Flash));
    }

    public Result remoteStart(String password) {
        return new Result(tesla.invokeCommand(
                Action_RemoteStart, "{'password' : '" + password + "'}"));
    }

    public Result wakeUp() {
        return new Result(tesla.invokeCommand(Action_Wakeup));
    }
    
/*------------------------------------------------------------------------------
 *
 * Utility Methods
 * 
 *----------------------------------------------------------------------------*/
    
    public Tesla tesla() { return tesla; }
    
    @Override public String toString() {
        return String.format(
                "VIN: %s\n" +
                "status: %s\n" +
                "options: [\n%s]",
                vin, status, options.toString()
            );
    }

}

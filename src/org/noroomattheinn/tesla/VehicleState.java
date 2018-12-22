/*
 * VehicleState.java - Copyright(c) 2013 Joe Pasqua
 * Provided under the MIT License. See the LICENSE file for details.
 * Created: Jul 5, 2013
 */

package org.noroomattheinn.tesla;

import org.noroomattheinn.utils.Utils;
import us.monoid.json.JSONObject;

/**
 * VehicleState: Contains an assortment of information about the current state
 * of the vehicle. This includes things like whether the doors are open, whether
 * the car is locked, what version of firmware is installed, and a lot of other
 * odds and ends.
 * 
 * @author Joe Pasqua <joe at NoRoomAtTheInn dot org>
 */
public class VehicleState extends BaseState {
/*------------------------------------------------------------------------------
 *
 * Constants and Enums
 * 
 *----------------------------------------------------------------------------*/
    public enum PanoPosition {open, closed, vent, comfort, moving, unknown, Unknown};
    public enum AutoParkState {ready, unavailable, Unknown};
    public enum AutoParkStyle {standard, Unknown};
    
/*------------------------------------------------------------------------------
 *
 * Public State
 * 
 *----------------------------------------------------------------------------*/
    public final  int      apiVersion;
    public final  boolean  isDFOpen;
    public final  boolean  isPFOpen;
    public final  boolean  isDROpen;
    public final  boolean  isPROpen;
    public final  boolean  isFTOpen;
    public final  boolean  isRTOpen;
    public final  boolean  locked;
    public final  boolean  hasPano;
    public final  int      panoPercent;
    public final  PanoPosition panoState;
    public final  String   version;
    public final  boolean  remoteStart;
    public final  String   autoParkState;       // Need more enumerations
    public final  String   autoParkStyle;       // Need more enumerations
    public final  String   lastAutoParkError;
    public final  boolean  isCalendarSupported;
    public final  String   centerDisplayState;  // Not clear what this represents
    public final  boolean  isHomelinkNearby; 
    public final  boolean  isUserPresent;
    public final  double   odometer;
    public final  boolean  isRemoteStartSupported;
    public final  boolean  areNotificationsSupported;
    public final  boolean  isParsedCalendarSupported;
    public final  boolean  isValetModeEnabled;
    public final  boolean  isValetPinNeeded;
    public final  String   vehicleName;

    public        boolean  isRemoteControlEnabled;
    public        int      softwareUpdateExpectedDuration;
    public        String   softwareUpdateStatus;
    public        boolean  hasSoftwareUpdateAvailable;
    public        boolean  isSpeedLimitModeOn;
    public        double   speedLimitCurrent;
    public        int      speedLimitMax;
    public        int      speedLimitMin;
    public        boolean  isSpeedLimitPinSet;
    
    protected final  JSONObject mediaState;    
    protected final  JSONObject softwareUpdate;
    protected final  JSONObject speedLimitMode;
    
/*==============================================================================
 * -------                                                               -------
 * -------              Public Interface To This Class                   ------- 
 * -------                                                               -------
 *============================================================================*/
    
    public VehicleState(JSONObject source) {
        super(source);
        apiVersion = source.optInt("api_version");
        isDFOpen = source.optInt("df") != 0;
        isPFOpen = source.optInt("pf") != 0;
        isDROpen = source.optInt("dr") != 0;
        isPROpen = source.optInt("pr") != 0;
        isFTOpen = source.optInt("ft") != 0;
        isRTOpen = source.optInt("rt") != 0;
        locked = source.optBoolean("locked");
        panoPercent = source.optInt("sun_roof_percent_open");
        panoState = Utils.stringToEnum(
                VehicleState.PanoPosition.class, source.optString("sun_roof_state"));
        hasPano = (panoState != PanoPosition.Unknown && panoState != PanoPosition.unknown);
        version = source.optString("car_version");
        remoteStart = source.optBoolean("remote_start");
        
        autoParkState = source.optString("autopark_state_v2");
        autoParkStyle = source.optString("autopark_style");
        lastAutoParkError = source.optString("last_autopark_error");
        odometer = source.optDouble("odometer");
        centerDisplayState = source.optString("center_display_state");
        isCalendarSupported = source.optBoolean("calendar_supported");
        areNotificationsSupported = source.optBoolean("notifications_supported");
        isParsedCalendarSupported = source.optBoolean("parsed_calendar_supported");
        isHomelinkNearby = source.optBoolean("homelink_nearby");
        isRemoteStartSupported = source.optBoolean("remote_start_supported");
        isUserPresent = source.optBoolean("is_user_present");
        isValetModeEnabled = source.optBoolean("valet_mode");
        isValetPinNeeded = source.optBoolean("valet_pin_needed");
        vehicleName = source.optString("vehicle_name");
        
        mediaState = source.optJSONObject("media_state");
        isRemoteControlEnabled = false;
        if (mediaState != null) {
            isRemoteControlEnabled = mediaState.optBoolean("remote_control_enabled");
        }
        
        softwareUpdate = source.optJSONObject("software_update");
        softwareUpdateExpectedDuration = 0;
        softwareUpdateStatus = "";
        if (softwareUpdate != null) {
            softwareUpdateExpectedDuration = softwareUpdate.optInt("expected_duration_sec");
            softwareUpdateStatus = softwareUpdate.optString("status");
        }
        hasSoftwareUpdateAvailable = (softwareUpdateStatus != null && !softwareUpdateStatus.isEmpty());
        
        speedLimitMode = source.optJSONObject("speed_limit_mode");
        isSpeedLimitModeOn = false;
        isSpeedLimitPinSet = false;
        speedLimitCurrent = 0.0;
        speedLimitMax = 0;
        speedLimitMin = 0;
        if (speedLimitMode != null) {
            isSpeedLimitModeOn = speedLimitMode.optBoolean("active");
            speedLimitCurrent = speedLimitMode.optDouble("current_limit_mph");
            speedLimitMax = speedLimitMode.optInt("max_limit_mph");
            speedLimitMin = speedLimitMode.optInt("min_limit_mph");
            isSpeedLimitPinSet = speedLimitMode.optBoolean("pin_code_set");
        }
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isDFOpen) sb.append("    Driver Front Door is open\n");
        if (isDROpen) sb.append("    Driver Rear Door is open\n");
        if (isPFOpen) sb.append("    Passenger Front Door is open\n");
        if (isPROpen) sb.append("    Passenger Rear Door is open\n");
        if (isFTOpen) sb.append("    Frunk is open\n");
        if (isRTOpen) sb.append("    Trunk is open\n");
        sb.append("    The car is "); 
        sb.append(locked ? "locked\n" : "unlocked\n");
        if (hasPano) {
            sb.append("    Panoramic roof: ");
            if (panoState != PanoPosition.unknown)
                sb.append(panoState);
            sb.append(" ("); sb.append(panoPercent); sb.append("%)\n");
        }
        
        sb.append("    Remote start is ");
        if (!remoteStart) sb.append("not ");
        sb.append("enabled\n");
        
        sb.append("    Firmware version: "); sb.append(version);
        return sb.toString();
    }
    
}

/*
 * HVACState.java - Copyright(c) 2013 Joe Pasqua
 * Provided under the MIT License. See the LICENSE file for details.
 * Created: Jul 5, 2013
 */

package org.noroomattheinn.tesla;

import org.noroomattheinn.utils.Utils;
import us.monoid.json.JSONObject;

/**
 * HVACState: Stores the state of the HVAC system.
 *
 * @author Joe Pasqua <joe at NoRoomAtTheInn dot org>
 */

public class HVACState extends BaseState {
/*------------------------------------------------------------------------------
 *
 * Public State
 * 
 *----------------------------------------------------------------------------*/
    public final double  insideTemp;
    public final double  outsideTemp;
    public final double  driverTemp;
    public final double  passengerTemp;
    public final boolean isAutoConditioningOn;
    public final boolean isFrontDefrosterOn;
    public final boolean isRearDefrosterOn;
    public final int     fanStatus;
    public final boolean isBatteryHeaterOn;
    public final boolean isBatteryHeaterPowerless;
    public final boolean isClimateOn;
    public final boolean isPreconditioningOn;
    public final double  maxAvailableTemp;
    public final double  minAvailableTemp;
    public final boolean isLeftSeatHeaterOn;
    public final boolean isRearCenterSeatHeaterOn;
    public final boolean isRearLeftSeatHeaterOn;
    public final boolean isRearLeftBackSeatHeaterOn;
    public final boolean isRightSeatHeaterOn;
    public final boolean isRearRightSeatHeaterOn;
    public final boolean isRearRightBackSeatHeaterOn;
    public final boolean isSideMirrorHeaterOn;
    public final boolean isSmartPreconditioningOn;
    public final boolean isSteeringWheelHeaterOn;
    public final boolean isWiperBladeHeaterOn;
    public final String  leftTempDirection;
    public final String  rightTempDirection;
    
/*==============================================================================
 * -------                                                               -------
 * -------              Public Interface To This Class                   ------- 
 * -------                                                               -------
 *============================================================================*/
    
    public HVACState(JSONObject source) {
        super(source);
        insideTemp = source.optDouble("inside_temp"); 
        outsideTemp = source.optDouble("outside_temp"); 
        driverTemp = source.optDouble("driver_temp_setting"); 
        passengerTemp = source.optDouble("passenger_temp_setting"); 
        isAutoConditioningOn = source.optBoolean("is_auto_conditioning_on"); 
        isFrontDefrosterOn = source.optBoolean("is_front_defroster_on"); 
        isRearDefrosterOn = source.optBoolean("is_rear_defroster_on"); 
        fanStatus = source.optInt("fan_status"); 
        isBatteryHeaterOn = source.optBoolean("battery_heater");
        isBatteryHeaterPowerless = source.optBoolean("battery_heater_no_power");
        isClimateOn = source.optBoolean("is_climate_on");
        isPreconditioningOn = source.optBoolean("is_preconditioning");
        maxAvailableTemp = source.optDouble("max_avail_temp");
        minAvailableTemp = source.optDouble("min_avail_temp");
        isLeftSeatHeaterOn = source.optBoolean("seat_heater_left");
        isRearCenterSeatHeaterOn = source.optBoolean("seat_heater_rear_center");
        isRearLeftSeatHeaterOn = source.optBoolean("seat_heater_rear_left");
        isRearLeftBackSeatHeaterOn = source.optBoolean("seat_heater_rear_left_back");
        isRightSeatHeaterOn = source.optBoolean("seat_heater_right");
        isRearRightSeatHeaterOn = source.optBoolean("seat_heater_rear_right");
        isRearRightBackSeatHeaterOn = source.optBoolean("seat_heater_rear_right_back");
        isSideMirrorHeaterOn = source.optBoolean("side_mirror_heaters");
        isSmartPreconditioningOn = source.optBoolean("smart_preconditioning");
        isSteeringWheelHeaterOn = source.optBoolean("steering_wheel_heater");
        isWiperBladeHeaterOn = source.optBoolean("wiper_blade_heater");
        leftTempDirection = source.optString("left_temp_direction");
        rightTempDirection = source.optString("right_temp_direction");
    }
    
    @Override public String toString() {
        return String.format(
            "    Inside Temp: %3.0f\n" +
            "    Outside Temp: %3.0f\n" +
            "    Driver Setpoint: %3.0f\n" +
            "    Passenger Setpoint: %3.0f\n" +
            "    HVAC On: %s\n" +
            "    Front Defroster On: %s\n" +
            "    Rear Defroster On: %s\n" +
            "    Fan Setting: %d\n", 
            Utils.cToF(insideTemp),
            Utils.cToF(outsideTemp),
            Utils.cToF(driverTemp),
            Utils.cToF(passengerTemp),
            Utils.yesNo(isAutoConditioningOn),
            Utils.yesNo(isFrontDefrosterOn),
            Utils.yesNo(isRearDefrosterOn),
            fanStatus
            );
    }
}

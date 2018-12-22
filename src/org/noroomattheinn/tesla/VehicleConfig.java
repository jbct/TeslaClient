/*
 * VehicleConfig.java - Copyright(c) 2018 James Burke
 * Provided under the MIT License. See the LICENSE file for details.
 * Created: Dec 18, 2018
 */

package org.noroomattheinn.tesla;

import org.noroomattheinn.utils.Utils;
import us.monoid.json.JSONObject;
/**
 * VehicleConfig: Contains an assortment of information about the current config
 * of the vehicle. This includes things like whether the car can accept various
 * API requests, information about the seats, wheels, charging port, colors and
 * even the trim level. It's a relatively new API.
 * 
 * @author James Burke <jburke at jbctech dot com>
 */
public class VehicleConfig extends BaseState {
    
/*------------------------------------------------------------------------------
 *
 * Public State
 * 
 *----------------------------------------------------------------------------*/
    public final  boolean  canAcceptNavRequests;
    public final  boolean  canActuateTrunks;
    public final  String   carSpecialType;
    public final  String   carType;
    public final  String   chargePortType;
    public final  boolean  isEuropeanVehicle;
    public final  String   externalColor;
    public final  boolean  hasAirSuspension;
    public final  boolean  hasLudicrousMode;
    public final  boolean  hasMotorizedChargePort;
    public final  String   performanceConfig;
    public final  boolean  plg;
    public final  int      numRearSeatHeaters;
    public final  int      rearSeatType;
    public final  boolean  rhd;
    public final  String   roofColor;
    public final  int      seatType;
    public final  String   spoilerType;
    public final  int      sunRoofInstalled;
    public final  String   thirdRowSeats;
    public final  String   trimBadging;
    public final  String   wheelType;
    
/*==============================================================================
 * -------                                                               -------
 * -------              Public Interface To This Class                   ------- 
 * -------                                                               -------
 *============================================================================*/
    
    public VehicleConfig(JSONObject source) {
        super(source);
        canAcceptNavRequests = source.optBoolean("can_accept_navigation_requests");
        canActuateTrunks = source.optBoolean("can_actuate_trunks");
        carSpecialType = source.optString("car_special_type");
        carType = source.optString("car_type");
        chargePortType = source.optString("charge_port_type");
        isEuropeanVehicle = source.optBoolean("eu_vehicle");
        externalColor = source.optString("exterior_color");
        hasAirSuspension = source.optBoolean("has_air_suspension");
        hasLudicrousMode = source.optBoolean("has_ludicrous_mode");
        hasMotorizedChargePort = source.optBoolean("motorized_charge_port");
        performanceConfig = source.optString("perf_config");
        plg = source.optBoolean("plg");
        numRearSeatHeaters = source.optInt("rear_seat_heaters");
        rearSeatType = source.optInt("rear_seat_type");
        rhd = source.optBoolean("rhd");
        roofColor = source.optString("roof_color");
        seatType = source.optInt("seat_type");
        spoilerType = source.optString("spoiler_type");
        sunRoofInstalled = source.optInt("sun_roof_installed");
        thirdRowSeats = source.optString("third_row_seats");
        trimBadging = source.optString("trim_badging");
        wheelType = source.optString("wheel_type");
    }
    
    @Override public String toString() {
        return "";
    }
}

/*
 * Options.java - Copyright(c) 2013 Joe Pasqua
 * Provided under the MIT License. See the LICENSE file for details.
 * Created: Jul 5, 2013
 */

package org.noroomattheinn.tesla;

import org.noroomattheinn.utils.Utils;
import java.util.HashMap;
import java.util.Map;

/**
 * Options: This class parses and contains the many options made available in
 * the Tesla vehicle description. Many of the options are represented by
 * enum's of the known types. All of those enum's have an "Unknown" instance
 * to handle unexpected or new option types. For example, if Tesla adds a new
 * paint color, it will be reported as Unknown until the code is updated.
 *
 * @author Joe Pasqua <joe at NoRoomAtTheInn dot org>
 */

public class Options {
/*------------------------------------------------------------------------------
 *
 * Constants and Enums
 * 
 *----------------------------------------------------------------------------*/
    public enum WheelType {
        WT19("Silver 19\""),
        WT1P("Silver 19\""),
        WT20("Silver 20\" Slipstream"),
        WT21("Silver 21\""),
        WT2E("Silver 21\" Euro"),
        WTAE("Aero 19\""),
        WTAP("Aero 19\""),
        WTAS("Silver 19\" Slipstream"),
        WTDS("Grey 19\" Slipstream"),
        WTGP("Turbine 19\" Charcoal"),
        WTSE("Charcoal 21\" Euro"),
        WTSG("Super 21\" Gray"),
        WTSP("Charcoal 21\""),
        WTSS("Super 21\" Silver"),
        WTTB("Turbine 19\""),        
        WTTP("Turbine 19\""),
        WTTG("Turbine 19\" Charcoal"),
        WTX1("Silver 19\""),
        Unknown("Unknown");
        
        private String descriptiveName;

        WheelType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }

    public enum TrimLevel  {
        TM00("General Production Trim"),
        TM02("General Production Signature Trim"),
        TM0A("ALPHA PRE-PRODUCTION NON-SALEABLE"),
        TM0B("BETA PRE-PRODUCTION NON-SALEABLE"),
        TM0C("PRE-PRODUCTION SALEABLE"),
        Unknown("Unknown");

        private String descriptiveName;

        TrimLevel(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }

    public enum InteriorColor {Black, Tan, Gray, White};
    
    public enum SeatType {
        IBMB("Base Textile, Black", InteriorColor.Black),
        IPMB("Leather, Black", InteriorColor.Black),
        IPMG("Leather, Gray", InteriorColor.Gray),
        IPMT("Leather, Tan", InteriorColor.Tan),
        IZZW("Perf Leather with Grey Piping, White", InteriorColor.White),
        QYMT("Leather, Tan", InteriorColor.Tan),
        QZMB("Perf Leather with Piping, Black", InteriorColor.Black),
        IZMB("Perf Leather with Piping, Black", InteriorColor.Black),
        IZMG("Perf Leather with Piping, Gray", InteriorColor.Gray),
        IZMT("Perf Leather with Piping, Tan", InteriorColor.Tan),
        ISZW("Signature Perforated Leather, White", InteriorColor.White),
        ISZT("Signature Perforated Leather, Tan", InteriorColor.Tan),
        ISZB("Signature Perforated Leather, Black", InteriorColor.Black),
        Unknown("Unknown", InteriorColor.Gray);

        private String descriptiveName;
        private InteriorColor color;
        
        SeatType(String name, InteriorColor c) {
            this.descriptiveName = name;
            this.color = c;
        }
        
        public InteriorColor getColor() { return color; }
        @Override public String toString() { return descriptiveName; }
    }

    public enum RoofType {
        RF3G("Glass"),
        RFBK("Black"),
        RFBC("Body Color"),
        RFFG("Glass"),
        RFPO("Panoramic"),
        RFP2("Sunroof"),
        RFPX("Model X"),
        Unknown("Unknown");

        private String descriptiveName;

        RoofType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }

    public enum Region {
        RENA("United States"),
        RENC("Canada"),
        REEU("Europe"),
        Unknown("Unknown");

        private String descriptiveName;

        Region(String name) { this.descriptiveName = name; }

        @Override
        public String toString() { return descriptiveName; }
    }

    public enum PaintColor {
        PBCW("Catalina White"),
        PBSB("Sierra Black"),
        PMAB("Anza Brown Metallic"),
        PMBL("Obsidian Black Multi-Coat"),
        PMMB("Monterey Blue Metallic"),
        PMMR("Multi-Coat Red"),
        PMNG("Midnight Silver Metallic"),
        PMSG("Sequoia Green Metallic"),
        PMSS("San Simeon Silver Metallic"),
        PMTG("Dolphin Gray Metallic"),
        PPMR("Muir Red Multi-Coat"),
        PPSB("Deep Blue Metallic"),
        PPSR("Signature Red"),
        PPSW("Shasta Pearl White Multi-Coat"),
        PPTI("Titanium Metallic"),
        Unknown("Unknown");

        private String descriptiveName;

        PaintColor(String name) { this.descriptiveName = name; }

        @Override
        public String toString() { return descriptiveName; }

    }
    
    public enum DriveSide  {
        DRLH("Left Hand"),
        DRRH("Right Hand"),
        Unknown("Unknown");

        private String descriptiveName;

        DriveSide(String name) { this.descriptiveName = name; }

        @Override
        public String toString() { return descriptiveName; }

    }

    public enum DecorType {
        IDBA("Dark Ash Wood"),
        IDBO("Figured Ash Wood"),
        IDCF("Carbon Fiber"),
        IDOM("Matte Obeche Wood"),
        IDOG("Gloss Obeche Wood"),
        IDLW("Lacewood"),
        IDPB("Piano Black"),
        IDHM("Matte Abachi"),
        Unknown("Unknown");

        private String descriptiveName;

        DecorType(String name) { this.descriptiveName = name; }

        @Override
        public String toString() { return descriptiveName; }

    }
    
    public enum BatteryType  {
        BT37("75kWh (Model 3)"),
        BT40("40kWh (Software Limited)"),        
        BT60("60kWh"),
        BT70("70kWh"),
        BT85("85kWh"),
        BTX4("90kWh"),
        BTX5("75kWh"),
        BTX6("100kWh"),
        BTX7("75kWh"),
        BTX8("85kWh"),        
        Unknown("Unknown");

        private String descriptiveName;

        BatteryType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }

    public enum AdapterType  {
        AD02("NEMA 14-50"),
        AD04("European 3-Phase"),
        AD05("European 3-Phase, IT"),
        AD06("Schuko (1 phase, 230V 13A)"),
        AD07("Red IEC309 (3 phase 400V 16A)"),
        ADPX2("Type 2 Public Charging Connector"),
        ADX8("Blue IEC309 (1 phase 230V 32A)"),
        Unknown("Unknown");

        private String descriptiveName;

        AdapterType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }
    
    public enum DriveType {
        DV2W("RWD"),
        DV4W("AWD"),
        Unknown("Unknown");
        
        private String descriptiveName;

        DriveType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    }

    public enum Model {
        MDLS("Model S"),
        MDLX("Model X"),
        MDL3("Model 3"),
        Unknown("Unknown");
        
        private String descriptiveName;

        Model(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }        
    }
    
    public enum ModelType {
        // MODEL S
        // RWD Standard Models
        S40("40"),
        S60("60"),
        S70("70"),
        S75("75"),
        S85("85"),
        // RWD Performance Models
        P85("P85"),
        P85Plus("P85+"),
        // AWD Standard & Performance Models
        S60D("60D"),
        S70D("70D"),
        S75D("75D"),
        S85D("85D"),
        P85D("P85D"),
        S90D("90D"),
        P90D("P90D"),
        S100D("100D"),
        P100D("P100D"),
        
        // MODEL 3
        THREE75("LR"),
        Unknown("Unknown");
        
        private String descriptiveName;

        ModelType(String name) { this.descriptiveName = name; }

        @Override public String toString() { return descriptiveName; }
    };
    
/*------------------------------------------------------------------------------
 *
 * Internal State
 * 
 *----------------------------------------------------------------------------*/
    private final Map<String,String> optionsFound;
    private int productionYear = 2012;
    
/*==============================================================================
 * -------                                                               -------
 * -------              Public Interface To This Class                   ------- 
 * -------                                                               -------
 *============================================================================*/
    
    public Options(String optionsString) {
        optionsFound = new HashMap<>();

        if (optionsString == null) {
            return;
        }

        // Deal with special case for P85D
        optionsString = optionsString.replace("P85D", "PD01");

        // Deal with the one 3 letter prefix in the options: PBT. Turn this into BT
        optionsString = optionsString.replace("PBT", "BT");

        // Sometimes it appears that a P85+ (perhaps other perf models as well)
        // will have two wheel types listed, the real one and WTX0 or WTX1.
        // Unfortunately WTX0/X1 are often the last one encountered and gets 
        // used as the wheel type. We need the real one so ignore WTXO/X1.
        // The ASSERTION is that it never appears by itself. Other code depends
        // on that assertion being true.
        optionsString = optionsString.replace("WTX0", "wtXO");
        optionsString = optionsString.replace("WTX1", "wtX1");
        

        String[] tokens = optionsString.split(",");
        for (String token : tokens) {
            if (token.length() < 2) {
                Tesla.logger.warning("Malformed Option token: " + token);
                continue;
            }
            String prefix = token.substring(0,2);
            
            // The MS token appears to be a model year or production year. MS
            // is followed by a two digit number which is monotonically increasing
            // starting with 01. 01 corresponds to the first production year, 2012.
            // 02 corresponds to 2013 and so on.
            if (prefix.equals("MS")) {
                if (token.length() == 4) {  // This is a well formed token
                    int yearOffset = Integer.valueOf(token.substring(2,4));
                    productionYear = 2011 + yearOffset;
                    continue;
                }
            }
            
            // X0 options are handled differently. Speculation is that these are
            // the old way Tesla handled things. In this case we store the whole
            // token as the key and the value.
            if (prefix.equals("X0")) prefix = token;
            
            optionsFound.put(prefix, token);
        }
    }
    
    
/*------------------------------------------------------------------------------
 *
 * Access to the option information
 * 
 *----------------------------------------------------------------------------*/
    
    public Region region() { return optionToEnum(Region.class, "RE"); }
    public TrimLevel trimLevel() { return optionToEnum(TrimLevel.class, "TM"); }
    public DriveSide driveSide() { return optionToEnum(DriveSide.class, "DR"); }
    public BatteryType batteryType() {
        BatteryType bt = optionToEnum(BatteryType.class, "BT");
        if (bt == Options.BatteryType.Unknown) {
            if (isP85D() || isPerfPlus()) { return Options.BatteryType.BT85; }
            return Options.BatteryType.BT70;
        }
        return bt;
    }
    public RoofType roofType() { return optionToEnum(RoofType.class, "RF"); }
    public WheelType wheelType() { return optionToEnum(WheelType.class, "WT"); }
    public DecorType decorType() { return optionToEnum(DecorType.class, "ID"); }
    public AdapterType adapterType() { return optionToEnum(AdapterType.class, "AD"); }
    public PaintColor paintColor() { 
        return optionToEnum(PaintColor.class, "PB", "PM", "PP"); }
    public SeatType seatType() {
        return optionToEnum(SeatType.class, "IB", "IP", "IZ", "IS"); }
    public DriveType driveType() {
        DriveType dt = optionToEnum(DriveType.class, "DV");
        if (dt == Options.DriveType.Unknown) { return Options.DriveType.DV2W; }
        return dt;
    }
    public Model model() { return optionToEnum(Model.class, "MD"); }
    
    public ModelType modelType() {
        if (batteryType() == Options.BatteryType.BT37) { return ModelType.THREE75; }
        
        if (isAWD()) {
            if (isP85D()) { return ModelType.P85D; }
            if (batteryType() == Options.BatteryType.BT85) { return ModelType.S85D; }
            return ModelType.S70D;
        }
        if (isPerfPlus()) { return ModelType.P85Plus; }
        else if (isPerformance()) { return ModelType.P85; }
        else if (batteryType() == Options.BatteryType.BT85) { return ModelType.S85; } 
        return ModelType.S60;
    }
    public int productionYear() { return productionYear; }

    public boolean isPerformance() { return hasOption("PF"); }
    public boolean isPerfPlus() { return hasOption("PX") || wheelType() == WheelType.WTSG; }
    public boolean isP85D() { return hasOption("PD"); }
    public boolean isAWD() { return driveType() == Options.DriveType.DV4W; }
    public boolean hasThirdRow() { return hasOption("TR"); }
    public boolean hasAirSuspension() { return hasOption("SU"); }
    public boolean hasSupercharger() { return hasOption("SC") || isPerfPlus(); }    
    public boolean hasTechPackage() { return hasOption("TP"); }
    public boolean hasAudioUpgrade() { return hasOption("AU"); }
    public boolean hasTwinCharger() { return hasOption("CH"); }
    public boolean hasHPWC() { return hasOption("HP"); }
    public boolean hasHEPAFilter() { return hasOption("AF"); }
    public boolean hasAutoPilot() { return hasOption("DA"); }
    public boolean hasLudicrousSpeed() { return hasOption("BP"); }
    public boolean hasBatterySoftwareLimit() { return hasOption("BR"); }
    public boolean hasPaintArmor() { return hasOption("PA"); }
    public boolean hasParcelShelf() { return hasOption("PS"); }
    public boolean hasPowerLiftgate() { return hasOption("X001"); }
    public boolean hasNavSystem() { return hasOption("X003"); }
    public boolean hasPremiumLighting() { return hasOption("X007"); }
    public boolean hasHomeLink() { return hasOption("X011"); }
    public boolean hasSatRadio() { return hasOption("X013"); }
    public boolean hasPerfExterior() { return hasOption("X019"); }
    public boolean hasSpoiler() { return hasOption("X019"); }
    public boolean hasPerfPowertrain() { return hasOption("X024"); }
    public boolean hasLightedDoorHandles() { return hasOption("X027"); }
    public boolean hasKeylessEntry() { return hasOption("X031"); }
    public boolean hasFoldingMirrors() { return hasOption("X037"); }
    public boolean hasParkingSensors() { return hasOption("PK"); }
    public boolean hasLightingPackage() { return hasOption("LP"); }
    public boolean hasSecurityPackage() { return hasOption("SP"); }
    public boolean hasColdWeather() { return hasOption("CW"); }
    public boolean hasFogLamps() { return hasOption("FG"); }
    public boolean hasExtendedNappaTrim() { return hasOption("IX"); }
    public boolean hasYachtFloor() { return hasOption("YF"); }
    // Brake Calipers: Red = BC0R, Black = BC0B
    public boolean hasRedCalipers() { return optionsFound.get("BC0R") != null; }
    
    @Override
    public String toString() {
        return String.format(
                "    Model: %s %s\n" +
                "    Region: %s\n" +
                "    Year: %d\n" +
                "    Trim: %s\n" +
                "    Drive Side: %s\n" +
                "    Dual Motor: %s\n" +
                "    Performance Options: [\n" +
                "       Performance: %b\n" +
                "       Performance+: %b\n" +
                "       P85D: %b\n" +
                "       Performance Exterior: %b\n" +
                "       Performance Powertrain: %b\n" +
                "    ]\n" +
                "    Battery: %s\n" +
                "    Color: %s\n" +
                "    Roof: %s\n" +
                "    Wheels: %s\n" +
                "    Seats: %s\n" +
                "    Decor: %s\n" +
                "    Extended Nappa Leather Time: %b\n" +
                "    Air Suspension: %b\n" +
                "    Tech Upgrades: [\n" +
                "        Tech Package: %b\n" +
                "        Power Liftgate: %b\n" +
                "        Premium Lighting: %b\n" +
                "        HomeLink: %b\n" +
                "        Navigation: %b\n" +
                "    ]\n" +
                "    Audio: [\n" +
                "        Upgraded: %b\n" +
                "        Sat Radio: %b\n" +
                "    ]\n" +
                "    Charging: [\n" +
                "        Supercharger: %b\n" +
                "        Twin Chargers: %b\n" +
                "        HPWC: %b\n" +
                "    ]\n" +
                "    Options: [\n" +
                "        Parcel Shelf: %b\n" +
                "        Paint Armor: %b\n" +
                "        Third Row Seating: %b\n" +
                "    ]\n" +
                "    Newer Options: [\n" +
                "        Parking Sensors: %b\n" +
                "        Lighting Package: %b\n" + 
                "        Security Package: %b\n" +
                "        Fog Lamps: %b\n" +
                "        Cold Weather Package: %b\n" +
                "    ]\n",
                model(), modelType(), region(), productionYear(), trimLevel(), driveSide(),
                isAWD(), isPerformance(), isPerfPlus(), isP85D(),
                hasPerfExterior(), hasPerfPowertrain(),
                batteryType(), paintColor(), roofType(), wheelType(),
                seatType(), decorType(), hasExtendedNappaTrim(), hasAirSuspension(),
                hasTechPackage(), hasPowerLiftgate(), hasPremiumLighting(),
                hasHomeLink(), hasNavSystem(),
                hasAudioUpgrade(), hasSatRadio(),
                hasSupercharger(), hasTwinCharger(), hasHPWC(),
                hasParcelShelf(), hasPaintArmor(), hasThirdRow(),
                hasParkingSensors(), hasLightingPackage(), hasSecurityPackage(),
                hasFogLamps(), hasColdWeather());
    }

/*------------------------------------------------------------------------------
 *
 * Private Option Handling Methods
 * 
 *----------------------------------------------------------------------------*/

    private boolean hasOption(String optionName) {
        String option = optionsFound.get(optionName);
        if (option == null)
            return false;
        
        // 'X' options are either there or not, they aren't a prefix
        // followed by 00 or 01. Treat them specially.
        if (optionName.startsWith("X"))
            return true;
        return !option.equals(optionName + "00");
    }
        
    /*
     * Returns a member of an Enumeration corresponding to a particular option.
     * found in the optionsFound Map.
     * <P>
     * For example, lets say we want to create an
     * instance of the Region enum that corresponds to whatever Region was
     * actually returned by the REST API. We'd pass in Region as the Type
     * parameter and "RE" as the option name (All regions start with RE).
     * <p>
     * This code will look up "RE" in the optionsFound Map and find the actual
     * option code that was returned for the "RE" prefix. Let's say that it is
     * "RENA". The code will then instantiate a Region instance with the value
     * corresponding to the String "RENA". If an error occurs during instantiation,
     * an Unknown value is returned.
     * <P>
     * Note that all Enum types used with this method must have an "Unknown"
     * instance for this to work.
     * <P>
     * This code is a little weird because it is both parameterized by the Enum
     * Type and the class is alo passed in. This is required to handle some
     * oddities of dealing with Generic Enums.
     * 
     * @param eClass    The class of the Enum for which we want an instance
     * @param prefix    A variable length list of one or more prefixes
     *                  that we'll look for in turn. Some options (like 
     *                  PaintColor) use multiple prefixes to encode them
     *                  so we need to look through all of them.
     * @return          An instance of the specified Enum type corresponding
     *                  to the specified prefix
     */
    private <T extends Enum<T>> T optionToEnum(Class<T> eClass, String... prefix) {
        String option = null;
        for (String p : prefix) {
            if ( (option = optionsFound.get(p)) != null)
                break;
        }
        return Utils.stringToEnum(eClass, option);
    }

}
    
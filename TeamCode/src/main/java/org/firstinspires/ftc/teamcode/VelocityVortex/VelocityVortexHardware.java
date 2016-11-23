package org.firstinspires.ftc.teamcode.VelocityVortex;

        import android.hardware.Sensor;

        import com.qualcomm.ftccommon.DbgLog;
        import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.GyroSensor;
        import com.qualcomm.robotcore.hardware.HardwareDevice;
        import com.qualcomm.robotcore.hardware.LightSensor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.hardware.TouchSensor;

        import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;

/**
 * Created by spmce on 11/3/2016.
 */
public class VelocityVortexHardware extends OpMode {
    /**
     * Velocity Vortex Hardware Constructor
     */
    VelocityVortexHardware () {
        // Constructor Code
    }
    //------------Hardware Devices------------
    // ADD HARDWARE DEVICES HERE:
    // DcMotors
    protected DcMotor mFL; // Front Left Drive Motor
    protected DcMotor mFR; // Front Right Drive Motor
    protected DcMotor mBL; // Back Left Drive Motor
    protected DcMotor mBR; // Back Right Drive Motor
    protected DcMotor mSweeper; // Sweeper motor
    protected DcMotor mLauncher; // launcher motor

    // Servos
    protected Servo sLeftBeacon;
    protected Servo sRightBeacon;
    // Sensors
    protected TouchSensor touch;
    protected ColorSensor color1;
    protected ColorSensor color2;
    protected LightSensor light1;
    protected LightSensor light2;
    protected GyroSensor gyro;
    protected ModernRoboticsI2cRangeSensor range;
    //------------initial positions------------
    // ADD INITIAL POWER AND POSITIONS VARIABLES HERE:
    // DcMotors - Initial Power
    private double initLeftDrivePower = 0;
    private double initRightDrivePower = 0;
    private double initBackLeftPower = 0;
    private double initBackRightPower = 0;
    private double initSweeperPower = 0;
    private double initlauncherPower = 0;
    // Servos - Initial Positions
    protected double initLeftBeacon = 0.72;
    protected double initRightBeacon = 0.15;
    //------------loop positions------------
    // ADD LOOP POWER AND POSITION VARIABLES HERE:
    // DcMotors - Loop Power
    protected double leftDrivePower = 0;
    protected double rightDrivePower = 0;
    protected double backLeftPower = 0;
    protected double backRightPower = 0;
    protected double sweeperPower = 0;
    protected double launcherPower = 0;
    // Servos - Loop Positions
    protected double leftBeaconPosition = 0.72;
    protected double rightBeaconPosition = 0.15;
    //------------Telemetry Warnings------------
    // Create message of warning if created
    protected String warningMessage = "";
    protected String motorWarningMessage = "";
    protected String driveWarningMessage = "";
    protected String servoWarningMessage = "";
    protected String sensorWarningMessage= "";
    //------------------------Init------------------------
    /**
     * Init
     */
    @Override
    public void init() {
        VelocityVortexTelemetry tele = new VelocityVortexTelemetry();
        tele.initTele();
        // Initialize Warnings Generated and Warning Messages
        Warning message = new Warning();
        message.initWarnings(); //Provide telemetry data to a class user
        //Hardware Map
        Map hardware = new Map();
        // ADD HARDWARE MAP HERE;
        // DcMotors - Map
        /*mFL = hardware.map(mFL,initLeftDrivePower,"fl");
        mFR = hardware.map(mFR,initRightDrivePower,"fr",true); // "true" reverses motor direction
        mBL = hardware.map(mBL,initBackLeftPower,"bl");
        mBR = hardware.map(mBR,initBackRightPower,"br",true); // "true" reverses motor direction
        mSweeper = hardware.map(mSweeper,initSweeperPower,"swpr");*/
        // Servos - Map
        //sLeftBeacon = hardware.map(sLeftBeacon,initLeftBeacon);
        //sRightBeacon = hardware.map(sRightBeacon,initRightBeacon);
        // Sensors - Map

        //VelocityVortexMap hardware = new VelocityVortexMap();
        //hardware.map();
        mFL = hardwareMap.dcMotor.get("fl");
        mFR = hardwareMap.dcMotor.get("fr");
        mFR.setDirection(DcMotorSimple.Direction.REVERSE);
        mBL = hardwareMap.dcMotor.get("bl");
        mBR = hardwareMap.dcMotor.get("br");
        mBR.setDirection(DcMotorSimple.Direction.REVERSE);
        mSweeper = hardwareMap.dcMotor.get("swpr");
        mLauncher = hardwareMap.dcMotor.get("Launcher");
        sLeftBeacon = hardwareMap.servo.get("sLeftButt");
        sRightBeacon = hardwareMap.servo.get("sRightButt");
        sLeftBeacon.setPosition(initLeftBeacon);
        sRightBeacon.setPosition(initRightBeacon);
        touch = hardwareMap.touchSensor.get("touch");
        color1 = hardwareMap.colorSensor.get("color1");
        color1.enableLed(false);
        color2 = hardwareMap.colorSensor.get("color1");
        color2.enableLed(false);
        light1 = hardwareMap.lightSensor.get("light1");
        light2 = hardwareMap.lightSensor.get("light2");
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        //range = hardwareMap.get("range");
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class,"range");
        mFL.setPower(0);
        mFR.setPower(0);
        mBL.setPower(0);
        mBL.setPower(0);        //tele.warningTele();
    }
    //------------------------Loop------------------------
    /**
     * Loop
     */
    @Override
    public void loop() {

    }

    public DcMotor getmBL() {
        return mBL;
    }

    public DcMotor getmBR() {
        return mBR;
    }

    public DcMotor getmFL() {
        return mFL;
    }

    public DcMotor getmFR() {
        return mFR;
    }

    public DcMotor getmSweeper() {
        return mSweeper;
    }

    public double getBackLeftPower() {
        return backLeftPower;
    }

    public double getBackRightPower() {
        return backRightPower;
    }

    public double getInitBackLeftPower() {
        return initBackLeftPower;
    }

    public double getInitBackRightPower() {
        return initBackRightPower;
    }

    public double getInitLeftBeacon() {
        return initLeftBeacon;
    }

    public double getInitLeftDrivePower() {
        return initLeftDrivePower;
    }

    public double getInitRightBeacon() {
        return initRightBeacon;
    }

    public double getInitRightDrivePower() {
        return initRightDrivePower;
    }

    public double getInitSweeperPower() {
        return initSweeperPower;
    }

    public double getLeftBeaconPosition() {
        return leftBeaconPosition;
    }

    public double getLeftDrivePower() {
        return leftDrivePower;
    }

    public double getRightBeaconPosition() {
        return rightBeaconPosition;
    }

    public double getRightDrivePower() {
        return rightDrivePower;
    }

    public double getSweeperPower() {
        return sweeperPower;
    }

    public Servo getsLeftBeacon() {
        return sLeftBeacon;
    }

    public Servo getsRightBeacon() {
        return sRightBeacon;
    }

    public String getDriveWarningMessage() {
        return driveWarningMessage;
    }

    public String getMotorWarningMessage() {
        return motorWarningMessage;
    }

    public String getSensorWarningMessage() {
        return sensorWarningMessage;
    }

    public String getServoWarningMessage() {
        return servoWarningMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setmFL(DcMotor mFL) {
        this.mFL = mFL;
    }

    public void setmFR(DcMotor mFR) {
        this.mFR = mFR;
    }

    public void setmBL(DcMotor mBL) {
        this.mBL = mBL;
    }

    public void setmBR(DcMotor mBR) {
        this.mBR = mBR;
    }

    public void setmSweeper(DcMotor mSweeper) {
        this.mSweeper = mSweeper;
    }

    public void setsLeftBeacon(Servo sLeftBeacon) {
        this.sLeftBeacon = sLeftBeacon;
    }

    public void setsRightBeacon(Servo sRightBeacon) {
        this.sRightBeacon = sRightBeacon;
    }

    public void setInitLeftDrivePower(double initLeftDrivePower) {
        this.initLeftDrivePower = initLeftDrivePower;
    }

    public void setInitRightDrivePower(double initRightDrivePower) {
        this.initRightDrivePower = initRightDrivePower;
    }

    public void setInitBackLeftPower(double initBackLeftPower) {
        this.initBackLeftPower = initBackLeftPower;
    }

    public void setInitBackRightPower(double initBackRightPower) {
        this.initBackRightPower = initBackRightPower;
    }

    public void setInitSweeperPower(double initSweeperPower) {
        this.initSweeperPower = initSweeperPower;
    }

    public void setInitLeftBeacon(double initLeftBeacon) {
        this.initLeftBeacon = initLeftBeacon;
    }

    public void setInitRightBeacon(double initRightBeacon) {
        this.initRightBeacon = initRightBeacon;
    }

    public void setLeftDrivePower(double leftDrivePower) {
        this.leftDrivePower = leftDrivePower;
    }

    public void setRightDrivePower(double rightDrivePower) {
        this.rightDrivePower = rightDrivePower;
    }

    public void setBackLeftPower(double backLeftPower) {
        this.backLeftPower = backLeftPower;
    }

    public void setBackRightPower(double backRightPower) {
        this.backRightPower = backRightPower;
    }

    public void setSweeperPower(double sweeperPower) {
        this.sweeperPower = sweeperPower;
    }

    public void setLeftBeaconPosition(double leftBeaconPosition) {
        this.leftBeaconPosition = leftBeaconPosition;
    }

    public void setRightBeaconPosition(double rightBeaconPosition) {
        this.rightBeaconPosition = rightBeaconPosition;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public void setMotorWarningMessage(String motorWarningMessage) {
        this.motorWarningMessage = motorWarningMessage;
    }

    public void setDriveWarningMessage(String driveWarningMessage) {
        this.driveWarningMessage = driveWarningMessage;
    }

    public void setServoWarningMessage(String servoWarningMessage) {
        this.servoWarningMessage = servoWarningMessage;
    }

    public void setSensorWarningMessage(String sensorWarningMessage) {
        this.sensorWarningMessage = sensorWarningMessage;
    }
}

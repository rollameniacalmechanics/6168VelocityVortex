package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by spmce on 12/16/2016.
 */
//@Autonomous (name = "Simple Auto", group = "Autonomous")
public class SimpleAuto extends VelocityVortexAutoMeth {

    private int state = -1;
    //private AutoLaunch launch = new AutoLaunch();
    private boolean isFinished;
    int num = 0;

    public void init() {
        super.init();
        mFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //mSweeper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //mLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void start() {
        mFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //mSweeper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //mLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void autoLoop(boolean ifBlue) {
        switch (state) {
            case -1:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                num++;
                if (num > 13) {
                    state++;
                }
            case 0:
                driveForward(0.5);
                if (mFL.getCurrentPosition() > 1280) {
                    state++;
                }
                break;
            case 1:
                zeroDrive();
                state++;
                break;
            case 2:
                isFinished = shooter();
                if (isFinished) {
                    state++;
                }
                break;
            case 3:
                driveForward(.5);
                if (mFL.getCurrentPosition() > 5580) {
                    state++;
                }
                break;
            case 4:
                drivePow(Math.PI,1,ifBlue);
                try {
                    Thread.sleep(800); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            default:
                zeroDrive();
                telemetry.addData("default", "default state");
                break;
        }
        telemetry.addData("state",state);
        robotTele();
    }
    public void stop() {
        robotTele();
        zeroDrive();
    }
    void robotTele() {
        telemetry.addData("fl encoder", mFL.getCurrentPosition());
        telemetry.addData("fr encoder", mFR.getCurrentPosition());
        telemetry.addData("bl encoder", mBL.getCurrentPosition());
        telemetry.addData("br encoder", mBR.getCurrentPosition());
    }
}

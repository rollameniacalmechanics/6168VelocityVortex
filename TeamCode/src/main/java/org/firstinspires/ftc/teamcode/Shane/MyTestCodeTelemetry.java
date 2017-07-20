package org.firstinspires.ftc.teamcode.Shane;<<<<<<< HEAD
package org.firstinspires.ftc.teamcode.TestCode;

/**
 * Created by team on 7/19/2017.
 */
public class MyTestCodeTelemetry extends MyTestCodeHardware {

=======
package org.firstinspires.ftc.teamcode.Shane;

/**
 * Created by lsatt on 7/19/2017.
 */

public class MyTestCodeTelemetry extends MyTestCodeHardware {
>>>>>>> origin/master
    @Override
    public void init() {
        super.init();
    }
<<<<<<< HEAD

=======
>>>>>>> origin/master
    @Override
    public void loop() {
        super.loop();
    }
<<<<<<< HEAD

=======
>>>>>>> origin/master
    protected void myTelemetry() {
        telemetry.addData("Right Motor Power", rightPower);
        telemetry.addData("Left Motor Power", leftPower);
        telemetry.addData("Lift Motor Power", liftPower);
<<<<<<< HEAD
        telemetry.addData("Arm Motor Power", armPower);

    }

=======
        telemetry.addData("arm Motor Power", armPower);
    }
>>>>>>> origin/master
}

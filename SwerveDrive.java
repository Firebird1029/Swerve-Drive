// Written by Brandon Yee. April 16, 2016. Collaboration with Cole Nagata, Jason Tay, and Mihir Kolli.

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
// import com.qualcomm.robotcore.util.Range;

public class SwerveDrive extends OpMode {
    DcMotor m1; // front right
    DcMotor m2; // front left
    DcMotor m3; // rear left
    DcMotor m4; // rear right

    Servo s1; // front right
    Servo s2; // front left
    Servo s3; // rear left
    Servo s4; // rear right

    double defaultServoPosition = 0.5;

    public double ms (double equation) {
        return Math.sqrt(equation);
    }

    public double mp (double varToIncrease) {
        return Math.pow(varToIncrease, 2);
    }

    public double getSpeed (double q, double m) {
        return ((m > 1) ? q/m : q);
    }

    public double getDirection (double q1, double q2) {
    	// ((q1 == 0 && q2 == 0) ? 0 : Math.atan2(q1, q2) * 180 / Math.PI)
        return (((q1 == 0 && q2 == 0) ? 0 : Math.atan2(q1, q2) / Math.PI) + 0.5);
    }

    public SwerveDrive () {}

    @Override
    public void init () {
        m1 = hardwareMap.dcMotor.get("m1");
        m2 = hardwareMap.dcMotor.get("m2");
        m3 = hardwareMap.dcMotor.get("m3");
        m4 = hardwareMap.dcMotor.get("m4");

        // motorLeft.setDirection(DcMotor.Direction.REVERSE);
        // motorRight.setDirection(DcMotor.Direction.REVERSE);

        s1 = hardwareMap.servo.get("s1");
        s2 = hardwareMap.servo.get("s2");
        s3 = hardwareMap.servo.get("s3");
        s4 = hardwareMap.servo.get("s4");

        // All wheels facing forward. (direction = 0)
        s1.setPosition(defaultServoPosition);
        s2.setPosition(defaultServoPosition);
        s3.setPosition(defaultServoPosition);
        s4.setPosition(defaultServoPosition);
    }

    @Override
    public void loop () {
        float f = -gamepad1.left_stick_y;
        float p = gamepad1.left_stick_x;
        float w = gamepad1.right_stick_x;

        f = (float)scaleInput(f);
        p = (float)scaleInput(p);
        w = (float)scaleInput(w);

        int l = 30;
        int t = 24;
        double r = ms(mp(l) + mp(t));

        double a = p - ((l/r) * w);
        double b = p + ((l/r) * w);
        double c = f - ((t/r) * w);
        double d = f + ((t/r) * w);

        double e = ms(mp(b) + mp(c));
        double g = ms(mp(b) + mp(d));
        double j = ms(mp(a) + mp(d));
        double k = ms(mp(a) + mp(c));

        double[] mQuick = {e, g, j, k};
        Arrays.sort(mQuick);
        double m = mQuick[3];

        double w1s = getSpeed(e, m);
        double w1d = getDirection(b, c);
        m1.setPower(w1s);
        s1.setPosition(w1d);

        double w2s = getSpeed(g, m);
        double w2d = getDirection(b, d);
        m2.setPower(w2s);
        s2.setPosition(w2d);

        double w3s = getSpeed(j, m);
        double w3d = getDirection(a, d);
        m3.setPower(w3s);
        s3.setPosition(w3d);

        double w4s = getSpeed(k, m);
        double w4d = getDirection(a, c);
        m4.setPower(w4s);
        s4.setPosition(w4d);

        // TELEMETRY
        /*
        telemetry.addData("Left Motor",  "" + left);
        telemetry.addData("Right Motor", "" + right);
        telemetry.addData("String Puller", "" + puller);
        telemetry.addData("String Releaser", "" + stringReleaser.getDirection());
        telemetry.addData("Left Servo", "" + servoLeft.getPosition());
        telemetry.addData("Right Servo", "" + servoRight.getPosition());
        telemetry.addData("Dropper", "" + dropper.getPosition());
        */

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal){
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        int index = (int) (dVal * 16.0); if (index < 0) {index = -index;}
        if (index > 16) {index = 16;}
        double dScale = 0.0; if (dVal < 0) {dScale = -scaleArray[index];} else {dScale = scaleArray[index];}
        return dScale;
    }
}

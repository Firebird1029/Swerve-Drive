package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class NewTeleOp extends OpMode {
	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor stringPuller;
	DcMotor stringReleaser;

    Servo servoLeft;
    Servo servoRight;
	Servo dropper;

    double slPosition = 1.0;
    double srPosition = 0.0;
    double delta = 0.1;
	double dropperPosition = 1.0;

	public NewTeleOp () {}

	@Override
	public void init () {
		motorLeft = hardwareMap.dcMotor.get("m1");
		motorRight = hardwareMap.dcMotor.get("m2");
		stringPuller = hardwareMap.dcMotor.get("m3");
		stringReleaser = hardwareMap.dcMotor.get("m4");

		// motorLeft.setDirection(DcMotor.Direction.REVERSE);
		motorRight.setDirection(DcMotor.Direction.REVERSE);

        servoLeft = hardwareMap.servo.get("s1");
        servoRight = hardwareMap.servo.get("s2");
		dropper = hardwareMap.servo.get("s3");

        servoLeft.setPosition(slPosition);
        servoRight.setPosition(srPosition);
		dropper.setPosition(dropperPosition);
	}

	@Override
	public void loop () {

		float throttle = -gamepad1.left_stick_y;
		float direction = gamepad1.left_stick_x;
		float right = throttle - direction;
		float left = throttle + direction;

		float throttle2 = -gamepad1.right_stick_y;
		float direction2 = gamepad1.right_stick_x;
		right += (throttle2 - direction2) / 2;
		left += (throttle2 + direction2) / 2;

		if (gamepad1.dpad_up) {
			left = -1;
			right = -1;
		}
		else if (gamepad1.dpad_down) {
			left = 1;
			right = 1;
		}
		else if (gamepad1.dpad_right) {
			left = 1;
			right = -1;
		}
		else if (gamepad1.dpad_left) {
			left = -1;
			right = 1;
		}

		float puller = gamepad2.right_trigger;
		puller += -gamepad2.left_trigger;

		if (gamepad2.right_bumper) {
			if (!gamepad2.left_bumper) {
                stringReleaser.setPower(-1);
            } else {
                stringReleaser.setPower(0);
            }
		}
		else if (gamepad2.left_bumper) {
			if (!gamepad2.right_bumper) {
                stringReleaser.setPower(1);
            } else {
                stringReleaser.setPower(0);
            }
		}
		else {
			stringReleaser.setPower(0);
		}

        if (gamepad1.x) {
			slPosition += delta;
			srPosition -= delta;
			dropperPosition += delta;

        } else if (gamepad1.y) {
			slPosition -= delta;
			srPosition += delta;
			dropperPosition -= delta;
        }

        // DC MOTORS

		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);
		puller = Range.clip(puller, -1, 1);

		right = (float)scaleInput(right);
		left = (float)scaleInput(left);
		puller = (float)scaleInput(puller);

		motorRight.setPower(right);
		motorLeft.setPower(left);
		stringPuller.setPower(puller);

        // SERVOS

        slPosition = Range.clip(slPosition, 0.4, 1);
        srPosition = Range.clip(srPosition, 0, 0.6);
		dropperPosition = Range.clip(dropperPosition, 0, 1);

        servoLeft.setPosition(slPosition);
        servoRight.setPosition(srPosition);
		dropper.setPosition(dropperPosition);

        // TELEMETRY

		telemetry.addData("Left Motor",  "" + left);
		telemetry.addData("Right Motor", "" + right);
		telemetry.addData("String Puller", "" + puller);
        telemetry.addData("String Releaser", "" + stringReleaser.getDirection());
        telemetry.addData("Left Servo", "" + servoLeft.getPosition());
        telemetry.addData("Right Servo", "" + servoRight.getPosition());
		telemetry.addData("Dropper", "" + dropper.getPosition());

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

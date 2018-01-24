package opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.MotorGroup;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Robot2017;
import org.firstinspires.ftc.teamcode.sensors.Gyro;

@Autonomous(name = "Sensor OpMode", group = "Test OpMode")
public class Sensor_OpMode extends LinearOpMode {

  @Override
  public void runOpMode() throws InterruptedException {
    Robot robot = new Robot2017(hardwareMap);

    MotorGroup left = robot.getTurnMotor(0);
    MotorGroup right = robot.getTurnMotor(1);
    left.useEncoders();
    left.resetEncoders();

    Gyro gyro = (Gyro) robot.getSensor("gyro");
    gyro.calibrate();

    waitForStart();

    while (opModeIsActive()) {
      telemetry.addData("Gyro angle 1", gyro.getSensorValue().firstAngle);
      telemetry.addData("Gyro angle 2", gyro.getSensorValue().secondAngle);
      telemetry.addData("Gyro angle 3", gyro.getSensorValue().thirdAngle);
      telemetry.addData("Motor encoders left", left.getAverageClicks());
      telemetry.addData("Motor encoders right", right.getAverageClicks());
      telemetry.update();
    }
  }
}
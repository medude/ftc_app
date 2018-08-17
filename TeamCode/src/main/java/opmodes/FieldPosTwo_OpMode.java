package opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcontroller.external.AllianceGetter.Alliance;
import org.firstinspires.ftc.robotcontroller.external.AllianceGetter;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Robot2017;
import org.firstinspires.ftc.teamcode.commands.DriveForDistEst;
import org.firstinspires.ftc.teamcode.commands.DriveForTime;
import org.firstinspires.ftc.teamcode.commands.Grab;
import org.firstinspires.ftc.teamcode.commands.MoveKnocker;
import org.firstinspires.ftc.teamcode.commands.Release;
import org.firstinspires.ftc.teamcode.sensors.ColorSensor;
import org.firstinspires.ftc.teamcode.sensors.VuMarkIdentify;

/**
 * Created by rachel on 1/20/2018.
 */

@Autonomous(name = "FieldPosTwo", group = "Linear Opmode")
public class FieldPosTwo_OpMode extends LinearOpMode {

  @Override
  public void runOpMode() throws InterruptedException {

    AllianceGetter.Alliance alliance = AllianceGetter.getAlliance();
    Robot robot = new Robot2017(hardwareMap);
    ColorSensor colorSensor = (org.firstinspires.ftc.teamcode.sensors.ColorSensor)robot.getSensor("color");

    waitForStart();

    if(opModeIsActive()) {

      //initial glyph holding

      robot.getServo("claw").converge();
      new Grab(robot).start();
      telemetry.addData("currently: ", "grabbed glyph");
      telemetry.update();

      //jewel knock mockup

      new MoveKnocker(robot, 140).start();
      telemetry.addData("currently: ", "knocker down");
      telemetry.update();
      sleep(1000);

      NormalizedRGBA color = colorSensor.readColor();

      boolean isBlue = color.blue > color.red; //if not blue. false >:) also it doesnt uh well whatever null i guess
      double compensation = 0;

      telemetry.addData("sensor blue content", color.blue);
      telemetry.addData("sensor red content", color.red);
      telemetry.addData("is blue?", isBlue);

      if(alliance.equals(Alliance.BLUE) && isBlue){
        //jewel is blue, red is behind, back up to knock red, -.3f for compensation
        telemetry.addData(">", "jewel is blue, going to back up");
        telemetry.update();
        new DriveForDistEst( (float) 1 , 6.223f, (float) 1, robot).start();
        compensation += 1;

      }else if(alliance.equals(Alliance.BLUE) && !isBlue){
        new DriveForDistEst( (float) 1 , 6.223f, (float) 1, robot).start();


        //oops nvm
      }else if(alliance.equals(Alliance.RED) && isBlue){
        telemetry.addData(">", "jewel is blue, going to go fwd");
        telemetry.update();
        new DriveForDistEst( (float) 1 , 6.223f, (float) 1, robot).start();
        compensation += 1;

      }else if(alliance.equals(Alliance.RED) && !isBlue){
        new DriveForDistEst( (float) -1 , 6.223f, (float) 1, robot).start();
        //oops nvm
      }else{
        telemetry.addData("Status", "uhm");
        telemetry.update();
      }

      //grace period for my eyes
      sleep(3000);

      new MoveKnocker(robot, 0).start();
      telemetry.addData("currently: ", "knocker up");
      telemetry.update();
      sleep(1000);

      //vu
      VuMarkIdentify vuMarkIdentify = (VuMarkIdentify) robot.getSensor("vuMarkPictograph");

      telemetry.addData("mark: ", vuMarkIdentify.readVuMark());
      telemetry.update();

      float cryptoboxdist = .0f;

      if(vuMarkIdentify.readVuMark() == RelicRecoveryVuMark.LEFT){
              cryptoboxdist += (float) 4;
      }else if(vuMarkIdentify.readVuMark() == RelicRecoveryVuMark.CENTER){
            cryptoboxdist += (float)  6;
      }else if(vuMarkIdentify.readVuMark() == RelicRecoveryVuMark.RIGHT){
        cryptoboxdist += (float) 8;
      }else{
        cryptoboxdist += (float) 4; //default pos is left
      }


      telemetry.addData("compensation", compensation);
      telemetry.update();

      if(alliance.equals(Alliance.RED)){
        new DriveForTime( cryptoboxdist, -1, robot).start(); //make negative
        telemetry.addData("should be going:", "backwards");

      }else if(alliance.equals(Alliance.BLUE)){
        new DriveForTime( (cryptoboxdist), 1, robot).start(); //make positive
        telemetry.addData("should be going:", "forwards");

      }else{
        telemetry.addData("rip", "theres no reason this should be here. there is no reason you should be here");
      }

      telemetry.addData("alliance:", alliance);
      telemetry.addData("distance traveled:", compensation + cryptoboxdist );
      telemetry.update();

      sleep(3000);

      //turning

      new Release(robot).start();
      telemetry.addData(">", "glyph released");
      telemetry.update();
      //glyph open and shut and

      //drive straight

      //safesonze, chill out.
    }
  }
}

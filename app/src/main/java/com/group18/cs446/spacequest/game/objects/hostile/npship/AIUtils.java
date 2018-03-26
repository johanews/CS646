package com.group18.cs446.spacequest.game.objects.hostile.npship;

public class AIUtils {
    public static int getNewAngleFromTarget(int dx, int dy, int angle, int turnSpeed) {
        int targetAngle = ((int)(Math.atan2(dx, dy)*180/Math.PI)+180)%360;
        int targetTurn = targetAngle - angle;
        targetTurn = (targetTurn+360)%360;
        int turn = 0;
        if(targetTurn == 0){
            return angle;
        } else if(targetTurn < 180){
            if(targetTurn < turnSpeed){
                turn = targetTurn;
            } else {
                turn = turnSpeed;
            }
        } else { //tt > 180 || < 0
            if(360-targetTurn < turnSpeed){
                turn = -(360-targetTurn);
            } else {
                turn = -turnSpeed;
            }
        }
        angle = (360+angle+turn)%360;
        return angle;
    }
}

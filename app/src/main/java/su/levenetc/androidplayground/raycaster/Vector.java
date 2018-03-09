package su.levenetc.androidplayground.raycaster;

/**
 * Created by eugene.levenetc on 08/03/2018.
 */

public class Vector {

    double x1;
    double y1;

    double x2;
    double y2;

    public Vector(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Vector() {

    }

    public void set(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getNorm(){
        return Math.sqrt (x * x + y * y);
    }

}

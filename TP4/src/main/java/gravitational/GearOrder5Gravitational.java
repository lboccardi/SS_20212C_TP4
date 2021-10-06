package gravitational;

import models.Body;
import models.Oscillator;
import oscillators.IntegrationScheme;

import java.awt.geom.Point2D;
import java.util.List;

public class GearOrder5Gravitational{

    private final double G = 6.674E-20;
    private Body prevBody = null;
    private Body currBody;
    List<Body> bodies;

    private Point2D prev_rc;
    private Point2D prev_r1c;
    private Point2D prev_r2c;
    private Point2D prev_r3c;
    private Point2D prev_r4c;
    private Point2D prev_r5c;

    private Point2D curr_rc;
    private Point2D curr_r1c;
    private Point2D curr_r2c = new Point2D.Double(0,0);
    private Point2D curr_r3c = new Point2D.Double(0,0);
    private Point2D curr_r4c = new Point2D.Double(0,0);
    private Point2D curr_r5c = new Point2D.Double(0,0);

    public GearOrder5Gravitational(Body body, List<Body> bodies) {
        this.currBody = body;
        this.bodies = bodies;

        this.curr_rc = new Point2D.Double(body.getR().getX(),body.getR().getY());
        this.curr_r1c = new Point2D.Double(body.getV().getX(),body.getV().getY());
        this.prev_rc = new Point2D.Double(body.getR().getX(),body.getR().getY());
        this.prev_r1c= new Point2D.Double(body.getV().getX(),body.getV().getY());
        this.prev_r2c = calculateAcceleration(currBody);
        this.prev_r3c = new Point2D.Double(0,0);
        this.prev_r4c = new Point2D.Double(0,0);
        this.prev_r5c = new Point2D.Double(0,0);
    }

    private Point2D calculateAcceleration(Body currBody) {
        Point2D totalForce = new Point2D.Double(0,0);
        for (Body body:bodies) {
            if(body.getType()!=currBody.getType()){
                Point2D aux = calculateGravitationalForce(body,currBody);
                Point2D eNormalDirection = currBody.calculateNormalR(body);
                totalForce.setLocation(totalForce.getX()+aux.getX()*eNormalDirection.getX(),totalForce.getY()+aux.getY()*eNormalDirection.getY());
            }
        }
        totalForce.setLocation(totalForce.getX()/currBody.getMass(),totalForce.getY()/currBody.getMass());
        return  totalForce;
    }

    private Point2D calculateGravitationalForce(Body b1, Body b2) {
        Point2D distances = calculateDistance(b1, b2);
        double force = G * (b1.getMass() * b2.getMass()) / Math.sqrt(Math.pow(distances.getX(), 2) +
                Math.pow(distances.getY(), 2));
        double fx = force * distances.getX();
        double fy = force * distances.getY();
        return new Point2D.Double(fx, fy);
    }

    private Point2D calculateDistance(Body b1, Body b2) {
        double dx = b2.getR().getX() - b1.getR().getX();
        double dy = b2.getR().getY() - b1.getR().getY();
        return new Point2D.Double(dx, dy);
    }

    private static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }

    public Point2D calculatePosition(double dt) {
        double curr_rp_x = prev_rc.getX()
                + prev_r1c.getX() * dt
                + prev_r2c.getX() * Math.pow(dt, 2) / 2
                + prev_r3c.getX() * (Math.pow(dt, 3) / factorial(3))
                + prev_r4c.getX() * (Math.pow(dt, 4) / factorial(4))
                + prev_r5c.getX() * (Math.pow(dt, 5) / factorial(5));
        double curr_rp_y = prev_rc.getY()
                + prev_r1c.getY() * dt
                + prev_r2c.getY() * Math.pow(dt, 2) / 2
                + prev_r3c.getY() * (Math.pow(dt, 3) / factorial(3))
                + prev_r4c.getY() * (Math.pow(dt, 4) / factorial(4))
                + prev_r5c.getY() * (Math.pow(dt, 5) / factorial(5));
        Point2D curr_rp = new Point2D.Double(curr_rp_x, curr_rp_y);

        double curr_rp1_x = prev_r1c.getX()
                + prev_r2c.getX() * dt
                + prev_r3c.getX() * Math.pow(dt, 2) / 2
                + prev_r4c.getX() * (Math.pow(dt, 3) / factorial(3))
                + prev_r5c.getX() * (Math.pow(dt, 4) / factorial(4));
        double curr_r1p_y = prev_r1c.getY()
                + prev_r2c.getY() * dt
                + prev_r3c.getY() * Math.pow(dt, 2) / 2
                + prev_r4c.getY() * (Math.pow(dt, 3) / factorial(3))
                + prev_r5c.getY() * (Math.pow(dt, 4) / factorial(4));
        Point2D curr_r1p = new Point2D.Double(curr_rp1_x, curr_r1p_y);

        double curr_r2p_x = prev_r2c.getX()
                + prev_r3c.getX() * dt
                + prev_r4c.getX() * Math.pow(dt, 2) / 2
                + prev_r5c.getX() * (Math.pow(dt, 3) / factorial(3));
        double curr_r2p_y = prev_r2c.getY()
                + prev_r3c.getY() * dt
                + prev_r4c.getY() * Math.pow(dt, 2) / 2
                + prev_r5c.getY() * (Math.pow(dt, 3) / factorial(3));
        Point2D curr_r2p = new Point2D.Double(curr_r2p_x, curr_r2p_y);

        double curr_r3p_x = prev_r3c.getX()
                + prev_r4c.getX() * dt
                + prev_r5c.getX() * Math.pow(dt, 2) / 2;
        double curr_r3p_y = prev_r3c.getY()
                + prev_r4c.getY()  * dt
                + prev_r5c.getY()  * Math.pow(dt, 2) / 2;
        Point2D curr_r3p = new Point2D.Double(curr_r3p_x, curr_r3p_y);

        double curr_r4p_x = prev_r4c.getX()
                + prev_r5c.getX() * dt;
        double curr_r4p_y = prev_r4c.getY()
                + prev_r5c.getY() * dt;
        Point2D curr_r4p = new Point2D.Double(curr_r4p_x, curr_r4p_y);

        double curr_r5p_x = prev_r5c.getX();
        double curr_r5p_y = prev_r5c.getY();

        Point2D curr_r5p = new Point2D.Double(curr_r5p_x, curr_r5p_y);

        Body auxiliarBody = new Body(curr_rp.getX(),curr_rp.getY(),curr_r1p.getX(),curr_r1p.getY(),currBody.getMass(),currBody.getType());
        Point2D predicted_a = calculateAcceleration(auxiliarBody);
        double delta_a_x = predicted_a.getX() - curr_r2p.getX();
        double delta_a_y = predicted_a.getY() - curr_r2p.getY();

        double delta_R2_x = (delta_a_x * Math.pow(dt, 2)) / 2;
        double delta_R2_y = (delta_a_y * Math.pow(dt, 2)) / 2;

        curr_rc = new Point2D.Double(
                curr_rp.getX() + (3.0/16) * delta_R2_x,
                curr_rp.getY() + (3.0/16) * delta_R2_y
        );
        curr_r1c = new Point2D.Double(
                curr_r1p.getX() + (251.0/360) * delta_R2_x / dt,
                curr_r1p.getY() + (251.0/360) * delta_R2_y / dt
        );
        curr_r2c = new Point2D.Double(
                curr_r2p.getX() + 1 * delta_R2_x * 2 /Math.pow(dt, 2),
                curr_r2p.getY() + 1 * delta_R2_y * 2 /Math.pow(dt, 2)
        );
        curr_r3c = new Point2D.Double(
                curr_r3p.getX() + (11.0/18) * delta_R2_x * factorial(3) / Math.pow(dt, 3),
                curr_r3p.getY() + (11.0/18) * delta_R2_y * factorial(3) / Math.pow(dt, 3)
        );
        curr_r4c = new Point2D.Double(
                curr_r4p.getX() + (1.0/6) * delta_R2_x * factorial(4) / Math.pow(dt, 4),
                curr_r4p.getY() + (1.0/6) * delta_R2_y * factorial(4) / Math.pow(dt, 4)
        );
        curr_r5c = new Point2D.Double(
                curr_r5p.getX() + (1.0/60) * delta_R2_x * factorial(5) / Math.pow(dt, 5),
                curr_r5p.getY() + (1.0/60) * delta_R2_y * factorial(5) / Math.pow(dt, 5)
        );

        prev_rc = curr_rc;
        prev_r1c = curr_r1c;
        prev_r2c = curr_r2c;
        prev_r3c = curr_r3c;
        prev_r4c = curr_r4c;
        prev_r5c = curr_r5c;

        return curr_rc;
    }

    public Point2D calculateVelocity(double dt) {
        return curr_r1c;
    }

}

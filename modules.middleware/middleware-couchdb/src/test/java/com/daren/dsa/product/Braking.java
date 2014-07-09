package com.daren.dsa.product;

/**
 * @类描述：制动参数类
 * @创建人：sunlf
 * @创建时间：2014-04-06 下午12:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Braking extends ParameterItem {
    //前轮制动器
    private Braking frontWheelBrake = new Braking("前轮制动器");
    //后轮制动器
    private Braking rearWheelBrake = new Braking("后轮制动器");

    public Braking(String name) {
        super(name);
    }

    public Braking getFrontWheelBrake() {
        return frontWheelBrake;
    }

    public void setFrontWheelBrake(Braking frontWheelBrake) {
        this.frontWheelBrake = frontWheelBrake;
    }

    public Braking getRearWheelBrake() {
        return rearWheelBrake;
    }

    public void setRearWheelBrake(Braking rearWheelBrake) {
        this.rearWheelBrake = rearWheelBrake;
    }
}

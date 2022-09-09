package org.ryuu.libgdxlibrary.box2d.interfacecontact;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;

public class MouseJointMove {
    private final World world;
    private final MouseJointDef mouseJointDef;
    private MouseJoint mouseJoint;
    private final Array<Joint> joints = new Array<>();
    private final Body body;

    public MouseJointMove(World world, Body body, Vector2 position) {
        this.world = world;
        this.body = body;
        BodyDef dummyBodyDef = new BodyDef();
        dummyBodyDef.type = BodyDef.BodyType.StaticBody;
        mouseJointDef = new MouseJointDef();
        mouseJointDef.bodyA = world.createBody(dummyBodyDef);
        mouseJointDef.bodyB = this.body;
        mouseJointDef.maxForce = body.getMass() * Integer.MAX_VALUE;
        mouseJointDef.frequencyHz = 1;
        mouseJointDef.dampingRatio = 1;
        createJoint();
        move(position);
    }

    public void move(Vector2 targetPosition) {
        setStartPosition(body.getPosition());
        setEndPosition(targetPosition);
    }

    private void setStartPosition(Vector2 startPosition) {
        mouseJointDef.target.set(startPosition);
        destroyJoint();
        createJoint();
    }

    private void setEndPosition(Vector2 endPosition) {
        mouseJoint.setTarget(endPosition);
    }

    public void createJoint() {
        mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
    }

    public void destroyJoint() {
        world.getJoints(joints);
        if (joints.contains(mouseJoint, false)) {
            world.destroyJoint(mouseJoint);
        }
    }

    public void setMaxForce(float maxForce) {
        mouseJointDef.maxForce = body.getMass() * maxForce;
    }

    public void setFrequency(float frequency) {
        mouseJointDef.frequencyHz = frequency;
    }

    public float getFrequency() {
        return mouseJointDef.frequencyHz;
    }
}
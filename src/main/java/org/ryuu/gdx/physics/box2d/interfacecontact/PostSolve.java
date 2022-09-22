package org.ryuu.gdx.physics.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;

@FunctionalInterface
public interface PostSolve {
    void postSolve(Contact contact, ContactImpulse impulse, Fixture self, Fixture other);
}
package org.ryuu.gdx.physics.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

@FunctionalInterface
public interface BeginContact {
    void beginContact(Contact contact, Fixture self, Fixture other);
}
package org.ryuu.libgdxlibrary.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface IBeginContact {
    void beginContact(Contact contact, Fixture self, Fixture other);
}

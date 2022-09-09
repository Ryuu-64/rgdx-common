package org.ryuu.libgdxlibrary.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface IEndContact {
    void endContact(Contact contact, Fixture self, Fixture other);
}
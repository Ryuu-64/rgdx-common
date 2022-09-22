package org.ryuu.gdx.physics.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

@FunctionalInterface
public interface PreSolve {
    void preSolve(Contact contact, Manifold oldManifold, Fixture self, Fixture other);
}
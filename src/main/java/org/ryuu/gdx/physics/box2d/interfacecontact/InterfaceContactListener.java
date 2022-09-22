package org.ryuu.gdx.physics.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public final class InterfaceContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof BeginContact) {
            ((BeginContact) userDataA).beginContact(contact, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof BeginContact) {
            ((BeginContact) userDataB).beginContact(contact, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof EndContact) {
            ((EndContact) userDataA).endContact(contact, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof EndContact) {
            ((EndContact) userDataB).endContact(contact, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof PreSolve) {
            ((PreSolve) userDataA).preSolve(contact, oldManifold, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof PreSolve) {
            ((PreSolve) userDataB).preSolve(contact, oldManifold, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof PostSolve) {
            ((PostSolve) userDataA).postSolve(contact, impulse, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof PostSolve) {
            ((PostSolve) userDataB).postSolve(contact, impulse, contact.getFixtureB(), contact.getFixtureA());
        }
    }
}
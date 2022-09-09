package org.ryuu.libgdxlibrary.box2d.interfacecontact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public final class InterfaceContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof IBeginContact) {
            ((IBeginContact) userDataA).beginContact(contact, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof IBeginContact) {
            ((IBeginContact) userDataB).beginContact(contact, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof IEndContact) {
            ((IEndContact) userDataA).endContact(contact, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof IEndContact) {
            ((IEndContact) userDataB).endContact(contact, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof IPreSolve) {
            ((IPreSolve) userDataA).preSolve(contact, oldManifold, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof IPreSolve) {
            ((IPreSolve) userDataB).preSolve(contact, oldManifold, contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Object userDataA = contact.getFixtureA().getUserData();
        if (userDataA instanceof IPostSolve) {
            ((IPostSolve) userDataA).postSolve(contact, impulse, contact.getFixtureA(), contact.getFixtureB());
        }
        Object userDataB = contact.getFixtureB().getUserData();
        if (userDataB instanceof IPostSolve) {
            ((IPostSolve) userDataB).postSolve(contact, impulse, contact.getFixtureB(), contact.getFixtureA());
        }
    }
}
/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.lpui;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 *
 * @author Leonard
 */
public class LPBase extends PApplet {

    private int sizeWidth;
    private int sizeHeight;
    private LPComponent mousePressedTarget;
    private LPComponent base;

    public LPBase() {
    }

    public void setComponent(LPComponent c) {
        base = c;
        c.setParent(null);
        c.setBaseApplet(this);
    }

    @Override
    public void draw() {
        if (width != sizeWidth || height != sizeHeight) {
            onResize(width, height);
        }

        int dt = (int)(1000 / frameRate);
        base.animateInternal(dt);

        translate(base.getPosX(), base.getPosY());
        clip(0, 0, base.getWidth(), base.getHeight());
        base.drawInternal(this);
        noClip();
        translate(-base.getPosX(), -base.getPosY());

    }

    protected void onResize(int w, int h) {
        sizeWidth = w;
        sizeHeight = h;
        base.setSize(w, h);
    }

    @Override
    public void mousePressed() {
        LPComponent clickedComponent = base.onMousePressedInternal(mouseX, mouseY, mouseButton);

        //invalidate current focused component.
        if (LPContainer.getFocused() != null) {
            LPContainer.getFocused().invalidate();
        }
        mousePressedTarget = clickedComponent;
        if (clickedComponent != null) {
            LPComponent.setFocused(clickedComponent);
            clickedComponent.invalidate();
        }
    }

    @Override
    public void mouseReleased() {
        if (mousePressedTarget != null) {
            mousePressedTarget.onMouseReleasedInternal(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        base.onMouseScrollInternal(mouseX, mouseY, event.getCount());
    }

    @Override
    public void mouseMoved() {
        base.onMouseMoveInternal(mouseX, mouseY);
    }

    @Override
    public void mouseDragged() {
        base.onMouseMoveInternal(mouseX, mouseY);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        LPComponent focusedComponent = LPComponent.getFocused();
        if (focusedComponent != null) {
            focusedComponent.onKeyPressedInternal(event);
        }
    }

    /**
     * Event for when a key press occured that was not captured.
     *
     * @param event
     */
    public void keyPressedFallthrough(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
        LPComponent focusedComponent = LPComponent.getFocused();
        if (focusedComponent != null) {
            focusedComponent.onKeyReleasedInternal(event);
        }
    }

    /**
     * Event for when a key release occured that was not captured.
     *
     * @param event
     */
    public void keyReleasedFallthrough(KeyEvent event) {
    }

}

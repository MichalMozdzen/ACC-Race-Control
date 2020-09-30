/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming.visualisation.gui;

import processing.core.PApplet;

/**
 *
 * @author Leonard
 */
public class LPComponent {

    /**
     * The component that currently has focus.
     */
    private static LPComponent focused;
    /**
     * Reference to the PApplet.
     */
    protected static PApplet applet;
    /**
     * horizontal position in the applet.
     */
    private float posX;
    /**
     * Vertical position in the applet.
     */
    private float posY;
    /**
     * Width of this component.
     */
    private float width;
    /**
     * Height of this component.
     */
    private float height;
    /**
     * Flags that this component needs to be redrawn.
     */
    private boolean isInvalid = false;

    private String name;
    private LPComponent parent;

    /**
     * Creates a new instance.
     */
    public LPComponent() {
    }

    /**
     * Sets the position of this component.
     *
     * @param x X-position.
     * @param y Y-position.
     */
    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Returns the X-position.
     *
     * @return the X-position.
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Returns the Y-position.
     *
     * @return the Y-position.
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Sets the size for this component.
     *
     * @param w Width.
     * @param h Height.
     */
    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
        onResize((int)w, (int)h);
        invalidate();
    }

    /**
     * Returns the current width of this component.
     *
     * @return the width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the current height of this component.
     *
     * @return the height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Set the base PApplet.
     *
     * @param applet PApplet.
     */
    public static void setApplet(PApplet a) {
        applet = a;
    }

    /**
     * Set the parent element for this component.
     *
     * @param parent parent component.
     */
    protected void setParent(LPComponent parent) {
        this.parent = parent;
    }

    /**
     * Set name for this component.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this component.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Invalidate this component to mark it to be redrawn.
     */
    public void invalidate() {
        isInvalid = true;
    }

    /**
     * Returns if the component is invalid.
     *
     * @return
     */
    public boolean isInvalid() {
        return isInvalid;
    }

    /**
     * Returns true if this component is the one currently focused.
     *
     * @return
     */
    protected boolean isFocused() {
        return this == focused;
    }

    /**
     * Sets the focused component.
     *
     * @param comp the focused component.
     */
    protected static void setFocused(LPComponent comp) {
        focused = comp;
    }

    /**
     * Returns the currently focused component.
     *
     * @return the focused component.
     */
    protected static LPComponent getFocused() {
        return focused;
    }

    /**
     * Draws this component. Used internaly.
     */
    protected void drawInternal() {
        if (isInvalid) {
            draw();
            isInvalid = false;
        }
    }

    /**
     * Draws this component.
     */
    public void draw() {
    }

    /**
     * Mouse pressed event. Used internaly.
     *
     * @param mouseX X-position of the mouse click.
     * @param mouseY Y-position of the mouse click.
     * @param mouseButton which mouse button was pressed.
     * @return Returns the top most component that was pressed.
     */
    protected LPComponent mousePressedInternal(int mouseX, int mouseY, int mouseButton) {
        LPComponent clickedComponent = null;
        if (mouseX > posX && mouseX < posX + width
                && mouseY > posY && mouseY < posY + height) {
            clickedComponent = this;

            //run mouse pressed event for this component.
            mousePressed((int) (mouseX - posX), (int) (mouseY - posY), mouseButton);
        }
        return clickedComponent;
    }

    /**
     * Mouse pressed event.
     */
    public void mousePressed(int x, int y, int button) {
    }

    /**
     * Mouse released event. Used internaly.
     *
     * @param mouseX X-position of the mouse click.
     * @param mouseY Y-position of the mouse click.
     * @param mouseButton which mouse button was pressed.
     */
    public void mouseReleasedInternal(int mouseX, int mouseY, int mouseButton) {
        mouseReleased(mouseX, mouseY, mouseButton);
        if (parent != null) {
            parent.mouseReleasedInternal(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * mouse released event.
     */
    public void mouseReleased(int x, int y, int button) {
    }

    /**
     * Resize event.
     *
     * @param w
     * @param h
     */
    public void onResize(int w, int h) {

    }

}

package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.ORANGE;


/**
 * Base class for all game objects
 */
public abstract class SpaceObject {
    private double posX;
    private double posY;

    private int HP;
    private int maxHP;

    public SpaceObject(double x, double y, int maxHP) {
        this.posX = x;
        this.posY = y;
        this.maxHP = maxHP;
        this.HP = maxHP;

        initGraphics();
        getGraphics().setTranslateX(this.posX);
        getGraphics().setTranslateY(this.posY);
    }

    public double getPosX() {
        return this.posX;
    }
    public double getPosY() {
        return this.posY;
    }
    public void setPosX(double x) {
        this.posX = x;
        getGraphics().setTranslateX(x);
    }
    public void setPosY(double y) {
        this.posY = y;
        getGraphics().setTranslateY(y);
    }
    /**
     * Amount of hull points (0 - maxHP)
     */
    public int getHP() {
        return this.HP;
    }
    public void setHP(int hp) {
        this.HP = Math.min(hp, maxHP);
    }

    /**
     * Gets rectangular pseudobar with width and height of 10 representing current HP,
     * which is automatically updated when damage is received
     * @return Graphical node object representing current hull points
     */
    Node getHPBar() {
        Group barGroup = new Group();
        Rectangle bg = new Rectangle(-5, -5, 10, 10);
        bg.setFill(Color.DIMGRAY);
        Rectangle bar = new Rectangle(-5,-5, 10, 10);
        bar.setFill(Color.LIMEGREEN);
        onDamageReceived((object, newHP) -> {
            int hpPercent = 100 * newHP / object.maxHP;
            Paint barColor = Color.LIMEGREEN;
            if (hpPercent < 33) barColor = Color.RED;
            else if (hpPercent < 66) barColor = ORANGE;

            bar.setWidth(10.0 * newHP / object.maxHP);
            bar.setFill(barColor);
        });
        barGroup.getChildren().addAll(bg, bar);
        return barGroup;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public abstract void update(GameLoop gameLoop, long deltaNanos);

    private List<DamageReceivedObserver> damageObservers = new ArrayList<>();
    public void onDamageReceived(DamageReceivedObserver o) {
        damageObservers.add(o);
    }

    public void applyDamage(int damage, DamageType type) {
        int modifiedDamage = handleDamageEvent(damage, type);
        int newHP = Math.min(Math.max(getHP() - modifiedDamage, 0), getMaxHP());
        setHP(newHP);

        // call listeners
        for (DamageReceivedObserver o : damageObservers) {
            o.onDamageReceived(this, newHP);
        }
    }

    /**
     * Override in derived classes for different behaviour when damage is received
     * @param damage original amount of damage
     * @param type type of damage
     * @return modified amount of damage after object properties are applied (armor, vulnerability to some kind of dmg)
     */
    int handleDamageEvent(int damage, DamageType type) {
        return damage;
    }

    public abstract Node getGraphics();

    protected abstract void initGraphics();

    public void select() {
        // do nothing, usually to be overridden in derived classes
    }
    public void unselect() {
        // do nothing, usually to be overridden in derived classes
    }

    public abstract String getDisplayName();
}

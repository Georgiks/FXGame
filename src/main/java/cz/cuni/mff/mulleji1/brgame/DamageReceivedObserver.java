package cz.cuni.mff.mulleji1.brgame;

@FunctionalInterface
public interface DamageReceivedObserver {
    void onDamageReceived(SpaceObject object, int newHP);
}

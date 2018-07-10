package su.levenetc.androidplayground.raytracer.drawers;

public class InterferenceDrawer extends SpotDrawer {

    @Override
    protected int calculateSpotAlpha(double fadeLoc, float brightness) {
        fadeLoc *= 10;
        fadeLoc = Math.abs(1 / Math.sin(fadeLoc));
        return (int) (fadeLoc * 255) / 20;
    }
}
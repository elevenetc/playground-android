package su.levenetc.androidplayground.mergeview;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public interface Mergable {
	boolean mergableWith(Mergable value);
}

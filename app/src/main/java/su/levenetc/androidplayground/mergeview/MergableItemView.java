package su.levenetc.androidplayground.mergeview;

/**
 * Created by eugene.levenetc on 16/06/2017.
 */
public interface MergableItemView<T extends Mergable> {
	void set(T data);
	T get();
}

package su.levenetc.androidplayground.utils;

/**
 * Created by Eugene Levenetc on 17/07/2016.
 */
public class FloatArraySet implements Cloneable {

	private SparseArrayCompatFloat<FloatArraySet> backingMap;

	/**
	 * Constructs a new empty instance of {@code FloatArraySet}.
	 */
	public FloatArraySet() {
		this(new SparseArrayCompatFloat<>());
	}

	/**
	 * Constructs a new instance of {@code FloatArraySet} containing the unique
	 * elements in the specified array.
	 *
	 * @param items the array of elements to add.
	 */
	private FloatArraySet(float[] items) {
		this(new SparseArrayCompatFloat<>(items.length));
		for (int i = 0; i < items.length; i++) {
			add(items[i]);
		}
	}

	/**
	 * Constructs a new instance of {@code FloatArraySet} with a specified capacity.
	 *
	 * @param capacity the initial capacity of this {@code FloatArraySet}.
	 */
	private FloatArraySet(int capacity) {
		this(new SparseArrayCompatFloat<>(capacity));
	}

	/**
	 * Internal CTor to initialize the internal backing map.
	 * @param backingMap
	 */
	private FloatArraySet(SparseArrayCompatFloat<FloatArraySet> backingMap) {
		this.backingMap = backingMap;
	}

	/**
	 * Creates a new {@code FloatArraySet} with a specified capacity.
	 *
	 * @param capacity the initial capacity of this {@code FloatArraySet}.
	 * @return FloatArraySet
	 */
	public static FloatArraySet newIntArraySetWithCapacity(int capacity) {
		return new FloatArraySet(capacity);
	}

	/**
	 * Creates a new {@code FloatArraySet} containing the unique
	 * elements in the specified array.
	 *
	 * @param elements the array of elements to add.
	 * @return FloatArraySet
	 */
	public static FloatArraySet newIntArraySet(float[] elements) {
		return new FloatArraySet(elements);
	}

	/**
	 * Adds the specified item to this {@code FloatArraySet}, if not already present.
	 *
	 * @param item
	 *            the object to add.
	 */
	public void add(float item) {
		backingMap.put(item, this);
	}

	/**
	 * Removes all elements from this {@code FloatArraySet}, leaving it empty.
	 *
	 * @see #isEmpty
	 * @see #size
	 */
	public void clear() {
		backingMap.clear();
	}

	/**
	 * Searches this {@code FloatArraySet} for the specified object.
	 *
	 * @param item
	 *            the object to search for.
	 * @return {@code true} if {@code object} is an element of this
	 *         {@code FloatArraySet}, {@code false} otherwise.
	 */
	public boolean contains(int item) {
		return backingMap.indexOfKey(item) >= 0;
	}

	/**
	 * Returns true if this {@code FloatArraySet} has no elements, false otherwise.
	 *
	 * @return {@code true} if this {@code FloatArraySet} has no elements,
	 *         {@code false} otherwise.
	 * @see #size
	 */
	public boolean isEmpty() {
		return backingMap.size() == 0;
	}

	/**
	 * Removes the specified object from this {@code FloatArraySet}.
	 *
	 * @param item
	 *            the object to remove.
	 */
	public void remove(int item) {
		backingMap.remove(item);
	}

	/**
	 * Returns the number of elements in this {@code FloatArraySet}.
	 *
	 * @return the number of elements in this {@code FloatArraySet}.
	 */
	public int size() {
		return backingMap.size();
	}

	/**
	 * Get all the items in this {@code FloatArraySet}.
	 *
	 * @return  {@code int[]} array of all the items in the set.
	 *          A new array will be allocated for each request, modifying it won't affect the set.
	 */
	public float[] getAllItems() {
		int size = backingMap.size();
		float ret[] = new float[size];

		for (int i = 0; i < size; i++) {
			ret[i] = backingMap.keyAt(i);
		}
		return ret;
	}

	@Override
	@SuppressWarnings("unchecked")
	public FloatArraySet clone() {
		FloatArraySet clone = null;
		try {
			clone = (FloatArraySet) super.clone();
			clone.backingMap = backingMap.clone();
		} catch (CloneNotSupportedException cnse) {
            /* ignore */
		}
		return clone;
	}
}

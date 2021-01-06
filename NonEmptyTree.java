package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */
	private K key;
	private V value;
	private Tree<K, V> left, right;

	/**
	 * Only constructor we need.
	 * 
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;

	}

	public V search(K key) {
		int comparison = key.compareTo(this.key);
		if (comparison == 0) {
			return value;
		} else if (comparison > 0) {
			V val = right.search(key);
			return val;
		} else {
			V val = left.search(key);
			return val;
		}

	}

	public NonEmptyTree<K, V> insert(K key, V value) {
		int comparison = key.compareTo(this.key);

		if (comparison == 0) { // Check if value is the same
			this.value = value;
			return this;
		} else if (comparison < 0) {
			left = left.insert(key, value);
			return this;
		} else if (comparison > 0) {
			right = right.insert(key, value);
			return this;
		} else {
			return new NonEmptyTree<K, V>(key, value, left, right);
		}
	}

	public Tree<K, V> delete(K key) {
		int comparison = key.compareTo(this.key);
		if (comparison > 0) {
			right = right.delete(key);
		}
		if (comparison < 0) {
			left = left.delete(key);
		}
		if (comparison == 0) {
			try {
				K max = left.max();
				this.key = max;
				this.value = left.search(max);
				left = left.delete(max);

			} catch (TreeIsEmptyException e) {
				return right;

			}

		}
		return this;

	}

	public K max() {
		try {
			K max = right.max();
			if (key.compareTo(max) > 0) {
				return key;
			}
			return max;
		} catch (TreeIsEmptyException e) {

			return key;
		}

	}

	public K min() {
		try {
			K min = left.min();
			if (key.compareTo(min) < 0) {
				return key;
			}
			return min;
		} catch (TreeIsEmptyException e) {

			return key;
		}
	}

	public int size() {

		return 1 + left.size() + right.size();

	}

	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(this.key);
		right.addKeysToCollection(c);

	}

	public Tree<K, V> subTree(K fromKey, K toKey) {

		if (key.compareTo(fromKey) >= 0 && key.compareTo(toKey) <= 0) {
			Tree<K, V> tree = new NonEmptyTree<K, V>(key, value, left.subTree(fromKey, toKey),
					right.subTree(fromKey, toKey));
			return tree;
		} else {
			return EmptyTree.getInstance();
		}

	}

	public int height() {

		return 1 + Math.max(left.height(), right.height());

	}

	public void inorderTraversal(TraversalTask<K, V> p) {
		left.inorderTraversal(p);
		p.performTask(key, value);
		right.inorderTraversal(p);

	}

	public void rightRootLeftTraversal(TraversalTask<K, V> p) {
		right.rightRootLeftTraversal(p);
		p.performTask(key, value);
		left.rightRootLeftTraversal(p);
	}

}
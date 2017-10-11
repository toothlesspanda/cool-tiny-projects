package adts;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A bounded queue.
 * 
 * @author fmartins
 *
 * @param <T> The type of the elements of the queue.
 */
public class BoundedQueue<T> {

	/**
	 * An array list to store the elements. The implementation 
	 * interprets the array list as a circular list.
	 */
	public ArrayList<T> elems;
	
	/**
	 * The index of the starting element of the queue. In fact 
	 * the actual element is at the next position.
	 */
	// Variable isn't initialized
	public int start;
	
	/**
	 * The index of the last element of the queue.
	 */
	// Variable isn't initialized
	public int end;
	
	/**
	 * The actual number of the elements of the queue. 
	 */
	public int size;
	
	/**
	 * Creates a bounded queue given its size.
	 * 
	 * @param capacity The maximum number of elements of the queue.
	 * 
	 * @pre: capacity > 0
	 */
	public BoundedQueue(int capacity) {
		elems = new ArrayList<>(capacity);
		for (int i = 0; i < capacity; i++)
			elems.add(null);
		size = 0;
	}
	
	/**
	 * Inserts an element an the tail of the queue.
	 * 
	 * @param elem The element to be inserted at the end of the queue.
	 * 
	 * @pre: !isFull()
	 */
	public void enqueue(T elem) {
		end = nextNumModLen(end);
		elems.set(end, elem);
		size++;
	}
	
	/**
	 * Removes the element at the end of the queue.
	 * 
	 * @pre: !isEmpty() 
	 */
	public void dequeue() {
		start = nextNumModLen(start);
	}
	
	/**
	 * Adds a collection of elements to the queue.
	 * 
	 * @param collection The collection to be added to que queue.
	 * 
	 * @throws BoundedQueueException In case the queue has not enough free space to 
	 * hold the collection on elements.
	 */
	public void addAll(Collection<? extends T> collection) throws BoundedQueueException {
		if (collection.size() <= elems.size() - size)
			throw new BoundedQueueException();
		else
			for (T elem: collection) 
				enqueue(elem);
	}
	
	/**
	 * @return The element at the head of the queue.
	 * 
	 * @pre: !isEmpty()
	 */
	public T head() {
		T result = elems.get(start);
		start = nextNumModLen(start);
		return result;
	}
	
	/**
	 * @return true if the queue is full. 
	 */
	public boolean isFull() {
		return size == elems.size();
	}
	
	/**
	 * @return true if the queue is empty.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * @param n The number to compute the successor modulo the length of the queue.
	 * 
	 * @return The next element modulo the length of the queue.
	 */
	private int nextNumModLen(int n) {
		return n + 1 == elems.size() ?  0 : n + 1;
	}

	/**
	 * @return The actual number of elements of the queue
	 */
	public int size() {
		return size + 1;
	}
	
	/**
	 * The permutations of the queue. It uses a factorial number system, converting the numbers
	 * 1, 2, ..., n! to the factorial base (https://en.wikipedia.org/wiki/Factorial_number_system) 
	 * and then uses the Lehmer code (https://en.wikipedia.org/wiki/Lehmer_code) to get the indices 
	 * of the queue for each permutation. 
	 * 
	 * @return A bounded queue with bounded queues corresponding to the permutations of 
	 * this queue.
	 *  
	 * @throws BoundedQueueException In case the size of the queue is greater than 5.
	 */
	public BoundedQueue<BoundedQueue<T>> permutations() throws BoundedQueueException {
		// only compute permutations for queues with less than 6 elements
		if (size > 5)
			throw new BoundedQueueException();
		
		// compute the number of permutations 
		int fact = 1;
		for (int i = 2; i <= size; i++)
			fact *= i;
		
		// compute the permutations 
		BoundedQueue<BoundedQueue<T>> result = new BoundedQueue<>(fact);	
		for (int i = 0; i < fact; i++) {
			// converts i to the factorial base 
			int[] numFact = new int[size];
			for (int j = 0; j < numFact.length; j++)
				numFact[j] = 0;
			
			int j = numFact.length - 1;
			int k = 1;
			int num = i;
			while (num != 0) {
				numFact[j] = num % k;
				num = num / k;
				k++;
				j--;
			}
			
			// computes the next permutation
			
			// the indices of the next permutation
			ArrayList<Integer> pos = new ArrayList<>(numFact.length);
			for (int n = 0; n < numFact.length; n++)
				pos.add(n);
			
			// the next permutation
			ArrayList<T> perm = new ArrayList<>(numFact.length);
			for (int m = 0; m < numFact.length; m++) 
				perm.add(null);
			
			// lehmer encoding 
			for (int m = 0; m < numFact.length; m++) {
				perm.set(m, (T) elems.get(pos.get(numFact[m])));
				pos.remove(numFact[m]);
			}
			
			// adds the next permutation to the result
			BoundedQueue<T> bq = new BoundedQueue<>(elems.size());
			bq.addAll(perm);
			result.enqueue(bq);
		}
		return result;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = start, count = 0; count < size; count++, i = nextNumModLen(i)) {
			sb.append(elems.get(i));
			if (count + 1 < size) 
				sb.append(", ");
		}
		
		return sb.toString();
	}
	
}
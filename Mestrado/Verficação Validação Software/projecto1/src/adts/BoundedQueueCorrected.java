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
public class BoundedQueueCorrected<T> {

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
	public BoundedQueueCorrected(int capacity) {
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
		if(elem == null){
			throw new IllegalArgumentException();
		}
		elems.set(end, elem);
		end = nextNumModLen(end);
		size++;
	}
	
	/**
	 * Removes the element at the end of the queue.
	 * 
	 * @pre: !isEmpty() 
	 */
	public void dequeue() {
		start = nextNumModLen(start);
		size--;
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
			if (collection.size() == 0 || collection.size() > elems.size() - size){
				System.out.println("");
				throw new BoundedQueueException();
			}else
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
		return size;
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
	public BoundedQueueCorrected<BoundedQueueCorrected<T>> permutations() throws BoundedQueueException {
		// only compute permutations for queues with less than 6 elements
		
		System.out.print("## ----0,");
		if (size > 5){ //0
			System.out.print("1,");	
			System.out.println("");
			throw new BoundedQueueException(); //1
		}
		// compute the number of permutations 
		System.out.print("2,");
		int fact = 1; //2
		for (int i = 2; i <= size; i++){ //2 //3
			System.out.print("3,4,");
			fact *= i; //4
		}
	
		System.out.print("3,5,");
		// compute the permutations
		BoundedQueueCorrected<BoundedQueueCorrected<T>> result = new BoundedQueueCorrected<>(fact);	//5
		for (int i = 0; i < fact; i++) { //5 //6
			System.out.print("6,");
			// converts i to the factorial base
			System.out.print("7,");
			int[] numFact = new int[size]; //7
			for (int j = 0; j < numFact.length; j++){ //7 //8
				System.out.print("8,9,");
				numFact[j] = 0; //9
			}
			System.out.print("8,10,");
			int j = numFact.length - 1; //10
			int k = 1; //10
			int num = i; //10
			while (num != 0) { //11
				System.out.print("11,12,");
				numFact[j] = num % k; //12
				num = num / k; //12
				k++; //12
				j--; //12
			}
			System.out.print("11,13,");
			// computes the next permutation
			
			// the indices of the next permutation

			ArrayList<Integer> pos = new ArrayList<>(numFact.length); //13
			for (int n = 0; n < numFact.length; n++){ //13 //14
				System.out.print("14,15,");
				pos.add(n); //15
			}
			System.out.print("14,16,");
			// the next permutation
			ArrayList<T> perm = new ArrayList<>(numFact.length); //16
			for (int m = 0; m < numFact.length; m++){  //16 //17
				System.out.print("17,18,");
				perm.add(null); //18
			}
			System.out.print("17,19,");
			// lehmer encoding 
			for (int m = 0; m < numFact.length; m++) { //19 //20
				System.out.print("20,21,");
				perm.set(m, (T) elems.get(pos.get(numFact[m]))); //21
				pos.remove(numFact[m]); //21
			}
			
			System.out.print("20,22,");
			// adds the next permutation to the result
			BoundedQueueCorrected<T> bq = new BoundedQueueCorrected<>(elems.size()); //22
			bq.addAll(perm); //22
			result.enqueue(bq); //22
		}
		System.out.print("6,23,");
		System.out.println("");
		return result; //23
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = start, count = 0; count < size; count++, i = nextNumModLen(i)) {
			sb.append(elems.get(i));
			if (count + 1 < size) 
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
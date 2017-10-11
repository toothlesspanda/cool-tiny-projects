package adts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoundedQueueTest {

	BoundedQueue<Integer> bq;

	@Before
	public void before() {
		bq = new BoundedQueue<>(4);
	}

	@After
	public void after() {
		bq = null;
	}

	/**
	 * Enqueue | C2B1->C3B3->C4B2->C11B2
	 */
	@Test(expected = IllegalArgumentException.class)
	public void enqueueTestOne() {
		Integer elem = null;
		bq.end = 0;
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.size = 2;
		bq.enqueue(elem);

		// C2B1
		Integer expectedElem = null;
		Integer actualElem = bq.elems.get(0);
		assertEquals("Elem e' null (primeira posicao)", expectedElem, actualElem);

		// C3B3
		int expectedEnd = 1;
		int actualEnd = bq.end;
		assertEquals("Posicao da cauda (end)", expectedEnd, actualEnd);

		// C4B2
		int expectedSizeElems = 3;
		int actualSizeElems = bq.elems.size();
		assertTrue("Lista nao esta cheia", expectedSizeElems < actualSizeElems);

		// C11B2
		int expectedSize = 2;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);
	}

	/**
	 * Enqueue | C2B2->C3B3->C4B2->C11B2
	 */
	@Test
	public void enqueueTestTwo() {
		Integer elem = 4;
		bq.end = 0;
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.size = 2;
		bq.enqueue(elem);

		// C2B1
		Integer expectedElem = 4;
		Integer actualElem = bq.elems.get(0);
		assertEquals("Elem nao null (primeira posicao)", expectedElem, actualElem);

		// C3B3
		int expectedEnd = 1;
		int actualEnd = bq.end;
		assertEquals("Posicao da cauda (end)", expectedEnd, actualEnd);

		// C4B2
		int expectedSizeElems = 3;
		int actualSizeElems = bq.elems.size();
		assertTrue("Lista nao esta cheia", expectedSizeElems < actualSizeElems);

		// C11B2
		int expectedSize = 3;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);
	}

	/**
	 * Dequeue | C5B3-C6B2-C11B2
	 */
	@Test
	public void dequeueTestOne() {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.size = 3;
		bq.start = 0;
		bq.dequeue();

		// C5B3
		int expectedStart = 1;
		int actualStart = bq.start;
		assertEquals("Posicao da cabeca (start)", expectedStart, actualStart);

		// C6B2
		int actualElemsSize = bq.size;
		assertTrue("Lista nao estar vazia", actualElemsSize > 0);

		// C11B2
		int expectedSize = 2;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);

	}

	/**
	 * addAll | C1B1-C7B2-C8B2-C11B2
	 * 
	 * @throws BoundedQueueException
	 */
	@Test
	// capacidade ser maior que zero - collection nao ser null
	// - collection.size == 0 - size não negativo
	public void addAllTestOne() throws BoundedQueueException {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.size = 2;
		Collection<Integer> collection = new ArrayList<Integer>(0);
		bq.addAll(collection);

		// C1B1
		int actualSizeElems = bq.elems.size();
		assertTrue("Capacidade maior que zero", actualSizeElems > 0);

		// C7B2
		assertNotNull("Collection nao null", collection);

		// C8B2
		int expectedSizeCol = 0;
		int actualSizeCol = collection.size();
		assertTrue("Size da collection tem de ser maior que zero", actualSizeCol > expectedSizeCol);

		// C11B2
		int expectedSize = 2;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);
	}

	/**
	 * addAll | C1B1-C7B2-C8B3-C11B2
	 * 
	 * @throws BoundedQueueException
	 */
	@Test(expected = BoundedQueueException.class)
	// capacidade ser maior que zero - collection nao ser null
	// - collection.size > 0 - size não negativo
	public void addAllTestTwo() throws BoundedQueueException {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.size = 2;
		List<Integer> collection = new ArrayList<Integer>(5);
		collection.add(5);
		collection.add(6);
		collection.add(7);
		collection.add(8);
		collection.add(9);
		bq.addAll(collection);

		// C1B1
		int actualSizeElems = bq.elems.size();
		assertTrue("Capacidade maior que zero", actualSizeElems > 0);

		// C7B2
		assertNotNull("Collection nao null", collection);

		// C8B2
		int expectedSizeCol = 0;
		int actualSizeCol = collection.size();
		assertTrue("Size da collection tem de ser maior que zero", actualSizeCol > expectedSizeCol);

		// C11B2
		int expectedSize = 2;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);
	}

	/**
	 * head | C9B1-C5B3-C6B2
	 */
	@Test
	public void headTestOne() {
		bq.elems.set(0, null);
		bq.elems.set(1, 2);
		bq.start = 0;
		bq.size = 2;
		Integer result = bq.head();

		// C9B1
		assertNull("Elem nao null", result);

		// C5B3
		int expectedStart = 0;
		int actualStart = bq.start;
		assertEquals("Start nao ser negativo e que se mantenha", expectedStart, actualStart);

		// C6B2
		int actualElemsSize = bq.size;
		assertTrue("Lista nao estar vazia", actualElemsSize > 0);
	}

	/**
	 * head | C9B2-C5B3-C6B2
	 */
	@Test
	public void headTestTwo() {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.start = 0;
		bq.size = 3;
		Integer result = bq.head();

		// C9B2
		//elemento não é null
		assertNotNull("Elem nao null", result);

		// C5B3
		// a cabeça move uma casa
		int expectedStart = 0;
		int actualStart = bq.start;
		assertEquals("Start nao ser negativo e que se mantenha", expectedStart, actualStart);

		// C6B2
		// a fila não esta vazia
		int actualElemsSize = bq.size;
		assertTrue("Lista nao estar vazia", actualElemsSize > 0);
		
	}

	/**
	 * nextNumModLen | C1B1-C10B3
	 */
	/*@Test
	public void nextNumModLenTestOne() {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.size = 3;
		int n = 5;
		int index = bq.nextNumModLen(n);

		// C1B1
		int actualSizeElems = bq.elems.size();
		assertTrue("Capacidade maior que zero", actualSizeElems > 0);

		// C10B3
		int expectedResult = index - 1;
		int actualResult = bq.elems.size();
		assertTrue("N tem de ser menor que elems.size", expectedResult <= actualResult);
	}*/

	/**
	 * size | Ainda por definir
	 */
	@Test
	public void sizeTestOne() {
		bq.elems.set(0, 1);
		bq.elems.set(1, 2);
		bq.elems.set(2, 4);
		bq.size = 3;

		//Return
		int expectedResult = bq.size;
		int actualResult = bq.size();
		assertEquals("Numero e elementos da fila", expectedResult, actualResult);
	}

	/**
	 * toString | C2B1-C5B3-C11B2
	 */
	@Test
	public void toStringTestOne() {
		bq.elems.set(0, null);
		bq.elems.set(1, 2);
		bq.size = 2;
		bq.start = 0;
		bq.end = 2;
		
		String result = bq.toString();
		
		// C2B1
		Integer expectedElem = null;
		Integer actualElem = bq.elems.get(0);
		assertEquals("Elem e' null (primeira posicao)", expectedElem, actualElem);
		
		// C5B3
		int expectedStart = 0;
		int actualStart = bq.start;
		assertEquals("Start nao ser negativo e que se mantenha", expectedStart, actualStart);
		
		// C11B2
		int expectedSize = 2;
		int actualSize = bq.size;
		assertEquals("Size nao negativo e # Elementos na lista", expectedSize, actualSize);
		
		// Return
		String expectedString = "[null, 2]";
		String actualString = result;
		assertEquals("Representacao da fila", expectedString, actualString);
		
	}
}

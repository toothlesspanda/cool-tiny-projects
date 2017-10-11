package adts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PermutationTest {
	
	BoundedQueueCorrected<Integer> bq;

	@Before
	public void before() {
		bq = new BoundedQueueCorrected<>(6);
	}

	@After
	public void after() {
		bq = null;
	}
	
	
	
	/**
	 * Permutation</br>
	 * Size = 0;
	 * Falta, n�o deveria receber size = 0;
	 * Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * Prime Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * @throws BoundedQueueException
	 */
	@Test(expected = BoundedQueueException.class)
	public void permutationsTestSize0() throws BoundedQueueException {
		bq.permutations();
	}
	
	
	
	/**
	 * Permutation</br>
	 * Size = 1;
	 * Falta, n�o deveria receber size = 0;
	 * Path: </br>
	 * Prime Path: </br>
	 * @throws BoundedQueueException
	 */
	@Test
	public void permutationsTestSize1() throws BoundedQueueException {
		bq.enqueue(1);
		assertEquals("Permutations output","[[1]]",	bq.permutations().toString());
	}
	
	
	/**
	 * Permutation</br>
	 * Size = 2;
	 * Path: </br>
	 * Prime Path: </br>
	 * @throws BoundedQueueException
	 */
	@Test
	public void permutationsTestSize2() throws BoundedQueueException {
		bq.enqueue(1);
		bq.enqueue(2);
		assertEquals("Permutations output","[[1, 2], [2, 1]]",	bq.permutations().toString());
	}
	
	/**
	 * Permutation</br>
	 * Size = 3;
	 * Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * Prime Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * @throws BoundedQueueException
	 */
	@SuppressWarnings("serial")
	@Test
	public void permutationsTestSize3NotAllEquals() throws BoundedQueueException {
		bq.enqueue(1);
		bq.enqueue(2);
		bq.enqueue(3);
		BoundedQueueCorrected<BoundedQueueCorrected<Integer>> output= bq.permutations();
		
		//size of the perm's output
		int expectedPermSize = 6; //fact
		int actualPermSize = output.size();		
		assertEquals("Tamanho do resultado final",expectedPermSize, actualPermSize);
		
		
		//number of elems for each elem of perms
		int expectedPermNumElems = 3; 
		int actualPermNumElems = bq.size();
		assertEquals("Numero de elementos no elems",expectedPermNumElems, actualPermNumElems);
		
		//check if there are any duplicated
		ArrayList<BoundedQueueCorrected<Integer>> permOutput = output.elems;
		List<Integer> countEquals = new ArrayList<Integer>(6){{
			add(0);
			add(0);
			add(0);
			add(0);
			add(0);
			add(0);
		}};
		
		
		for(int i = 0; i < actualPermSize; i++ ){ 
			for(int j = 0; j < actualPermSize; j++ ){
				if(i != j){
					if(permOutput.get(i).toString().equals(permOutput.get(j).toString())){		
						countEquals.set(i, countEquals.get(i)+1); // counts the number of occurrences of an elem from output
					}
				}
			}
		}

		int expectedEqualPerms = 0;
		int actualEqualPerms = 0;
		
		for(Integer i : countEquals){
			if(i > 0)
				actualEqualPerms++;
			//if(i == expectedPermSize -1)
				
		}
			
		assertEquals("Numero de permutacoes iguais",expectedEqualPerms, actualEqualPerms);
	}
	
	
	/**
	 * Permutation</br>
	 * Size = 3;
	 * Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * Prime Path: 0-2-3-5-6-7-8-10-11-13-14-16-17-19-20-22</br>
	 * @throws BoundedQueueException
	 */
	@SuppressWarnings("serial")
	@Test
	public void permutationsTestSize3AllEquals() throws BoundedQueueException {
		bq.enqueue(3);
		bq.enqueue(3);
		bq.enqueue(3);
		BoundedQueueCorrected<BoundedQueueCorrected<Integer>> output= bq.permutations();
		
		//size of the perm's output
		int expectedPermSize = 6; //fact
		int actualPermSize = output.size();		
		assertEquals("Tamanho do resultado final",expectedPermSize, actualPermSize);
		
		
		//number of elems for each elem of perms
		int expectedPermNumElems = 3; 
		int actualPermNumElems = bq.size();
		assertEquals("Numero de elementos no elems",expectedPermNumElems, actualPermNumElems);
		
		//check if there are any duplicated
		ArrayList<BoundedQueueCorrected<Integer>> permOutput = output.elems;
		List<Integer> countEquals = new ArrayList<Integer>(6){{
			add(0);
			add(0);
			add(0);
			add(0);
			add(0);
			add(0);
		}};
		
		
		for(int i = 0; i < actualPermSize; i++ ){ 
			for(int j = 0; j < actualPermSize; j++ ){
				if(i != j){
					if(permOutput.get(i).toString().equals(permOutput.get(j).toString())){		
						countEquals.set(i, countEquals.get(i)+1); // counts the number of occurrences of an elem from output
					}
				}
			}
		}

		int expectedEqualPerms = 0;
		int actualEqualPerms = 0;
		
		for(Integer i : countEquals){
			if(i > 0)
				actualEqualPerms++;
			//if(i == expectedPermSize -1)
				
		}
			
		assertEquals("Numero de permutacoes iguais",expectedEqualPerms, actualEqualPerms);
	}
	
	
	/**
	 * Permutation</br>
	 * Size = 4;
	 * Falta, n�o deveria receber size = 0;
	 * Path:</br>
	 * Prime Path: </br>
	 * @throws BoundedQueueException
	 */
	@Test
	public void permutationsTestSize4() throws BoundedQueueException {
		bq.enqueue(1);
		bq.enqueue(2);
		bq.enqueue(3);
		bq.enqueue(4);

		bq.permutations();
	}
	
	
	/**
	 * Permutation</br>
	 * Size = 5;
	 * Path: </br>
	 * Prime Path: </br>
	 * @throws BoundedQueueException
	 */
	@Test
	public void permutationsTestSize5() throws BoundedQueueException {
		bq.enqueue(1);
		bq.enqueue(2);
		bq.enqueue(3);
		bq.enqueue(4);
		bq.enqueue(5);
		bq.permutations();
	}
	
	
	/**
	 * Permutation</br>
	 * Size = 6;
	 * Path: 0-1</br>
	 * Prime Path: 0-1</br>
	 * @throws BoundedQueueException
	 */
	@Test(expected = BoundedQueueException.class)
	public void permutationsTestSize6() throws BoundedQueueException {
		bq.enqueue(1);
		bq.enqueue(2);
		bq.enqueue(3);
		bq.enqueue(4);
		bq.enqueue(5);
		bq.enqueue(6);
		bq.permutations();
	}

}

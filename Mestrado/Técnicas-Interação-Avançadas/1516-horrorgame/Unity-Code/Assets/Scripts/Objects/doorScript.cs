using UnityEngine;
using System.Collections;

public class doorScript : MonoBehaviour {

	private float maxRotate;
	private float startRotate;
	private bool colSignal = false;

	void Start(){
		startRotate = transform.eulerAngles.y;
		maxRotate = transform.eulerAngles.y + 89;
		
	}
				
	void OnTriggerStay(Collider other) {
		
		if (other.tag == "Player"){
			if (transform.eulerAngles.y < maxRotate) {
				transform.Rotate (Vector3.up, Time.deltaTime * 15, Space.World);
				colSignal = true;
			}
		}
	}
		

}

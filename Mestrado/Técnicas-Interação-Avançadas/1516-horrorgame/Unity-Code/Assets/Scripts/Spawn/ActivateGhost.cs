using UnityEngine;
using System.Collections;

public class ActivateGhost : MonoBehaviour {

	private GameObject go, newObject;
	public bool alreadySpawn = false;
	private float reverseDegree = 0.0f;
	public bool activateOnTrigger = false;
	public GameObject prefab;

	void Start () {
		alreadySpawn = false;
		reverseDegree = 0.0f;
		activateOnTrigger = false;
	}

	void OnTriggerEnter(Collider other) {

		if (other.tag == "Player" && !alreadySpawn && activateOnTrigger) {
			go = transform.GetChild (0).gameObject;
			newObject = (GameObject) Instantiate ( prefab, go.transform.position  , Quaternion.identity );
			newObject.GetComponent<ghostScript> ().path = this.name;
			go = newObject.transform.GetChild(0).gameObject;
			/*if (reverse) {
				reverseDegree = 180;
			}*/

			if(this.name.Equals("Ghost3") || this.name.Equals("Ghost5") 
				|| this.name.Equals("Ghost6") || this.name.Equals("Ghost8") 
				|| this.name.Equals("Ghost9"))
				go.transform.rotation = Quaternion.Euler (270, 0+reverseDegree, 0);
			else
				go.transform.rotation = Quaternion.Euler (270, 270 + reverseDegree, 0);
	
			alreadySpawn = true;

		}


	}

}

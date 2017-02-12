using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class doorScriptFinal : MonoBehaviour {

	public Text textBox;

	void OnTriggerEnter (Collider obj) {
		if (obj.tag == "Player" && textBox.text == "Keys:3 / 3") {
			var thedoor = GameObject.FindGameObjectWithTag ("SF_Door");
			thedoor.GetComponent<Animation> ().Play ("open");
		}
	}

	void OnTriggerExit (Collider obj) {

		if (obj.tag == "Player" && textBox.text == "Keys:3 / 3") {
			var thedoor = GameObject.FindGameObjectWithTag ("SF_Door");
			thedoor.GetComponent<Animation> ().Play ("close");
		}

	}
}

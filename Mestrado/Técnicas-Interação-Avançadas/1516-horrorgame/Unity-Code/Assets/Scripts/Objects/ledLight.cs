using UnityEngine;
using System.Collections;

public class ledLight : MonoBehaviour {

	public LensFlare lf;
	public float tr = 0;

	void Update() {
		
		if (lf.brightness > 0 && Time.time > tr) {
			lf.brightness = lf.brightness - 0.5f;
			tr = Time.time + 0.5f;
		}
		else if (lf.brightness <= 0) {
			lf.brightness = 1f;
			tr = Time.time + 0.7f;
		}
	}
}

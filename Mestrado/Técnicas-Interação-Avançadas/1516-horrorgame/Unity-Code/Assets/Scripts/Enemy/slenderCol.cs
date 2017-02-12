using UnityEngine;
using System.Collections;

public class slenderCol : MonoBehaviour {
	public AudioClip clip;
	AudioSource a;
	// Use this for initialization
	void Start () {
		a = gameObject.GetComponent<AudioSource>();
		if (!a.isPlaying)
			a.PlayOneShot (clip);
	}
}

using UnityEngine;
using System.Collections;

public class slenderScript : MonoBehaviour {
	public string path;
	public float time;
	private AudioSource a;

	// Use this for initialization
	void Start () {
		iTween.MoveTo (gameObject, iTween.Hash ("path", iTweenPath.GetPath(path), "time", time, "easetype", iTween.EaseType.linear,"orienttopath", true,"loopType", "loop"));
		a = gameObject.GetComponent<AudioSource> ();
	}

	void Update(){
		if (!a.isPlaying) {
			a.Play ();
		}
	
	}
}

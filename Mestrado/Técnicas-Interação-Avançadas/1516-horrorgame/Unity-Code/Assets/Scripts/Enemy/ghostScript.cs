using UnityEngine;
using System.Collections;

public class ghostScript : MonoBehaviour {

	public AudioClip  jumpscareSound; // Sound that plays when enemy is in the middle of the screen
	private AudioSource audio;
	public string path;
	public bool reverse = false;
	// Use this for initialization
	void Start () {
		if (reverse) {
			iTween.MoveTo (gameObject, iTween.Hash ("path", iTweenPath.GetPathReversed (path), "time", 4, "onComplete", "destroyGhost", "easetype", iTween.EaseType.linear,"orienttopath", true));
		} else {
			iTween.MoveTo (gameObject, iTween.Hash ("path", iTweenPath.GetPath(path), "time", 4, "onComplete", "destroyGhost"));
		}
			
		audio = GetComponent<AudioSource>();
		audio.PlayOneShot (jumpscareSound);
	}

	void destroyGhost(){
		Destroy (gameObject);
	}
		

}

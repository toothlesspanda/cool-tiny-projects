using UnityEngine;
using System.Collections;

public class spiderScript : MonoBehaviour {

	public string path;

	// Use this for initialization
	void Start () {
		iTween.MoveTo (gameObject, iTween.Hash ("path", iTweenPath.GetPath (path), "time", 30,"loopType", "loop", "easetype", iTween.EaseType.linear,"orienttopath", true));
	}
}

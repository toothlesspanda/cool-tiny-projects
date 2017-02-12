using UnityEngine;
using System.Collections;

public class freedyScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
		iTween.MoveTo (gameObject, iTween.Hash ("path", iTweenPath.GetPath ("FreedyPath"), "time", 200, "onComplete", "destroyGhost", "easetype", iTween.EaseType.linear,"orienttopath", true));
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}

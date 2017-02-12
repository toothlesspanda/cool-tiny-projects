using UnityEngine;
using System.Collections;

public class SpiderSpawnerFromNODEJS : MonoBehaviour {

	private GameObject go, go2;
	public GameObject prefab;
	SocketIO.SocketIOComponent socket ;

	// Use this for initialization
	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("spider", spiderLocation);
	}

	public void spiderLocation(SocketIO.SocketIOEvent e){

		Debug.Log (e.data);
		JSONObject k  = e.data["spider"];

		string pos = k.str.Substring (k.str.Length - 4);
		Debug.Log ("|"+pos+"teste|");
		if (pos.Equals ("Lab4")) {
			go2 = GameObject.Find ("teste4");
		}
		else if (pos.Equals ("Lab6")) {
			go2 = GameObject.Find ("teste6");
		}
		else if (pos.Equals ("Lab8")) {
			go2 = GameObject.Find ("teste8");
		}else go2 = GameObject.Find (pos);

		GameObject g = (GameObject)Instantiate ( prefab, go2.transform.position  , Quaternion.identity );
		spiderScript s = g.GetComponent<spiderScript> ();
		s.path = "Spider1"+pos ;
		g = (GameObject)Instantiate ( prefab, go2.transform.position  , Quaternion.identity );
		s = g.GetComponent<spiderScript> ();
		s.path = "Spider2"+pos ;

	}
}

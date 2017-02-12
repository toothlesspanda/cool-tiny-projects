using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class keySpawnPosition : MonoBehaviour {

	private GameObject go, go2;
	public GameObject prefab;
	SocketIO.SocketIOComponent socket ;

	// Use this for initialization
	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("keys", keyLocation);
		//keyLocation();
	}
	
	public void keyLocation(SocketIO.SocketIOEvent e){

		Debug.Log (e.data);
		JSONObject k  = e.data["key1"];
		go2 = GameObject.Find (k.str);
		GameObject g = (GameObject) Instantiate ( prefab, go2.transform.position  , Quaternion.identity );
		g.name = "key1";

		k  = e.data["key2"];
		go2 = GameObject.Find (k.str);
		g = (GameObject) Instantiate ( prefab, go2.transform.position  , Quaternion.identity );
		g.name = "key2";

		k  = e.data["key3"];
		go2 = GameObject.Find (k.str);
		g = (GameObject) Instantiate ( prefab, go2.transform.position  , Quaternion.identity );
		g.name = "key3";
	}

}

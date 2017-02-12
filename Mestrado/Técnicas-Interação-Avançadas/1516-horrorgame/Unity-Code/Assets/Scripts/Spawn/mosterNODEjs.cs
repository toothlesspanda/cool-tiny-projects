using UnityEngine;
using System.Collections;

public class mosterNODEjs : MonoBehaviour {

	private GameObject go, go2;
	public GameObject prefabSlender;
	public GameObject prefabTank;
	public GameObject prefabGrunt;
	public GameObject prefabGrunt2;
	SocketIO.SocketIOComponent socket ;

	// Use this for initialization
	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("enemy", enemyLocation);
		//keyLocation();
	}

	public void enemyLocation(SocketIO.SocketIOEvent e){

		Debug.Log (e.data["enemy"]);
		JSONObject k = e.data ["enemy"];
		go2 = GameObject.Find (k.str);
		if (k.str.Equals ("slender")) {
			Instantiate (prefabSlender, go2.transform.position, Quaternion.identity);
		}
		if (k.str.Equals ("tank")) {
			Instantiate (prefabTank, go2.transform.position, Quaternion.identity);
		}
		if (k.str.Equals ("gruntLab4")) {
			Instantiate (prefabGrunt, go2.transform.position, Quaternion.identity);
		}
		if (k.str.Equals ("gruntLab6")) {
			Instantiate (prefabGrunt2, go2.transform.position, Quaternion.identity);
		}

	}
}

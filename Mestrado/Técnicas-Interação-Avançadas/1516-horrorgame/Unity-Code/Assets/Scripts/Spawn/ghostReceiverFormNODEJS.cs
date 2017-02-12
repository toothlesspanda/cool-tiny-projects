using UnityEngine;
using System.Collections;

public class ghostReceiverFormNODEJS : MonoBehaviour {

	private GameObject go, go2;
	SocketIO.SocketIOComponent socket ;

	// Use this for initialization
	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("ghost", ghostActive);
	}

	public void ghostActive(SocketIO.SocketIOEvent e){

	
		JSONObject ghostToActivate = e.data ["ghost"];
		Debug.Log (ghostToActivate.str);
		go2 = transform.Find (ghostToActivate.str).gameObject;
		ActivateGhost script = go2.GetComponent<ActivateGhost> ();
		script.activateOnTrigger = true;
		script.alreadySpawn = false;

	}
}

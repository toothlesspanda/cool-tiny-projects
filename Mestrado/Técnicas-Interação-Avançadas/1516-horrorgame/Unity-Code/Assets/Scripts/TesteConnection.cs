using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class TesteConnection : MonoBehaviour {
	
	GameObject go ;
	SocketIO.SocketIOComponent socket ;

	public float fireRate = 0.5F;
	private float nextFire = 0.0F;

	// Use this for initialization
	public void Start(){
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		Debug.Log("TESTE");
		socket.On("boop",TestBoop);
	}
	public void Update(){
		if (Input.GetKey("up") && Time.time > nextFire){
			nextFire = Time.time + fireRate;
			socket.Emit("beep");
		}
		else if (Input.GetKey("down") && Time.time > nextFire){
			nextFire = Time.time + fireRate;
			Debug.Log("Down");

		}


	}
		
	public void TestBoop(SocketIO.SocketIOEvent e){
		Debug.Log("BeepBoop");
	}

		
}

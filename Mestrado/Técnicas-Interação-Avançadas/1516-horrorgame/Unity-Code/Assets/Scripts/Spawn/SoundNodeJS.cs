using UnityEngine;
using System.Collections;

public class SoundNodeJS : MonoBehaviour {

	public AudioClip thunder, diabolic, scream, door, chains;
	private AudioSource audio;
	private GameObject go;
	SocketIO.SocketIOComponent socket ;

	// Use this for initialization
	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("sound", soundActivate);
		audio = GetComponent<AudioSource>();
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void soundActivate(SocketIO.SocketIOEvent e){
		JSONObject jso = e.data ["sound"];
		Debug.Log (jso.str);
		if (jso.str.Equals ("thunder")) {
			audio.PlayOneShot (thunder);
		} else if (jso.str.Equals ("scream")) {
			audio.PlayOneShot (scream);
		} else if (jso.str.Equals ("diabolic")) {
			audio.PlayOneShot (diabolic);
		} else if (jso.str.Equals ("door")) {
			audio.PlayOneShot (door);
		} else if (jso.str.Equals ("chains")) {
			audio.PlayOneShot (chains);
		} 
	}
}

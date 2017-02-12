using UnityEngine;
using System.Collections;

public class LampScript : MonoBehaviour {
	public Light Lamp1Lab8,Lamp2Lab8,Lamp1Lab5,Lamp2Lab5,Lamp1Lab4,Lamp2Lab4;
	// Use this for initialization
	SocketIO.SocketIOComponent socket ;
	GameObject go;

	void Start () {
		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On ("lights", lightOff);
	}
	
	// Update is called once per frame


	public void lightOff(SocketIO.SocketIOEvent e){

		JSONObject jso =  e.data ["location"];
		string s = jso.str;

		Debug.Log (s);
		Debug.Log (e.data["active"].str);

		if( s.Equals("lab5_sec1") ){
			if (e.data ["active"].str.Equals ("1"))
				Lamp1Lab5.intensity = 2.8f;
			else Lamp1Lab5.intensity = 0f;
		}
		if( s.Equals("lab5_sec2") ){
			if (e.data ["active"].str.Equals ("1"))
				Lamp2Lab5.intensity = 2.8f;
			else Lamp2Lab5.intensity = 0f;
			
		}
		if( s.Equals("lab4_sec1") ){
			if (e.data ["active"].str.Equals ("1"))
				Lamp1Lab4.intensity = 2.8f;
			else Lamp1Lab4.intensity = 0f;
			
		}
		if( s.Equals("lab4_sec2") ){
			if (e.data ["active"].str.Equals ("1"))
				Lamp2Lab4.intensity = 2.8f;
			else Lamp2Lab4.intensity = 0f;	
		}
		if( s.Equals("lab8_sec1") ){
			if (e.data ["active"].str.Equals ("1")) {
				Lamp1Lab8.intensity = 2.8f;
				Lamp2Lab8.intensity = 2.8f;	
			}
			else {
				Lamp1Lab8.intensity = 0f;
				Lamp2Lab8.intensity = 0f;
			}
		}


	}
}

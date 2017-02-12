using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour {

	public float velocity = 50.0f;
	private int keys;
	public Text keysText;
	public Text infoBox;
	public Text timeBox;
	private float time;
	public float delayTime; 
	private GameObject go ;
	SocketIO.SocketIOComponent socket ;
	private int mil = 0;
	private int sec;
	private int min;
	private bool gameOver = false;

	// Use this for initialization
	void Start () {
		keys = 0;
		keysText.text = "Keys:" + keys + " / 3";
		infoBox.text = "Get The keys to survive !!!";

		go = GameObject.Find("SocketIO");
		socket = go.GetComponent<SocketIO.SocketIOComponent>();
		socket.On("heartRate",heartRef);
		socket.On("movement",movementPlayer);

	}

	// Update is called once per frame
	void Update () {

		if (!gameOver) {
			if (Input.GetKey (KeyCode.W)) {
				transform.Translate (Vector3.forward * Time.deltaTime * velocity);
			}
			if (Input.GetKey (KeyCode.A)) {
				transform.Rotate (Vector3.down * Time.deltaTime * 90);
			}
			if (Input.GetKey (KeyCode.D)) {
				transform.Rotate (Vector3.up * Time.deltaTime * 90);
			}
			if (Time.time > delayTime) {
				infoBox.text = "";
			}

			timeBoxUpdate ();
		}


	}

	void OnTriggerEnter(Collider other) {

		if (other.tag == "importantKey") {
			Destroy (other.gameObject);
			keys++;
			keysText.text = "Keys:" + keys + " / 3";
			if ((3 - keys) == 0)
				infoBox.text = "You got all the keys. Go to the Door to escape";
			else
				infoBox.text = "You got a key. " + (3 - keys) + " left!!! Worry Up";
			delayTime = Time.time + delayTime;
		} else if (other.tag == "Final") {
			gameOver = true;
			infoBox.text = "You survive to doctor Doom!!!";
		}
	}

	public void heartRef(SocketIO.SocketIOEvent e){
		Debug.Log("heartBeat");
	}

	public void movementPlayer(SocketIO.SocketIOEvent e){
		Debug.Log("movementPlayer");
	}

	public void timeBoxUpdate(){
		mil++;
		if (mil == 60) {
			sec = sec + 1;
			if (sec == 60) {
				min = min + 1;
				sec = 0;
			}
			mil = 0;
		}
		if (sec < 10) timeBox.text = min + ":0" + sec;

		else timeBox.text = min + ":" + sec;
	}

}
	

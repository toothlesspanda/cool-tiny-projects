using System;
using UnityEngine;
using UnityStandardAssets.CrossPlatformInput;
using UnityStandardAssets.Utility;
using Random = UnityEngine.Random;
using UnityEngine.UI;
using System.Collections.Generic;
using System.Collections;

namespace UnityStandardAssets.Characters.FirstPerson
{
    [RequireComponent(typeof (CharacterController))]
    [RequireComponent(typeof (AudioSource))]
    public class FirstPersonController : MonoBehaviour
    {
        [SerializeField] private bool m_IsWalking;
        [SerializeField] private float m_WalkSpeed;
        [SerializeField] private float m_RunSpeed;
        [SerializeField] [Range(0f, 1f)] private float m_RunstepLenghten;
        [SerializeField] private float m_JumpSpeed;
        [SerializeField] private float m_StickToGroundForce;
        [SerializeField] private float m_GravityMultiplier;
        [SerializeField] private MouseLook m_MouseLook;
        [SerializeField] private bool m_UseFovKick;
        [SerializeField] private FOVKick m_FovKick = new FOVKick();
        [SerializeField] private bool m_UseHeadBob;
        [SerializeField] private CurveControlledBob m_HeadBob = new CurveControlledBob();
        [SerializeField] private LerpControlledBob m_JumpBob = new LerpControlledBob();
        [SerializeField] private float m_StepInterval ;
        [SerializeField] private AudioClip[] m_FootstepSounds;    // an array of footstep sounds that will be randomly selected from.
        [SerializeField] private AudioClip m_JumpSound;           // the sound played when character leaves the ground.
        [SerializeField] private AudioClip m_LandSound;           // the sound played when character touches back on ground.

		private Camera m_Camera;
		private Camera m_Camera2;
        private bool m_Jump;
        private float m_YRotation;
        private Vector2 m_Input;
        private Vector3 m_MoveDir = Vector3.zero;
        private CharacterController m_CharacterController;
        private CollisionFlags m_CollisionFlags;
        private bool m_PreviouslyGrounded;
        private Vector3 m_OriginalCameraPosition;
        private float m_NextStep;
        private AudioSource m_AudioSource;

		public float speed = 5.0f;
		private int keys;
		public Text keysText;
		public Text infoBox;
		public Text timeBox;
		public Text heartBox;
		public Text batteryBox;
		private float time;
		public float delayTime; 
		private GameObject go ;
		SocketIO.SocketIOComponent socket ;
		private int mil = 0;
		private int sec;
		private int min;
		private bool gameOver = true;

		private string lvl = "lvl1";
		private string lastLvl = "";

		public float scaryjumpTime = 0;
		public float scaryjumpDelayTime = 0.5f;

		public AudioClip  jumpscareSound; // Sound that plays when enemy is in the middle of the screen
		public AudioClip  jumpscareSound2; // Sound that plays when enemy is in the middle of the screen
		public AudioClip  jumpscareSound3; // Sound that plays when enemy is in the middle of the screen
		public AudioClip  batterySound; // Sound that plays when enemy is in the middle of the screen
		public AudioClip  keySound; // Sound that plays when enemy is in the middle of the screen
		public AudioClip teleSound;
		public GameObject ScaryJump1;
		public GameObject ScaryJump2;
		public GameObject ScaryJump3;
		public GameObject slenderJump;
		public GameObject gruntJump;
		public GameObject tankJump;
		public Light l;
		private int typeLight;
		public float timeLightNextStage;
		public float delayTimeLight;
		private AudioSource audio;
		private float deleteMsg;
		private bool activateLightMoster = false;

		private float lightScare;
		private float blink;
		private bool signal = false;
		private Rigidbody r;
		private bool colDetected = false;
        // Use this for initialization
        private void Start()
        {
            m_CharacterController = GetComponent<CharacterController>();
            m_Camera = Camera.main;
			m_Camera.enabled = true;
			m_OriginalCameraPosition = m_Camera.transform.localPosition;
            m_FovKick.Setup(m_Camera);
            m_HeadBob.Setup(m_Camera, m_StepInterval);
            m_AudioSource = GetComponent<AudioSource>();
			m_MouseLook.Init(transform , m_Camera.transform);




			keys = 0;
			keysText.text = "Keys:" + keys + " / 3";
			//infoBox.text = "Get The keys to survive !!!";

			infoBox.text = "Espera da mesa !!!";

			typeLight = 4;
			batteryBox.text = "Battery:"+ (l.intensity/4)*100 + "%"; 
			r = GetComponent<Rigidbody> ();
			go = GameObject.Find("SocketIO");
			socket = go.GetComponent<SocketIO.SocketIOComponent>();
			socket.On("heartBeat",heartRef);
			socket.On("movement",movementPlayer);
			socket.On("teleport",teleportPlayer);
			socket.On ("keys", startGame);
			socket.On ("flashlight", lightScary);

			Input.gyro.enabled = true;
			//startGame (null);
        }






        // Update is called once per frame
        private void Update()
        {

			if (Input.GetKey (KeyCode.S)) {
				startGame (null);
			}

			if (!gameOver) {
				//StartCoroutine ("movementPlayer");
				if( Time.time > scaryjumpTime && colDetected ){
					m_Camera.enabled = true;
					colDetected = false;
					m_Camera = Camera.main;
					GameObject g = GameObject.Find ("scaryjumpScene");
					Destroy (g);
				}
				if (Time.time > deleteMsg) {
					infoBox.text = "";
				}
				timeBoxUpdate ();

				if( Math.Abs(Input.gyro.rotationRateUnbiased.y) > 0.05f){
					//m_Camera.transform.Rotate (2*-Input.gyro.rotationRateUnbiased.x,0, 0);
					m_CharacterController.transform.Rotate (0,6*-Input.gyro.rotationRateUnbiased.y,0);
				}

				if (Time.time > timeLightNextStage && typeLight > 0 && !signal) {
					l.intensity = l.intensity - 1f;
					string perc = (l.intensity / 4) * 100 + "%";
					batteryBox.text = "Battery: "+ perc; 
					timeLightNextStage = Time.time + delayTimeLight;
					typeLight--;

					Dictionary<string,string> data = new Dictionary<string,string> ();
					data ["battery"] = perc;
					socket.Emit ("batteryState", new JSONObject(data));

				
				}
					
				if (Input.GetKey (KeyCode.W)) {
					//transform.Translate (Vector3.forward * Time.deltaTime * speed);
					m_CollisionFlags = m_CharacterController.Move(transform.forward*speed);
					PlayFootStepAudio ();
				}

				if (Input.GetKey (KeyCode.A)) {
					m_CharacterController.transform.Rotate (0,transform.rotation.y-90,0);
					PlayFootStepAudio ();
				}

				if (Input.GetKey (KeyCode.D)) {
					m_CharacterController.transform.Rotate (0,transform.rotation.y+90,0);
					PlayFootStepAudio ();
				}



				if (Time.time > lightScare && signal) {
					if(activateLightMoster) scaryJump (ScaryJump3, jumpscareSound3);
					signal = false;
					l.intensity = 1f * typeLight;
					batteryBox.text = "Battery:"+ (l.intensity/4)*100 + "%";
					activateLightMoster = false;
				}





				m_PreviouslyGrounded = m_CharacterController.isGrounded;
        	}
		}



		void OnTriggerEnter(Collider other) {

			if (other.tag == "importantKey") {
				Destroy (other.gameObject);
				m_AudioSource.PlayOneShot (keySound);
				keys++;
				keysText.text = "Keys:" + keys + " / 3";
				if ((3 - keys) == 0)
					infoBox.text = "You got all the keys. Go to the Door to escape";
				else
					infoBox.text = "You got a key. " + (3 - keys) + " left!!!";
				deleteMsg = Time.time + 5f;
				Dictionary<string,string> data = new Dictionary<string,string> ();
				data ["pos"] = other.gameObject.name;
				socket.Emit ("keyPickup", new JSONObject (data));
				delayTime = Time.time + delayTime;
			} else if (other.tag == "Final") {
				gameOver = true;
				infoBox.text = "You survive !!!";
			} else if (other.tag.Substring (0, 3) == "lab" || other.tag.Substring (0, 3) == "tun") {
				StartCoroutine (BeepBoop (other.tag, other.name));
			} else if (other.tag == "ghost" && !colDetected) {
				Destroy (other.gameObject);
				scaryJump ( ScaryJump1 , jumpscareSound );
			}else if (other.tag == "spider" &&	!colDetected) {
				Destroy (other.gameObject);
				scaryJump ( ScaryJump2 , jumpscareSound2 );
			}else if (other.tag == "battery" &&	!colDetected) {
				if (typeLight < 4) {
					Destroy (other.gameObject);
					m_AudioSource.PlayOneShot (batterySound);
					typeLight++;
					l.intensity = l.intensity + 1f;
					string perc = (l.intensity / 4) * 100 + "%";
					batteryBox.text = "Battery: "+ perc;


					Dictionary<string,string> data = new Dictionary<string,string> ();
					data ["battery"] = perc;
					socket.Emit ("batteryState", new JSONObject(data));

				} else {
					infoBox.text = "Cant carry anymore!!!";
					deleteMsg = Time.time + 5f;
				}
			}else if (other.tag == "slender" && !colDetected) {
				Destroy (other.gameObject);
				scaryJump ( slenderJump);
			}else if (other.tag == "grunt" && !colDetected) {
				Destroy (other.gameObject);
				scaryJump ( gruntJump);
			}
			else if (other.tag == "tank" && !colDetected) {
				Destroy (other.gameObject);
				scaryJump ( tankJump);
			}

		}

		private void scaryJump( GameObject ScaryJump, AudioClip ScarySound){
			GameObject t = GameObject.Find ("scaryJump");
			GameObject g = (GameObject) Instantiate (ScaryJump, t.transform.position, Quaternion.identity);
			g.name = "scaryjumpScene";
			audio = GetComponent<AudioSource>();
			audio.PlayOneShot (ScarySound);
			m_Camera.enabled = false;
			m_Camera2 = Camera.main;
			scaryjumpTime = Time.time + scaryjumpDelayTime;
			colDetected = true;
		}

		private void scaryJump( GameObject ScaryJump){
			GameObject t = GameObject.Find ("scaryJump");
			GameObject g = (GameObject) Instantiate (ScaryJump, t.transform.position, Quaternion.identity);
			g.name = "scaryjumpScene";
			m_Camera.enabled = false;
			m_Camera2 = Camera.main;
			scaryjumpTime = Time.time + scaryjumpDelayTime+1f;
			colDetected = true;
		}


		public void changeCamera(){
			m_Camera.enabled = false;
			m_Camera2 = Camera.main;
			scaryjumpTime = Time.time + scaryjumpDelayTime;
			colDetected = true;
		}
			
		private void lightScary(SocketIO.SocketIOEvent e){

			JSONObject obj =  e.data ["time"];

			Debug.Log ("Time: "+obj.str);
			lightScare = Time.time +float.Parse(obj.str);

			obj =  e.data ["activate"];
			Debug.Log ("Activate: " + obj.str);
			if (obj.str.Equals ("1")) {
				activateLightMoster = true;
			}
				
			signal = true;
			l.intensity = 0.0f;

		}

		private void startGame(SocketIO.SocketIOEvent e){
			gameOver = false;
			timeLightNextStage = Time.time + delayTimeLight;
			deleteMsg = Time.time + 5f;

			Dictionary<string,string> data = new Dictionary<string,string> ();
			data ["battery"] = "100";
			socket.Emit ("batteryState", new JSONObject( data));

		}


			
		private IEnumerator BeepBoop(String lvl, String sector)
		{
			// wait 1 seconds and continue
			yield return new WaitForSeconds(1);
			if (lastLvl != sector) {
				Dictionary<string,string> data = new Dictionary<string,string> ();
				data["lvl"] = lvl;
				data["sec"] = sector;
				socket.Emit("lvl", new JSONObject(data));
				lastLvl = sector;
			}

		}
			
		public void heartRef(SocketIO.SocketIOEvent e){
			Debug.Log ("HEartBeat");
			heartBox.text = e.data ["heartBeat"].str.Remove(e.data ["heartBeat"].str.Length-4) + "bpm" ;
		}

		public void teleportPlayer(SocketIO.SocketIOEvent e){
			JSONObject jso = e.data ["teleport"];
			GameObject t = GameObject.Find (jso.str);
			transform.transform.position = new Vector3 (t.transform.position.x, 1, t.transform.position.z);

			audio = GetComponent<AudioSource>();
			audio.PlayOneShot (teleSound);

	
		}

		public void movementPlayer(SocketIO.SocketIOEvent e){
			if (!gameOver) {
				m_CollisionFlags = m_CharacterController.Move(transform.forward*speed);
				PlayFootStepAudio ();
			}
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




        private void PlayFootStepAudio()
        {
			if ((Time.time > m_NextStep)) {
				m_AudioSource.clip = m_FootstepSounds[0];
				m_AudioSource.PlayOneShot(m_AudioSource.clip);
				// move picked sound to index 0 so it's not picked next time
				m_NextStep = Time.time + 0.5f;
			}
        }

    }
}

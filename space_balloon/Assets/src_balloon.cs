using UnityEngine;
using System.Collections;

public class src_balloon : MonoBehaviour
{
		Animator anim;
//		public AudioClip itemSound;
		bool exist = false;
		public GameObject GAMEMANAGER, pop, item;
		public GameObject[] effects = new GameObject[3];
		public Sprite   balloon;
		bool isUndead = false;
		bool isMonster = false;
		string monster;
		// Use this for initialization
		void Start ()
		{
				anim = GetComponent<Animator> ();

		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void create (int num)
		{
				exist = true;
//				if (num < 4)
				GetComponent<SpriteRenderer> ().sprite = balloon;
//				Debug.Log ("OnEnable()");
				anim = GetComponent<Animator> ();
				anim.SetBool ("balloonExist", true);
				anim.SetInteger ("super", num);
		}

		void cancel (int num)
		{
				exist = false;
				anim.SetBool ("balloonExist", false);
				anim.SetInteger ("cancel", num);
				
		}

		void undead (bool mbool)
		{
				isUndead = mbool;
		}

		void superMode (int num)
		{
				anim.SetInteger ("super", num);
		
		}

		void stopBalloon (bool stop)
		{
				anim.SetBool ("stop", stop);
	
		}

		void onMonster (string temp)
		{
				monster = temp;
				isMonster = true;
		}

		void offMonster ()
		{
				isMonster = false;
		}

		void OnTriggerEnter (Collider myTrigger)
		{
				if (myTrigger.transform.tag == "enemy" && exist && !isUndead && !isMonster) {
						
						exist = false;
						GAMEMANAGER.SendMessage ("getBalloonMSG", 1);
						
				}
				if (myTrigger.transform.tag == "enemy" && exist && !isUndead && isMonster) {
//						offMonster ();
						Debug.Log (" ");
						if (monster.Equals ("b")) {
								Instantiate (effects [0], transform.position, Quaternion.identity);
								Debug.Log (" ");
								GetComponent<SpriteRenderer> ().sprite = balloon;
								Destroy (GameObject.FindGameObjectWithTag ("monster"));
						}
						if (monster.Equals ("o")) {
						}
						if (monster.Equals ("p")) {
						}
			
			
				}
				if (myTrigger.transform.tag == "item") {
						GAMEMANAGER.SendMessage ("getItem");
//						audio.PlayOneShot (itemSound);
						Instantiate (item, myTrigger.gameObject.transform.position, Quaternion.identity);
						Destroy (myTrigger.gameObject);
			
			
				}
		 
		}
	 
}

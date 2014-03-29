using UnityEngine;
using System.Collections;

public class src_balloon : MonoBehaviour
{
		Animator anim;
		bool exist = false;
		public GameObject GAMEMANAGER, pop;
		public Sprite crash, balloon;
		// Use this for initialization
		void Start ()
		{
				anim = GetComponent<Animator> ();
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void create ()
		{
				exist = true;
				GetComponent<SpriteRenderer> ().sprite = balloon;
//				Debug.Log ("OnEnable()");
				anim = GetComponent<Animator> ();
				anim.SetBool ("balloonExist", true);
		}

		void cancel (int num)
		{
				exist = false;
				anim.SetBool ("balloonExist", false);
				anim.SetInteger ("super", 0);
				anim.SetInteger ("cancel", num);
				
		}

		void superMode (int num)
		{
				anim.SetInteger ("super", num);
		
		}
		 
		void OnTriggerEnter (Collider myTrigger)
		{
				if (myTrigger.transform.tag == "enemy" && exist) {
						
						Debug.Log ("onTrigger");
						exist = false;
						GAMEMANAGER.SendMessage ("getBalloonMSG", 1);
			                        
						
				}
		
		 
		}


}

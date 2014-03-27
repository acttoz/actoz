using UnityEngine;
using System.Collections;

public class src_balloon : MonoBehaviour
{
		Animator anim;
		bool exist = false;
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
				Debug.Log ("OnEnable()");
				anim = GetComponent<Animator> ();
				anim.SetBool ("balloonExist", true);
		}

		void reset ()
		{
				
		}

		void cancel (int num)
		{
				exist = false;
				anim.SetBool ("balloonExist", false);
				anim.SetInteger ("super", 0);
				superBack (0);
				Debug.Log ("cancel()");
				StartCoroutine ("removeTimer");
				anim.SetInteger ("cancel", num);
		}

		IEnumerator removeTimer ()
		{
				yield return new WaitForSeconds (0.2f);
				cancel (0);
				Debug.Log ("removeTimer");
				GameObject.Find ("GAMEMANAGER").SendMessage ("balloonRemove");
		}

		void superMode (int num)
		{
				anim.SetInteger ("super", num);
		
		}

		void superBack (int num)
		{
				GameObject.Find ("back").SendMessage ("superMode", num);
		                                             
				Debug.Log ("super" + num);
				 
		}
		 
		void OnTriggerEnter (Collider myTrigger)
		{
				if (myTrigger.transform.tag == "enemy" && exist) {
			
						GameObject.Find ("GAMEMANAGER").SendMessage ("gameOver");
						
				}
		
		 
		}

}

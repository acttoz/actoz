using UnityEngine;
using System.Collections;

public class src_balloon : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void success ()
		{
				GameObject.Find ("GAMEMANAGER").SendMessage ("balloonSuccess");
				Animator anim = GetComponent<Animator> ();
				anim.SetBool ("charged", true);

				 
		}

		void destroySelf ()
		{
				Destroy (this.gameObject);
		}

	void OnTriggerEnter (Collider myTrigger)
	{
		if (myTrigger.transform.tag == "enemy") {
			
			GameObject.Find ("GAMEMANAGER").SendMessage ("gameOver");
		}
		
		 
	}

}

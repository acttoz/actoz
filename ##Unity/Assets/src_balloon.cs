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

		void superMode (int num)
		{
				GameObject.Find ("GAMEMANAGER").SendMessage ("superMode", num);
		                                             
				Debug.Log ("super" + num);
				 
		}

		void destroy ()
		{
				superMode (0);
				Destroy (this.gameObject);
		}

		void OnTriggerEnter (Collider myTrigger)
		{
				if (myTrigger.transform.tag == "enemy") {
			
						GameObject.Find ("GAMEMANAGER").SendMessage ("gameOver");
						destroy ();
				}
		
		 
		}

}

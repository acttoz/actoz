using UnityEngine;
using System.Collections;

public class scr_item : MonoBehaviour
{
		// Use this for initialization
		void Start ()
		{
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void OnTriggerEnter (Collider myTrigger)
		{
				Destroy (this.gameObject);
				Debug.Log ("itemGet");
				if (myTrigger.transform.tag == "balloon") {
			
			
				}
		
		
		}

}

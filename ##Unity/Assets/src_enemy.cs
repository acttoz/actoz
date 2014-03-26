using UnityEngine;
using System.Collections;

public class src_enemy : MonoBehaviour
{

		// Use this for initialization
		void Awake ()
		{
				rigidbody.velocity = new Vector3 (5,-5, 0);
		}

		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
		}

		void OnTriggerEnter (Collider myTrigger)
		{
				if (myTrigger.gameObject.name == "down") {

						rigidbody.velocity = new Vector3 (rigidbody.velocity.x, -rigidbody.velocity.y, 0);
						

						Debug.Log ("wall trigger");
				}

				if (myTrigger.gameObject.name == "up") {
			
						rigidbody.velocity = new Vector3 (rigidbody.velocity.x, -rigidbody.velocity.y, 0);
			
			
						Debug.Log ("wall trigger");
				}
				if (myTrigger.gameObject.name == "left") {
			
						rigidbody.velocity = new Vector3 (-rigidbody.velocity.x, rigidbody.velocity.y, 0);
			
			
						Debug.Log ("wall trigger");
				}
				if (myTrigger.gameObject.name == "right") {
			
						rigidbody.velocity = new Vector3 (-rigidbody.velocity.x, rigidbody.velocity.y, 0);
			
			
						Debug.Log ("wall trigger");
				}
		}
 

	 
}

using UnityEngine;
using System.Collections;

public class src_enemy : MonoBehaviour
{
//	Random.Range(1, 5);
		public float speed;
		// Use this for initialization
		void Awake ()
		{
				int xTemp = 1;
				int yTemp = 1;
				if (Random.Range (0, 2) == 0) {
						xTemp = -1;
				}
				if (Random.Range (0, 2) == 0) {
						yTemp = -1;
				}
				Debug.Log (xTemp + " " + yTemp);
				rigidbody.velocity = new Vector3 (xTemp * speed, yTemp * speed, 0);
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
						

				}

				if (myTrigger.gameObject.name == "up") {
			
						rigidbody.velocity = new Vector3 (rigidbody.velocity.x, -rigidbody.velocity.y, 0);
			
			
				}
				if (myTrigger.gameObject.name == "left") {
			
						rigidbody.velocity = new Vector3 (-rigidbody.velocity.x, rigidbody.velocity.y, 0);
			
			
				}
				if (myTrigger.gameObject.name == "right") {
			
						rigidbody.velocity = new Vector3 (-rigidbody.velocity.x, rigidbody.velocity.y, 0);
			
			
				}
		}
 

	 
}

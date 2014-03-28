using UnityEngine;
using System.Collections;

public class src_enemy : MonoBehaviour
{
//	Random.Range(1, 5);
		public float speed;
		int xTemp = 1;
		int yTemp = 1;
		Vector3 velocity;
		float superNum = 1;
		// Use this for initialization
		void Awake ()
		{
				
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

		void superMode (int num)
		{

				
				switch (num) {
				
				case 1:
						if (superNum > 1 && superNum < 2)
								superNum = 0.67f;
						if (superNum > 2)
								superNum = 0.455f;

						break;
				case 2:
						superNum = 1.3f;
						break;
				case 3:
						superNum = 1.7f;
				
						break;
				
				default:
						break;
				
				
				}
				velocity = rigidbody.velocity;
				rigidbody.velocity = new Vector3 (velocity.x * superNum, velocity.y * superNum, 0);
			
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

using UnityEngine;
using System.Collections;

public class scr_destroyTimer : MonoBehaviour
{
		public float destroyTime;
		// Use this for initialization
		void Start ()
		{
				StartCoroutine ("timer");
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		IEnumerator timer ()
		{
				yield return new WaitForSeconds (destroyTime);
				Destroy (this.gameObject);
		
		}

}

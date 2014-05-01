using UnityEngine;
using System.Collections;

public class scr_selfTimer : MonoBehaviour
{
		public float time;
		// Use this for initialization
		void Start ()
		{
				StartCoroutine ("destroy");
		}

		IEnumerator destroy ()
		{
				yield return new WaitForSeconds (time);
				Destroy (this.gameObject);
		}
		// Update is called once per frame
		void Update ()
		{
	
		}
}

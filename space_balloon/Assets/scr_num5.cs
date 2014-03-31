using UnityEngine;
using System.Collections;

public class scr_num5 : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
				StartCoroutine ("selfDestroy");
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		IEnumerator selfDestroy ()
		{
				yield return new WaitForSeconds (0.2f);
				Destroy (this.gameObject);

		}
}

using UnityEngine;
using System.Collections;

public class scr_warn : MonoBehaviour
{
		public GameObject prf_enemy;
		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void create ()
		{
				Instantiate (prf_enemy, transform.position, Quaternion.identity);
		}
}

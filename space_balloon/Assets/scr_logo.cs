using UnityEngine;
using System.Collections;

public class scr_logo : MonoBehaviour
{
		public AudioClip logo;
	public GameObject logo_back;
		// Use this for initialization
		void Start ()
		{
	
		}

		 

		void sound ()
		{
				audio.PlayOneShot (logo);
		}
		// Update is called once per frame
		void Update ()
		{
	
		}
}

using UnityEngine;
using System.Collections;

public class scr_logo_back : MonoBehaviour
{
		public GameObject menu;
		// Use this for initialization
		void Start ()
		{
	
		}

		void destroy ()
		{
				menu.SetActive (true);
				Destroy (this.gameObject);
		}

		// Update is called once per frame
		void Update ()
		{
	
		}
}

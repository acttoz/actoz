using UnityEngine;
using System.Collections;

public class scr_logo_back : MonoBehaviour
{
		public GameObject menu, mainCam;
		// Use this for initialization
		void Start ()
		{
	
		}

		void destroy ()
		{
				menu.SetActive (true);
				mainCam.SendMessage ("playAfterLogo");
				Destroy (this.gameObject);
		}

		// Update is called once per frame
		void Update ()
		{
	
		}
}

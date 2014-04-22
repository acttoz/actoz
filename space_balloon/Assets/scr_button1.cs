using UnityEngine;
using System.Collections;

public class scr_button1 : MonoBehaviour
{
		public GameObject mainCam;
		public GameObject btn1;
		// Use this for initialization
		void Start ()
		{
				mainCam.SendMessage ("playAfterLogo");
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void OnTap (TapGesture gesture)
		{
				 
				if (gesture.Selection == btn1) {
						btn1.GetComponent<SpriteRenderer> ().color = Color.yellow;
						Application.LoadLevel (1);
					
				}

				 
		}

		 
}

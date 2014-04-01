using UnityEngine;
using System.Collections;

public class scr_button : MonoBehaviour
{
		public GameObject btn1, btn2;
		// Use this for initialization
		void Start ()
		{
				btn1.gameObject.GetComponent<SpriteRenderer> ().color = Color.white;
				btn2.gameObject.GetComponent<SpriteRenderer> ().color = Color.white;
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void OnTap (TapGesture gesture)
		{
				 
				if (gesture.Selection == btn1) {
						Debug.Log ("ontapbtn1");			
						btn1.GetComponent<SpriteRenderer> ().color = Color.yellow;
						Application.LoadLevel (1);
				}
				if (gesture.Selection == btn2) {
						btn2.gameObject.GetComponent<SpriteRenderer> ().color = Color.yellow;
//						Application.LoadLevel (1);
				}
		}

		 
}

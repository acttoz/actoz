using UnityEngine;
using System.Collections;

public class scr_button : MonoBehaviour
{
		public GameObject btn1, btn2, loading;
		// Use this for initialization
		void Start ()
		{
				DontDestroyOnLoad (GameObject.Find ("back"));
				

//				btn1.gameObject.GetComponent<SpriteRenderer> ().color = Color.white;
//				btn2.gameObject.GetComponent<SpriteRenderer> ().color = Color.white;
		}
	
		// Update is called once per frame
		void Update ()
		{
				if (Application.platform == RuntimePlatform.Android) {
						if (Input.GetKey (KeyCode.Escape)) {
				
								Application.Quit ();
				
								return;
				
						}
				}
		}
	
		void OnTap (TapGesture gesture)
		{
				 
				if (btn1 != null && gesture.Selection == btn1) {
						btn1.GetComponent<SpriteRenderer> ().color = Color.yellow;
						StartCoroutine ("loadLV", 2);
					
				}
				if (btn2 != null && gesture.Selection == btn2) {
						btn2.gameObject.GetComponent<SpriteRenderer> ().color = Color.yellow;
						StartCoroutine ("loadLV", 3);
//						Application.LoadLevel (1);
				}

				 

		}

		IEnumerator loadLV (int num)
		{
				loading.SetActive (true);
				animation.Play ("anim_menu2");
				yield return new WaitForSeconds (1.5f);
				Application.LoadLevel (num);
		}
		 
}

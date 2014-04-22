using UnityEngine;
using System.Collections;

public class scr_timesup : MonoBehaviour {

	// Use this for initialization
	GameObject btn_menu,btn_replay;
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	void OnFingerDown (FingerDownEvent e)
	{
		btn_menu = GameObject.Find ("btn_menu");
		btn_replay = GameObject.Find ("btn_replay");
		//				Instantiate (effectSuper1, new Vector2 (0, 0), Quaternion.identity);
		//				Instantiate (effectSuper2, new Vector2 (0, 0), Quaternion.identity);
		//				effectSuper1.renderer.sortingLayerName = "Foreground";
		
		//				Instantiate (balloon, GetWorldPos (e.Position), Quaternion.identity);
		Debug.Log (btn_menu);			
		if (e.Selection == btn_menu) {
			Debug.Log ("ontapbtn1");			
			//						btn1.GetComponent<SpriteRenderer> ().color = Color.yellow;
			//						Application.LoadLevel (0);
		}
		if (e.Selection == btn_replay) {
			Debug.Log ("ontapbtn2");			
			//						btn1.GetComponent<SpriteRenderer> ().color = Color.yellow;
			//						Application.LoadLevel (0);
		}
		 
		
		
		//				Debug.Log ("click");
	}
	
	void OnFingerUp (FingerUpEvent e)
	{
		//				GameObject[] balloon = GameObject.FindGameObjectsWithTag ("balloon");
		//
		//				if (balloon.Length!=0) {
		//						Debug.Log ("ballonnExist");
		//						foreach (GameObject element in balloon) {
		//								element.SendMessage ("destroySelf");
		//						}
		//						
		//						existBalloon = false;
		//				}
		//				Debug.Log ("release");
			
//		}
		//		balloonRemove ();
		//				Debug.Log ("release");
	}
}

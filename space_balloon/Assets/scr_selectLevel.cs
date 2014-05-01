using UnityEngine;
using System.Collections;

public class scr_selectLevel : MonoBehaviour
{
		GameObject[] oLevels = new GameObject[9];
		public GameObject btn_back, btn_back2, oCart, btn_play;
		public GameObject oItem_time, oItem_shield, oItem_smaller, oItem_star, oSelectedPan1, oSelectedPan2;
		public Sprite[] items = new Sprite[5] ;
		int selectedLevel = 5;
		int numGem;
		int iSelectedItem = 0;
		// Use this for initialization
		void Start ()
		{
				numGem = PlayerPrefs.GetInt ("NUMGEM");
				for (int i=0; i<9; i++) {
						oLevels [i] = GameObject.Find ("" + (i + 1));
				}
		}

		void OnTap (TapGesture gesture)
		{
				if (gesture.Selection == btn_back) {
						StartCoroutine ("loadLV", 1);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == btn_back2) {
						offCart ();
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == btn_play) {
						StartCoroutine ("play", selectedLevel);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == oItem_time) {
						itemSelect (gesture.Selection, 0);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == oItem_shield) {
						itemSelect (gesture.Selection, 1);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == oItem_star) {
						Debug.Log (" ");
						itemSelect (gesture.Selection, 2);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == oItem_smaller) {
						itemSelect (gesture.Selection, 3);
						//						Application.LoadLevel (1);
				}
				if (gesture.Selection == oLevels [0]) {
						selectedLevel = 5 + 0;
						cart ();
				}
				if (gesture.Selection == oLevels [1]) {
						selectedLevel = 5 + 1;
						cart ();
				}
				if (gesture.Selection == oLevels [2]) {
						selectedLevel = 5 + 2;
						cart ();
				}
				if (gesture.Selection == oLevels [3]) {
						selectedLevel = 5 + 3;
						cart ();
				}
				if (gesture.Selection == oLevels [4]) {
						selectedLevel = 5 + 4;
						cart ();
				}
				if (gesture.Selection == oLevels [5]) {
						selectedLevel = 5 + 5;
						cart ();
				}
				if (gesture.Selection == oLevels [6]) {
						selectedLevel = 5 + 6;
						cart ();
				}
				if (gesture.Selection == oLevels [7]) {
						selectedLevel = 5 + 7;
						cart ();
				}
				if (gesture.Selection == oLevels [8]) {
						selectedLevel = 5 + 8;
						cart ();
				}
			 

		
		}

		void itemSelect (GameObject obj, int num)
		{
				
				
				switch (iSelectedItem) {
				case 0:
						iSelectedItem++;
						obj.SendMessage ("selected");
						PlayerPrefs.SetInt (obj.transform.name, 1);
						oSelectedPan1.GetComponent<SpriteRenderer> ().sprite = items [num];
						break;
				case 1:
						iSelectedItem++;
						obj.SendMessage ("selected");
						PlayerPrefs.SetInt (obj.transform.name, 1);
						oSelectedPan2.GetComponent<SpriteRenderer> ().sprite = items [num];
						break;
				case 2:
						 
						break;
				default:
						break;
				}
		}

		IEnumerator loadLV (int num)
		{
//				animation.Play ("anim_menu2");
				Application.LoadLevel (num);
				yield return new WaitForSeconds (0f);
		}

		IEnumerator play (int num)
		{
				oCart.animation.Play ("anim_menu2");
				yield return new WaitForSeconds (1.5f);
				Application.LoadLevel (num);
		}

		void cart ()
		{

				oCart.animation ["anim_menu_cart_on2"].speed = 1.0f;
				animation ["anim_menu_cart_out"].speed = 1.0f;

				oCart.animation ["anim_menu_cart_on2"].time = 0;
				animation ["anim_menu_cart_out"].time = 0;
				
				oCart.animation.Play ();
				animation.Play ("anim_menu_cart_out");
		}

		void offCart ()
		{

				oCart.animation ["anim_menu_cart_on2"].speed = -1.0f;
				animation ["anim_menu_cart_out"].speed = -1.0f;

				oCart.animation ["anim_menu_cart_on2"].time = oCart.animation ["anim_menu_cart_on2"].length;
				animation ["anim_menu_cart_out"].time = animation ["anim_menu_cart_out"].length;


				oCart.animation.Play ();
				animation.Play ("anim_menu_cart_out");
		}

		// Update is called once per frame
		void Update ()
		{
				if (Application.platform == RuntimePlatform.Android) {
						if (Input.GetKey (KeyCode.Escape)) {
								Application.LoadLevel (1);
								return;
						}
				}
				if (numGem < 5) {
						oItem_star.SendMessage ("selected");
						oItem_time.SendMessage ("selected");
						oItem_smaller.SendMessage ("selected");
						oItem_shield.SendMessage ("selected");
				}
	
		}
}

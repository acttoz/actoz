using UnityEngine;
using System.Collections;

public class scr_level : MonoBehaviour
{
		public Sprite unlockedIcon, unlockedPanel;
		string level;
		// Use this for initialization
		void Start ()
		{
				level = gameObject.transform.name;
				GetComponentInChildren<tk2dTextMesh> ().text = level;

				PlayerPrefs.SetInt ("1", 1);
				PlayerPrefs.SetInt ("2", 1);

				if (PlayerPrefs.GetInt (level, 0) == 1) {
						//Cleared/////////////////////////////////////////
						GetComponent<BoxCollider> ().enabled = true;
						SpriteRenderer[] componentArray = GetComponentsInChildren<SpriteRenderer> ();
						GetComponentInChildren<tk2dTextMesh> ().color = Color.white;
						componentArray [0].sprite = unlockedIcon;
						componentArray [1].sprite = unlockedPanel;

				}
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}
}

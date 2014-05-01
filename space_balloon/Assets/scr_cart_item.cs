using UnityEngine;
using System.Collections;

public class scr_cart_item : MonoBehaviour
{
		public Sprite selectedSprite;
		Sprite unselectedSprite;
		// Use this for initialization
		void Start ()
		{
				unselectedSprite = GetComponent<SpriteRenderer> ().sprite;
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void selected ()
		{
				GetComponent<SpriteRenderer> ().sprite = selectedSprite;
				GetComponent<BoxCollider> ().enabled = false;
		}

		void unSelected ()
		{
				GetComponent<BoxCollider> ().enabled = true;
				GetComponent<SpriteRenderer> ().sprite = unselectedSprite;
		}
}

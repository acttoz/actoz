using UnityEngine;
using System.Collections;

public class scr_monster : MonoBehaviour
{
		public string monsterColor;
		public float speed;
		// Use this for initialization
		void Start ()
		{
		}
	
		// Update is called once per frame
		void Update ()
		{
			
		}

		void gogo ()
		{
				transform.parent.gameObject.GetComponent<scr_monParent> ().enabled = true;
		}

		void itemUse ()
		{
				GameObject.Find ("GAMEMANAGER").SendMessage ("itemUse", monsterColor);
				transform.parent.gameObject.animation.Play ();
				transform.parent.SendMessage ("countDown");
		}
}

using UnityEngine;
using System.Collections;

public class scr_monster : MonoBehaviour
{
		public string monsterColor;
		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void itemUse ()
		{
				 
				GameObject.Find ("GAMEMANAGER").SendMessage ("itemUse", monsterColor);
				Destroy (this.gameObject);
		}
}

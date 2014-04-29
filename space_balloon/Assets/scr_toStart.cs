using UnityEngine;
using System.Collections;

public class scr_toStart : MonoBehaviour
{
		
		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void OnTap ()
		{
				
				GameObject.Find ("GAMEMANAGER").SendMessage ("gameStart");
				Destroy (this.gameObject);
		}
}

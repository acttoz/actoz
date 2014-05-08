using UnityEngine;
using System.Collections;

public class scr_air : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void endEffect ()
		{
				GameObject.Find ("GAMEMANAGER").SendMessage ("getBalloonMSG", 4);
		}
}

﻿using UnityEngine;
using System.Collections;

public class scr_toStart : MonoBehaviour
{
		public GameObject manager;
		
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
				
				manager.SendMessage ("gameStart");
		}
}

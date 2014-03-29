using UnityEngine;
using System.Collections;

public class scr_startManager : MonoBehaviour
{
		public GameObject start, manager;
		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void gameOver ()
		{
				start.SetActive (true);
				manager.SetActive (false);
		}

		void gameStart ()
		{
				start.SetActive (false);
				manager.SetActive (true);
		}
}

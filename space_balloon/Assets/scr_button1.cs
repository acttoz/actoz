using UnityEngine;
using System.Collections;

public class scr_button1 : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
				this.gameObject.GetComponent<SpriteRenderer> ().color = Color.white;
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void OnTap ()
		{
				this.gameObject.GetComponent<SpriteRenderer> ().color = Color.yellow;
		}
}

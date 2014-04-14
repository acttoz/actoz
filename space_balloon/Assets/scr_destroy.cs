using UnityEngine;
using System.Collections;

public class scr_destroy : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void destroySelf ()
		{
				Destroy (this.gameObject);
		}
}

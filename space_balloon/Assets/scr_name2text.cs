using UnityEngine;
using System.Collections;

public class scr_name2text : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
				GetComponentInChildren<tk2dTextMesh> ().text = gameObject.transform.name;
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}
}

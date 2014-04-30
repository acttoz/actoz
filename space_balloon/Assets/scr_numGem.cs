using UnityEngine;
using System.Collections;

public class scr_numGem : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
				GetComponent<tk2dTextMesh> ().text = "" + PlayerPrefs.GetInt ("NUMGEM");
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}
}

using UnityEngine;
using System.Collections;

public class scr_lv : MonoBehaviour
{
		Animator anim;
		// Use this for initialization
		void Start ()
		{
				anim = GetComponent<Animator> ();
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void levelUp ()
		{
				StartCoroutine ("ani");
		
		}

		IEnumerator ani ()
		{
		anim.SetBool ("up", true);
		yield return new WaitForSeconds (0.5f);
		anim.SetBool ("up", false);
		}

}

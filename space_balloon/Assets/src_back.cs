using UnityEngine;
using System.Collections;

public class src_back : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void superMode (int num)
		{
				Animator anim = GetComponent<Animator> ();
				anim.SetInteger ("super", num);

		}
}

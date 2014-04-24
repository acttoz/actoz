using UnityEngine;
using System.Collections;

public class scr_bgm : MonoBehaviour
{

		// Use this for initialization
		void Start ()
		{
	
		}
	
		// Update is called once per frame
		void Update ()
		{
	
		}

		void playAfterLogo ()
		{
				audio.Play ();
		}

		void superMode (int num)
		{
		
		
				switch (num) {
			
				case 1:
						audio.pitch = 1;
						break;
				case 2:
						audio.pitch = 1.2f;
						break;
				case 3:
						audio.pitch = 1.5f;
						
						break;
				case 4:
						audio.pitch = 1.7f;
			
						break;
				case 5:
						audio.pitch = 2f;
			
						break;
			
				default:
						break;
			
			
				}
		
		
		}
}

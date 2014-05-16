using UnityEngine;
using System.Collections;

public class scr_back2 : MonoBehaviour
{
	float[] levels = new float[]{0,0.01f,0.03f,0.1f,0.2f,0.4f,0};
	// Use this for initialization
	void Start ()
	{
	}
	
	// Update is called once per frame
	void Update ()
	{
		transform.position -= new Vector3 (0, levels [scr_manager.superLevel], 0);
		if (transform.position.y < -10)
			Destroy (this.gameObject);
	}
	
	//		void changeSpeed ()
	//		{
	//				level = levels [scr_manager.superLevel];
	//		}
	
	
}

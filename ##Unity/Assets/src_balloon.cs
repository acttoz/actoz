using UnityEngine;
using System.Collections;

public class src_balloon : MonoBehaviour
{

	 // Use this for initialization
	 void Start ()
	 {
	
	 }
	
	 // Update is called once per frame
	 void Update ()
	 {
	
	 }

	 void success ()
	 {
			GameObject.Find ("GAMEMANAGER").SendMessage ("balloonSuccess");

			Destroy (this.gameObject);
	 }

	 void destroySelf ()
	 {
			Destroy (this.gameObject);
	 }
}

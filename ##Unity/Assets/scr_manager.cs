using UnityEngine;
using System.Collections;

public class scr_manager : MonoBehaviour
{
	 public GameObject dragObject;
	 // Use this for initialization
	 bool existBalloon = false;

	 void Start ()
	 {

	 }
	
	 // Update is called once per frame
	 void Update ()
	 {
	 }

	 void balloonSuccess ()
	 {
			existBalloon = false;
			Debug.Log ("balloonSuccess");
	 }

	 void balloonFail ()
	 {
			GameObject balloon = GameObject.FindGameObjectWithTag ("balloon");
		
			if (balloon != null) {
				 balloon.SendMessage ("destroySelf");
				 existBalloon = false;
			}
			Debug.Log ("balloonFail");
	 }

	 int dragFingerIndex = -1;

	 void OnDrag (DragGesture gesture)
	 {
			// first finger
			FingerGestures.Finger finger = gesture.Fingers [0];
			GameObject balloon = GameObject.FindGameObjectWithTag ("balloon");
		
			if (existBalloon) {
				 if (gesture.Phase == ContinuousGesturePhase.Started) {
						// dismiss this event if we're not interacting with our drag object
//				 if (gesture.Selection != dragObject)
//						return;
			
						Debug.Log ("Started dragging with finger " + finger);
			
						// remember which finger is dragging dragObject
						dragFingerIndex = finger.Index;

				 
			
						// spawn some particles because it's cool.
//				 SpawnParticles (dragObject);
				 } else if (finger.Index == dragFingerIndex) {  // gesture in progress, make sure that this event comes from the finger that is dragging our dragObject
						if (gesture.Phase == ContinuousGesturePhase.Updated) {
							 // update the position by converting the current screen position of the finger to a world position on the Z = 0 plane
							 balloon.transform.position = GetWorldPos (gesture.Position);
						} else {
							 Debug.Log ("Stopped dragging with finger " + finger);
				
							 // reset our drag finger index
							 dragFingerIndex = -1;
				
							 // spawn some particles because it's cool.
//						SpawnParticles (dragObject);
				
						}
				 }
			}
	 }

	 void OnFingerDown (FingerDownEvent e)
	 {
		 

			Instantiate (dragObject, GetWorldPos (e.Position), Quaternion.identity);
			existBalloon = true;
			Debug.Log ("click");
	 }

	 void OnFingerUp (FingerUpEvent e)
	 {
			GameObject balloon = GameObject.FindGameObjectWithTag ("balloon");

			if (balloon != null) {
				 balloon.SendMessage ("destroySelf");
				 existBalloon = false;
			}
			Debug.Log ("release");
	 }

	 public static Vector3 GetWorldPos (Vector2 screenPos)
	 {
			Ray ray = Camera.main.ScreenPointToRay (screenPos);
		
			// we solve for intersection with z = 0 plane
			float t = -ray.origin.z / ray.direction.z;
		
			return ray.GetPoint (t);
	 }


}

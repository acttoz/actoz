using UnityEngine;
using System.Collections;

public class scr_manager : MonoBehaviour
{
		public GameObject balloon;
		public GameObject back, startManager;
		public Vector3 backSize;
		public Vector3 balloonSize;
		public int superTime;
		int superTimer;
		public int normalTimer;
		int superLevel = 0;
		// Use this for initialization
		bool existBalloon = false;

		void Start ()
		{
				superTimer = superTime;
//				backSize = back.renderer.bounds.size; 
//				Debug.Log (backSize);	
		}
	
		// Update is called once per frame
		void Update ()
		{
		 
//				GameObject balloon = GameObject.FindGameObjectWithTag ("balloon");
//				balloonSize = balloon.renderer.bounds.size;
//				Debug.Log (balloonSize);
		}

		void normalModeCount ()
		{
				normalTimer--;
				
				if (normalTimer < 1) {
			
			
			
						CancelInvoke ("normalModeCount");
						
						superMode (superLevel);
			
			
				}
		
		}
	
		void superModeCount ()
		{
				superTimer--;
				GameObject.Find ("second").guiText.text = "" + superTimer;
//				Debug.Log ("superTimer" + superTimer);
				if (superTimer < 0) {
			
						
						
						CancelInvoke ("superModeCount");
						superLevel++;
						superMode (superLevel);
						
			
				}
		
		}

		void superMode (int num)
		{
				superTimer = superTime;
//				existBalloon = false;
				InvokeRepeating ("superModeCount", 0.1f, 1f);
				balloon.SendMessage ("superMode", num);
				Debug.Log ("super:" + num);
		}

		void balloonCreate (Vector3 touch)
		{
				balloon.SetActive (true);
				balloon.SendMessage ("create");
				InvokeRepeating ("normalModeCount", 0.1f, 1f);
				balloon.transform.position = touch;
		}

		void balloonRemove ()
		{
				superLevel = 0;
				if (existBalloon) {
					
						balloon.SetActive (false);
			
						existBalloon = false;
				}
		}

		void balloonCancle (int num)
		{
				CancelInvoke ("superModeCount");
				superLevel = 0;
				balloon.SendMessage ("cancel", num);
		}

		void gameOver ()
		{
				balloonRemove ();
				startManager.SendMessage ("gameOver");
		}

		int dragFingerIndex = -1;

		void OnDrag (DragGesture gesture)
		{
				// first finger
				FingerGestures.Finger finger = gesture.Fingers [0];
			 
		
				if (existBalloon) {
						if (gesture.Phase == ContinuousGesturePhase.Started) {
								// dismiss this event if we're not interacting with our drag object
//				 if (gesture.Selection != balloon)
//						return;
			
								Debug.Log ("Started dragging with finger " + finger);
			
								// remember which finger is dragging balloon
								dragFingerIndex = finger.Index;

				 
			
								// spawn some particles because it's cool.
//				 SpawnParticles (balloon);
						} else if (finger.Index == dragFingerIndex) {  // gesture in progress, make sure that this event comes from the finger that is dragging our balloon
								if (gesture.Phase == ContinuousGesturePhase.Updated) {
										// update the position by converting the current screen position of the finger to a world position on the Z = 0 plane
										Vector3 touchXY = GetWorldPos (gesture.Position);
					
										
										balloon.transform.position = touchXY;
						
									
//										float touchX = touchXY.x;
//										float touchY = touchXY.y;
//
//										if (touchX > -2 && touchX < 2 && touchY < 4.4 && touchY > -4.4)		
//										Debug.Log ("dragging" + touchXY);
								} else {
										Debug.Log ("Stopped dragging with finger " + finger);
				
										// reset our drag finger index
										dragFingerIndex = -1;
				
										// spawn some particles because it's cool.
//						SpawnParticles (balloon);
				
								}
						}
				}
		}

		void OnFingerDown (FingerDownEvent e)
		{
		 

//				Instantiate (balloon, GetWorldPos (e.Position), Quaternion.identity);
				if (!existBalloon) {
						balloonCreate (GetWorldPos (e.Position));
						existBalloon = true;
				}
				Debug.Log ("click");
		}

		void OnFingerUp (FingerUpEvent e)
		{
//				GameObject[] balloon = GameObject.FindGameObjectsWithTag ("balloon");
//
//				if (balloon.Length!=0) {
//						Debug.Log ("ballonnExist");
//						foreach (GameObject element in balloon) {
//								element.SendMessage ("destroySelf");
//						}
//						
//						existBalloon = false;
//				}
//				Debug.Log ("release");
				if (existBalloon)
						balloonCancle (1);		
//		balloonRemove ();
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

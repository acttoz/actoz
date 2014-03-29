using UnityEngine;
using System.Collections;

public class scr_manager : MonoBehaviour
{
		public GameObject balloon;
		public GameObject back, backStart, bgm;
		public GameObject effectSuper1, effectSuper2, effectPop;
		public Vector3 backSize;
		public Vector3 balloonSize;
		GameObject[] enemy;
		public int superTime;
		int superTimer;
		public AudioClip create, remove, pop, bing, levelUp;
		TextMesh scoreText;
		float score = 0;
		int superLevel = 0;
		// Use this for initialization
		bool existBalloon = false;

		void Start ()
		{
				enemy = GameObject.FindGameObjectsWithTag ("enemy");
				scoreText = GameObject.Find ("score").GetComponent<TextMesh> ();
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

		void Create (Vector3 touch)
		{
				balloon.GetComponent<SpriteRenderer> ().color = Color.red;
				balloon.SetActive (true);
				balloon.SendMessage ("create");
				balloon.transform.position = touch;
				superMode (0);
				audio.PlayOneShot (create);

		}

		IEnumerator Remove (int num)
		{
				CancelInvoke ("scoreCount");
		
				CancelInvoke ("superModeCount");
				switch (num) {
				case 1:
			
						balloon.SendMessage ("cancel", 1);
//						if (audio.isPlaying)
						audio.Stop ();
						audio.PlayOneShot (remove);
			
						break;
			
				case 2:
						balloon.SendMessage ("cancel", 2);
						score = 0;
						disableTouch ();
//						if (audio.isPlaying)
						audio.Stop ();
						audio.PlayOneShot (pop);
						Instantiate (effectPop, balloon.transform.position, Quaternion.identity);
						balloon.SetActive (false);
						
						break;
				}
		
		
				if (num == 1) {
						yield return new WaitForSeconds (0.2f);
				} else {
						yield return new WaitForSeconds (1f);
				}
				Debug.Log ("remove" + existBalloon);
				superLevel = 0;
				if (existBalloon) {
						Debug.Log ("remove" + existBalloon);
						balloon.transform.localScale = new Vector3 (0, 0, 0);
						if (num == 1)
								balloon.SetActive (false);
						int i = 0;

						// back & enemy reset
						back.SendMessage ("superMode", i);

						foreach (GameObject element in enemy) {
								element.SendMessage ("superMode", 1);
						}
						bgm.SendMessage ("superMode", 1);
			
						//---------------

						existBalloon = false;
			
						superTimer = superTime;
						superLevel = 0;
				}

				if (num == 2) {
						backStart.SetActive (true);
						score = 0;
						scoreText.text = "Score: " + score;
				}

				//				Debug.Log ("removeTimer");
				
		}

		void superModeCount ()
		{
				superTimer--;
				 
				//				Debug.Log ("superTimer" + superTimer);
				if (superTimer < 0) {
			
			
			
						CancelInvoke ("superModeCount");
						superLevel++;
						if (superLevel < 4)
								superMode (superLevel);
						
			
			
				}
		
		}
	
		void superMode (int num)
		{
		 
				superTimer = superTime;
				if (num == 0) {
						superTimer = 3;
						superLevel = 0;
				}
				InvokeRepeating ("superModeCount", 0.1f, 1f);

				balloon.SendMessage ("superMode", num);
				foreach (GameObject element in enemy) {
						element.SendMessage ("superMode", num);
				}
				bgm.SendMessage ("superMode", num);
				back.SendMessage ("superMode", num);
				Debug.Log ("super:" + num);

				//********************score
				CancelInvoke ("scoreCount");
				switch (num) {
			
				case 1:
						InvokeRepeating ("scoreCount", 0.1f, 0.7f);
						break;
				case 2:
						audio.PlayOneShot (levelUp);
						Instantiate (effectSuper1, new Vector2 (0, 0), Quaternion.identity);
						Instantiate (effectSuper2, new Vector2 (0, 0), Quaternion.identity);
						InvokeRepeating ("scoreCount", 0.1f, 0.3f);
						break;
				case 3:
						audio.PlayOneShot (levelUp);
						Instantiate (effectSuper1, new Vector2 (0, 0), Quaternion.identity);
						Instantiate (effectSuper2, new Vector2 (0, 0), Quaternion.identity);
						InvokeRepeating ("scoreCount", 0.1f, 0.1f);
						
						break;
			
				default:
						break;
			
			
				}
		}

		void scoreCount ()
		{
				score += 5;
				audio.PlayOneShot (bing);
			 
				
				scoreText.text = "Score: " + score;
				Debug.Log (score);
		}
		 
		void enableTouch ()
		{
				GetComponent<FingerDownDetector> ().enabled = true;
				GetComponent<FingerUpDetector> ().enabled = true;
				GetComponent<DragRecognizer> ().enabled = true;
		}

		void disableTouch ()
		{
				GetComponent<FingerDownDetector> ().enabled = false;
				GetComponent<FingerUpDetector> ().enabled = false;
				GetComponent<DragRecognizer> ().enabled = false;
		}

		void gameStart ()
		{
				backStart.SetActive (false);
				enableTouch ();
		}

		void getBalloonMSG (int num)
		{
				switch (num) {
			
				case 1:
						StartCoroutine (Remove (2));
						break;
				case 2:
						score += 10;
						break;
				case 3:
						score += 50;
						break;
			
				default:
						break;
			
			
				}
		}
		/************************ Control **********************/




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
			
//								Debug.Log ("Started dragging with finger " + finger);
			
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
//										Debug.Log ("Stopped dragging with finger " + finger);
				
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
						existBalloon = true;
						Create (GetWorldPos (e.Position));
				}
//				Debug.Log ("click");
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
				if (existBalloon) {
						StartCoroutine (Remove (1));			
		
				}
//		balloonRemove ();
//				Debug.Log ("release");
		}

		public static Vector3 GetWorldPos (Vector2 screenPos)
		{
				Ray ray = Camera.main.ScreenPointToRay (screenPos);
		
				// we solve for intersection with z = 0 plane
				float t = -ray.origin.z / ray.direction.z;
		
				return ray.GetPoint (t);
		}


}

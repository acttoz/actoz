using UnityEngine;
using System.Collections;

public class scr_manager : MonoBehaviour
{
		public GameObject balloon, itemBlue, itemOrange, itemPurple, monsterB, monsterO, monsterP, monsterEffect, super_back1, super_back2;
		public Sprite bStar, oStar, pStar, eStar;
		public float timer;
		Sprite tempStar;
		SpriteRenderer star1, star2, star3;
		GameObject existItem, createItem;
		public GameObject back, backStart, bgm, gauge, lv;
		public GameObject effectSuper1, effectSuper2, effectPop, effectPoint, effectPointBack;
		public Vector3 backSize;
		public Vector3 balloonSize;
		string colHave = "n", colCreate = "n";//b;o,p
		int numHave = 0;
		GameObject[] enemy, realEnemy;
		public int superTime;
		int superTimer;
		public AudioClip create, remove, pop, bing, levelUp, go, itemSound;
		tk2dTextMesh scoreText;
		tk2dTextMesh lvText;
		float score = 0;
		int superLevel = 0;
		float mUp, mDown, mLeft, mRight;
		// Use this for initialization
		bool existBalloon = false;
		bool timeStarted = true;
	
		void Start ()
		{
				star1 = GameObject.Find ("star1").GetComponent<SpriteRenderer> ();
				star2 = GameObject.Find ("star2").GetComponent<SpriteRenderer> ();
				star3 = GameObject.Find ("star3").GetComponent<SpriteRenderer> ();
				enemy = GameObject.FindGameObjectsWithTag ("enemy");
				realEnemy = GameObject.FindGameObjectsWithTag ("realenemy");
				scoreText = GameObject.Find ("score").GetComponent<tk2dTextMesh> ();
				lvText = GameObject.Find ("lv").GetComponent<tk2dTextMesh> ();
				superTimer = superTime;
				mUp = 5.5f;
				mDown = mUp * -1;
				mLeft = GameObject.Find ("lv").transform.position.x;
				mRight = mLeft * -1;
				Debug.Log ("screenSize=" + mUp + " " + mDown + " " + mLeft + " " + mRight + " ");
				InvokeRepeating ("itemCreate", 1f, 3f);
		
				//				backSize = back.renderer.bounds.size; 
				//				Debug.Log (backSize);	
		}
	
		// Update is called once per frame
		void Update ()
		{
		
//				if (timeStarted == true) {
//						timer -= Time.deltaTime;
//						if (timer < 0)
//								StartCoroutine ("timesUp");
//				}   
		
		}

//		IEnumerator timesUp ()
//		{
//
//		}
		/// <summary>
		/// Items the create.
		/// </summary>
		void itemCreate ()
		{
				int tempCol = Random.Range (1, 4);
				switch (tempCol) {
				case 1:
						colCreate = "b";
						createItem = itemBlue;
						tempStar = bStar;
						break;

				case 2:
						createItem = itemOrange;
						colCreate = "o";
						tempStar = oStar;
						break;

				case 3:
						createItem = itemPurple;
						colCreate = "p";
						tempStar = pStar;
						break;
				}

				if (existItem != null)
						Destroy (existItem);
				float tempX = (Random.Range (mLeft * 100, mRight * 100)) / 100;
//				float tempY = (Random.Range (mDown * 100, mUp * 100)) / 100;
				existItem = Instantiate (createItem, new Vector3 (tempX, 7, 0), Quaternion.identity) as GameObject;
		}

		void getItem ()
		{
				switch (numHave) {
				case 0:
						colHave = colCreate;
						star1.sprite = tempStar;
						StartCoroutine ("getAnim", GameObject.Find ("star1"));
						numHave++;
						audio.PlayOneShot (itemSound);
						
						break;
			
				case 1:
						if (colHave == colCreate) {
								numHave++;
								StartCoroutine ("getAnim", GameObject.Find ("star2"));
						
								audio.PlayOneShot (itemSound);
								star2.sprite = tempStar;
						} else {
								resetStar ();
								getItem ();
						}
						break;
			
				case 2:
			 
						if (colHave == colCreate) {
								numHave++;
								StartCoroutine ("getAnim", GameObject.Find ("star3"));
								
								star3.sprite = tempStar;
								StartCoroutine ("monster", colHave);
								StopCoroutine ("undead");
								StartCoroutine ("undead");
								//moster
								

						} else {
								resetStar ();
								getItem ();
						}
						break;
				}
				 

		}

		IEnumerator monster (string mColHave)
		{
				//Stop item Create
				//stop enemyTrigger
				//enemy alpha
				Debug.Log (mColHave);
				if (mColHave.Equals ("b"))
						Instantiate (monsterB, new Vector2 (0, 0), Quaternion.identity);
				if (mColHave.Equals ("o"))
						Instantiate (monsterO, new Vector2 (0, 0), Quaternion.identity);
				if (mColHave.Equals ("p"))
						Instantiate (monsterP, new Vector2 (0, 0), Quaternion.identity);
				Instantiate (monsterEffect, new Vector2 (0, 0), Quaternion.identity);
		
		
				 
			
		
		
				yield return new WaitForSeconds (3f);
				resetStar ();
		}

		IEnumerator  undead ()
		{
				CancelInvoke ("itemCreate");
				balloon.GetComponent<SphereCollider> ().enabled = false;
				foreach (GameObject element in realEnemy) {
						element.GetComponent<SpriteRenderer> ().color = new Color (1, 1, 1, 0.5f);
				}
								

				yield return new WaitForSeconds (4f);

				foreach (GameObject element in realEnemy) {
						element.GetComponent<SpriteRenderer> ().color = new Color (1, 1, 1, 1f);
				}
				InvokeRepeating ("itemCreate", 0.1f, 3f);
				balloon.GetComponent<SphereCollider> ().enabled = true;
		}

		IEnumerator getAnim (GameObject star)
		{
				star.GetComponent<Animator> ().SetInteger ("item", 1);
				yield return new WaitForSeconds (0.5f);
				star.GetComponent<Animator> ().SetInteger ("item", 0);
				
		}

		void resetStar ()
		{
				star1.sprite = eStar;
				star2.sprite = eStar;
				star3.sprite = eStar;
				numHave = 0;
		}

		/// <summary>
		/// Create & Remove	/// </summary>
		/// <param name="touch">Touch.</param>

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
				CancelInvoke ("normalModeCount");
				switch (num) {
				case 1:
			
						balloon.SendMessage ("cancel", 1);
			//						if (audio.isPlaying)
						audio.Stop ();
						audio.PlayOneShot (remove);
			
						break;
			
				case 2:
						audio.Stop ();
						audio.PlayOneShot (pop);
						balloon.SendMessage ("cancel", 2);
						score = 0;
						disableTouch ();
			//						if (audio.isPlaying)
						
						GameObject ep = (GameObject)GameObject.Instantiate (effectPop);
						ep.transform.position = balloon.transform.position;
//			ep.renderer.sortingLayerName = "ui";
			
						balloon.SetActive (false);
			
						break;
				}
		
		
				if (num == 1) {
						yield return new WaitForSeconds (0.2f);
				} else {
						yield return new WaitForSeconds (1f);
						resetStar ();
				}
				Debug.Log ("remove" + existBalloon);
				superLevel = 0;
				if (existBalloon) {
						Debug.Log ("remove" + existBalloon);
						balloon.transform.localScale = new Vector3 (0, 0, 0);
						
						lvText.text = "L\tv.1";
						gauge.transform.localScale = new Vector3 (1.75f, 0.3f, 1);
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
		/// <summary>
		/// Supers the mode count.
		/// </summary>
		/// 
		void superModeCount ()
		{
				if (gauge.transform.localScale.y > 1.75f) {
						CancelInvoke ("superModeCount");
						superLevel++;
						if (superLevel < 4) {
								gauge.transform.localScale = new Vector3 (1.75f, 0.3f, 1);
								Debug.Log ("supermode(" + superLevel);
								superMode (superLevel);
						}
				} else {

						gauge.transform.localScale += new Vector3 (0, superTimer / 10000f, 0);
				}
		
		}
	
		void normalModeCount ()
		{
				superTimer--;
		
				Debug.Log ("superTimer" + superTimer);
				if (superTimer < 0) {
							
							
						Debug.Log ("normalCancel");
						CancelInvoke ("normalModeCount");
						superLevel++;
						if (superLevel < 4)
								superMode (superLevel);
							
		
		
		
				}
		
		
		}
	
		void superMode (int num)
		{
		
				if (num == 0) {
						superTimer = 3;
						superLevel = 0;
						InvokeRepeating ("normalModeCount", 0.1f, 1f);
				} else {
						superTimer = superTime;
						InvokeRepeating ("superModeCount", 0.1f, 0.1f);
				}
		
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
						StopCoroutine ("undead");
						StartCoroutine ("undead");
						lv.SendMessage ("levelUp");
						lvText.text = "Lv.2";
						audio.PlayOneShot (levelUp);
						GameObject ps = (GameObject)GameObject.Instantiate (effectSuper1);
						ps.transform.position = new Vector2 (0, 0);
						GameObject ps2 = (GameObject)GameObject.Instantiate (effectSuper2);
						ps2.transform.position = new Vector2 (0, 0);
						Instantiate (super_back1, new Vector2 (0, 0), Quaternion.identity);
						InvokeRepeating ("scoreCount", 0.1f, 0.3f);
						break;
				case 3:
						StopCoroutine ("undead");
						StartCoroutine ("undead");
						lv.SendMessage ("levelUp");
						lvText.text = "Lv.3";
						audio.PlayOneShot (levelUp);
						GameObject ps3 = (GameObject)GameObject.Instantiate (effectSuper1);
						ps3.transform.position = new Vector2 (0, 0);
						ps3.renderer.sortingLayerName = "ui";
						GameObject ps4 = (GameObject)GameObject.Instantiate (effectSuper2);
						ps4.transform.position = new Vector2 (0, 0);
						ps4.renderer.sortingLayerName = "ui";
						InvokeRepeating ("scoreCount", 0.1f, 0.1f);
						Instantiate (super_back2, new Vector2 (0, 0), Quaternion.identity);
						break;
			
				default:
						break;
			
			
				}
		}
	
		void scoreCount ()
		{
				score += 5;
				audio.PlayOneShot (bing);
				Instantiate (effectPoint, balloon.transform.position, Quaternion.identity);
				Instantiate (effectPointBack, balloon.transform.position, Quaternion.identity);
				scoreText.text = "Score: " + score;
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
				audio.PlayOneShot (go);
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
		
				//				Instantiate (effectSuper1, new Vector2 (0, 0), Quaternion.identity);
				//				Instantiate (effectSuper2, new Vector2 (0, 0), Quaternion.identity);
				//				effectSuper1.renderer.sortingLayerName = "Foreground";
		
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

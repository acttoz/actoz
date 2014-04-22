using UnityEngine;
using System.Collections;

public class scr_manager : MonoBehaviour
{
		public GameObject btnRestart, balloon, itemBlue, itemOrange, itemPurple, monsterB, monsterO, monsterP, monsterEffect, super_back1, super_back2;
		public Sprite bStar, oStar, pStar, eStar;
		public GameObject itemEffectO, itemEffectB, effectPoint2, itemEffectP, itemEffectBack, walls, btn_pause, prf_pause, btn_resume;
		float timer;
		public float gameTime;
		public int leftTime;
		bool onPlay;
		int min, sec, countScore = 0, countGem = 0;
		Sprite tempStar;
		SpriteRenderer star1, star2, star3;
		GameObject existItem, createItem, btn_menu, btn_replay;
		public GameObject back, backStart, bgm, gauge, lv, oTimeUp;
		public GameObject effectSuper1, effectSuper2, effectPop, effectPoint, effectPointBack;
		public Vector3 backSize;
		public Vector3 balloonSize;
		string colHave1 = "n", colHave2 = "n", colHave3 = "n", colCreate = "n";//b;o,p
		int numHave = 0;
		GameObject[] enemy, realEnemy;
		public int superTime;
		int superTimer;
		public AudioClip create, remove, pop, bing, levelUp, go, itemSound, timesup;
		tk2dTextMesh scoreText;
		tk2dTextMesh lvText;
		tk2dTextMesh timeText, resultText, gemText;
		int score = 0;
		int gem = 0;
		int superLevel = 0;
		float mUp, mDown, mLeft, mRight;
		// Use this for initialization
		bool existBalloon = false;
		bool timeStarted = false;
		bool isScoreUp = false;

		void Start ()
		{
				backStart.SetActive (true);
				timer = gameTime+1;
				star1 = GameObject.Find ("star1").GetComponent<SpriteRenderer> ();
				star2 = GameObject.Find ("star2").GetComponent<SpriteRenderer> ();
				star3 = GameObject.Find ("star3").GetComponent<SpriteRenderer> ();
				enemy = GameObject.FindGameObjectsWithTag ("enemy");
				realEnemy = GameObject.FindGameObjectsWithTag ("realenemy");
				scoreText = GameObject.Find ("score").GetComponent<tk2dTextMesh> ();
				lvText = GameObject.Find ("lv").GetComponent<tk2dTextMesh> ();
				timeText = GameObject.Find ("time").GetComponent<tk2dTextMesh> ();
				superTimer = superTime;
				mUp = 5.5f;
				mDown = mUp * -1;
				mLeft = GameObject.Find ("lv").transform.position.x;
				mRight = mLeft * -1;
				Debug.Log ("screenSize=" + mUp + " " + mDown + " " + mLeft + " " + mRight + " ");
				
		
				//				backSize = back.renderer.bounds.size; 
				//				Debug.Log (backSize);	

				//test
//				btn_menu = GameObject.Find ("btn_menu");
//				btn_replay = GameObject.Find ("btn_replay");
		}
	
		// Update is called once per frame
		void Update ()
		{
		
				if (timeStarted == true) {
						timer -= Time.deltaTime;
						leftTime = (int)timer;
						min = Mathf.FloorToInt (timer / 60F);
						sec = Mathf.FloorToInt (timer - min * 60);
						if (sec < 10) {
								timeText.text = min + " : 0" + sec;
						} else {
								timeText.text = min + " : " + sec;
						}

						
						if (leftTime == 0) {
								timeStarted = false;
				
								StartCoroutine ("timesUp");
						}
				}   
		
		}

		void gameStart ()
		{
				audio.PlayOneShot (go);
				backStart.SetActive (false);
				enableTouch ();
				timeStarted = true;
				onPlay = true;
				InvokeRepeating ("itemCreate", 1f, 2.3f);
				int i = 0;
				back.SendMessage ("superMode", i);
				foreach (GameObject element in enemy) {
						float tempX = (Random.Range (mLeft * 100, mRight * 100)) / 100;
						float tempY = (Random.Range (mDown * 100, mUp * 100)) / 100;

						element.transform.position = new Vector2 (tempX, tempY);
				}
		}

		void gameReset ()
		{
				CancelInvoke ("itemCreate");
				gauge.transform.localScale = new Vector3 (1.75f, 0.3f, 1);
				backStart.SetActive (true);
				onPlay = false;
				score = 0;
				scoreText.text = "Score: " + score;
				walls.SetActive (true);
				timeStarted = false;
				disableTouch ();
				resetStar ();
				timer = gameTime;
				///level reset
				lvText.text = "Lv.1";
				superTimer = superTime;
				superLevel = 0;
				bgm.SendMessage ("superMode", 1);
		
				// back & enemy reset
				balloon.transform.localScale = new Vector3 (0, 0, 0);
				existBalloon = false;
				foreach (GameObject element in enemy) {
						element.SendMessage ("superMode", 1);
				}
				foreach (GameObject element in realEnemy) {
						element.GetComponent<SpriteRenderer> ().color = new Color (1, 1, 1, 1f);
				}
				balloon.GetComponent<SphereCollider> ().enabled = true;

		}

		IEnumerator pauseGame ()
		{
				onPlay = false;
				Instantiate (prf_pause, new Vector2 (0, 0), Quaternion.identity);
				
				btn_menu = GameObject.Find ("btn_menu");
				btn_replay = GameObject.Find ("btn_replay");
				btn_resume = GameObject.Find ("btn_resume");
				disableTouch ();
				yield return new WaitForSeconds (0.5f);
				enableTouch ();
				Time.timeScale = 0;

		}

		IEnumerator timesUp ()
		{
				onPlay = false;
				StopCoroutine ("undead");
				CancelInvoke ("itemCreate");
				CancelInvoke ("scoreCount");
				back.SendMessage ("superMode", 2);
				CancelInvoke ("superModeCount");
				CancelInvoke ("normalModeCount");
				balloon.SetActive (false);
				walls.SetActive (false);
				disableTouch ();
				audio.PlayOneShot (timesup);
				Instantiate (oTimeUp, new Vector2 (0, 0), Quaternion.identity);

				yield return new WaitForSeconds (1f);
				countGem = 0;
				countScore = 0;
				resultText = GameObject.Find ("numscore").GetComponent<tk2dTextMesh> ();
				gemText = GameObject.Find ("numgem").GetComponent<tk2dTextMesh> ();
				btn_menu = GameObject.Find ("btn_menu");
				btn_replay = GameObject.Find ("btn_replay");
				InvokeRepeating ("resultCount", 0f, 0.01f);
		
		}

		void resultCount ()
		{
				if (score < countScore) {
						audio.PlayOneShot (itemSound);
			
						CancelInvoke ("resultCount");

						resultText.text = "" + score;
						if (score > 1000) {
								gem = score / 1000;
								InvokeRepeating ("resultGemCount", 0.5f, 1f);
						} else {
								enableTouch ();
						}
				} else {
						audio.PlayOneShot (bing);
						resultText.text = "" + countScore;
						countScore += 10;
				}
				

		}

		void resultGemCount ()
		{
				audio.PlayOneShot (itemSound);
				countGem++;
				gemText.text = "" + countGem;
				resultText.text = "" + (score -= 1000);
				
				if (gem == countGem) {

						CancelInvoke ("resultGemCount");
			
						enableTouch ();
				}  
		
		
		}
		/// <summary>
		/// Items the create.
		/// </summary>
		void itemCreate ()
		{
				float tempX = (Random.Range (mLeft * 100, mRight * 100)) / 100;
				if (existItem != null)
						Destroy (existItem);
				Debug.Log ("itemCreate");
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
				
				
//				float tempY = (Random.Range (mDown * 100, mUp * 100)) / 100;
				existItem = Instantiate (createItem, new Vector3 (tempX, 7, 0), Quaternion.identity) as GameObject;
		}

		void getItem ()
		{
				switch (numHave) {
				case 0:
						colHave1 = colCreate;
						star1.sprite = tempStar;
						StartCoroutine ("getAnim", GameObject.Find ("star1"));
						numHave++;
						audio.PlayOneShot (itemSound);
//						StartCoroutine ("monster", colHave1);
//						StopCoroutine ("undead");
//						StartCoroutine ("undead");
						break;
			
				case 1:
						colHave2 = colCreate;
						numHave++;
						StartCoroutine ("getAnim", GameObject.Find ("star2"));
						
						audio.PlayOneShot (itemSound);
						star2.sprite = tempStar;
						break;
			
				case 2:
						audio.PlayOneShot (itemSound);
						if (colHave1.Equals (colHave2) && colHave2.Equals (colCreate)) {
								//monster
								StartCoroutine ("getAnim", GameObject.Find ("star1"));
								StartCoroutine ("getAnim", GameObject.Find ("star2"));
								StartCoroutine ("getAnim", GameObject.Find ("star3"));
								
								star3.sprite = tempStar;
								StartCoroutine ("monster", colCreate);
								StopCoroutine ("undead");
								StartCoroutine ("undead");
								//moster
								

						} else {
								//score
								star3.sprite = tempStar;
								StartCoroutine ("getAnim", GameObject.Find ("star3"));
								StartCoroutine ("notMonster");
								
						}
						break;
				}
				 

		}
	
		IEnumerator getAnim (GameObject star)
		{
				star.GetComponent<Animator> ().SetInteger ("item", 1);
				yield return new WaitForSeconds (0.5f);
				star.GetComponent<Animator> ().SetInteger ("item", 0);
		}

		IEnumerator notMonster ()
		{

				yield return new WaitForSeconds (1f);
				audio.PlayOneShot (levelUp);
				resetStar ();
				itemEffectO.animation.Play ();
				Instantiate (itemEffectBack, itemEffectO.transform.position, Quaternion.identity);
				yield return new WaitForSeconds (0.2f);
				int i = 500;
				if (isScoreUp) 
						i = 1000;
				while (i>0) {
						yield return new WaitForSeconds (0.04f);
						i -= 10;
						score += 10;
						scoreText.text = "Score: " + score;
						audio.PlayOneShot (bing);
						
				}
		}

		void itemUse (string col)
		{
				if (col.Equals ("b")) {
						timer += 20;
						audio.PlayOneShot (levelUp);
						itemEffectB.animation.Play ();
						Instantiate (itemEffectBack, itemEffectB.transform.position, Quaternion.identity);
				}
				if (col.Equals ("o")) {
						audio.PlayOneShot (levelUp);
						itemEffectO.animation.Play ();
						StopCoroutine ("scoreUp");
						StartCoroutine ("scoreUp");
						Instantiate (itemEffectBack, itemEffectO.transform.position, Quaternion.identity);
				}
				if (col.Equals ("p")) {
						audio.PlayOneShot (levelUp);
						itemEffectP.animation.Play ();
						Instantiate (itemEffectBack, itemEffectP.transform.position, Quaternion.identity);
						if (gauge.transform.localScale.y > 1.3) {
								float temp = 1.74f - gauge.transform.localScale.y;
								gauge.transform.localScale += new Vector3 (0, temp, 0);
						} else {
								gauge.transform.localScale += new Vector3 (0, 0.5f, 0);
						}
						
				}
		}

		IEnumerator scoreUp ()
		{
				isScoreUp = true;
				itemEffectO.animation.wrapMode = WrapMode.Loop;
				itemEffectO.animation.Play ();

				GameObject.Find ("score").GetComponent<tk2dTextMesh> ().color = new Color (1, 0.5f, 0);

				yield return new WaitForSeconds (5f);
				itemEffectO.animation.wrapMode = WrapMode.Once;
				GameObject.Find ("score").GetComponent<tk2dTextMesh> ().color = new Color (1, 1, 1);
				itemEffectO.animation.Stop ();
				isScoreUp = false;
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
				CancelInvoke ("decreaseEnergy");
				balloon.transform.position = touch;
				if (superLevel < 2) {
						superMode (superLevel);
				} else {
						superTimer = superTime;
						InvokeRepeating ("superModeCount", 0.1f, 0.1f);
						if (superLevel == 2)
								InvokeRepeating ("scoreCount", 0.1f, 0.3f);
						if (superLevel == 3)
								InvokeRepeating ("scoreCount", 0.1f, 0.1f);
				}
				balloon.SendMessage ("create", superLevel);
				Debug.Log ("create Level" + superLevel);

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
			//decrease energy
						InvokeRepeating ("decreaseEnergy", 0.2f, 0.2f);
			
						break;
			
				case 2:
						audio.Stop ();
						audio.PlayOneShot (pop);
						balloon.SendMessage ("cancel", 2);
			///level reset
						lvText.text = "Lv.1";
						gauge.transform.localScale = new Vector3 (1.75f, 0.3f, 1);
						superTimer = superTime;
						superLevel = 0;
						bgm.SendMessage ("superMode", 1);

			// back & enemy reset
			
						foreach (GameObject element in enemy) {
								element.SendMessage ("superMode", 1);
						}
						
						
						GameObject ep = (GameObject)GameObject.Instantiate (effectPop);
						ep.transform.position = balloon.transform.position;
//			ep.renderer.sortingLayerName = "ui";
			
						balloon.SetActive (false);
			
						break;
				}
		
		
				yield return new WaitForSeconds (0.2f);
				if (existBalloon) {
						balloon.transform.localScale = new Vector3 (0, 0, 0);
						
						

//						if (num == 1)
						balloon.SetActive (false);
						
			
						
					
//						if (num == 2) {
//								backStart.SetActive (true);
//								score = 0;
//								scoreText.text = "Score: " + score;
//						}
						//---------------
			
						existBalloon = false;
			
						
				}
		
				
		
				//				Debug.Log ("removeTimer");
		
		}

		void decreaseEnergy ()
		{
				if (gauge.transform.localScale.y > 0.3f) {
						gauge.transform.localScale -= new Vector3 (0, superTimer / 3000f, 0);
			
				}  
		}
		/// <summary>
		/// Supers the mode count.
		/// </summary>
		/// 
		void superModeCount ()
		{
				
				if (gauge.transform.localScale.y > 1.75f) {
						CancelInvoke ("superModeCount");
						if (superLevel < 3) {
								superLevel++;
								gauge.transform.localScale = new Vector3 (1.75f, 0.3f, 1);
								Debug.Log ("supermode(" + superLevel);
								superMode (superLevel);
						}
				} else {
						gauge.transform.localScale += new Vector3 (0, superTimer / 5000f, 0);
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
						InvokeRepeating ("superModeCount", 0.1f, 0.2f);
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
				if (isScoreUp) {
						Instantiate (effectPoint2, balloon.transform.position, Quaternion.identity);
						score += 10;
						scoreText.text = "Score: " + score + " x2";
				} else {
						Instantiate (effectPoint, balloon.transform.position, Quaternion.identity);
						score += 5;
						scoreText.text = "Score: " + score;
				}
				audio.PlayOneShot (bing);
				Instantiate (effectPointBack, balloon.transform.position, Quaternion.identity);
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
								// remember which finger is dragging balloon
								dragFingerIndex = finger.Index;
				
								// spawn some particles because it's cool.
								//				 SpawnParticles (balloon);
						} else if (finger.Index == dragFingerIndex) {  // gesture in progress, make sure that this event comes from the finger that is dragging our balloon
								if (gesture.Phase == ContinuousGesturePhase.Updated) {
										// update the position by converting the current screen position of the finger to a world position on the Z = 0 plane
										Vector3 touchXY = GetWorldPos (gesture.Position);
										balloon.transform.position = touchXY;
								} else {
										// reset our drag finger index
										dragFingerIndex = -1;
								}
						}
				}
		}
	
		void OnFingerDown (FingerDownEvent e)
		{
		
				 
				if (e.Selection == btn_menu) {
						btn_menu.GetComponent<SpriteRenderer> ().color = Color.yellow;
				}
				if (e.Selection == btn_replay) {
						btn_replay.GetComponent<SpriteRenderer> ().color = Color.yellow;
//												Application.LoadLevel (0);
				}
				if (!existBalloon && onPlay && e.Selection != btn_pause) {
						existBalloon = true;
						Create (GetWorldPos (e.Position));
				}

				if (e.Selection == btn_pause && onPlay) {
						btn_pause.GetComponent<SpriteRenderer> ().color = Color.yellow;
						//												Application.LoadLevel (0);
				}

				if (e.Selection == btn_resume) {
						btn_resume.GetComponent<SpriteRenderer> ().color = Color.yellow;

						//												Application.LoadLevel (0);
				}
				

				//				Debug.Log ("click");
		}
	 
		void OnFingerUp (FingerUpEvent e)
		{
				if (e.Selection == btn_menu) {
						btn_menu.GetComponent<SpriteRenderer> ().color = Color.white;
						Time.timeScale = 1.0f;
//						gameReset ();
						Application.LoadLevel (1);
				}
				if (e.Selection == btn_resume) {
						btn_resume.GetComponent<SpriteRenderer> ().color = Color.white;
						Destroy (GameObject.Find ("prf_pause(Clone)"));
						onPlay = true;
						Time.timeScale = 1.0f;
				}
				if (e.Selection == btn_replay) {
						btn_replay.GetComponent<SpriteRenderer> ().color = Color.white;
						Destroy (GameObject.Find ("prf_timesup(Clone)"));
						Destroy (GameObject.Find ("prf_pause(Clone)"));
						Time.timeScale = 1.0f;
						gameReset ();
						//												Application.LoadLevel (0);
				}
				if (e.Selection == btn_pause && onPlay) {
						btn_pause.GetComponent<SpriteRenderer> ().color = Color.white;
						StartCoroutine (pauseGame ());

						//												Application.LoadLevel (0);
				}
				if (existBalloon && onPlay) {
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

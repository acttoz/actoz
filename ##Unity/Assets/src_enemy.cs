﻿using UnityEngine;
using System.Collections;

public class src_enemy : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	}
 

	void FixedUpdate() {
		rigidbody.AddForce(0, -100, 0);
	}
}
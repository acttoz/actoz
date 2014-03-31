var ballSpeed : float = 100;
//var score : float = 0;
function Awake() 
{
	rigidbody.AddForce(0, 0, -ballSpeed);
}

function OnCollisionEnter(hit : Collision)
{
	if(hit.gameObject.tag == "Player")
	{
		rigidbody.AddForce(0, -ballSpeed, 0);
	}
	
	if(hit.gameObject.tag == "block")
	{
		audio.Play();
	}
	
	if(hit.gameObject.tag == "gameover")
	{
		Destroy(gameObject);
	}
}
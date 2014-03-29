using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample level completed window.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowCompleted")]
public class tk2dUIExtDemoWindowCompleted : tk2dUIExtNavigationElement
{
    /// <summary>
    /// Visualizes the player's performance with stars.
    /// </summary>
    public tk2dUIExtIndicator indicatorStars;

    /// <summary>
    /// Visualizes the player's currently achieved score.
    /// </summary>
    public tk2dTextMesh labelScore;

    private int maxScore = 1325;
    private int score = 0;
    private bool isScoreCharging = true;
    private float nextStarTime;
    private float starInterval = 0.5f;

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {
        // Initialize variables
        maxScore = 1325;
        score = 0;
        isScoreCharging = true;
        starInterval = 0.5f;
    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }

    /// <summary>
    /// Animates the level completed window.
    /// </summary>
    void Update()
    {
        float currentTime = Time.time;

        // Animate score
        if (isScoreCharging)
        {
            // Increase score
            score = Mathf.Clamp(score + 10, 0, maxScore);
            labelScore.text = score.ToString();

            // Stop charging
            if (score >= 1325)
            {
                isScoreCharging = false;
                nextStarTime = currentTime + starInterval;
            }
        }
        else if (nextStarTime <= currentTime && indicatorStars.currentLevel < indicatorStars.maxLevels)
        {
            // Show stars
            indicatorStars.ChangeLevel(indicatorStars.currentLevel + 1);
            nextStarTime = currentTime + starInterval;
        }
        
    }

    /// <summary>
    /// Loads the menu.
    /// </summary>
    public void OnMenuClick()
    {
        Application.LoadLevel("DemoMenu");
    }

    /// <summary>
    /// Restarts the game.
    /// </summary>
    public void OnRestartClick()
    {
        Application.LoadLevel("DemoGame");
    }

    /// <summary>
    /// Loads the next level.
    /// </summary>
    public void OnNextClick()
    {
        Application.LoadLevel("DemoGame");
    }

    /// <summary>
    /// Shares level results on Facebook
    /// </summary>
    public void OnShareClick()
    {
        Debug.Log("Shared");
    }
}

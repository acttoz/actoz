using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample missions window.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowMissions")]
public class tk2dUIExtDemoWindowMissions : tk2dUIExtNavigationElement
{
    /// <summary>
    /// List of missions.
    /// </summary>
    public tk2dUIExtDemoMission[] missions;

    /// <summary>
    /// Visualizes the progress of the missions.
    /// </summary>
    public tk2dUIProgressBar progressBar;

    /// <summary>
    /// Skips the current mission.
    /// </summary>
    public tk2dUIItem buttonSkip;

    /// <summary>
    /// Index of the current mission.
    /// </summary>
    public int currentMission;

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {
        progressBar.Value = (float)currentMission / (float)missions.Length;
    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }

    /// <summary>
    /// Subscribe to the ui events.
    /// </summary>
    void OnEnable()
    {
        buttonSkip.OnClick += buttonSkip_OnClick;   
    }

    /// <summary>
    /// Unsubscribe from the ui events.
    /// </summary>
    void OnDisable()
    {
        buttonSkip.OnClick -= buttonSkip_OnClick;  
    }

    /// <summary>
    /// Handles skip button click.
    /// </summary>
    void buttonSkip_OnClick()
    {
        SkipMission();
    }

    /// <summary>
    /// Skips the current mission.
    /// </summary>
    private void SkipMission()
    {
        if (HasMissions())
        {
            // Skip mission
            missions[currentMission++].SetCompleted(true);

            // Update UI state
            buttonSkip.gameObject.SetActive(HasMissions());
            progressBar.Value = (float)currentMission / (float)missions.Length;
        }
    }

    /// <summary>
    /// Indicates whether there are any available missons or not.
    /// </summary>
    /// <returns>The result</returns>
    private bool HasMissions()
    {
        return currentMission < missions.Length;
    }
}

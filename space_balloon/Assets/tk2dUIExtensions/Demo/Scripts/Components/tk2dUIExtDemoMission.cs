using UnityEngine;
using System.Collections;

/// <summary>
/// Control that manages the state of a mission and visualizes relevant informations.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Components/tk2dUIExtDemoMission")]
public class tk2dUIExtDemoMission : MonoBehaviour
{
    /// <summary>
    /// Base sprite of the mission.
    /// </summary>
    public tk2dSprite spriteBase;

    /// <summary>
    /// Number sprite of the mission.
    /// </summary>
    public tk2dSprite spriteNumber;

    /// <summary>
    /// Number of the mission.
    /// </summary>
    public GameObject number;

    /// <summary>
    /// Indicates whether the mission is completed or not.
    /// </summary>
    public bool isCompleted = false;

    /// <summary>
    /// Name of the background sprite that is shown if the mission is completed.
    /// </summary>
    public string completedBaseSpriteName = "PanelListActive";

    /// <summary>
    /// Name of the background sprite that is shown if the mission is locked.
    /// </summary>
    public string lockedBaseSpriteName = "PanelListInactive";

    /// <summary>
    /// Name of the number sprite that is shown if the mission is completed.
    /// </summary>
    public string completedNumberSpriteName = "ButtonAccept";

    /// <summary>
    /// Name of the number sprite that is shown if the mission is locked.
    /// </summary>
    public string lockedNumberSpriteName = "ListNumber";

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        UpdateUI();
    }

    /// <summary>
    /// Sets the state of the mission.
    /// </summary>
    /// <param name="completed">New state</param>
    public void SetCompleted(bool completed)
    {
        isCompleted = completed;
        UpdateUI();
    }

    /// <summary>
    /// Updates the UI based on the mission's state
    /// </summary>
    private void UpdateUI()
    {
        spriteBase.SetSprite(isCompleted ? completedBaseSpriteName : lockedBaseSpriteName);
        spriteNumber.SetSprite(isCompleted ? completedNumberSpriteName : lockedNumberSpriteName);
        number.SetActive(!isCompleted);
    }
}

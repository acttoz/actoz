using UnityEngine;
using System.Collections;

/// <summary>
/// Control that manages the state of a level and visualizes relevant informations.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Components/tk2dUIExtDemoLevel")]
public class tk2dUIExtDemoLevel : MonoBehaviour
{
    /// <summary>
    /// Number of the level.
    /// </summary>
    public int number = 1;

    /// <summary>
    /// Number of stars achieved on this level.
    /// </summary>
    public int stars = 0;

    /// <summary>
    /// The state of the level.
    /// </summary>
    public bool locked = true;

    /// <summary>
    /// Lock icon.
    /// </summary>
    public GameObject spriteLock;

    /// <summary>
    /// Sprite of the level panel.
    /// </summary>
    public tk2dSprite spritePanel;

    /// <summary>
    /// Name of the sprite that represents the unlocked state of the panel.
    /// </summary>
    public string unlockedPanelSpriteName;

    /// <summary>
    /// Name of the sprite that represents the locked state of the panel.
    /// </summary>
    public string lockedPanelSpriteName;

    /// <summary>
    /// Name of the sprite that represents the locked state of the indicators.
    /// </summary>
    public string lockedIndicatorSpriteName;

    private tk2dTextMesh levelText;
    private tk2dUIExtIndicator levelLock;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        levelText = GetComponentInChildren<tk2dTextMesh>();
        levelLock = GetComponentInChildren<tk2dUIExtIndicator>();  
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        levelText.text = number.ToString();
        SetState(locked);
    }

    /// <summary>
    /// Changes the state of the module.
    /// </summary>
    /// <param name="locked">State of the module</param>
    public void SetState(bool locked)
    {
        this.locked = locked;
        spriteLock.SetActive(this.locked);
        spritePanel.SetSprite(this.locked ? lockedPanelSpriteName : unlockedPanelSpriteName);
        if (this.locked)
        {
            levelLock.Deactivate(lockedIndicatorSpriteName);
        }
        else
        {
            SetStars(stars);
        }      
    }

    /// <summary>
    /// Changes the number of stars on the indicator.
    /// </summary>
    /// <param name="stars">Number of stars</param>
    public void SetStars(int stars)
    {
        this.stars = stars;
        levelLock.ChangeLevel(this.stars);
    }
}

using UnityEngine;
using System.Collections;
using System;

/// <summary>
/// Control that manages the state of an upgrade and visualizes relevant informations.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Components/tk2dUIExtDemoUpgrade")]
public class tk2dUIExtDemoUpgrade : MonoBehaviour
{
    /// <summary>
    /// Cost of the upgrade on each level.
    /// </summary>
    public int[] costs;

    /// <summary>
    /// Effect of the upgrade on each level.
    /// </summary>
    public int[] effects;

    /// <summary>
    /// Current level of the upgrade.
    /// </summary>
    public int level;

    /// <summary>
    /// Description of the upgrade. Use {0} where you want the effect of the upgrade to appear.
    /// </summary>
    public string descriptionText;

    /// <summary>
    /// Button that handles upgrade purchase.
    /// </summary>
    public tk2dUIItem buttonPurchase;

    /// <summary>
    /// Visualizes the upgrades current level.
    /// </summary>
    public tk2dUIExtIndicator levelIndicator;

    /// <summary>
    /// Visualizes the upgrade's description.
    /// </summary>
    public tk2dTextMesh labelDescription;

    /// <summary>
    /// Upgrade maxed label.
    /// </summary>
    public GameObject labelMaxed;

    private tk2dUIExtStatusBar labelCost;

    /// <summary>
    /// Setup references
    /// </summary>
    void Awake()
    {
        labelCost = buttonPurchase.GetComponent<tk2dUIExtStatusBar>();
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        UpdateLevel();
    }

    /// <summary>
    /// Subscribe to the ui events.
    /// </summary>
    void OnEnable()
    {
        buttonPurchase.OnClick += buttonPurchase_OnClick;
    }

    /// <summary>
    /// Unsubscribe from the ui events.
    /// </summary>
    void OnDisable()
    {
        buttonPurchase.OnClick -= buttonPurchase_OnClick;
    }

    /// <summary>
    /// Handles upgrade purchase.
    /// </summary>
    void buttonPurchase_OnClick()
    {
        if (level < levelIndicator.maxLevels)
        {
            level++;
            UpdateLevel();
        } 
    }

    /// <summary>
    /// Updates the UI.
    /// </summary>
    private void UpdateLevel()
    {
        levelIndicator.ChangeLevel(level);
        labelDescription.text = String.Format(descriptionText, effects[level]);
        if (level < levelIndicator.maxLevels)
        {
            labelCost.Status = costs[level];
        }
        else
        {
            labelCost.gameObject.SetActive(false);
            labelMaxed.SetActive(true);
        }
    }
}

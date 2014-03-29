using UnityEngine;
using System.Collections;

/// <summary>
/// Triggers the tooltip on press and hides it on release.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtTooltipTrigger")]
public class tk2dUIExtTooltipTrigger : MonoBehaviour
{
    /// <summary>
    /// The tooltip to trigger.
    /// </summary>
    public GameObject tooltip;

    private tk2dUIItem uiItem;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        uiItem = GetComponentInChildren<tk2dUIItem>();
    }

    /// <summary>
    /// Subscribe to the ui events
    /// </summary>
    void OnEnable()
    {
        uiItem.OnDown += uiItem_OnPressed;
        uiItem.OnRelease += uiItem_OnRelease;
    }

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        uiItem.OnDown -= uiItem_OnPressed;
        uiItem.OnRelease -= uiItem_OnRelease;
    }

    /// <summary>
    /// Activates the tooltip if the trigger is pressed.
    /// </summary>
    void uiItem_OnPressed()
    {
        if (!tooltip.activeSelf)
        {
            tooltip.SetActive(true);
        }
    }

    /// <summary>
    /// Deactivates the tooltip if the trigger is released.
    /// </summary>
    void uiItem_OnRelease()
    {
        if (tooltip.activeSelf)
        {
            tooltip.SetActive(false);
        }
    }  
}

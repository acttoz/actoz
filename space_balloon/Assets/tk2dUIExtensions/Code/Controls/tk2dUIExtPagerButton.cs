using UnityEngine;
using System.Collections;

/// <summary>
/// Pager button that has two visual states.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtPagerButton")]
public class tk2dUIExtPagerButton : MonoBehaviour
{
    /// <summary>
    /// Specifies the button's different behaviors on disable.
    /// </summary>
    public enum PagerButtonDisableBehavior
    {
        HideControl,
        VisualizeState,
    }

    /// <summary>
    /// Behavior of the button when it needs to be disabled.
    /// </summary>
    public PagerButtonDisableBehavior disableBehavior = PagerButtonDisableBehavior.VisualizeState;

    /// <summary>
    /// Name of the sprite that is shown if the button is enabled.
    /// </summary>
    public string spriteEnabledName;

    /// <summary>
    /// Name of the sprite that is shown if the button is disabled
    /// and the VisualizeState disable behavior is selected.
    /// </summary>
    public string spriteDisabledName;

    private tk2dSprite buttonSprite;
    private bool isEnabled = true;

	/// <summary>
	/// Setup references.
	/// </summary>
	void Awake()
    {
        buttonSprite = GetComponentInChildren<tk2dSprite>();
	}

    /// <summary>
    /// Changes the state of the pager button.
    /// </summary>
    /// <param name="state">New state</param>
    public void SetState(bool state)
    {
        isEnabled = state;
        buttonSprite.SetSprite(isEnabled ? spriteEnabledName : spriteDisabledName);
    }

    /// <summary>
    /// Returns the state of the pager button.
    /// </summary>
    /// <returns>The state</returns>
    public bool IsEnabled()
    {
        return isEnabled;
    }
}

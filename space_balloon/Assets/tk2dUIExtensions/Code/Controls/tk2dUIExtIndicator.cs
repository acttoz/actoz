using UnityEngine;
using System.Collections;

/// <summary>
/// This control is capable of visualizing progress or selection.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtIndicator")]
public class tk2dUIExtIndicator : MonoBehaviour
{
    /// <summary>
    /// Represents the different behaviors of the indicator.
    /// </summary>
    public enum IndicatorBehavior
    {
        Select,
        Progress,
    }

    /// <summary>
    /// Name of the sprite that represents active state.
    /// </summary>
    public string spriteNameActive;

    /// <summary>
    /// Name of the sprite that represents inactive state.
    /// </summary>
    public string spriteNameInactive;

    /// <summary>
    /// Max number of levels the indicator can visualize.
    /// </summary>
    public int maxLevels = 5;

    /// <summary>
    /// Current level of the indicator.
    /// </summary>
    public int currentLevel = 1;

    /// <summary>
    /// Determines the behavior of the indicator.
    /// </summary>
    public IndicatorBehavior behavior;

    private tk2dSprite[] indicatorSprites;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        indicatorSprites = GetComponentsInChildren<tk2dSprite>();
        ChangeLevel(currentLevel);
    }

    /// <summary>
    /// Changes the level of the indicator to the specified one.
    /// </summary>
    /// <param name="level">desired level</param>
    public void ChangeLevel(int level)
    {
        currentLevel = Mathf.Clamp(level, 0, maxLevels);

        for (int i = 0; i < indicatorSprites.Length; i++)
        {
            tk2dSprite indicator = indicatorSprites[i];
            if (behavior == IndicatorBehavior.Select)
            {
                indicator.SetSprite((i + 1) == currentLevel ? spriteNameActive : spriteNameInactive);
            }
            else if (behavior == IndicatorBehavior.Progress)
            {
                indicator.SetSprite((i + 1) <= currentLevel ? spriteNameActive : spriteNameInactive);
            }
        }
    }

    /// <summary>
    /// Deactivates the indicator.
    /// </summary>
    /// <param name="spriteName">Name of the sprite that represents the inactive state</param>
    public void Deactivate(string spriteName)
    {
        foreach (var indicator in indicatorSprites)
        {
            indicator.SetSprite(spriteName);
        }
    }
}

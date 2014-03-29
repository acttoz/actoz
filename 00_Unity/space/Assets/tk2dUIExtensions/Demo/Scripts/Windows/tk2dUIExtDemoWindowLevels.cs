using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample level selection window.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowLevels")]
public class tk2dUIExtDemoWindowLevels : tk2dUIExtNavigationElement
{
    /// <summary>
    /// Unique identifier that is used when passing agruments during navigation.
    /// </summary>
    public static readonly string NavigationArgKeyPage = "tk2dUIExtDemoWindowLevelsPage";

    /// <summary>
    /// Manages navigation between pages.
    /// </summary>
    public tk2dUIExtPager pager;

    /// <summary>
    /// List of levels
    /// </summary>
    public tk2dUIItem[] levels;

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {
        // Set page index
        int pageIndex = 0;
        if (args != null)
        {
            if (args.ContainsKey(NavigationArgKeyPage))
            {
                pageIndex = (int)args[NavigationArgKeyPage];
            }
        }

        // Navigate to specified page
        pager.SetPage(pageIndex);
    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }

    /// <summary>
    /// Subscribe to the ui events
    /// </summary>
    void OnEnable()
    {
        foreach (var level in levels)
        {
            level.OnClickUIItem += level_OnClickUIItem;
        }
    }

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        foreach (var level in levels)
        {
            level.OnClickUIItem -= level_OnClickUIItem;
        }
    }

    /// <summary>
    /// Handle level selection.
    /// </summary>
    /// <param name="obj">Selected level</param>
    void level_OnClickUIItem(tk2dUIItem obj)
    {
        tk2dUIExtDemoLevel level = obj.GetComponent<tk2dUIExtDemoLevel>();
        if (level.locked)
        {
            level.SetState(false);
        }
        else
        {
            Application.LoadLevel("DemoGame");
        }
    }

    /// <summary>
    /// Navigate to the Coins tab of the Store windows.
    /// </summary>
    public void OnStoreClick()
    {
        Dictionary<string, object> args = new Dictionary<string, object>()
        {
            { tk2dUIExtDemoWindowStore.NavigationArgKeyTab, 1 },
        };
        tk2dUIExtNavigationManager.Instance.Navigate("WindowStore", args);
    }
}

using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample menu window (landing page of the game).
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowMenu")]
public class tk2dUIExtDemoWindowMenu : tk2dUIExtNavigationElement
{
    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {

    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }

    /// <summary>
    /// Navigate to the first page of the Levels window.
    /// </summary>
    public void OnPlayClick()
    {
        Dictionary<string, object> args = new Dictionary<string, object>()
        {
            { tk2dUIExtDemoWindowLevels.NavigationArgKeyPage, 0 },
        };
        tk2dUIExtNavigationManager.Instance.Navigate("WindowLevels", args);
    }

    /// <summary>
    /// Navigate to the Missions windows.
    /// </summary>
    public void OnMissionsClick()
    {
        tk2dUIExtNavigationManager.Instance.Navigate("WindowMissions", null);
    }

    /// <summary>
    /// Navigate to the Upgrades tab of the Store windows.
    /// </summary>
    public void OnUpgradesClick()
    {
        Dictionary<string, object> args = new Dictionary<string, object>()
        {
            { tk2dUIExtDemoWindowStore.NavigationArgKeyTab, 0 },
        };
        tk2dUIExtNavigationManager.Instance.Navigate("WindowStore", args);
    }

    /// <summary>
    /// Navigate to the Coins tab of the Store windows.
    /// </summary>
    public void OnCoinsClick()
    {
        Dictionary<string, object> args = new Dictionary<string, object>()
        {
            { tk2dUIExtDemoWindowStore.NavigationArgKeyTab, 1 },
        };
        tk2dUIExtNavigationManager.Instance.Navigate("WindowStore", args);
    }

    /// <summary>
    /// Opens the Facebook page of the game.
    /// </summary>
    public void OnFacebookClick()
    {
        Application.OpenURL("https://www.facebook.com/obumogames");
    }

    /// <summary>
    /// Opens the Twitter page of the game.
    /// </summary>
    public void OnTwitterClick()
    {
        Application.OpenURL("https://twitter.com/ObumoGames");
    }

    /// <summary>
    /// Opens the Google Plus page of the game.
    /// </summary>
    public void OnGooglePlusClick()
    {
        Application.OpenURL("https://plus.google.com/+ObumogamesEnterprise");
    }
}

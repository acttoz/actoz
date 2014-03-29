using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample store window with Upgrades and Coins tab.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowStore")]
public class tk2dUIExtDemoWindowStore : tk2dUIExtNavigationElement
{
    /// <summary>
    /// Unique identifier that is used when passing agruments during navigation.
    /// </summary>
    public static readonly string NavigationArgKeyTab = "tk2dUIExtDemoWindowStoreTab";

    /// <summary>
    /// Manages navigation between tabs.
    /// </summary>
    public tk2dUIExtTabContainer tabContainer;

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {
        // Set tab index
        int tabIndex = 0;
        if (args != null)
        {
            if (args.ContainsKey(NavigationArgKeyTab))
            {
                tabIndex = (int)args[NavigationArgKeyTab];
            }
        }

        // Navigate to the specified tab
        tabContainer.SelectTab(tabIndex);
    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }
}

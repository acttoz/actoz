using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample game paused window.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowPaused")]
public class tk2dUIExtDemoWindowPaused : tk2dUIExtNavigationElement
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
    /// Resumes the game.
    /// </summary>
    public void OnResumeClick()
    {
        tk2dUIExtNavigationManager.Instance.Navigate("WindowGame", null);
    }

    /// <summary>
    /// Restarts the game.
    /// </summary>
    public void OnRestartClick()
    {
        Application.LoadLevel("DemoGame");
    }

    /// <summary>
    /// Navigates back to the menu.
    /// </summary>
    public void OnMenuClick()
    {
        Application.LoadLevel("DemoMenu");
    }
}

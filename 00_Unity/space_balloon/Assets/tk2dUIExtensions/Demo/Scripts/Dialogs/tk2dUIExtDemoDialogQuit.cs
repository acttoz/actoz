using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample quit dialog.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Dialogs/tk2dUIExtDemoDialogQuit")]
public class tk2dUIExtDemoDialogQuit : tk2dUIExtNavigationElement
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
    /// Quits the application.
    /// </summary>
    public void OnYesClick()
    {
        Application.Quit();
    }

    /// <summary>
    /// Closes the dialog.
    /// </summary>
    public void OnNoClick()
    {
        tk2dUIExtNavigationManager.Instance.CloseCurrentDialog();
    }
}

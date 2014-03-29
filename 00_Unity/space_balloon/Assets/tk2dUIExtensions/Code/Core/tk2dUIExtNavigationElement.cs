using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Base class of navigation capable elements like windows or dialogs.
/// </summary>
public abstract class tk2dUIExtNavigationElement : MonoBehaviour
{
    /// <summary>
    /// Stores the initial transform of the navigation element.
    /// </summary>
    public class InitTransform
    {
        public Vector3 pos;
        public Vector3 scale;
        public float angle;
    }

    /// <summary>
    /// Unique identifier that is used during naviagation.
    /// </summary>
    public string uniqueId;

    private InitTransform initTransform;

    /// <summary>
    /// Registers the intial transform of the element.
    /// </summary>
    public void Register()
    {
        initTransform = new InitTransform();
        initTransform.pos = transform.position;
        initTransform.scale = transform.localScale;
        initTransform.angle = transform.eulerAngles.z;
    }

    /// <summary>
    /// Provides access to the intial transform.
    /// </summary>
    /// <returns>Initial transform</returns>
    public InitTransform GetInitTransform()
    {
        return initTransform;
    }

    /// <summary>
    /// Navigates back to the previous page if possible.
    /// </summary>
    public void GoBack()
    {
        if (tk2dUIExtNavigationManager.Instance.CanGoBack())
        {
            tk2dUIExtNavigationManager.Instance.GoBack();
        }
    }

    #region Navigation

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public abstract void OnNavigatedTo(Dictionary<string, object> args);

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public abstract void OnNavigatedFrom();

    #endregion Navigation
}

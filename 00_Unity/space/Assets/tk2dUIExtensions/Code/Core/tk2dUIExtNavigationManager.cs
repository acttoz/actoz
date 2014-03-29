using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;

/// <summary>
/// Navigation manager that is responsible for handling the navigation between windows and dialogs.
/// Slide in/out animations can be configured between navigations and primitive types can be passes as navigation parameters.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Core/tk2dUIExtNavigationManager")]
public class tk2dUIExtNavigationManager : tk2dUIExtSingletonBase<tk2dUIExtNavigationManager>
{
    #region Singleton

    /// <summary>
    /// Singleton instance of the navigation manager.
    /// </summary>
    public static tk2dUIExtNavigationManager Instance
    {
        get
        {
            return ((tk2dUIExtNavigationManager)instance);
        }
        set
        {
            instance = value;
        }
    }

    #endregion Singleton

    #region Navigation

    /// <summary>
    /// Represents the slide in/out navigation directions.
    /// </summary>
    public enum SlideDirection
    {
        Left,
        Right,
        Top,
        Bottom,
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight,
    }

    /// <summary>
    /// List of navigation elements (windows and dialogs) on the scene.
    /// </summary>
    public tk2dUIExtNavigationElement[] navigationElements;

    /// <summary>
    /// Unique identifier of the first windows of the game.
    /// </summary>
    public string landingWindowId;

    /// <summary>
    /// Unique identifier of the quit dialog of the game.
    /// </summary>
    public string quitDialogId;

    private tk2dUIExtNavigationElement currentDialog;
    private Dictionary<string, tk2dUIExtNavigationElement> registeredElements = new Dictionary<string, tk2dUIExtNavigationElement>();
    private Stack<tk2dUIExtNavigationElement> navigationStack = new Stack<tk2dUIExtNavigationElement>();

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        // Register navigation elements
        foreach (var element in navigationElements)
        {
            RegisterElement(element);
        }
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        // Navigate to the first window of the game
        if (!String.IsNullOrEmpty(landingWindowId))
        {
            Navigate(landingWindowId, null);
        }
    }

    /// <summary>
    /// Handling keyboard input for navigation.
    /// </summary>
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            if (IsDialogOpen())
            {
                CloseCurrentDialog();
            }
            else if (!IsDialogOpen() && CanGoBack())
            {
                GoBack();
            }
            else if (!String.IsNullOrEmpty(quitDialogId))
            {
                // No need to show a dialog on Windows 8, because Unity's Application.Quit() crashes.
#if !UNITY_METRO || UNITY_EDITOR
                ShowDialog(quitDialogId, null);
#endif
            }
        }
    }

    /// <summary>
    /// Navigates to the specified page with the given arguments. This method handles both forward and backward navigation.
    /// The backward navigation is activated if the uniqueId is null. Both slide in/out animations can be configured.
    /// </summary>
    /// <param name="uniqueId">Unique identifier of the page you'd like to navigate to. Null if backward navigation</param>
    /// <param name="args">Navigation arguments passed between pages. Null if backward navigation</param>
    /// <param name="slideOutDir">Slide out animation direction of the page you navigate from</param>
    /// <param name="slideInDir">Slide in animation direction of the page you navigate to</param>
    public void Navigate(string uniqueId, Dictionary<string, object> args, SlideDirection slideOutDir = SlideDirection.Left, SlideDirection slideInDir = SlideDirection.Right)
    {
        // Determine navigation direction
        bool forwardNavigation = !String.IsNullOrEmpty(uniqueId);
        if (forwardNavigation && !registeredElements.ContainsKey(uniqueId))
        {
            return;
        }

        // Navigate from the previous element
        try
        {
            tk2dUIExtNavigationElement previousNavigationElement = forwardNavigation ? navigationStack.Peek() : navigationStack.Pop();
            previousNavigationElement.OnNavigatedFrom();
            AnimateHideElement(previousNavigationElement, GetPositionForAnimation(slideOutDir));
        }
        catch (Exception)
        {
            // The navigation stack is empty
        }

        // Navigate to the current element
        tk2dUIExtNavigationElement currentNavigationElement = forwardNavigation ? registeredElements[uniqueId] : navigationStack.Peek();
        AnimateShowElement(currentNavigationElement, GetPositionForAnimation(slideInDir));
        currentNavigationElement.OnNavigatedTo(args);

        // Push the current element on forward navigation only
        if (forwardNavigation)
        {
            navigationStack.Push(currentNavigationElement);
        }
    }

    /// <summary>
    /// Indicates whether backward navigation is possible or not.
    /// </summary>
    /// <returns>The result</returns>
    public bool CanGoBack()
    {
        return navigationStack != null ? navigationStack.Count > 1 : false;
    }

    /// <summary>
    /// Executes backward navigation.
    /// </summary>
    public void GoBack()
    {
        Navigate(null, null);
    }

    /// <summary>
    /// Returns the window from the top of the navigation stack.
    /// </summary>
    /// <returns>The current window</returns>
    public tk2dUIExtNavigationElement GetCurrentWindow()
    {
        return navigationStack != null ? navigationStack.Peek() : null;
    }

    /// <summary>
    /// Pops up the specified dialog with the given arguments. Slide in animation of the dialog can be configured.
    /// </summary>
    /// <param name="uniqueId">Unique identifier of the dialog you'd like to pop up</param>
    /// <param name="args">Navigation arguments of the dialog</param>
    /// <param name="slideInDir">Slide in animation direction of the dialog you pop up</param>
    public void ShowDialog(string uniqueId, Dictionary<string, object> args, SlideDirection slideInDir = SlideDirection.Top)
    {
        if (IsDialogOpen() || !registeredElements.ContainsKey(uniqueId))
        {
            return;
        }

        tk2dUIExtNavigationElement element = registeredElements[uniqueId];
        AnimateShowElement(element, GetPositionForAnimation(slideInDir));
        element.OnNavigatedTo(args);
        currentDialog = element;
    }

    /// <summary>
    /// Closes the currently open dialog. Slide out animation direction of the dialog can be configured.
    /// </summary>
    /// <param name="slideOutDir">Slide out animation direction of the dialog you close</param>
    public void CloseCurrentDialog(SlideDirection slideOutDir = SlideDirection.Bottom)
    {
        if (!IsDialogOpen())
        {
            return;
        }

        currentDialog.OnNavigatedFrom();
        AnimateHideElement(currentDialog, GetPositionForAnimation(slideOutDir));
        currentDialog = null;
    }

    /// <summary>
    /// Indicates whether or not there are any dialogs open.
    /// </summary>
    /// <returns>The result</returns>
    public bool IsDialogOpen()
    {
        return currentDialog != null;
    }

    /// <summary>
    /// Calculates animation position based on animation direction. This is used during navigation.
    /// </summary>
    /// <param name="direction">Direction of the animation</param>
    /// <returns>Animation position</returns>
    private Vector3 GetPositionForAnimation(SlideDirection direction)
    {
        switch (direction)
        {
            case SlideDirection.Left:
                return new Vector3(-5, 0, 0);
            case SlideDirection.Right:
                return new Vector3(5, 0, 0);
            case SlideDirection.Top:
                return new Vector3(0, 5, 0);
            case SlideDirection.Bottom:
                return new Vector3(0, -5, 0);
            case SlideDirection.TopLeft:
                return new Vector3(-5, 5, 0);
            case SlideDirection.TopRight:
                return new Vector3(5, 5, 0);
            case SlideDirection.BottomLeft:
                return new Vector3(-5, -5, 0);
            case SlideDirection.BottomRight:
                return new Vector3(5, -5, 0);
            default:
                return new Vector3(5, 0, 0);
        }
    }

    #endregion Navigation

    #region Helpers

    protected void RegisterElement(tk2dUIExtNavigationElement element)
    {
        Transform elementTransform = element.transform;
        RemoveUnity3HackFromElement(elementTransform);
        ShowElement(elementTransform);
        element.Register();
        registeredElements.Add(element.uniqueId, element);
        HideElement(elementTransform);
    }

    protected void AnimateShowElement(tk2dUIExtNavigationElement element, Vector3 from)
    {
        tk2dUIExtNavigationElement.InitTransform it = element.GetInitTransform();
        Transform elementTransform = element.transform;
        ShowElement(elementTransform);
        elementTransform.localPosition = from;
        elementTransform.localScale = Vector3.zero;
        elementTransform.localEulerAngles = new Vector3(0, 0, 10);
        StartCoroutine(coTweenTransformTo(elementTransform, 0.3f, it.pos, it.scale, it.angle));
    }

    protected void AnimateHideElement(tk2dUIExtNavigationElement element, Vector3 to)
    {
        StartCoroutine(coAnimateHideElement(element.transform, to));
    }

    private IEnumerator coAnimateHideElement(Transform t, Vector3 to)
    {
        yield return StartCoroutine(coTweenTransformTo(t, 0.3f, to, Vector3.zero, -10));
        HideElement(t);
    }

    #endregion Helpers

    #region SimpleTweens

    protected IEnumerator coResizeLayout(tk2dUILayout layout, Vector3 min, Vector3 max, float time)
    {
        Vector3 minFrom = layout.GetMinBounds();
        Vector3 maxFrom = layout.GetMaxBounds();
        for (float t = 0; t < time; t += tk2dUITime.deltaTime)
        {
            float nt = Mathf.SmoothStep(0, 1, Mathf.Clamp01(t / time));
            Vector3 currMin = Vector3.Lerp(minFrom, min, nt);
            Vector3 currMax = Vector3.Lerp(maxFrom, max, nt);
            layout.SetBounds(currMin, currMax);
            yield return 0;
        }
        layout.SetBounds(min, max);
    }

    protected IEnumerator coTweenAngle(Transform t, float xAngle, float time)
    {
        float xStart = t.localEulerAngles.x;
        if (xStart > 0) xStart -= 360;
        for (float ut = 0; ut < time; ut += Time.deltaTime)
        {
            float nt = Mathf.SmoothStep(0, 1, Mathf.Clamp01(ut / time));
            float a = Mathf.Lerp(xStart, xAngle, nt);
            t.localEulerAngles = new Vector3(a, 0, 0);
            yield return 0;
        }
        t.localEulerAngles = new Vector3(xAngle, 0, 0);
    }

    protected IEnumerator coMove(Transform t, Vector3 targetPosition, float time)
    {
        Vector3 startPosition = t.position;
        for (float ut = 0; ut < time; ut += Time.deltaTime)
        {
            float nt = Mathf.SmoothStep(0, 1, Mathf.Clamp01(ut / time));
            t.position = Vector3.Lerp(startPosition, targetPosition, nt);
            yield return 0;
        }
        t.position = targetPosition;
    }

    protected IEnumerator coShake(Transform t, Vector3 translateConstraint, Vector3 rotationConstraint, float time)
    {
        Vector3 pos = t.position;
        Quaternion rot = t.rotation;
        for (float ut = 0; ut < time; ut += Time.deltaTime)
        {
            float nt = Mathf.Clamp01(ut / time);
            float strength = 1 - nt;

            t.position = pos + Vector3.Scale(UnityEngine.Random.onUnitSphere, translateConstraint).normalized * strength * 0.01f;
            t.rotation = rot;
            t.Rotate(Vector3.Scale(UnityEngine.Random.onUnitSphere, rotationConstraint), 2.0f * strength);

            yield return 0;
        }
        t.position = pos;
        t.rotation = rot;
    }

    protected IEnumerator coTweenTransformTo(Transform transform, float time, Vector3 toPos, Vector3 toScale, float toRotation)
    {
        Vector3 fromPos = transform.localPosition;
        Vector3 fromScale = transform.localScale;
        Vector3 euler = transform.localEulerAngles;
        float fromRotation = euler.z;

        for (float t = 0; t < time; t += tk2dUITime.deltaTime)
        {
            float nt = Mathf.Clamp01(t / time);
            nt = Mathf.Sin(nt * Mathf.PI * 0.5f);

            transform.localPosition = Vector3.Lerp(fromPos, toPos, nt);
            transform.localScale = Vector3.Lerp(fromScale, toScale, nt);
            euler.z = Mathf.Lerp(fromRotation, toRotation, nt);
            transform.localEulerAngles = euler;
            yield return 0;
        }

        euler.z = toRotation;
        transform.localPosition = toPos;
        transform.localScale = toScale;
        transform.localEulerAngles = euler;
    }

    #endregion SimpleTweens

    #region WindowManagement

    protected void DoSetActive(Transform t, bool state)
    {
#if UNITY_3_0 || UNITY_3_1 || UNITY_3_2 || UNITY_3_3 || UNITY_3_4 || UNITY_3_5 || UNITY_3_6 || UNITY_3_7 || UNITY_3_8 || UNITY_3_9
		t.gameObject.SetActiveRecursively(state);
#else
        t.gameObject.SetActive(state);
#endif
    }

    protected void ShowElement(Transform t)
    {
#if UNITY_3_0 || UNITY_3_1 || UNITY_3_2 || UNITY_3_3 || UNITY_3_4 || UNITY_3_5 || UNITY_3_6 || UNITY_3_7 || UNITY_3_8 || UNITY_3_9
		Vector3 v = t.position;
		v.y = v.y % 1;
		v.x = v.x % 1;
		t.position = v;
#else
        t.gameObject.SetActive(true);
#endif
    }

    protected void HideElement(Transform t)
    {
#if UNITY_3_0 || UNITY_3_1 || UNITY_3_2 || UNITY_3_3 || UNITY_3_4 || UNITY_3_5 || UNITY_3_6 || UNITY_3_7 || UNITY_3_8 || UNITY_3_9
		Vector3 v = t.position;
		v.y = (v.y % 1) + 100;
		t.position = v;
#else
        t.gameObject.SetActive(false);
#endif
    }

    // We move things away from the screen to "disable" them in Unity 3.x
    // It is horrible, but inevitable because SetActiveRecursively doesn't remember
    // disabled objects.
    // This isn't necessary in Unity 4.x, and we simply move everything back to the correct positions
    // on startup.
    protected void RemoveUnity3HackFromElement(Transform t)
    {
#if !(UNITY_3_0 || UNITY_3_1 || UNITY_3_2 || UNITY_3_3 || UNITY_3_4 || UNITY_3_5 || UNITY_3_6 || UNITY_3_7 || UNITY_3_8 || UNITY_3_9)
        Vector3 v = t.position;
        v.y = v.y % 1;
        v.x = v.x % 2;
        t.position = v;
#endif
    }

    #endregion WindowManagement
}

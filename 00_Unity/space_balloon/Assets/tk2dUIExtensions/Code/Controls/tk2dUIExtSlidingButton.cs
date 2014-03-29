using UnityEngine;
using System.Collections;

/// <summary>
/// Extends the functionaly of a simple button. This button contains a slinding panel that can be opened or closed.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtSlidingButton")]
public class tk2dUIExtSlidingButton : MonoBehaviour
{
    /// <summary>
    /// Indicates whether the panel is open or not.
    /// </summary>
    public bool isOpen = false;

    /// <summary>
    /// Transform of the sliding panel.
    /// </summary>
    public Transform transformPanel;

    /// <summary>
    /// Transform of the button icon.
    /// </summary>
    public Transform transformIcon;

    /// <summary>
    /// How long the tween (scaling) should last in seconds. If set to 0 no tween is used, happens instantly.
    /// </summary>
    public float tweenDuration = 0.2f;

    /// <summary>
    /// Target rotation angle of the icon when the panel is opening
    /// </summary>
    public float iconAngleOpen = 180;

    /// <summary>
    /// Target rotation angle of the icon when the panel is closing
    /// </summary>
    public float iconAngleClose = 0;

    private tk2dUIItem uiItem;
    private Vector3 onUpScale = new Vector3(1, 1, 1);
    private Vector3 onDownScale = new Vector3(1, 0, 1);
    private Vector3 tweenTargetScale;
    private Vector3 tweenStartingScale;
    private Quaternion tweenTargetRotation;
    private Quaternion tweenStartingRotation;
    private bool internalTweenInProgress = false;
    private float tweenTimeElapsed = 0;

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
        uiItem.OnClick += uiItem_OnClick;
    }

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        uiItem.OnClick -= uiItem_OnClick;
    }

    /// <summary>
    /// Toggles the button between open and closed state.
    /// </summary>
    void uiItem_OnClick()
    {
        if (tweenDuration <= 0)
        {
            // Open / close panel instantly
            transformPanel.localScale = isOpen ? onDownScale : onUpScale;
            transformIcon.localRotation = isOpen ? Quaternion.Euler(0, 0, iconAngleClose) : Quaternion.Euler(0, 0, iconAngleOpen);
            isOpen = !isOpen;
        }
        else if (!internalTweenInProgress)
        {
            // Start tween if it hasn't been started
            tweenTargetScale = isOpen ? onDownScale : onUpScale;
            tweenStartingScale = transformPanel.localScale;
            tweenTargetRotation = isOpen ? Quaternion.Euler(0, 0, iconAngleClose) : Quaternion.Euler(0, 0, iconAngleOpen);
            tweenStartingRotation = transformIcon.localRotation;
            StartCoroutine(TweenAnimation());
            internalTweenInProgress = true;
        }
    }

    /// <summary>
    /// Tweens the scale of the sliding panel.
    /// </summary>
    /// <returns></returns>
    private IEnumerator TweenAnimation()
    {
        tweenTimeElapsed = 0;
        while (tweenTimeElapsed < tweenDuration)
        {
            // Lerp between starting and target scale
            transformPanel.localScale = Vector3.Lerp(tweenStartingScale, tweenTargetScale, tweenTimeElapsed / tweenDuration);
            transformIcon.localRotation = Quaternion.Lerp(tweenStartingRotation, tweenTargetRotation, tweenTimeElapsed / tweenDuration);
            yield return null;
            tweenTimeElapsed += tk2dUITime.deltaTime;
        }

        // Tween finished
        transformPanel.localScale = tweenTargetScale;
        transformIcon.localRotation = tweenTargetRotation;
        internalTweenInProgress = false;
        isOpen = !isOpen;
    }
}

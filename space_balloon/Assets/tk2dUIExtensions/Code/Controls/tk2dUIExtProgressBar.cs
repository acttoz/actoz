using UnityEngine;
using System.Collections;

/// <summary>
/// Extends the functionality of the standard tk2dUIProgressBar by a progress label.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtProgressBar")]
public class tk2dUIExtProgressBar : MonoBehaviour
{
    /// <summary>
    /// Indicates whether to show progress label or not.
    /// </summary>
    public bool ShowProgress
    {
        get { return showProgress; }
        set
        {
            showProgress = value;
            if (Application.isPlaying)
            {
                UpdateProgress();
            }
        }
    }

    /// <summary>
    /// Progress label used in string.Format() method.
    /// </summary>
    public string ProgressLabel
    {
        get { return progressLabel; }
        set
        {
            progressLabel = value;
            if (Application.isPlaying)
            {
                UpdateProgress();
            } 
        }
    }

    [SerializeField]
    private bool showProgress = true;

    [SerializeField]
    private string progressLabel = "{0}%";

    private tk2dUIProgressBar progressBar;
    private tk2dTextMesh labelProgress;
    private float previousProgress;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        progressBar = GetComponentInChildren<tk2dUIProgressBar>();
        labelProgress = GetComponentInChildren<tk2dTextMesh>();
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        UpdateProgress();
    }

    /// <summary>
    /// Update is called every frame.
    /// </summary>
    void Update()
    {
        if (previousProgress != progressBar.Value)
        {
            UpdateProgress();
        }
    }

    /// <summary>
    /// Updates the label of the progress bar.
    /// </summary>
    private void UpdateProgress()
    {
        labelProgress.gameObject.SetActive(showProgress);
        if (showProgress)
        {
            labelProgress.text = string.Format(progressLabel, Mathf.RoundToInt(100 * progressBar.Value));
        }
        previousProgress = progressBar.Value;
    }
}

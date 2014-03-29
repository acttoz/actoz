using UnityEngine;
using System.Collections;

/// <summary>
/// Visualizes a simple integer value on the UI that can be either a currency or just a numberic value.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtStatusBar")]
public class tk2dUIExtStatusBar : MonoBehaviour
{
    /// <summary>
    /// Indicates whether to format value by the cureency formatter or not.
    /// </summary>
    public bool UseCurrencyFormatter
    {
        get { return useCurrencyFormatter; }
        set
        {
            useCurrencyFormatter = value;
            if (Application.isPlaying)
            {
                UpdateLabel();
            }
            
        }
    }

    /// <summary>
    /// Current status.
    /// </summary>
    public int Status
    {
        get { return status; }
        set
        {
            status = value;
            if (Application.isPlaying)
            {
                UpdateLabel();
            }
        }
    }

    [SerializeField]
    private bool useCurrencyFormatter;

    [SerializeField]
    private int status;

    private tk2dTextMesh labelValue;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        labelValue = GetComponentInChildren<tk2dTextMesh>();
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        Status = status;
    }

    /// <summary>
    /// Updates the label based on the current value.
    /// </summary>
    private void UpdateLabel()
    {
        labelValue.text = useCurrencyFormatter ? tk2dUIExtHelper.FormatCurrency(status) : status.ToString();
    }
}

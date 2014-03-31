using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Container class that provides tab selection functionality.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtTabContainer")]
public class tk2dUIExtTabContainer : MonoBehaviour
{
    /// <summary>
    /// List of tabs in the container.
    /// </summary>
    public tk2dUIToggleButton[] tabs;

    /// <summary>
    /// Content of the tabs.
    /// </summary>
    public GameObject[] contents;

    /// <summary>
    /// Index of the selected tab.
    /// </summary>
    public int selectedTab = 0;

    private List<tk2dUIItem> uiItems;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        uiItems = new List<tk2dUIItem>(tabs.Length);
        foreach (var tab in tabs)
        {
            uiItems.Add(tab.GetComponent<tk2dUIItem>());
        }
    }

    /// <summary>
    /// Use this for initialization.
    /// </summary>
	void Start ()
    {
        // Check the length of tabs and contents
        if (tabs.Length != contents.Length)
        {
            Debug.LogError("The length of tabs and contents doesn't match. Please setup your TabContainer correctly!");
            return;
        }

        // Select tab
        SelectTab(selectedTab);
	}

    /// <summary>
    /// Subscribe to the ui events
    /// </summary>
    void OnEnable()
    {
        foreach (var uiItem in uiItems)
        {
            uiItem.OnClickUIItem += uiItem_OnClickUIItem;
        }
    } 

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        foreach (var uiItem in uiItems)
        {
            uiItem.OnClickUIItem -= uiItem_OnClickUIItem;
        }
    }

    /// <summary>
    /// Handles tab click.
    /// </summary>
    /// <param name="obj">Clicked tab</param>
    void uiItem_OnClickUIItem(tk2dUIItem obj)
    {
        int index = uiItems.IndexOf(obj);
        if (selectedTab == index)
        {
            // Keep selected tab active if the user clicks on it
            tabs[selectedTab].IsOn = true;
        }
        else
        {
            // Select new tab
            SelectTab(index);
        }
    }

    /// <summary>
    /// Activates the tab with the specified index.
    /// </summary>
    /// <param name="tabIndex">Index of the tab to active</param>
    public void SelectTab(int tabIndex)
    {
        // Clamp index between min and max value
        selectedTab = Mathf.Clamp(tabIndex, 0, tabs.Length - 1);

        // Activate selected tab
        for (int i = 0; i < tabs.Length; i++)
        {
            bool isSelected = i == selectedTab;
            tabs[i].IsOn = isSelected;
            contents[i].SetActive(isSelected);
        }
    }
}

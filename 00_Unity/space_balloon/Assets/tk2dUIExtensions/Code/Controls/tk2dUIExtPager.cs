using UnityEngine;
using System.Collections;

/// <summary>
/// Provides pager functionality.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Controls/tk2dUIExtPager")]
public class tk2dUIExtPager : MonoBehaviour
{
    /// <summary>
    /// Navigates the pager to the previous page.
    /// </summary>
    public tk2dUIExtPagerButton pagerButtonPrev;
    
    /// <summary>
    /// Navigates the pager to the next page.
    /// </summary>
    public tk2dUIExtPagerButton pagerButtonNext;
    
    /// <summary>
    /// Visualizes the pager's state. Can be null.
    /// </summary>
    public tk2dUIExtIndicator indicator;

    /// <summary>
    /// Page contents.
    /// </summary>
    public GameObject[] pages;

    private tk2dUIItem buttonPrev;
    private tk2dUIItem buttonNext;
    private int currentPage = 0;
    private bool leftPressed = false;
    private bool rightPressed = false;

    /// <summary>
    /// Setup references.
    /// </summary>
    void Awake()
    {
        buttonPrev = pagerButtonPrev.GetComponent<tk2dUIItem>();
        buttonNext = pagerButtonNext.GetComponent<tk2dUIItem>();
    }

    /// <summary>
    /// Initialize variables.
    /// </summary>
    void Start()
    {
        SetPage(0);
    }

    /// <summary>
    /// Subscribe to the ui events
    /// </summary>
    void OnEnable()
    {
        buttonPrev.OnClick += buttonPrev_OnClick;
        buttonNext.OnClick += buttonNext_OnClick;
    }

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        buttonPrev.OnClick -= buttonPrev_OnClick;
        buttonNext.OnClick -= buttonNext_OnClick;
    }

    /// <summary>
    /// Handle keyboard navigation between pages.
    /// </summary>
    void Update()
    {
        // Handle mouse scroll wheel
        float mouseScroll = Input.GetAxis("Mouse ScrollWheel");
        if (mouseScroll > 0)
        {
            Prev();
        }
        else if (mouseScroll < 0)
        {
            Next();
        }

        // Handle left/right keys
        float horizontal = Input.GetAxisRaw("Horizontal");
        if(horizontal < 0)
        {
            if(!leftPressed)
            {
                // Left Down
                Prev();
                leftPressed = true;
            }
            if(rightPressed)
            {
                // Right Up
                rightPressed = false;
            }
        }
        else if(horizontal > 0)
        {
            if(leftPressed)
            {
                // Left Up
                leftPressed = false;
            }
            if(!rightPressed)
            {
                // Right Down
                Next();
                rightPressed = true;
            }
        }
        else
        {
            if(rightPressed)
            {
                // Right Up
                rightPressed = false;
            }
            if(leftPressed)
            {
                // Left Up
                leftPressed = false;
            }
        }

    }

    /// <summary>
    /// Switches to the previous page.
    /// </summary>
    public void Prev()
    {
        if (!HasPrev())
        {
            return;
        }

        currentPage--;
        ShowCurrentPage();
    }

    /// <summary>
    /// Switches to the next page.
    /// </summary>
    public void Next()
    {
        if (!HasNext())
        {
            return;
        }

        currentPage++;
        ShowCurrentPage();
    }

    /// <summary>
    /// Sets the current page.
    /// </summary>
    /// <param name="page">Index of the page</param>
    public void SetPage(int page)
    {
        if (page >= 0 && page < pages.Length)
        {
            currentPage = page;
            ShowCurrentPage();
        }
    }

    /// <summary>
    /// Returns the current page index.
    /// </summary>
    /// <returns>Page index</returns>
    public int GetCurrentPage()
    {
        return currentPage;
    }

    /// <summary>
    /// Click event handler of the next button.
    /// </summary>
    void buttonNext_OnClick()
    {
        Next();
    }

    /// <summary>
    /// Click event handler of the previous button.
    /// </summary>
    void buttonPrev_OnClick()
    {
        Prev();
    }

    /// <summary>
    /// Indicates whether the pages has previous pages or not.
    /// </summary>
    /// <returns>The result</returns>
    private bool HasPrev()
    {
        return currentPage > 0;
    }

    /// <summary>
    /// Indicates whether the pages has additional pages or not.
    /// </summary>
    /// <returns>The result</returns>
    private bool HasNext()
    {
        return currentPage < pages.Length - 1;
    }

    /// <summary>
    /// Shows the current page and hides the rest.
    /// </summary>
    private void ShowCurrentPage()
    {
        // Show current page
        for (int i = 0; i < pages.Length; i++)
        {
            pages[i].SetActive(i == currentPage);
        }

        // Update indicator state
        if (indicator != null)
        {
            indicator.ChangeLevel(currentPage + 1);
        }

        // Change button states
        pagerButtonPrev.SetState(HasPrev());
        pagerButtonNext.SetState(HasNext());
    }
}

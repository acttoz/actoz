using UnityEngine;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// Sample gameplay window.
/// </summary>
[AddComponentMenu("2D Toolkit UI Extensions/Demo/Windows/tk2dUIExtDemoWindowGame")]
public class tk2dUIExtDemoWindowGame : tk2dUIExtNavigationElement
{
    /// <summary>
    /// Progressbar that visualizes the life of the player.
    /// </summary>
    public tk2dUIProgressBar progressBarLife;

    /// <summary>
    /// Progressbar that visualizes the shield of the player.
    /// </summary>
    public tk2dUIProgressBar progressBarShield;

    /// <summary>
    /// Progressbar that visualizes the energy of the player.
    /// </summary>
    public tk2dUIProgressBar progressBarEnergy;

    /// <summary>
    /// Progressbar that visualizes level progress.
    /// </summary>
    public tk2dUIProgressBar progressBarLevelProgress;

    /// <summary>
    /// Status bar that visualizes coins.
    /// </summary>
    public tk2dUIExtStatusBar statusBarCoins;

    /// <summary>
    /// Status bar that visualizes gems.
    /// </summary>
    public tk2dUIExtStatusBar statusBarGems;

    /// <summary>
    /// Status bar that visualizes cash.
    /// </summary>
    public tk2dUIExtStatusBar statusBarCash;

    /// <summary>
    /// Status bar that visualizes points.
    /// </summary>
    public tk2dUIExtStatusBar statusBarPoints;

    /// <summary>
    /// Pause button.
    /// </summary>
    public tk2dUIItem buttonPause;

    /// <summary>
    /// Level completed button.
    /// </summary>
    public tk2dUIItem buttonCompleted;

    private bool isLifeBarCharging = true;
    private bool isShieldBarCharging = true;
    private bool isEnergyBarCharging = true;
    private bool isLevelProgressBarCharging = true;
    private bool isCoinsBarCharging = true;
    private bool isGemsBarCharging = true;
    private bool isCashBarCharging = true;
    private bool isPointsBarCharging = true;

    /// <summary>
    /// Called when the user navigates to the element.
    /// </summary>
    /// <param name="args">Navigation parameters</param>
    public override void OnNavigatedTo(Dictionary<string, object> args)
    {
        // Initialize controls
        progressBarLife.Value = 0;
        progressBarShield.Value = 0;
        progressBarEnergy.Value = 0;
        progressBarLevelProgress.Value = 0;
        statusBarCoins.Status = 0;
        statusBarGems.Status = 0;
        statusBarCash.Status = 0;
        statusBarPoints.Status = 0;

        // Initialie variables
        isLifeBarCharging = true;
        isShieldBarCharging = true;
        isEnergyBarCharging = true;
        isLevelProgressBarCharging = true;
        isCoinsBarCharging = true;
        isGemsBarCharging = true;
        isCashBarCharging = true;
        isPointsBarCharging = true;
    }

    /// <summary>
    /// Called when the user navigates from the element.
    /// </summary>
    public override void OnNavigatedFrom()
    {

    }

    /// <summary>
    /// Subscribe to the ui events
    /// </summary>
    void OnEnable()
    {
        buttonPause.OnClick += buttonPause_OnClick;
        buttonCompleted.OnClick += buttonCompleted_OnClick;
    }

    /// <summary>
    /// Unsubscribe from the ui events
    /// </summary>
    void OnDisable()
    {
        buttonPause.OnClick -= buttonPause_OnClick;
        buttonCompleted.OnClick -= buttonCompleted_OnClick;
    }

    /// <summary>
    /// Updates the state of the game.
    /// </summary>
    void Update()
    {
        float dt = Time.deltaTime;

        // Animate progress bar life
        if (isLifeBarCharging && progressBarLife.Value >= 1)
        {
            isLifeBarCharging = false;
        }
        else if (!isLifeBarCharging && progressBarLife.Value <= 0)
        {
            isLifeBarCharging = true;
        }
        progressBarLife.Value += isLifeBarCharging ? dt * 0.5f : -dt * 0.5f;

        // Animate progress bar shield
        if (isShieldBarCharging && progressBarShield.Value >= 1)
        {
            isShieldBarCharging = false;
        }
        else if (!isShieldBarCharging && progressBarShield.Value <= 0)
        {
            isShieldBarCharging = true;
        }
        progressBarShield.Value += isShieldBarCharging ? dt * 0.25f : -dt * 0.25f;

        // Animate progress bar energy
        if (isEnergyBarCharging && progressBarEnergy.Value >= 1)
        {
            isEnergyBarCharging = false;
        }
        else if (!isEnergyBarCharging && progressBarEnergy.Value <= 0)
        {
            isEnergyBarCharging = true;
        }
        progressBarEnergy.Value += isEnergyBarCharging ? dt * 2.0f : -dt * 2.0f;

        // Animate progress bar level progress
        if (isLevelProgressBarCharging && progressBarLevelProgress.Value >= 1)
        {
            isLevelProgressBarCharging = false;
        }
        else if (!isLevelProgressBarCharging && progressBarLevelProgress.Value <= 0)
        {
            isLevelProgressBarCharging = true;
        }
        progressBarLevelProgress.Value += isLevelProgressBarCharging ? dt * 1.0f : -dt * 1.0f;

        // Animate status bar coins
        if (isCoinsBarCharging && statusBarCoins.Status >= 1000)
        {
            isCoinsBarCharging = false;
        }
        else if (!isCoinsBarCharging && statusBarCoins.Status <= 0)
        {
            isCoinsBarCharging = true;
        }
        statusBarCoins.Status += isCoinsBarCharging ? 1 : -1;

        // Animate status bar gems
        if (isGemsBarCharging && statusBarGems.Status >= 1000)
        {
            isGemsBarCharging = false;
        }
        else if (!isGemsBarCharging && statusBarGems.Status <= 0)
        {
            isGemsBarCharging = true;
        }
        statusBarGems.Status += isGemsBarCharging ? 2 : -2;

        // Animate status bar cash
        if (isCashBarCharging && statusBarCash.Status >= 1000)
        {
            isCashBarCharging = false;
        }
        else if (!isCashBarCharging && statusBarCash.Status <= 0)
        {
            isCashBarCharging = true;
        }
        statusBarCash.Status += isCashBarCharging ? 3 : -3;

        // Animate status bar points
        if (isPointsBarCharging && statusBarPoints.Status >= 1000)
        {
            isPointsBarCharging = false;
        }
        else if (!isPointsBarCharging && statusBarPoints.Status <= 0)
        {
            isPointsBarCharging = true;
        }
        statusBarPoints.Status += isPointsBarCharging ? 4 : -4;
    }

    /// <summary>
    /// Navigate to paused window.
    /// </summary>
    void buttonPause_OnClick()
    {
        tk2dUIExtNavigationManager.Instance.Navigate("WindowPaused", null);
    }

    /// <summary>
    /// Navigate to level completed window.
    /// </summary>
    void buttonCompleted_OnClick()
    {
        tk2dUIExtNavigationManager.Instance.Navigate("WindowCompleted", null);
    }
}

using System.Globalization;

/// <summary>
/// Helper class with usefull formatting methods.
/// </summary>
public static class tk2dUIExtHelper
{
    /// <summary>
    /// Formats time to a UI friendly form (mm:ss).
    /// </summary>
    /// <param name="seconds">Time to format in seconds</param>
    /// <returns>Formatted time string</returns>
    public static string FormatTime(int seconds)
    {
        int min = seconds / 60;
        int sec = seconds % 60;
        return (min < 10 ? "0" + min.ToString() : min.ToString()) + ":" +
               (sec < 10 ? "0" + sec.ToString() : sec.ToString());
    }

    /// <summary>
    /// Formats currency to a UI friendly form (1,234,567).
    /// </summary>
    /// <param name="value">Currency to format</param>
    /// <returns>Formatted currency string</returns>
    public static string FormatCurrency(int value)
    {
        return value.ToString("#,#0", CultureInfo.InvariantCulture);
    }
}

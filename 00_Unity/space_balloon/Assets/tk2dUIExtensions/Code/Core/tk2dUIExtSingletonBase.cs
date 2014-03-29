using UnityEngine;
using System.Collections;

/// <summary>
/// Provides basic singleton functionality for the inherited class.
/// </summary>
/// <typeparam name="T">Type of your inherited singleton class</typeparam>
public class tk2dUIExtSingletonBase<T> : MonoBehaviour where T : tk2dUIExtSingletonBase<T>
{
    /*
    /// <summary>
    /// Paste this to every script that inherits from Singleton to work properly
    /// </summary>
    public static T Instance {
        get {
            return ((T)instance);
        } set {
            instance = value;
        }
    }
    */

    protected static tk2dUIExtSingletonBase<T> instance
    {
        get
        {
            if (!_instance)
            {
                T[] managers = GameObject.FindObjectsOfType(typeof(T)) as T[];
                if (managers.Length != 0)
                {
                    if (managers.Length == 1)
                    {
                        _instance = managers[0];
                        _instance.gameObject.name = typeof(T).Name;
                        return _instance;
                    }
                    else
                    {
                        Debug.LogError("You have more than one " + typeof(T).Name + " in the scene. You only need 1, it's a singleton!");
                        foreach (T manager in managers)
                        {
                            Destroy(manager.gameObject);
                        }
                    }
                }
                GameObject gO = new GameObject(typeof(T).Name, typeof(T));
                _instance = gO.GetComponent<T>();
                DontDestroyOnLoad(gO);
            }
            return _instance;
        }
        set
        {
            _instance = value as T;
        }
    }
    private static T _instance;
}
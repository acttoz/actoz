using UnityEngine;
using System.Collections;
using UnityEditor;

[CanEditMultipleObjects]
[CustomEditor(typeof(tk2dUIExtStatusBar))]
public class tk2dUIExtStatusBarEditor : Editor
{
    public override void OnInspectorGUI()
    {
        tk2dGuiUtility.LookLikeInspector();
        bool markAsDirty = false;
        tk2dUIExtStatusBar statusBar = (tk2dUIExtStatusBar)target;

        bool tempUseCurrencyFormatter = EditorGUILayout.Toggle("UseCurrencyFormatter", statusBar.UseCurrencyFormatter);
        if (tempUseCurrencyFormatter != statusBar.UseCurrencyFormatter)
        {
            markAsDirty = true;
            statusBar.UseCurrencyFormatter = tempUseCurrencyFormatter;
        }

        int tempStatus = EditorGUILayout.IntField("Status", statusBar.Status);
        if (tempStatus != statusBar.Status)
        {
            markAsDirty = true;
            statusBar.Status = tempStatus;
        }

        if (markAsDirty || GUI.changed)
        {
            EditorUtility.SetDirty(statusBar);
        }
    }
}

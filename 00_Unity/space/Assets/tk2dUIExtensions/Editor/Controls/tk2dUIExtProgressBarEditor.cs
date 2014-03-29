using UnityEngine;
using System.Collections;
using UnityEditor;

[CanEditMultipleObjects]
[CustomEditor(typeof(tk2dUIExtProgressBar))]
public class tk2dUIExtProgressBarEditor : Editor
{
    public override void OnInspectorGUI()
    {
        tk2dGuiUtility.LookLikeInspector();
        bool markAsDirty = false;
        tk2dUIExtProgressBar progressBar = (tk2dUIExtProgressBar)target;

        bool tempShowProgress = EditorGUILayout.Toggle("ShowProgress", progressBar.ShowProgress);
        if (tempShowProgress != progressBar.ShowProgress)
        {
            markAsDirty = true;
            progressBar.ShowProgress = tempShowProgress;
        }

        string tempProgressLabel = EditorGUILayout.TextField("ProgressLabel", progressBar.ProgressLabel);
        if (tempProgressLabel != progressBar.ProgressLabel)
        {
            markAsDirty = true;
            progressBar.ProgressLabel = tempProgressLabel;
        }

        if (markAsDirty || GUI.changed)
        {
            EditorUtility.SetDirty(progressBar);
        }
    }
}

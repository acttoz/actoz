/*
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package a.kr.co.moon.chunk;

import android.media.MediaRecorder;

public class RecordManager
{
	private MediaRecorder recorder = null;
	private String filePath, fileName = "record.m4a";
	private boolean isRecorded = false;

	public RecordManager()
	{
        try
        {
		    filePath = new FileUtil().mkdir("loup");
        }
        catch (Exception e)
        {
            filePath = "/";
        }
    }
	
	public void start()
	{
		isRecorded = true;
		if (recorder == null) recorder = new MediaRecorder();
        recorder.reset();
        recorder.setAudioSamplingRate(44100);
        recorder.setAudioEncodingBitRate(75000);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(filePath + fileName);
        try
        {
            recorder.prepare();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            isRecorded = false;
        }
        recorder.start();
    }
	  
	  public void stop()
	  {
          isRecorded = false;
          if (recorder == null) return;
          try
		  {
			  recorder.stop();
		  }
		  catch(Exception e){}
		  finally
		  {
			  recorder.release();
			  recorder = null;
		  }
	  }
	  
	  public boolean isRecorded()
	  {
		  return isRecorded;
	  }
	  
	  public void setRecorded(boolean recorded)
	  {
		  recorded = recorded;
	  }
}

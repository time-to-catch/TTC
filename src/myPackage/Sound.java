package myPackage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound extends Thread {
	Clip clip;
	Sound(String file, boolean Loop) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);
			// Loop ����true�� ������������ѹݺ���ŵ�ϴ�.
			// false�� �ѹ��������ŵ�ϴ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Clip getB()
	{
		return clip;
	}
	
	public void stopB(Clip s)
	{
		s.stop();
	}
}

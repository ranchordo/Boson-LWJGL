package audio;

import static org.lwjgl.openal.AL10.*;

public class Source { //Very self explanatory.
	public int sourceId;
	public boolean playing=false;
	public Source() {
		sourceId=alGenSources();
		alSourcef(sourceId,AL_GAIN,1);
		alSourcef(sourceId,AL_PITCH,1);
		alSource3f(sourceId,AL_POSITION,0,0,0);
		alSourceStop(sourceId);
	}
	public void setGain(float gain) {
		alSourcef(sourceId,AL_GAIN,gain);
	}
	public void setPitch(float pitch) {
		alSourcef(sourceId,AL_PITCH,pitch);
	}
	public void play(int buffer) {
		alSourcei(sourceId,AL_BUFFER,buffer);
		alSourcePlay(sourceId);
	}
	public void stop() {
		alSourceStop(sourceId);
	}
	public void play(String fname) {
		this.play(Audio.getOGG(fname));
	}

	public void close() {
		alDeleteSources(sourceId);
	}
	public boolean isPlaying() {
		 if (alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING) {
             return true;
         }
		 return false;
	}
}

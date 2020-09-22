package audio;

import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.openal.EXTThreadLocalContext.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Objects;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.stb.STBVorbis;

public class Audio {
	private static long device;
	private static long context;
	public static void initListener() { //Exactly what it sounds like
		alListener3f(AL_POSITION,0,0,0);
		alListener3f(AL_VELOCITY,0,0,0);
	}
	public static void init() { //Get a device, initialize it, and prepare it.
		device=alcOpenDevice((ByteBuffer)null);
		if(device==NULL) {
			throw new IllegalStateException("Can't open audio device");
		}
		ALCCapabilities devcaps=ALC.createCapabilities(device);
		if(!devcaps.OpenALC10) {
			throw new IllegalStateException();
		}
		System.out.println("Performing driver ALC support check:");
		System.out.println("OpenALC10: "+devcaps.OpenALC10);
		System.out.println("OpenALC11: "+devcaps.OpenALC11);
		System.out.println("ALC_EXT_EFX: "+devcaps.ALC_EXT_EFX);
		
		if(devcaps.OpenALC11) {
			List<String> devs=ALUtil.getStringList(NULL, ALC_ALL_DEVICES_SPECIFIER);
			if(devs==null) {
				int err = alcGetError(NULL);
		        if (err != ALC_NO_ERROR) {
		            throw new RuntimeException(alcGetString(NULL, err));
		        }
			} else {
				System.out.println("Performing device fetch:");
				for(int i=0;i<devs.size();i++) {
					System.out.println(i+": "+devs.get(i));
				}
			}
		}
		String defaultDevSpecifier=Objects.requireNonNull(alcGetString(NULL,ALC_DEFAULT_DEVICE_SPECIFIER));
		System.out.println(defaultDevSpecifier+": Performing ALCINFO fetch:");
		
		context=alcCreateContext(device,(IntBuffer)null);
		alcSetThreadContext(context);
		AL.createCapabilities(devcaps);
		
		System.out.println("ALC_FREQUENCY: " + alcGetInteger(device, ALC_FREQUENCY) + "Hz");
        System.out.println("ALC_REFRESH: " + alcGetInteger(device, ALC_REFRESH) + "Hz");
        System.out.println("ALC_SYNC: " + (alcGetInteger(device, ALC_SYNC) == ALC_TRUE));
        System.out.println("ALC_MONO_SOURCES: " + alcGetInteger(device, ALC_MONO_SOURCES));
        System.out.println("ALC_STEREO_SOURCES: " + alcGetInteger(device, ALC_STEREO_SOURCES));
        
	}
	public static void cleanUp() {
		alcMakeContextCurrent(NULL);
		AL.setCurrentThread(null);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
	
	//From the LWJGL 3.2.3 OPENAL demo:
	public static int getOGG(String file) {
		int buffer=alGenBuffers();
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = readVorbis(file, 32 * 1024, info);

            //copy to buffer
            alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }
		return buffer;
	}
	static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error   = BufferUtils.createIntBuffer(1);
        long      decoder = STBVorbis.stb_vorbis_open_memory(vorbis, error, null);
        if (decoder == NULL) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }

        STBVorbis.stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        ShortBuffer pcm = BufferUtils.createShortBuffer(STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * channels);

        STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
        STBVorbis.stb_vorbis_close(decoder);
        return pcm;
    }
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;
		InputStream source = Audio.class.getResourceAsStream(resource);
		if(source==null) {
			System.out.println("Audio source is null! Check if file exists?");
			cleanUp();
			System.exit(1);
		}
		ReadableByteChannel rbc = Channels.newChannel(source);
		buffer = BufferUtils.createByteBuffer(bufferSize);

        while (true) {
            int bytes = rbc.read(buffer);
            if (bytes == -1) {
                break;
            }
            if (buffer.remaining() == 0) {
                buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
            }
        }

        buffer.flip();
        return buffer;
    }
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
	//End
}

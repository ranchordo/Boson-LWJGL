# Boson-LWJGL  
This is a recreation of the game Boson X by Mu and Heyo. This recreation uses the LightWeight Java Game Library to improve performance. All credit for level design, original game idea, and awesomeness go to them. I just rewrote everything.  
I did not use any of their code. I used their assets, but the control logic and other programming is mine.
Everything from the "resources" folder (except for "shaders") was taken from a decompilation of the BosonX Android app. Ignore the SyncToy.dat files.  
  
A word about itstate.scrr:  
itstate.scrr is a score record file, hence the extension "scrr". If you delete this file, your high scores will be lost.  
An itstate.scrr file will be created if it does not exist on program run, in the same directory as the program main path, or, more precisely,  
```java
new File(BosonX.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath()
```
It will be fetched from the same place if it exists on program run.  
There may be an itstate.scrr in /jar/ if I test the .jar before I push this and forget to delete it.  
It also might have a score under level 1 if I briefly test the level functionality.  
If you do not like this, just delete itstate.scrr and restart Boson-LWJGL.  
  
The easiest way to execute Boson-LWJGL is to just run /jar/Boson-LWJGL.jar.
  
I will delete or edit these statements as I update this game:
*This is still in progress.* Whenever I have new functionality, I will sync everything over to here.  
*I expect that the final recreation* will use LWJGL 3.2 with the following bindings:  
1. LWJGL core  
2. LWJGL-OpenGL  
3. LWJGL-GLFW  
4. LWJGL-OpenAL  
5. LWJGL-OpenCL  
# Boson-LWJGL  
Boson-LWJGL is a re-creation of the game Boson X by Mu and Heyo. This re-creation uses the LightWeight Java Game Library to improve performance. All credit for level design, original game idea, and general awesomeness go to them. I just rewrote everything.  
You can find the original game here: <http://boson-x.com>.  
Boson-LWJGL is designed for the Boson X modding community. With a documented, open source re-creation of this game, modifications to the core functionality of the game are easier.  
I did not use any of their code. I used their assets for this re-creation, but I have written all of the source code for Boson-LWJGL.
Everything from the "resources" folder (except for "shaders") was taken from a decompilation of the Boson X Android app. The SyncToy.dat files are artifacts left over from the program I'm using to copy and update files from my eclipse project directory to my Github repo directory, and they are not critical to the function of Boson-LWJGL in any way.  
**The images and audio from the original game are copyrighted by Jon Kerny and Ian MacLarty, respectively, and I am using them with permission. Any licensing on the source code for Boson-LWJGL does not apply to the images and audio, i.e. the image and audio assets are not licensed under a GNU General Public License.**  
  
A word about itstate.scrr:  
itstate.scrr is a score record file, hence the extension "scrr". If you delete this file, your high scores will be lost.  
An itstate.scrr file will be created if it does not exist on program run, in the same directory as the program main path, or, more precisely, the main class's protectionDomain's codeSource's location's parent's path.  
It will be fetched from the same place if it exists on program run.  
There may be an itstate.scrr in /jar/ if I test the .jar before I push this and forget to delete it.  
  
The easiest way to execute Boson-LWJGL is to just run /jar/Boson-LWJGL.jar.
  
I will delete or edit these statements as I update this game:
*This is still in progress.* Whenever I have new functionality, I will sync everything over to here.  
*I expect that the final re-creation* will use LWJGL 3.2 with the following bindings:  
1. LWJGL core  
2. LWJGL-OpenGL  
3. LWJGL-GLFW  
4. LWJGL-OpenAL  
5. LWJGL-OpenCL  
  
    
At the minute it uses  
1. LWJGL core  
2. LWJGL-OpenGL  
3. LWJGL-GLFW  
4. LWJGL-OpenAL  
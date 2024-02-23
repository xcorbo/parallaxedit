# **SC:R Parallax Editor v0.1.1-alpha**

SC:R Parallax Editor is an application that allows you to edit the `*.spk` format from Starcraft's Remastered version of the format. You can open an SPK file to edit just the stars, though, if you provide a JSON along with the PNG frames you will be able to preview the layers as well. This relies on the use of Animosity, another SCR Tool by neiv, and you need to provide the program with the JSON only, it will open the extracted frames by itself. Make sure you extract the frames with animosity with the "separate frames" option enabled. 
SC:R Parallax Editor needs java 8 to run, so make sure to install it if you want to run the .jar file. Otherwise source code is provided

![javaw_qFRajPr5u6](https://github.com/xcorbo/parallaxedit/assets/11607265/d6b101cd-d918-4ac9-940f-cbe6d1192b6a)

With this program you will be able to:
- Add completely new stars
- Edit existing and new stars
- Duplicate existing stars
- Delete stars
- Edit the position of the stars using click-drag
- Preview your SPK and its layers

![javaw_yquIJzPP1B](https://github.com/xcorbo/parallaxedit/assets/11607265/1adbb6a3-c37b-48b3-aa80-bbd64a467cf2)

Provided you have modified your PNG files with completely new things and you know the coordinates you can add absolutely different things from the boring parallax that SCR has, right now. 

For example, in this case i had modified the PNG file for layer 3 so it had a sun in coordinates (2,188) with a size of (50,50). Here is the simple process of me adding a new star, entering those coordinates and now my sun is added to the SPK and the preview!

![sublime_text_Wyczjz4QOg](https://github.com/xcorbo/parallaxedit/assets/11607265/7132d57c-9763-460d-89da-ba72a831d95b)

A couple of things to note:
- Program really does rely on the use of Animosity and extracted separate frames as PNG format. If you make changes to these PNG files, such as making them bigger and adding more things to them, make sure to modify this in either animosity or the JSON, SPK editor will NOT edit the PNG files or the JSON for you, it merely uses them to display you the information and preview, according to each star information. 
- There's three sets of coordinates for each star.
    - `(x, y)` is the top-left corner position of the star inside the LAYER, as in, where SC renders it. 
    - `(ddsX, ddsY)` is the top-left corner position of the star inside the DDS file (which should be extracted as a PNG), as in, the location within the actual image file
    - `(width, height)` is the size of the object

So, for example, if you have `(5, 10, 125, 200, 50, 45)` it will grab a star with a size `50x45` from the dds layer at `125,200` pixel and put it into the position of `5,10` in the scr rendered parallax. 


A little bit of thank you based [Farty](http://www.staredit.net/profile/124/) for creating the initial SPK Format for SC 1.16.1 and helping me decode this one, also for making the first SPKEdit and also [neiv](http://www.staredit.net/profile/9585/) for making Animosity. 
For more cool modding stuff about Starcraft and Starcraft: Remastered visit: [http://www.staredit.net/portal/](http://www.staredit.net/portal/)

Please take note that this program is still under development and in very early alpha, non-tested stage, I am only releasing because it's been a while since i have had this code laying around and decided to make it at least functional enough to push a small release so people can use it as well. So take it as a grain of salt and while it works and it will be useful, there will be bug, glitches and, again, it's still under development so I appreciate any ideas, feedback, bug finds, everything you can think of. 

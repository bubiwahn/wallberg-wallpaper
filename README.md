# wallberg-wallpaper
Setting the popular wallberg webcam image as wallpaper on your windows desktop using groovy and a powershell snippet grabbed from the internet. This project is mainly intended as a running example to demonstrate the benefits of groovy as tool for task automation spanning different platforms, but I use it regularly to observe the weather and wind situation at the wallberg take-off site as well.
Moreover it is simply enjoyable and relaxing to see the many different changing colours and cloud formations that mother nature is creating on a single spot.

The project uses maven and runs on Windows and WSL (Windows Subsystem for Linux) with ubuntu 18.04 inside.
As IDE I tested the latest Eclipse for Java Developers (Version: 2018-09 (4.9.0)) downloaded from here (https://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-developers), which I had pimped up with the latest Eclipse groovy plugins from here (https://github.com/groovy/groovy-eclipse/wiki).
With that, there is everything in place for effective groovy development (with code-completion, code-highlighting, integrated version control with GIT and all the other fancy stuff I didn't discovered yet). However, other IDEs may be suitable as well.

After setting up the enviroment and cloning the repository:
* `mvn clean install` builds the project
* `mnv groovy:execute@run` executes the script via maven using the maven-groovy plugin

Then simply import the project in Eclipse as "existing project".

**Note:** This project doesn't run on native Linux, primarily because I don't have a native Linux box available to test the final step "setting the desktop wallpaper", which is highly platform dependent. I would appreciate if someone else could contribute this piece of the puzzle for native Linux.

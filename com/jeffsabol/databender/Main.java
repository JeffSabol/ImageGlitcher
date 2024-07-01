/* Jeff Sabol
DataBender is an application to add glitch effects to images.
Main.java: Initializes the application and manage the GUI.

Known bugs:
1. For the same image: open > close > open results in a blank image until you load a different image first
2. Cannot save steganographic images via the save button (low quality work around was added to open a save dialog after the message is hidden)
3. Steganography only works with .jpg

TODO List:
add view zooming (if image is too big it will be cut off from the user's view)
Allow user to specify randomize pixel color percentage
Apply more OOP principles
   move all the ActionListeners to their own respective classes
   move menubar to it's own class
test to see if steganography works with other file types
sort effects by file type?
Add more effects
   Convert image to ascii art
       (allows user to save as txt file)
   Fractal generator
       (takes image input as a seed and will always generate the same fractal per image)
Give file extension selection option (tried not sure how) instead of user manually entering them
Add additional option to let random pixel color to ignore white pixels (maybe different shades of white could screw this up)
Make chromatic aberration work with other file types besides transparent PNGs
   (if direction = x move picture left by x, etc)
   Let the user select colors for chromatic aberration
*/
package com.jeffsabol.databender;

public class Main {
    public static void main(String[] args) {
        DataBender app = new DataBender();
        app.start();
    }
}

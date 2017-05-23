### Changelog

While Github's built in system is generally sufficient for keeping track of general changes, this is more suitable for keeping track of changes by version and for elaborating on changed stuff.

---

**MAY 12 2017** (ver.PTB-A) (U1)

- Added changelog file to keep track of a more detailed list of changes to the program.

- Added some more content to the README.

- Re-added content that was (for some reason) not automatically included in this build, namely the changes made yesterday.

- Updated the game loader (gameLoader)

    - Split the loading into separate methods based on function. Not everything should be loaded at once, by splitting it up it becomes more easy to curate when things are being loaded.

- Updated the map loader (mapGenerator)

    - Converted the map tile system to 2D arrays, which allows for more efficient indexing of tiles.

- Updated the XML loader (xmlLoader)

    - Added new error codes to more accurately show the error reached. This is mainly for highlighting the differences between a critical error that stops the loading of the XML data, versus an error that just defaults the value.

    - Increased code efficiency by using breaks when a conclusive result is reached in the code. Essentially, the program will no longer attempt to index data in the loops if it has already validated/invalidated the data. This should increase loading time somewhat as it doesn't need to run through the loops for as long as period of time

- Slightly fiddled with the UI code.

- Added some more comments to better describe the purpose of different program elements.

**MAY 12 2017** (ver.PTB-A) (U2)

- Reworked UI framework from the ground up.

    - Old UI was clunky and pretty broken, reworked it into a newer, more flexible format that'll hopefully suit my needs better down the road.

- Removed old UI code.

- Fixed issue with changelog file type.

- Slightly adjusted formatting in gameLoader.

**MAY 12 2017** (ver.PTB-A) (U3)

- Patched a major bug in the XML loader that would potentially cause the program to break if all vanilla content was replaced with broken XML. This is as a result of the program removing the vanilla content with the same ID without verifying that the new content is actually valid. A slight rework of the order has fixed this issue.


**MAY 13 2017** (ver.PTB-A) (U1)

- Updated planetCore/planetClass

    - Implemented new moon weight system. Allows planets to have moons and whatnot that are randomly determined upon generation.

    - Removed redundant ArrayList declaration.

- Patched error in xmlLoader. Forgot a line when implementing yesterday's patch that caused planets to double generate when replacing content. Not intentional.

- Updated test mod. Gave it more interesting content.

- Added basis for the tech tree handler (techCore class). Might need a full rework to fit in XML format.

**MAY 14 2017** (ver.PTB-A) (U1)

- Transferred the tech tree data over to XML format.

- Added in a new section to the XML loader to read tech tree data (moddable techs!)

- Patched a minor syntax error in the XML loader. Was accidentally using the wrong variable when indexing.

- Updated gameLoader for the new information.

**MAY 15 2017** (ver.PTB-A) (U1)

- Added a new sorting algorithm to the techCore. Essentially just sorts the information by ID. Likely won't make a difference to performance, but we'll see.

- Updated gameLoader with new methods.

- Slightly adjusted planet spawn weights based on testing.

- Adding in missing code to call the tech tree loader in the xmlLoader.

    - Fixed syntax error when calling the techTree folder.

    - Fixed syntax error when parsing tech tree data.

**MAY 15 2017** (ver.PTB-A) (U2)

- Added new writing method to the XML loader to change the enabled state of mods / expansion packs.

**MAY 16 2017** (ver.PTB-A) (U1)

- Reworked the GUI core yet again...

**MAY 17 2017** (ver.PTB-A) (U1)

- Reworked the GUI framework for the fourth time, finally got something working.

    - GUI core now functions and supports multiple windows and dynamic addition of content.

    - All colours and fonts are predefined and easily changed if needed.

- Added support to the XML loader for reading and storing information about the XML.

**MAY 17 2017** (ver.PTB-A) (U2)

- Slightly adjusted UI design.

**MAY 18 2017** (ver.PTB-A) (U1)

- Patched error with writing XML, added the needed method.

- Added multithreading support.

- Added base core for SFX.

    - Supports audio multithreading and will automatically kill threads when the audio finishes.

- Added new loading screen to the UI core.

**MAY 18 2017** (ver.PTB-A) (U2)

- Added main menu method to the UI core, still need to add information.

    - Moved some variables to class variables from local variables in order to reuse content.

    - Slightly improved audio and thread handling in the UI.

    - Adjusted UI colour and design.

    - Updated logo, added it to the launcher and main menu.

- Added another test expansion pack.

**MAY 18 2017** (ver.PTB-A) (U3)

- Added gfxRepository to store GFX content.

    - Has loader methods to pre-load images so they don't bog the program down.

- Add custom scrollbar functionality.

- Added new SFX core functionality.

    - Attempted to add support for music looping/shuffling (is broken)

    - Moved audio declarations to be handled in the repository for more simple manipulation and storage.

- Updated the launcher UI design.

    - Added new border, changed object positions, colours slightly.

- Add more functionality in the guiCore

    - Loading scree is now semi-dynamic.

    - Variables are slightly more organized.

    - Added "new game" button to test map generation and game content.

**MAY 20 2017** (ver.PTB-A) (U1)

- Added some framework for game/user data saving.

- Adjusted some planet/star weights.

- Updated the GFX repository to allow for randomized loading screen backgrounds.

- Added playerData class for storing the player's data.

- Added an animation core.

- Removed some unused variables from the XML loader.

**MAY 20 2017** (ver.PTB-A) (U2)

- Added support for looping audio to the audioCore.

- Added support for the loading and changing of mod content from the launcher. Still working out some bugs.

- Added a new test mod to play around with the launcher UI limitations.

**MAY 21 2017** (ver.PTB-A) (U1)

- Patched error in resizing the content window on the launcher. Will now correctly resize itself as new content is added.

- Adjusted UI colours slightly.

- Adjusted volume levels slightly.

- Added in code for a couple of other menu sounds.

**MAY 21 2017** (ver.PTB-A) (U2)

- Added in some more UI to the main menu for choosing settings when starting a new game. Incomplete.

- Added gameSettings class for storing game settings.

- Added extendedLabel class to add more constructors/functionality to JLabels.

**MAY 21 2017** (ver.PTB-A) (U3)

- Added more UI options for setting up a new game.

**MAY 22 2017** (ver.PTB-A) (U1)

- Added new GFX content.

- Reworked main menu GUI design and added new buttons.

- Played around more with the animation core.

- Adjusted some game settings.

**MAY 23 2017** (ver.PTB-A) (U1)

- Added experimental map to GUI.

- Adjusted some map generation settings and defaults.

- Reformatted SFX into a more organized system, added in some more SFX content.

**MAY 23 2017** (ver.PTB-B) (U1)

Version moved to PTB-B with the creation of the colonyCore class.

- Added colonyCore for handling planet colonies.

- Adjusted planet/star data saving to reduce characters.
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

**MAY 23 2017** (ver.PTB-B) (U2)

- Added some more SFX content.

- Added onto the colony core.

- Adjusted some of the methods and variables in the planetClass, etc.

- Added onto the GUI core slightly.

**MAY 24 2017** (ver.PTB-B) (U1)

- Added more to the UI core, namely a pause menu.

- Ported some repeated functions into methods to be called through them instead. More efficient.

- Moved all audio declaration and creation to the audoRepository to centralize it like I did the GFX content.

- Removed unnecessary code.

**MAY 24 2017** (ver.PTB-B) (U2)

- Added onto the pause menu.

    - Added proper header.

    - Added return button to close the menu and return to the game.

    - Added a slider to change the music volume.

- Fixed issue with sliders incorrectly displaying strange background bug. Should be patched now.

- Adjusted custom panel class slightly, removed unnecessary information.

- Removed more unused code.

- Updated audioCore with support for changing volume while the audio is playing.

    - Added necessary support to the audioRepository to allow for volume changing.

- Fixed minor visual issue with the pause menu button.

**MAY 24 2017** (ver.PTB-B) (U3)

- Added support for shuffling of music.

- Removed more unused code.

- Added the moons to the main menu background.

    - Added the necessary code to the animCore.

    - Adjusted the moon GFX files to fix some minor errors.

    - Slightly adjusted the methods of the animCore, and added the stopAnimation() and setAnimationStartTime() methods.

- Fiddled with the spaceport animation on the main menu. Should feel a lot less rigid now.

- Added some more content to the pause menu UI.

    - Slightly adjusted content ordering.

- Changed main menu music to loop.

- Resorted some GFX content to make things a little neater.

- Added new GFX icons and content.

- Added support for star GFX pictures.

    - Added XML support for star GFX.

- Added new methods to starCore/starClass to support portrait images.

- Added new star data screen on the map to show the star's information.

**MAY 25 2017** (ver.PTB-B) (U1)

- Added more content to the star data UI.

    - Added some missing lines.

    - Added new label for handling the star class's description.

- Changed 'gfx' in the xmlLoader to be 'gfxportrait' to better reflect the content that it is loading.

- Added in more star descriptions (taken from Stellaris, will likely change once I get a chance)

- Testing out html integration.

**MAY 25 2017** (ver.PTB-B) (U2)

- Reformatted code.

    - Moved all text and color declarations to the gfxRepository.

    - Changed all border declarations to variables and moved them to the gfxRepository.

- Added more constructors to the extendedLabel class.

**MAY 25 2017** (ver.PTB-B) (U3)

- Finished reformatting code.

    - Most UI content changed over to the extended swing classes.

    - Removed unnecessary and redundant code. Think I cleaned up around 200-300 lines by the end of it.

- Added some basic map generation logic.

    - Stars will no longer generate directly next to one another.

- Added some new UI elements.

    - Star data screen now shows the number of planets and the number of colonies in the system.

    - Star data screen was also reworked with a new design and formatting.

    - Added an icon to show the home system.

    - Added new sun glare effect to the main menu.

    - Testing custom button design and sprites, starting with the close button on the launcher.

- Added new methods to star/colony classes to determine home system/planet respectively.

- Added test .properties file.

& Other minor changes I forgot to document.

**MAY 26 2017** (ver.PTB-B) (U1)

- Added some new UI GFX, slightly changed the naming scheme for buttons.

**MAY 26 2017** (ver.PTB-B) (U2)

- Removed the minimize button on the launcher, replaced it with a button that disables/enables the launcher music.

- Switched the launcher buttons over to the new formatting.

- Added more functionality to the XButton class.

- Added a new minimize method to the XFrame class.

- Preparing to test enums in place of the current screen scale getter method.

**MAY 26 2017** (ver.PTB-B) (U3)

- Moved all custom Swing classes (X- prefix) to a dedicated folder named "SwingEX"

- Testing custom font styles.

- Major work on data saving.

    - Added saveData class to handle the writing of player data.

    - Added DataConstants interface to mimic Java's design.

    - Adjusted map/star/planet classes to better handle serialization.

    - Testing of the "transient" declaration.

- Started switching over buttons to new design.

    - Removed a LOT of redundant code as a result.

    - UI buttons now support custom designs (yay!)

    - Slightly adjusted xmlLoader to compensate.

- Added a new SFX sound, implemented another one into the audioRepository.

**MAY 27 2017** (ver.PTB-B) (U1)

- Continuing to port over all GFX buttons to new format.

    - Added a new GFX button graphic.

    - All launcher buttons now successfully ported, adjusted xmlLoader to fit the new format for mods.

    - Began porting over main menu buttons.

- Adjusted main menu design somewhat.

- Slightly adjusted colours.

- Switched over all fonts to new custom fonts.

**May 27 2017** (ver.PTB-B) (U2)

- Converted more UI content over to SwingEX format.

    - Converted sliders over to new SwingEX models (XSlider), reduced the amount of required code per slider as a result.

    - Converted most button ActionListeners over to XMouseListeners.

- Slightly reworked UI design to fit a better style.

- Added a new button design.

- Removed some gfxRepository dependencies from SwingEX.

- Added a storage for constants to the SwingEX database.

**MAY 28 2017** (ver.PTB-B) (U1)

- Added new star icon GFX.

    - Adjusted both the starCore/Class, UI, and XML to support it.

- Changed the main theme to a different song to see if it fits better.

- Major UI work.

    - Added a bunch of new UI content (most without purpose at the moment) to prepare.

    - Fixed up some old GFX content.

    - Added onto the XMouseListener and XFrame.

    - Added some new UI elements.

- Worked on data saving/loading somewhat.

**MAY 29 2017** (ver.PTB-B) (U1)

- Lots of UI adjustments.

    - Added code for the basis of the system view screen.

    - Adjusted the UI and GFX design somewhat.

    - Redid the UI design of the star data screen.

    - Removed the last of the ActionListeners (hopefully) and replaced them with MouseListeners.

    - Slightly altered the animation of the smaller moon on the main menu.

- Slight additions to the eventCore.

- Fixed some fundamental issues with the map generation that I hadn't previously noticed.

- Adjusted the SwingEX database, removing some dependencies and adding a default for when an image fails to load.

    - Added support for resizing images to the XLabel.

- Adjusted the game audio slightly to fix an overlap.

- Added in new GFX and SFX content.

- Added more comments where necessary.

**MAY 30 2017** (ver.PTB-B) (U1)

- Added onto the UI somewhat.

- Removed some unneeded GFX content.

**MAY 30 2017** (ver.PTB-B) (U2)

- Adjusted star GFX, added new images to diversify the icons somewhat. Still not final.

- Added new UI GFX content.

- Worked on the main UI somewhat.

    - Added a pause bar to show when the game is paused.

    - Changed the top bar design somewhat.

    - Added some missing information.

- Attempted to fix a minor issue with the audioCore.

**JUNE 1 2017** (ver.PTB-B) (U1)

- Slightly adjusted main menu moon animations.

- Attempted to add star names to map - no success.

**JUNE 1 2017** (ver.PTB-B) (U2)

- Added star GFX to system view.

- Added UI support for binary stars, will not properly display in name and system view.

- Added proper movement support for the system view.

- Adjusted UI top bar positioning of elements.

- Slightly adjusted planet and star classes.

**JUNE 1 2017** (ver.PTB-B) (U3)

- Added new GFX for planets and ships.

    - Updated planetCore/Class to work with the new GFX.

    - Temporary disabled star modding during this process.

- Added new planet view to the UI.

    - Planets are also now displayed in system view.

**JUNE 2 2017** (ver.PTB-B) (U1)

- Added class to handle turns (turnTicker)

- Added support for turns to the player data and certain UI elements.

**JUNE 2 2017** (ver.PTB-B) (U2)

- Added new GFX and SFX!

    - Planets now have SFX when they're viewed, including a couple of custom-made SFX/GFX for the Storm & Radiation worlds.

- Fixed some planet generation logic issues. Shouldn't screw up the spawns anymore.

- Added some more to the system view screen, including an icon for a colonized planet and a home planet.

- Slightly adjusted turn ticker logic to only increase player stats once every 10 (placeholder) turns.

- Fixed some minor UI bugs related to the pause menu.

- Added keybindings!

    - Current keybinds include space bar to pause/unpuase the game, + and - to speed up/slow down the turn ticker, and esc to open the pause menu.

- Adjusted main menu moon animation slightly.

**JUNE 3 2017** (ver.PTB-B) (U1)

- Updated some planet GFX.

- Added prototype spacecraft class (craftCore) and related classes to test.

- Slightly updated colonyCore to handle turns better.

- Updated map gen and GFX.

    - If a tile isn't 'explored' and it has a star, the star will appear as a '?' mark and will not be accessible.

    - If a tile is explored, behavior remains the same as previously.

    - Added a somewhat randomly placed nebula of sorts to the star map, aesthetic purposes only.

**JUNE 4 2017** (ver.PTB-B) (U1)

- Added new SwingEX content

    - New XListSorter, which automatically sorts, lists, and places any items put into it onto the specified component. Similar to a Layout, but with far more freedom.

    - New XTextImage, which takes an image and text as input, and places the text next to the image. Rather than initializing and placing two objects, now it only takes one.

    - XConstants, a storage for useful constants.

- Optimized UI code to use new SwingEX functions.

    - All image/text combos have now been switched over to use the XTextImage, which halves (!) the number of lines needed to generate it.

    - Switched over most listed content to use the new XListSorter. Less lines and faster initialization due to no dependencies. Can quickly swap order as needed, too.

- Fixed some minor issues with the Sol System.

**JUNE 4 2017** (ver.PTB-B) (U2)

- More UI optimizations.

    - Simplified the creation of custom buttons using a new XButtonCustom class.

    - More testing of the new XListSorter class, switching relevant data over to using it.

- Reworked planet generation.

    - Size is now measured as a metric between 4 and 25. Size 20+ planets can be gas giants. Will also allow for more advanced habitability algorithms later.

    - Removed redundant methods - No more moon calculations and whatnot.

- New GFX for continental & ocean planet portraits.

- Bug fixes

  - Fixed minor UI display error in the time scale.

  - Fixed XListSorter error when positioning and displaying objects.

**JUNE 5 2017** (ver.PTB-B) (U1)

- More UI code optimizations.

    - Converted the main menu new game settings window over to use the XListSorter. Much easier to edit the order and information now.

    - Converted more buttons to use the XButtonCustom. More than halved the number of lines needed per button. (10 to 4)

- Patched minor planet generation bug. Would occasionally generate planets >25 size. Shouldn't anymore.

**JUNE 5 2017** (ver.PTB-B) (U2)

- Removed redundant planet/star variables.

- Integrated planet resources into the UI and coding for planets and colonies.

- Turn Ticker updated.

    - Now supports decay of mineral deposits and increase of other parameters (energy, food, etc)

**JUNE 6 2017** (ver.PTB-B) (U1)

- Added basis for event window.

- Integrated planet resources into the turn ticker properly so it refreshes when necessary even while visible now.

- Slightly improved the colony functionality.

**JUNE 6 2017** (ver.PTB-B) (U2)

- Started working on converting the techCore and eventCore over to a new format.

    - New format uses anonymous classes and is far more fluid than the previous XML-forced style.

- Added new GFX content, mostly event pictures.

**JUNE 7 2017** (ver.PTB-B) (U1)

- Continued work on events.

    - Started building the event GUI elements.

    - Finished creating the main event builder.

    - Added new GFX.

**JUNE 7 2017** (ver.PTB-B) (U2)

- Finished building the new event system.

    - Can handle events with multiple options and actions.

    - Each event can also contain unique triggers and activation sequences.

    - Each event has an option for a customized header, image, title, and description, as well as whether or not it will be repeatable (activates more than once, if conditions are met)

- Improved the functionality of the XButtonCustom and XListSorter

    - XListSorter now supports bottom up sorting! It will place things at the bottom and sort up instead of down. Right-to-left not yet supported.

    - XButtonCustom's initialization was made more smooth, and no longer requires calling a unique "placeOn" method to initialize. This should increase the compatibilities.

**JUNE 8 2017** (ver.PTB-B) (U1)

- Began building the framework for the tech tree.

    - Created a basic UI for the tech tree, still missing most of the functionality.

    - Added new GFX.

    - Adjusted the tech tree data slightly, switched it over to an ArrayList format (same as the events)

- Slight adjustments to the events.

    - Testing of more HTML formatting and whatnot.

**JUNE 11 2017** (ver.PTB-B) (U1)

- Finished basic UI integration of the tech tree.

    - Only supports one of the three tech lines at the moment, and with very limited tech selection.

    - Integrated with the turn ticker and continually ticks research.

    - Research selector showcases all relevant information, and the regular tech window shows most.

- Slight improvements to SwingEX functionality.

- Added new tutorial event.

**JUNE 12 2017** (ver.PTB-B) (U1)

- Began working on UI integration of ships.

    - Slightly improved ship core handling.

    - Added in basis for the ship builder UI to the planets.

- Slightly improved event handling.

**JUNE 12 2017** (ver.PTB-B) (U2)

- Fixed some minor bugs when invalid input is given to the program.

- Zerg-rushed a prototype for the spacecraft builder (deadline tomorrow~!!!!!)

- More tech tree integration.

    - Couple of test (useless) techs and the second tree category.

- Updated events slightly, again.
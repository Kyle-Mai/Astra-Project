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


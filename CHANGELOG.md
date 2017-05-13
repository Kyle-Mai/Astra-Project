### Changelog

While Github's built in system is generally sufficient for keeping track of general changes, this is more suitable for keeping track of changes by version and for elaborating on changed stuff.

---

**MAY 12 2017** (ver.PTB-A)

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

**MAY 12 2017** (ver.PTB-A)

- Reworked Ui framework from the ground up.

    - Old UI was clunky and pretty broken, reworked it into a newer, more flexible format that'll hopefully suit my needs better down the road.

- Removed old UI code.

- Fixed issue with changelog file type.

- Slightly adjusted formatting in gameLoader.


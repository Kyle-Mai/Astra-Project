### ICS4U 2017 - Final Project

# Project Astra

## Description

Project Astra is a Java-based experimental game created as a final project for the ICS4U class of 2017. It was designed to demonstrate a wide variety of different Java applications as well as to promote a style of programming that I wasn't familiar with.

My largest project prior to this was an RPG-esque game created in the Visual Basic 2010 software. That project, while difficult, contained a lot of pre-generated elements (such as the UI), and was fairly repetitive. 

The 'AI' was simple, and tasked to simply do X when the turn was called. There was a tactical aspect in the form of health, shields, and armor, and the player would have to tactically chose attacks based on the enemy's stats in the aforemented categories. 

Overall, it was a fairly basic game taking around 2000 lines in total to program. The core gameplay loop was well developed, but the asset utilization was poor (.wav files and .gifs) thanks to a lack of support in VB.

Project Astra, by comparison, is far more advanced. I have utilized .mp3 instead of .wav for most audio, reducing the overall file size by a factor of ten, and minimized my usage of .gif files.

Project Astra is looking to top 10,000 lines of code(!), and contains an entirely custom generated UI. The gameplay loop is far more in-depth, and the AI actually utilizes proper procedures instead of a simple 'do X' loop.

## Design [WIP]

Project Astra is a simulation game based around the 4X genre - eXpand, eXplore, eXploit, and eXterminate.

The game is played in a 2D top-down map format - with the focus resting on grand strategy rather than individual control. The user is tasked with controlling and micro-managing multiple colonies and fleets rather than an individual character or ship.

The goal is to expand and manage an interstellar empire and protect it from threats both inside and out, such as revolts and rebellions caused by poor management.

A victory is reached once one of multiple victory conditions are met - such as controlling 80% of the map without any unstable colonies or reaching the end of the technology tree.

## Features [WIP]

- XML based modding. The game supports the addition and change of some core content (i.e. stars and planets) via the use of XML files. Mods can be enabled/disabled from the launcher.

- Expansion packs. Expansion packs are content packs that, like mods, can be enabled/disabled from the game launcher. The contain content that not all users may wish to have in the game, such as fictional and theoretical star types.

- Dynamically generated content. Every game will be different, with a different map arrangement and different stars/planets arranged around that map.

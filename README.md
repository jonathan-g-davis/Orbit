# spacestation13
Modified Baystation with a few ports/tweaks/additions. Oh yeah, and the rules make sense and our admins aren't shit.

## Current Development Model
Master Branch         -> What is currently being used on the live server

Development Branch    -> Where we are committing new ideas

Dev-Freeze Branch     -> Where we are bugfixing in prep for next release

When we feel that the Development Branch has enough content for the next release, we create a Dev-Freeze fork, and use that branch to focus solely on patching and balancing what is already there. When this process is finished, we merge Dev-Freeze into Master, and repeat the process.

## Contribution Model
To contribute to a project currently in development, you would create a branch off of your forked dev branch, and then do a pull request with the main dev branch as the base. You would then pull the changes down from the main dev branch into your branch.

To contribute a patch or fix in dev-freeze, you would go through the same process with your dev-freeze branch.

Pull requests won't be accepted with compile errors. This is to ensure that people pulling updates from the main dev branch don't have their forked branches corrupted.

## Guide to Contributing Common Features
Here are the locations of files you would have to change if you were attempting to add certain features.

#### New Job
You would have to make changes to:

1. The relevant department .dm file, found within `code/game/jobs/job/`
2. File cards_ids.dm, found within `code/game/objects/items/weapons/`
3. The relevant outfit .dm file, found within `code/datums/outfits/jobs/`
4. The main station map, found at `maps/exodus/exodus-1.dmm`

Depending on circumstance, you also might need to make changes to:

1. Relevant outfit .dm and .dmi files, if you are adding clothing. Typically found in `code/modules/clothing/` and `icons/obj/clothing/`
2. `code/game/area/Space Station 13 areas.dm` as well as `icons/turf/areas.dmi` if you are adding an associated room to the station for the job.

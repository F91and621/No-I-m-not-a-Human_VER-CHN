
# No,I'm not a human

> Psychological horror / interactive narrative game

## Game Description

After an abnormal solar event, "Visitors" appear – creatures that look human but have subtle abnormal features (messy teeth, dirty hands, red eyes, etc.). You live alone in a house. Every night strangers knock on your door, and during the day you can explore rooms and talk to guests who stayed.

**Can you tell who is a Visitor?**  
**Will you pull the trigger to protect yourself?**  
**Can you survive until the fourth night?**

## Core Gameplay

- **Night**: In the bedroom or yard, decide whether to open the gate. Talk to visitors and choose "Let in" or "Reject". Multiple visitors appear in sequence each night.
- **Day**: Move between bedroom, hallway, kitchen, storage, and living room. Talk to guests who are inside. From Day 3 onwards you can use **Inspect**:
  - Spend stamina (2–4 per day) to check teeth or hands.
  - Based on the results, choose "Kill" or "Spare".
- **Sleep**: Click the bed in the bedroom during the day to sleep and advance to the next night. You can sleep even if stamina is not empty.
- **Endings**:
  - **Win**: After the fourth night, no living Visitor remains in the house.
  - **Lose**: A Visitor kills you, or a Visitor remains alive after the fourth night.

## Controls

| Action | Key / Mouse |
|--------|-------------|
| Move / Interact | Left‑click on doors, characters, bed |
| Next dialogue line | Click anywhere on the dialogue box |
| Choose dialogue option | Click the option button |
| Skip intro video | Press `Enter` |
| Restart after game over | Press `Space` |

## Installation & Running

### Requirements
- Java 8 or higher (Java 11/17 recommended)
- **JavaFX 21** (required for intro video playback) – download from [openjfx.io](https://openjfx.io)

### Setup
1. Install Java and JavaFX SDK.
2. Clone or download this repository.
3. Make sure the `assets/` folder exists and contains all images, audio, and the intro video.
4. Compile and run using the JavaFX classpath:

```bash
javac --module-path /path/to/javafx-sdk-21.0.11/lib --add-modules javafx.controls,javafx.media *.java
java --module-path /path/to/javafx-sdk-21.0.11/lib --add-modules javafx.controls,javafx.media VisitorGame
```
*(Replace `/path/to/javafx-sdk-21.0.11/lib` with your actual JavaFX SDK location)*

### Asset Folder Structure (required)

```
assets/
├── videos/
│   └── intro.mp4
├── audios/
│   ├── knock.wav
│   ├── day.wav
│   ├── night1.wav
│   ├── night2.wav
│   ├── night3.wav
│   ├── night4.wav
│   ├── super.wav
│   └── shoot.wav
├── images/
│   ├── background/      # Scene backgrounds
│   ├── visit/           # Outside visitor portraits
│   ├── dayguests/       # Daytime guest sprites
│   ├── inspect/         # Teeth/hands inspection images
│   ├── guests/          # Dialogue portraits
│   ├── corpse/          # Corpse / trash bag images
│   ├── sprite/          # Tooth animation sprites
│   └── UI/              # Dialogue box, gun sprites
```

*All file names must match the hardcoded paths in the source code.*

## Features

- **Day‑night cycle** – Decide who enters at night, investigate and execute during the day.
- **Dialogue tree system** – Each character has unique branching dialogue.
- **Inspection mechanism** – Spend limited stamina to check teeth/hands for evidence.
- **Gun execution** – When in doubt, aim and shoot (animation + white flash).
- **Atmospheric audio** – Fading knock sounds, day/night music, gunshot.
- **Interactive highlights** – All clickable areas show semi‑transparent grey rings.
- **Intro video** – MP4 video, skippable with `Enter`.

---

**Good luck and survive until dawn.**

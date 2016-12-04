MusicPlayer

Model: Interface for my Model, only implemented by Score.

Score: Represents a piece of music. Data structure is an ArrayList of ArrayList of Notes,
       with each index of the outer ArrayList representing a single quarter note in the song
       and the inner ArrayList representing all the Notes that start on that beat.
       This class allows for changing the data involved with a song,
       including adding, removing, and moving notes.

Note: Represents a single note, and contains information such as pitch, startTime,
      and endTime which allows for convenient retrieval of note specific information.
      Contains mostly accessor methods for information contained within the Note.

View: Interface representing possible views for the music player. Contains ConsoleView,
      MidiViewImpl, GuiViewFrame, and CompositeView which are created by the ViewFactory factory.

KEYBOARD AND MOUSE CONTROLS:
Double left click a Note to increase duration of the Note
Double left click an empty space to create a new Note
Right click a Note to delete
Click on a Note and use WASD to move the Note up or down in pitch or left or right in song
Space: Pause/Play
Home: Jump to beginning of the song
End: Jump to end of the song

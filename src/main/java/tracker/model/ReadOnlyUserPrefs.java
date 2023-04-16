package tracker.model;

import java.nio.file.Path;

import tracker.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getBookTrackerFilePath();

}

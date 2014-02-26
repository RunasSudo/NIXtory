package io.github.runassudo.libnixtory;

import java.io.File;

public class SettingsProfile {
	public String name;
	public File location;
	public boolean modified;

	public String toString() {
		if (modified)
			return name + " (*)";
		return name;
	}
}

package tw.lanyitin.zools.runtime;

public class Location {
	public final int line;
	public final int column;
	public final String file_path;
	public Location(int line, int column, String file_path) {
		this.line = line;
		this.column = column;
		this.file_path = file_path;
	}
}

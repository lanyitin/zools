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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((file_path == null) ? 0 : file_path.hashCode());
		result = prime * result + line;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (column != other.column)
			return false;
		if (file_path == null) {
			if (other.file_path != null)
				return false;
		} else if (!file_path.equals(other.file_path))
			return false;
		if (line != other.line)
			return false;
		return true;
	}
}

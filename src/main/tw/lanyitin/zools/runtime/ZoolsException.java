package tw.lanyitin.zools.runtime;

import java.util.Arrays;
import java.util.List;

public class ZoolsException extends Exception {
	private static final long serialVersionUID = 4550980753334042860L;
	private List<ZoolsException> sub_exception;

	public ZoolsException(String msg) {
		super(msg);
		sub_exception = null;
	}

	public ZoolsException(String msg, List<ZoolsException> e) {
		super(msg);
		sub_exception = e;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (this.sub_exception == null) {
			return msg;
		} else {
			String childMsg = this.sub_exception.stream().map((ZoolsException e) -> e.getMessage())
					.reduce((a, b) -> a + "\n" + b).get();
			childMsg = Arrays.asList(childMsg.split("[\r\n]+")).stream().map((String s) -> {
				return "  " + s;
			}).reduce((String a, String b) -> {
				return a + "\n" + b;
			}).get();
			return msg + "\n" + childMsg;
		}
	}
}

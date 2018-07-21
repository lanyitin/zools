package tw.lanyitin.zools;

import static org.junit.Assert.*;

import org.junit.Test;

import tw.lanyitin.zools.runtime.FieldSelector;

public class EqualityTest {

	@Test
	public void fieldSelectorEqualityTest() {
		FieldSelector fsa = new FieldSelector("a", true);
		FieldSelector fsb = new FieldSelector("b", true);
		FieldSelector fsc = new FieldSelector("a", true);
		assertTrue(fsa.equals(fsc));
		assertTrue(!fsa.equals(fsb));
	}

}

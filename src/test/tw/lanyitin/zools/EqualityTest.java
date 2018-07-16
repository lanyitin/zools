package tw.lanyitin.zools;

import static org.junit.Assert.*;

import org.junit.Test;

import tw.lanyitin.zools.runtime.FieldSelector;

public class EqualityTest {

	@Test
	public void fieldSelectorEqualityTest() {
		FieldSelector fsa = new FieldSelector("a");
		FieldSelector fsb = new FieldSelector("b");
		FieldSelector fsc = new FieldSelector("a");
		assertTrue(fsa.equals(fsc));
		assertTrue(!fsa.equals(fsb));
	}

}

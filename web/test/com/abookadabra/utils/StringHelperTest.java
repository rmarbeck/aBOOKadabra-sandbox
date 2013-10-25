package com.abookadabra.utils;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class StringHelperTest {

	@Test
	public void truncateTest() {
		assertThat(StringHelper.truncate("HelloBoy", 0)).isEqualTo("");
		assertThat(StringHelper.truncate("HelloBoy", 1)).isEqualTo("H");
		assertThat(StringHelper.truncate("HelloBoy", 2)).isEqualTo("He");
		assertThat(StringHelper.truncate("HelloBoy", 3)).isEqualTo("Hel");
		assertThat(StringHelper.truncate("HelloBoy", 4)).isEqualTo("Hell");
		assertThat(StringHelper.truncate("HelloBoy", 5)).isEqualTo("Hello");
		assertThat(StringHelper.truncate("HelloBoy", 6)).isEqualTo("HelloB");
		assertThat(StringHelper.truncate("HelloBoy", 7)).isEqualTo("HelloBo");
		assertThat(StringHelper.truncate("HelloBoy", 8)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncate("HelloBoy", 9)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncate("HelloBoy", 10)).isEqualTo("HelloBoy");

		assertThat(StringHelper.truncate("HelloBoy", -1)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncate("HelloBoy", -1000)).isEqualTo("HelloBoy");
	}
	
	@Test
	public void truncateWithEndPatternLongerThanEndPatternTest() {
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 0)).isEqualTo("");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 1)).isEqualTo(".");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 2)).isEqualTo("..");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 3)).isEqualTo("...");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 4)).isEqualTo("H...");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 5)).isEqualTo("He...");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 6)).isEqualTo("Hel...");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 7)).isEqualTo("Hell...");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 8)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 9)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 10)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 11)).isEqualTo("HelloBoy");
		
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", 1000)).isEqualTo("HelloBoy");
		assertThat(StringHelper.truncateWithEndPattern("HelloBoy", -1000)).isEqualTo("HelloBoy");
	}
	
	
	@Test
	public void truncateWithEndPatternShorterThanEndPatternTest() {
		assertThat(StringHelper.truncateWithEndPattern("He", 0)).isEqualTo("");
		assertThat(StringHelper.truncateWithEndPattern("He", 1)).isEqualTo(".");
		assertThat(StringHelper.truncateWithEndPattern("He", 2)).isEqualTo("He");
		assertThat(StringHelper.truncateWithEndPattern("He", 3)).isEqualTo("He");
		assertThat(StringHelper.truncateWithEndPattern("He", 4)).isEqualTo("He");
		
		assertThat(StringHelper.truncateWithEndPattern("He", 1000)).isEqualTo("He");
		assertThat(StringHelper.truncateWithEndPattern("He", -1000)).isEqualTo("He");
	}

}

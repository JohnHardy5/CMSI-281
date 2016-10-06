package web_nav;

import static org.junit.Assert.*;

import org.junit.Test;

public class WebNavigatorTests {

	@Test
	public void generalTests() {
		WebNavigator navi = new WebNavigator();
		navi.visit("MinecraftWiki");
		navi.visit("Yahoo");
		navi.visit("GitHub");
		navi.visit("Youtube");
		assertEquals(navi.getCurrent(), "Youtube");
		navi.back();
		navi.back();
		navi.back();
		assertEquals(navi.getCurrent(), "MinecraftWiki");
		navi.forw();
		assertEquals(navi.getCurrent(), "Yahoo");
		navi.visit("Reddit");
		assertEquals(navi.getCurrent(), "Reddit");
		assertEquals(navi.getCurrentItem(), 2);
		navi.forw();
		assertEquals(navi.getCurrent(), "Reddit");
		assertEquals(navi.getCurrentItem(), 2);
		navi.back();
		assertEquals(navi.getCurrent(), "Yahoo");
		assertEquals(navi.getCurrentItem(), 1);
		navi.back();
		navi.back();
		assertEquals(navi.getCurrent(), "MinecraftWiki");
		assertEquals(navi.getCurrentItem(), 0);
	}

}

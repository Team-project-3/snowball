package tag;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class TagTest {

	@Test
	void testinit() {
		Main.init();
		List<Tag> tag_1=new ArrayList<>();
		List<String> exp= Arrays.asList("男","女");
		Tag tag_2= new Tag();
		tag_2.setTagID("性别");
		tag_2.setTagContent(exp);
        tag_1.add(tag_2);
        assertEquals(tag_1.get(0).getTagID(),Main.tagtag.get(0).getTagID());
        assertEquals(tag_1.get(0).getTagContent().get(0),Main.tagtag.get(0).getTagContent().get(0));
        assertEquals(tag_1.get(0).getTagContent().get(1),Main.tagtag.get(0).getTagContent().get(1));
	}
	@Test
	void testadd() {
		Main.init();
		List<String> exp2= Arrays.asList("13","17");
        Main.addtag("年龄",exp2);
        
        List<Tag> tag_3=new ArrayList<>();
		List<String> exp= Arrays.asList("13","17");
		Tag tag_4= new Tag();
		tag_4.setTagID("年龄");
		tag_4.setTagContent(exp);
        tag_3.add(tag_4);
        assertEquals(tag_3.get(0).getTagID(),Main.tagtag.get(1).getTagID());
        assertEquals(tag_3.get(0).getTagContent().get(0),Main.tagtag.get(1).getTagContent().get(0));
        assertEquals(tag_3.get(0).getTagContent().get(1),Main.tagtag.get(1).getTagContent().get(1));
	}

}

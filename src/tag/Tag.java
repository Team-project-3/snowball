package tag;

import java.util.List;

public class Tag {
	String tagID;
    List<String> tagContent;

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagContent(List<String> tagContent) {
        this.tagContent = tagContent;
    }

    public List<String> getTagContent() {
        return tagContent;
    }
}
